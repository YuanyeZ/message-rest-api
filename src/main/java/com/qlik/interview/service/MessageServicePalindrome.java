package com.qlik.interview.service;

import com.qlik.interview.models.Message;
import com.qlik.interview.repositories.MessageRepository;
import com.qlik.interview.service.validators.PalindromeValidator;
import com.qlik.interview.service.validators.PalindromeValidatorAlphabet;
import com.qlik.interview.service.validators.PalindromeValidatorRestrict;
import com.qlik.interview.service.validators.PalindromeValidatorType;
import com.qlik.interview.utils.MessageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    private static final Map<PalindromeValidatorType, PalindromeValidator> palindromeValidators = Map.of(
            PalindromeValidatorType.RESTRICT, new PalindromeValidatorRestrict(),
            PalindromeValidatorType.ALPHABET, new PalindromeValidatorAlphabet()
    );

    @Override
    public List<Message> getAllMessages(int page, int size) {
        return messageRepository.findAll(PageRequest.of(page, size)).getContent();
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
        message.isPalindrome(palindromeValidators.get(getPalindromeType(message.isRestrictMode())).isPalindrome(message.getContent()));
        Date current = new Date();
        message.setCreatedTime(current);
        message.setLastUpdate(current);
        return messageRepository.save(message);
    }

    @Override
    public List<Message> createNewMessages(List<Message> messages) {
        return messages.stream().map(this::createNewMessage).collect(Collectors.toList());
    }

    @Override
    public Message updateMessage(long messageId, Message newMessage) throws MessageNotFoundException {
        Message updatingMessage = messageRepository.findByMessageId(messageId).orElse(null);
        if (updatingMessage == null) {
            throw new MessageNotFoundException(messageId);
        }
        updatingMessage.setContent(newMessage.getContent());
        updatingMessage.isRestrictMode(newMessage.isRestrictMode());
        updatingMessage.isPalindrome(palindromeValidators.get(getPalindromeType(newMessage.isRestrictMode())).isPalindrome(newMessage.getContent()));
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

    private static PalindromeValidatorType getPalindromeType(boolean isRestrict) {
        return isRestrict ? PalindromeValidatorType.RESTRICT : PalindromeValidatorType.ALPHABET;
    }
}
