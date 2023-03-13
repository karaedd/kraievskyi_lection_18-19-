package com.kraievskyi.task.service.mapper;

import com.kraievskyi.task.dto.MessageRequestDto;
import com.kraievskyi.task.dto.MessageResponseDto;
import com.kraievskyi.task.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public Message toModel(MessageRequestDto messageRequestDto) {
        Message message = new Message();
        message.setSubject(messageRequestDto.getSubject());
        message.setContent(messageRequestDto.getContent());
        message.setEmail(messageRequestDto.getEmail());
        message.setEmailStatus("UNSENT");
        return message;
    }

    public MessageResponseDto toMessageResponseDto(Message message) {
        MessageResponseDto messageResponseDto = new MessageResponseDto();
        messageResponseDto.setId(message.getId());
        messageResponseDto.setContent(message.getContent());
        messageResponseDto.setSubject(message.getSubject());
        messageResponseDto.setEmail(message.getEmail());
        messageResponseDto.setEmailStatus(message.getEmailStatus());
        return messageResponseDto;
    }
}
