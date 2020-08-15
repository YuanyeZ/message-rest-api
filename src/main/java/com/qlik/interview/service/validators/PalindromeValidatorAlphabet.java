package com.qlik.interview.service.validators;

public class PalindromeValidatorAlphabet implements PalindromeValidator {
    @Override
    public boolean isPalindrome(String content) {
        if(content == null || content.length() == 0) {
            return true;
        }
        content = content.toLowerCase();
        int i = 0, j = content.length() - 1;
        while(i < j) {
            while(i < content.length() && !Character.isLetterOrDigit(content.charAt(i))) i++;
            while(j >= 0 && !Character.isLetterOrDigit(content.charAt(j))) j--;

            if(i < j && content.charAt(i++) != content.charAt(j--)) return false;
        }
        return true;
    }
}
