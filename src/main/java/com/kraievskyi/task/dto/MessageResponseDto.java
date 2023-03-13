package com.kraievskyi.task.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class MessageResponseDto {
    private String id;
    private String subject;
    private String content;
    private String email;
    private String emailStatus;
}
