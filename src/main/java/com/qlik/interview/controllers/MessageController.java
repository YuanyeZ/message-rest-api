package com.qlik.interview.controllers;

import com.qlik.interview.InterviewApplication;
import com.qlik.interview.models.Message;

import com.qlik.interview.service.MessageService;
import com.qlik.interview.utils.MessageNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * This the main class for handling REST APIs requests
 */
@RestController
@RequestMapping("/api/v1")
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewApplication.class);
    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public List<Message> getMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{messageId}")
    public Message getMessageById(@PathVariable(value = "messageId") long messageId) {
        try {
            return messageService.getMessageById(messageId);
        } catch (MessageNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/message")
    public Message createMessage(@RequestBody Message message) {
        return messageService.createNewMessage(message);
    }

    @PostMapping("/messages")
    public List<Message> createMessages(@RequestBody List<Message> messages) {
        return messageService.createNewMessages(messages);
    }

    @PutMapping("/messages/{messageId}")
    public Message updateMessage(@PathVariable(value = "messageId") long messageId,
                                 @RequestBody Message newMessage) {
        try {
            return messageService.updateMessage(messageId, newMessage);
        } catch (MessageNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/messages/{messageId}")
    public void deleteMessage(@PathVariable(value = "messageId") long messageId) {
        try {
            messageService.deleteByMessageId(messageId);
        } catch (MessageNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
