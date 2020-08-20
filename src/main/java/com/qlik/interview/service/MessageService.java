package com.qlik.interview.service;

import com.qlik.interview.models.Message;
import com.qlik.interview.utils.MessageNotFoundException;

import java.util.List;

public interface MessageService {
    List<Message> getAllMessages(int offset, int limit);
    Message getMessageById(long messageId) throws MessageNotFoundException;
    Message createNewMessage(Message message);
    List<Message> createNewMessages(List<Message> messages);
    Message updateMessage(long messageId, Message newMessage) throws MessageNotFoundException;
    void deleteByMessageId(long messageId) throws MessageNotFoundException;
}
