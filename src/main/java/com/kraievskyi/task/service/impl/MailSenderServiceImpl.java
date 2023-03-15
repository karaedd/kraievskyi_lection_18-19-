package com.kraievskyi.task.service.impl;

import com.kraievskyi.task.dto.MessageRequestDto;
import com.kraievskyi.task.dto.MessageResponseDto;
import com.kraievskyi.task.messaging.ReceivedMessage;
import com.kraievskyi.task.model.Message;
import com.kraievskyi.task.repository.MessageRepository;
import com.kraievskyi.task.service.MailSenderService;
import com.kraievskyi.task.service.mapper.MessageMapper;
import java.util.List;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderServiceImpl implements MailSenderService {

    private final MessageRepository messageRepository;
    private final MessageMapper mapper;

    private final JavaMailSender javaMailSender;

    public MailSenderServiceImpl(MessageRepository messageRepository,
                                 MessageMapper mapper,
                                 JavaMailSender javaMailSender) {
        this.messageRepository = messageRepository;
        this.mapper = mapper;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public MessageResponseDto save(MessageRequestDto message) {
        return mapper.toMessageResponseDto(messageRepository.save(mapper.toModel(message)));
    }

    @Override
    public List<Message> findAllByEmailStatusUnsent() {
        return messageRepository.findAllByEmailStatusEquals("UNSENT");
    }

    @Override
    public void sendEmail(ReceivedMessage receivedMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        Message byId = messageRepository.findById(receivedMessage.getMessageId()).orElseThrow();

        message.setTo(byId.getEmail());
        message.setSubject(byId.getSubject());
        message.setText(byId.getContent());

        javaMailSender.send(message);
        byId.setEmailStatus("SENT");
        messageRepository.save(byId);
    }
}
