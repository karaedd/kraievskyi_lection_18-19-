package com.kraievskyi.task.listener;

import com.kraievskyi.task.messaging.ReceivedMessage;
import com.kraievskyi.task.service.MailSenderService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageReceivedListener {

    private final MailSenderService mailSenderService;

    @KafkaListener(topics = "messageReceived")
    public void messageReceived(ReceivedMessage receivedMessage) {
        try {
            mailSenderService.sendEmail(receivedMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

