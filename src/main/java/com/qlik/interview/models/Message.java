package com.qlik.interview.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

@Document(collection = "message")
public class Message {

    @Transient
    public static final String SEQUENCE_NAME = "messagesSequence";

    public static final String UNIQUE_ID = "messageId";

    @Id
    @JsonProperty(value = "id", defaultValue = "")
    private String id;
    @JsonIgnore
    private long messageId;
    private String content;
    @JsonProperty(value = "restrictMode", defaultValue = "false")
    private boolean isRestrictMode;
    private boolean isPalindrome;
    private Date createdTime;
    private Date lastUpdate;
    @JsonIgnore
    @Version
    private Long version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    public boolean isRestrictMode() {
        return isRestrictMode;
    }

    public void isRestrictMode(boolean isRestrictMode) {
        this.isRestrictMode = isRestrictMode;
    }

    public boolean isPalindrome() {
        return isPalindrome;
    }

    public void isPalindrome(boolean palindrome) {
        this.isPalindrome = palindrome;
    }

    @NonNull
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(@Nullable Date createdTime) {
        this.createdTime = createdTime == null ? new Date() : createdTime;
    }

    @NonNull
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate == null ? new Date() : lastUpdate;
    }

    @NonNull
    public Long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", content='" + content + '\'' +
                ", isPalindrome=" + isPalindrome +
                '}';
    }

    public static class Builder {
        private long messageId;
        private String content;

        public Builder setMessageId(long id) {
            this.messageId = id;
            return this;
        }

        public Builder setContent(String msg) {
            this.content = msg;
            return this;
        }

        public Message build() {
            Message message = new Message();
            message.setMessageId(this.messageId);
            message.setContent(this.content);
            return message;
        }
    }
}
