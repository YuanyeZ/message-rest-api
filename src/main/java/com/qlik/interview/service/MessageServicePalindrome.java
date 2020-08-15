package com.qlik.interview.service;

import com.qlik.interview.models.Message;
import com.qlik.interview.repositories.MessageRepository;
import com.qlik.interview.service.validators.PalindromeValidator;
import com.qlik.interview.service.validators.PalindromeValidatorAlphabet;
import com.qlik.interview.service.validators.PalindromeValidatorRestrict;
import com.qlik.interview.utils.MessageNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * The service that handles all necessary functionalities about the message
 * Currently we only support validating palindrome
 */
@Service
public class MessageServicePalindrome implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    private static final PalindromeValidator palindromeValidatorAlphabet = new PalindromeValidatorAlphabet();
    private static final PalindromeValidator palindromeValidatorRestrict = new PalindromeValidatorRestrict();

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message getMessageById(long messageId) throws MessageNotFoundException {
        Message message = messageRepository.findByMessageId(messageId).orElse(null);
        if (message == null) {
            throw new MessageNotFoundException(messageId);
        }
        return message;
    }

    @Override
    public Message createNewMessage(Message message) {
        message.setMessageId(sequenceGeneratorService.getNextSequence(Message.SEQUENCE_NAME, Message.UNIQUE_ID));
        message.isPalindrome(getPalindromeValidator(message.isRestrictMode()).isPalindrome(message.getContent()));
        Date current = new Date();
        message.setCreatedTime(current);
        message.setLastUpdate(current);
        return messageRepository.save(message);
    }

    @Override
    public Message updateMessage(long messageId, Message newMessage) throws MessageNotFoundException {
        Message updatingMessage = messageRepository.findByMessageId(messageId).orElse(null);
        if (updatingMessage == null) {
            throw new MessageNotFoundException(messageId);
        }
        updatingMessage.setContent(newMessage.getContent());
        updatingMessage.isRestrictMode(newMessage.isRestrictMode());
        updatingMessage.isPalindrome(getPalindromeValidator(newMessage.isRestrictMode()).isPalindrome(newMessage.getContent()));
        updatingMessage.setLastUpdate(new Date());
        return messageRepository.save(updatingMessage);
    }

    @Override
    public void deleteByMessageId(long messageId) throws MessageNotFoundException {
        Message oldMessage = messageRepository.findByMessageId(messageId).orElse(null);
        if (oldMessage == null) {
            throw new MessageNotFoundException(messageId);
        }
        messageRepository.deleteByMessageId(messageId);
    }

    private static PalindromeValidator getPalindromeValidator(boolean restrict) {
        return restrict ? palindromeValidatorRestrict : palindromeValidatorAlphabet;
    }
}
