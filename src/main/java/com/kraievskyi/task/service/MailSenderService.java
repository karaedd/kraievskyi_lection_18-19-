package com.kraievskyi.task.service;

import com.kraievskyi.task.dto.MessageRequestDto;
import com.kraievskyi.task.dto.MessageResponseDto;
import com.kraievskyi.task.messaging.ReceivedMessage;
import com.kraievskyi.task.model.Message;
import jakarta.mail.MessagingException;

import java.util.List;

public interface MailSenderService  {

    MessageResponseDto save(MessageRequestDto message);

    //for tests
    MessageResponseDto getById(String id);

    List<Message> findAllByEmailStatusUnsent();
    void sendEmail(ReceivedMessage receivedMessage) throws MessagingException;

    void delete(String id);
}
