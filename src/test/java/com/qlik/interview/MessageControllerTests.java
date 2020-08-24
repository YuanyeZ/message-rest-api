package com.qlik.interview;

import com.qlik.interview.controllers.MessageController;
import com.qlik.interview.models.Message;
import com.qlik.interview.service.MessageService;
import com.qlik.interview.utils.MessageNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class MessageControllerTests {
    @Mock
    MessageService messageService;
    @InjectMocks
    MessageController messageController = new MessageController();

    @Test
    public void testGetMessages() {
        Message message1 = new Message.Builder().setMessageId(1).setContent("my message 1").build();
        Message message2 = new Message.Builder().setMessageId(2).setContent("my message 2").build();
        when(messageService.getAllMessages(0, 5)).thenReturn(Arrays.asList(message1, message2));

        List<Message> messageList = messageController.getMessages(0, 5);
        assertThat(messageList.size()).isEqualTo(2);
        assertThat(messageList.get(0)).isEqualTo(message1);
        assertThat(messageList.get(1)).isEqualTo(message2);
    }

    @Test
    public void testGetMessagesById() throws MessageNotFoundException {
        Message message1 = new Message.Builder().setMessageId(1).setContent("my message 1").build();
        when(messageService.getMessageById("1")).thenReturn(message1);
        assertThat(messageController.getMessageById("1").getMessageId()).isEqualTo(1);
    }

    @Test
    public void testCreateMessage() {
        Message testMessage = new Message.Builder().setMessageId(1).setContent("test").build();
        when(messageService.createNewMessage(testMessage)).thenReturn(testMessage);
        Message updatedMessage = messageController.createMessage(testMessage);
        assertThat(updatedMessage.getContent()).isEqualTo("test");
        assertThat(updatedMessage.getMessageId()).isEqualTo(1);
    }

    @Test
    public void testUpdateMessage() throws MessageNotFoundException {
        Message oldMessage = new Message.Builder().setContent("old message").build();
        Message newMessage = new Message.Builder().setContent("new message").build();
        when(messageService.updateMessage("1", oldMessage)).thenReturn(newMessage);

        assertThat(messageController.updateMessage("1", oldMessage)).isEqualTo(newMessage);
    }

    @Test
    public void testDeleteMessage() throws MessageNotFoundException {
        Message message = new Message.Builder().setMessageId(1).setContent("test").build();
        when(messageService.getMessageById("1")).thenReturn(message);

        messageController.deleteMessage(message.getId());
        verify(messageService, times(1)).deleteByMessageId(message.getId());
    }
}
