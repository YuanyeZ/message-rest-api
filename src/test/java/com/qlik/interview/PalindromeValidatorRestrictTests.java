package com.qlik.interview;

import com.qlik.interview.service.validators.PalindromeValidatorRestrict;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PalindromeValidatorRestrictTests {
    @Test
    public void testPalindromeRestrict() {
        assertTrue(new PalindromeValidatorRestrict().isPalindrome("abc cba"), "all lowercase characters, valid palindrome in restrict mode");

        assertFalse(new PalindromeValidatorRestrict().isPalindrome("abc CBA"), "lowercase and uppercase characters, invalid palindrome in restrict mode");

        assertFalse(new PalindromeValidatorRestrict().isPalindrome("a#bc cb@a"), "special characters, invalid palindrome in restrict mode");

        assertTrue(new PalindromeValidatorRestrict().isPalindrome("abc#&#cba"), "special characters, valid palindrome in restrict mode");
    }
}
