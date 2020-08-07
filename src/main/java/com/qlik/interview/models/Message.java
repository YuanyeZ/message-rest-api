package com.qlik.interview.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "message")
public class Message {

    @Transient
    public static final String SEQUENCE_NAME = "messagesSequence";

    @Id
    private String id;
    private long messageId;
    private String content;
    private boolean isPalindrome;

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPalindrome() {
        return isPalindrome;
    }

    public void isPalindrome(boolean palindrome) {
        isPalindrome = palindrome;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", content='" + content + '\'' +
                ", isPalindrome=" + isPalindrome +
                '}';
    }
}
