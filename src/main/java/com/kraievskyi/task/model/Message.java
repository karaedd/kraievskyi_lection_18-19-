package com.kraievskyi.task.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@EqualsAndHashCode
@Document(indexName = "message")
public class Message {

    @Id
    private String id;
    private String subject;
    private String content;
    private String email;
    private String emailStatus;
}
