package br.com.cinemenu.cinemenuapi.rest.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignServiceTest {

    private SignService signService = new SignService();

    @Test
    @DisplayName("Test passwordValidation method with valid password")
    void testPasswordValidationWithValidPassword() {
        // Given
        String password = "Password1!";
        String confirmationPassword = "Password1!";

        // When/Then
        assertDoesNotThrow(() -> signService.passwordValidation(password, confirmationPassword));
    }

    @Test
    @DisplayName("Test passwordValidation method with mismatched passwords")
    void testPasswordValidationWithMismatchedPasswords() {
        // Given
        String password = "Password1!";
        String confirmationPassword = "Password2!";

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> signService.passwordValidation(password, confirmationPassword));
        assertEquals("The entered passwords must be the same", exception.getMessage());
    }

    @Test
    @DisplayName("Test passwordValidation method with invalid password length")
    void testPasswordValidationWithInvalidPasswordLength() {
        // Given
        String password = "pass";
        String confirmationPassword = "pass";

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> signService.passwordValidation(password, confirmationPassword));
        assertEquals("The password must contain at least one uppercase letter, one number, one special character, and be between 8 and 32 characters", exception.getMessage());
    }

    @Test
    @DisplayName("Test checkUsernameSanitization method with valid username")
    void testCheckUsernameSanitizationWithValidUsername() {
        // Given
        String username = "username123";

        // When/Then
        assertDoesNotThrow(() -> signService.checkUsernameSanitization(username));
    }

    @Test
    @DisplayName("Test checkUsernameSanitization method with invalid username length")
    void testCheckUsernameSanitizationWithInvalidUsernameLength() {
        // Given
        String username = "thisusernameistoolong";

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> signService.checkUsernameSanitization(username));
        assertEquals("Username can not be length then 16 characters", exception.getMessage());
    }

    @Test
    @DisplayName("Test checkUsernameSanitization method with invalid characters in username")
    void testCheckUsernameSanitizationWithInvalidCharacters() {
        // Given
        String username = "user@name";

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> signService.checkUsernameSanitization(username));
        assertEquals("Username can have only letters or numbers", exception.getMessage());
    }

    @Test
    @DisplayName("Test checkCompleteNameSanitization method with valid name")
    void testCheckCompleteNameSanitizationWithValidName() {
        // Given
        String name = "John Doe";

        // When/Then
        assertDoesNotThrow(() -> signService.checkCompleteNameSanitization(name));
    }

    @Test
    @DisplayName("Test checkCompleteNameSanitization method with invalid name length")
    void testCheckCompleteNameSanitizationWithInvalidNameLength() {
        // Given
        String name = "This name is way too long and exceeds the maximum length allowed so i repeat the phrase to make it more longer";

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> signService.checkCompleteNameSanitization(name));
        assertEquals("Complete name can not be length then 80 characters", exception.getMessage());
    }

    @Test
    @DisplayName("Test checkCompleteNameSanitization method with invalid characters in name")
    void testCheckCompleteNameSanitizationWithInvalidCharacters() {
        // Given
        String name = "John123";

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> signService.checkCompleteNameSanitization(name));
        assertEquals("Complete name can have only letters", exception.getMessage());
    }

    @Test
    @DisplayName("Test checkEmailLength method with valid email")
    void testCheckEmailLengthWithValidEmail() {
        // Given
        String email = "test@example.com";

        // When/Then
        assertDoesNotThrow(() -> signService.checkEmailLength(email));
    }

    @Test
    @DisplayName("Test checkEmailLength method with invalid email length")
    void testCheckEmailLengthWithInvalidEmailLength() {
        // Given
        String email = "thisemailaddressiswaytoolongandexceedsthemaximumlengthallowedbasiclytofoundrightanswer@example.com";

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> signService.checkEmailLength(email));
        assertEquals("Email can not be length then 80 characters", exception.getMessage());
    }
}
