package com.qlik.interview.controllers;

import com.qlik.interview.models.Message;
import com.qlik.interview.repositories.MessageRepository;
import com.qlik.interview.service.MessageService;
import com.qlik.interview.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.module.ResolutionException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MessageController {

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
                            .orElseThrow(() -> new ResolutionException(String.format("Message was not found for id: %d", messageId)));
    }

    @PostMapping("/messages")
    public Message createMessage(@RequestBody Message message) {
        message.setMessageId(sequenceGeneratorService.getNextSequence(Message.SEQUENCE_NAME));
        message.isPalindrome(messageService.isPalindrome(message.getContent()));
        return messageRepository.save(message);
    }

    @PutMapping("/messages/{messageId}")
    public Message updateMessage(@PathVariable(value = "messageId") long messageId,
                                 @RequestBody Message newMessage) {
        Message message = messageRepository.findByMessageId(messageId)
                                        .orElseThrow(() -> new ResolutionException(String.format("Message was not found for id: %d", messageId)));
        message.setContent(newMessage.getContent());
        message.isPalindrome(messageService.isPalindrome(newMessage.getContent()));
        return messageRepository.save(message);
    }

    @DeleteMapping("/messages/{messageId}")
    public void deleteMessage(@PathVariable(value = "messageId") long messageId) {
        messageRepository.deleteByMessageId(messageId);
    }
}
