package com.kraievskyi.task.controller;

import com.kraievskyi.task.dto.MessageRequestDto;
import com.kraievskyi.task.dto.MessageResponseDto;
import com.kraievskyi.task.dto.ReceivedMessageDto;
import com.kraievskyi.task.messaging.ReceivedMessage;
import com.kraievskyi.task.service.MailSenderService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    @Value("${kafka.topic.messageReceived}")
    private String receivedTopic;

    private final KafkaOperations<String, ReceivedMessage> kafkaOperations;
    private final MailSenderService mailSenderService;

    @PostMapping("/mailConfirmation")
    public void receiveMessage(@RequestBody ReceivedMessageDto dto) {
        kafkaOperations.send(receivedTopic, toMessage(dto));
    }

    private static ReceivedMessage toMessage(ReceivedMessageDto dto) {
        return ReceivedMessage.builder()
                .messageId(dto.getMessageId())
                .transactionId(UUID.randomUUID().toString())
                .build();
    }

    @PostMapping("/message")
    public MessageResponseDto createMessage(@RequestBody MessageRequestDto dto) {
        return mailSenderService.save(dto);
    }

}
