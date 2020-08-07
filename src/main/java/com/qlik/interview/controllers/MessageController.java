package com.qlik.interview.controllers;

import com.qlik.interview.InterviewApplication;
import com.qlik.interview.models.Message;
import com.qlik.interview.repositories.MessageRepository;
import com.qlik.interview.service.MessageService;
import com.qlik.interview.service.SequenceGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ResolutionException;
import java.util.List;

/**
 * This the main class for handling REST APIs requests
 */
@RestController
@RequestMapping("/api/v1")
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewApplication.class);
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    @GetMapping("/messages/{messageId}")
    public Message getMessageById(@PathVariable(value = "messageId") long messageId) {
        return messageRepository.findByMessageId(messageId)
                            .orElseThrow(() -> {
                                LOGGER.error(String.format("Message was not found for id: %d", messageId));
                                return new ResponseStatusException(HttpStatus.NOT_FOUND);
                            });
    }

    @PostMapping("/message")
    public Message createMessage(@RequestBody Message message) {
        message.setMessageId(sequenceGeneratorService.getNextSequence(Message.SEQUENCE_NAME));
        message.isPalindrome(messageService.isPalindrome(message.getContent()));
        return messageRepository.save(message);
    }

    @PutMapping("/messages/{messageId}")
    public Message updateMessage(@PathVariable(value = "messageId") long messageId,
                                 @RequestBody Message newMessage) {
        Message message = messageRepository.findByMessageId(messageId)
                                .orElseThrow(() -> {
                                    LOGGER.error(String.format("Message was not found for id: %d", messageId));
                                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                                });
        message.setContent(newMessage.getContent());
        message.isPalindrome(messageService.isPalindrome(newMessage.getContent()));
        return messageRepository.save(message);
    }

    @DeleteMapping("/messages/{messageId}")
    public void deleteMessage(@PathVariable(value = "messageId") long messageId) {
        messageRepository.deleteByMessageId(messageId);
    }
}
