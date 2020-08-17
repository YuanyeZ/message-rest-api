package com.qlik.interview.service.validators;

/**
 * Count all the characters in original format for palindrome
 */
public class PalindromeValidatorRestrict implements PalindromeValidator {
    @Override
    public boolean isPalindrome(String content) {
        if(content == null || content.length() == 0) {
            return true;
        }
        int i = 0, j = content.length() - 1;
        while(i < j) {
            if(content.charAt(i++) != content.charAt(j--)) return false;
        }
        return true;
    }
}
