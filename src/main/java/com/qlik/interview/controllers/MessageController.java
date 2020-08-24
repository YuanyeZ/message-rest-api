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

    private static final String DEFAULT_OFFSET = "0";
    private static final String DEFAULT_LIMIT = "5";

    @GetMapping("/messages")
    public List<Message> getMessages(@RequestParam(value = "page", defaultValue = DEFAULT_OFFSET) int page,
                                     @RequestParam(value = "size", defaultValue = DEFAULT_LIMIT) int size) {
        return messageService.getAllMessages(page, size);
    }

    @GetMapping("/messages/{id}")
    public Message getMessageById(@PathVariable(value = "id") String id) {
        try {
            return messageService.getMessageById(id);
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

    @PutMapping("/messages/{id}")
    public Message updateMessage(@PathVariable(value = "id") String id,
                                 @RequestBody Message newMessage) {
        try {
            return messageService.updateMessage(id, newMessage);
        } catch (MessageNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/messages/{id}")
    public void deleteMessage(@PathVariable(value = "id") String id) {
        try {
            messageService.deleteByMessageId(id);
        } catch (MessageNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
