package com.kraievskyi.task.scheduler;

import com.kraievskyi.task.model.Message;
import com.kraievskyi.task.repository.MessageRepository;
import com.kraievskyi.task.service.MailSenderService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class MailScheduler {

    private final MailSenderService mailSenderService;
    private final JavaMailSender javaMailSender;
    private final MessageRepository messageRepository;

    public MailScheduler(MailSenderService mailSenderService, JavaMailSender javaMailSender, MessageRepository messageRepository) {
        this.mailSenderService = mailSenderService;
        this.javaMailSender = javaMailSender;
        this.messageRepository = messageRepository;
    }

    @Scheduled(fixedRate = 300000) // Запускати раз на 5 хвилин 300000
    public void retryFailedMessages() {
        List<Message> allByEmailStatusUnsent = mailSenderService.findAllByEmailStatusUnsent();

        SimpleMailMessage message = new SimpleMailMessage();

        for (Message value : allByEmailStatusUnsent) {
            message.setTo(value.getEmail());
            message.setSubject(value.getSubject());
            message.setText(value.getContent());
            javaMailSender.send(message);

            value.setEmailStatus("SENT");
            messageRepository.save(value);
        }
    }
}
