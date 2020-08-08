package com.qlik.interview.service;

import com.qlik.interview.models.Message;
import com.qlik.interview.repositories.MessageRepository;
import com.qlik.interview.utils.MessageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * The service that handles all necessary functionalities about the message
 * Currently we only support validating palindrome
 */
@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    /**
     * Function that check the string is palindrome or not
     * @param msg the message that needs to be validated
     * @return whether the input msg is a palindrome or not
     */
    public boolean isPalindrome(String msg) {
        if(msg == null || msg.length() == 0) {
            return true;
        }
        msg = msg.toLowerCase();
        int i = 0, j = msg.length() - 1;
        while(i < j) {
            while(i < msg.length() && !Character.isLetterOrDigit(msg.charAt(i))) i++;
            while(j >= 0 && !Character.isLetterOrDigit(msg.charAt(j))) j--;

            if(i < j && msg.charAt(i++) != msg.charAt(j--)) return false;
        }
        return true;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(long messageId) throws MessageNotFoundException {
        Message message = messageRepository.findByMessageId(messageId).orElse(null);
        if (message == null) {
            throw new MessageNotFoundException(messageId);
        }
        return message;
    }

    public Message createNewMessage(Message message) {
        message.setMessageId(sequenceGeneratorService.getNextSequence(Message.SEQUENCE_NAME, Message.UNIQUE_ID));
        message.isPalindrome(isPalindrome(message.getContent()));
        Date current = new Date();
        message.setCreatedTime(current);
        message.setLastUpdate(current);
        return messageRepository.save(message);
    }

    public Message updateMessage(long messageId, Message newMessage) throws MessageNotFoundException {
        Message oldMessage = messageRepository.findByMessageId(messageId).orElse(null);
        if (oldMessage == null) {
            throw new MessageNotFoundException(messageId);
        }
        oldMessage.setContent(newMessage.getContent());
        oldMessage.isPalindrome(isPalindrome(newMessage.getContent()));
        oldMessage.setLastUpdate(new Date());
        return messageRepository.save(oldMessage);
    }

    public void deleteByMessageId(long messageId) throws MessageNotFoundException {
        Message oldMessage = messageRepository.findByMessageId(messageId).orElse(null);
        if (oldMessage == null) {
            throw new MessageNotFoundException(messageId);
        }
        messageRepository.deleteByMessageId(messageId);
    }
}
