package com.qlik.interview;

import com.qlik.interview.service.validators.PalindromeValidatorRestrict;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PalindromeValidatorAlphabetTests {
    @Test
    public void testPalindromeAlphabet() {
        assertTrue(new PalindromeValidatorRestrict().isPalindrome("abc cba"), "all lowercase characters, valid palindrome in alphabet mode");

        assertFalse(new PalindromeValidatorRestrict().isPalindrome("abc CBA"), "lowercase and uppercase characters, valid palindrome in alphabet mode");

        assertFalse(new PalindromeValidatorRestrict().isPalindrome("a#bc cb@a"), "special characters, valid palindrome in alphabet mode");

        assertTrue(new PalindromeValidatorRestrict().isPalindrome("abc#&#cba"), "special characters, valid palindrome in alphabet mode");
    }
}
