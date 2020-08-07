package com.qlik.interview.repositories;

import com.qlik.interview.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    Optional<Message> findByMessageId(@Param("messageId") long messageId);
    void deleteByMessageId(@Param("messageId") long messageId);
}
