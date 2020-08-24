package com.qlik.interview.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlik.interview.models.Message;
import com.qlik.interview.service.MessageService;
import com.qlik.interview.utils.MessageNotFoundException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class MessageIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Test
    public void testGetMessages() throws Exception {
        Message message1 = new Message.Builder().setId("1").setContent("my message 1").build();
        Message message2 = new Message.Builder().setId("2").setContent("my message 2").build();
        when(messageService.getAllMessages(0, 5)).thenReturn(Arrays.asList(message1, message2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].content", is("my message 1")))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].content", is("my message 2")));

    }

    @Test
    public void testGetMessageById() throws Exception {
        Message message = new Message.Builder().setId("1").setContent("test message").build();
        try {
            when(messageService.getMessageById("1")).thenReturn(message);
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is("1")))
                    .andExpect(jsonPath("$.content", is("test message")));
        } catch (MessageNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateMessage() throws Exception {
        Message message = new Message.Builder().setId("1").setContent("test message").build();
        try {
            when(messageService.createNewMessage(any(Message.class))).thenReturn(message);
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/message")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsBytes(message)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is("1")))
                    .andExpect(jsonPath("$.content", is("test message")));
        } catch (MessageNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateMessage() throws Exception {
        Message newMessage = new Message.Builder().setId("1").setContent("new message").build();

        try {
            when(messageService.updateMessage(any(String.class), any(Message.class))).thenReturn(newMessage);
            mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/messages/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsBytes(newMessage)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is("1")))
                    .andExpect(jsonPath("$.content", is("new message")));
        } catch (MessageNotFoundException e) {
            e.printStackTrace();
        }
    }

}
