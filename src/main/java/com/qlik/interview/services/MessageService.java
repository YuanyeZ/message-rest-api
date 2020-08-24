package com.qlik.interview.service;

import com.qlik.interview.models.Message;
import com.qlik.interview.utils.MessageNotFoundException;

import java.util.List;

public interface MessageService {
    List<Message> getAllMessages(int page, int size);
    Message getMessageById(String id) throws MessageNotFoundException;
    Message createNewMessage(Message message);
    List<Message> createNewMessages(List<Message> messages);
    Message updateMessage(String id, Message newMessage) throws MessageNotFoundException;
    void deleteByMessageId(String id) throws MessageNotFoundException;
}
