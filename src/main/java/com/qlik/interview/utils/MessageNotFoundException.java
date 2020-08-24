package com.qlik.interview.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MessageNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public MessageNotFoundException(String id){
        super(String.format("Message was not found for id: %s", id));
    }
}
