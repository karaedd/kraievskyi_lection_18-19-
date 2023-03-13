package com.kraievskyi.task.repository;

import com.kraievskyi.task.model.Message;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MessageRepository extends ElasticsearchRepository<Message, String> {
    List<Message> findAllByEmailStatusEquals(String status);
}
