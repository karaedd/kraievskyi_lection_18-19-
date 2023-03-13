package com.kraievskyi.task.controller;

import com.kraievskyi.task.dto.MessageRequestDto;
import com.kraievskyi.task.dto.MessageResponseDto;
import com.kraievskyi.task.dto.ReceivedMessageDto;
import com.kraievskyi.task.messaging.ReceivedMessage;
import com.kraievskyi.task.model.Message;
import com.kraievskyi.task.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MessageController {

    @Value("${kafka.topic.messageReceived}")
    private String receivedTopic;

    private final KafkaOperations<String, ReceivedMessage> kafkaOperations;
    private final MailSenderService mailSenderService;

    @PostMapping("/mailConfirmation")
    public void receiveMessage(@RequestBody ReceivedMessageDto dto) {
        // As a message key we use orderId, so all messages related to
        // one Order will be routed to one topic partition and processed sequentially
        kafkaOperations.send(receivedTopic, toMessage(dto));
    }

    private static ReceivedMessage toMessage(ReceivedMessageDto dto) {
        return ReceivedMessage.builder()
                .messageId(dto.getMessageId())
                .transactionId(UUID.randomUUID().toString())
                .build();
    }

    //for tests
    @GetMapping("/{id}")
    public MessageResponseDto getMessage(@PathVariable String id) {
        return mailSenderService.getById(id);
    }

    @PostMapping("/message")
    public MessageResponseDto createMessage(@RequestBody MessageRequestDto dto) {
        return mailSenderService.save(dto);
    }

    @GetMapping("/all")
    public List<Message> getAll() {
        return mailSenderService.findAllByEmailStatusUnsent();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        mailSenderService.delete(id);
    }
}
