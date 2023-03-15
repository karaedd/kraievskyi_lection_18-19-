package com.kraievskyi.task.repository;

import com.kraievskyi.task.model.Message;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MessageRepository extends ElasticsearchRepository<Message, String> {
    List<Message> findAllByEmailStatusEquals(String status);
}
