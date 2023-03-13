package com.kraievskyi.task.messaging;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class ReceivedMessage {
    private String transactionId;
    private String messageId;
}
