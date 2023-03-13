package com.kraievskyi.task.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class MessageRequestDto {
    private String subject;
    private String content;
    private String email;
}
