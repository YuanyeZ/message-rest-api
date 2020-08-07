package com.qlik.interview.service;

import org.springframework.stereotype.Service;

@Service
public class MessageService {
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
}
