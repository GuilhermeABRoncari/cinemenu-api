package br.com.cinemenu.cinemenuapi.domain.dto.requestdto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

class CineMenuUserRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Test valid CineMenuUserRequestDto")
    void testValidCineMenuUserRequestDto() {
        // Given
        String name = "John Doe";
        String username = "johndoe";
        String email = "johndoe@example.com";
        String password = "password123";
        String confirmationPassword = "password123";

        // When
        CineMenuUserRequestDto requestDto = new CineMenuUserRequestDto(
                name, username, email, password, confirmationPassword);

        // Then
        Set<ConstraintViolation<CineMenuUserRequestDto>> violations = validator.validate(requestDto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Test invalid CineMenuUserRequestDto with blank fields")
    void testInvalidCineMenuUserRequestDtoWithBlankFields() {
        // Given
        String name = "";
        String username = "";
        String email = "";
        String password = "";
        String confirmationPassword = "";

        // When
        CineMenuUserRequestDto requestDto = new CineMenuUserRequestDto(
                name, username, email, password, confirmationPassword);

        // Then
        Set<ConstraintViolation<CineMenuUserRequestDto>> violations = validator.validate(requestDto);
        Assertions.assertEquals(5, violations.size());
    }

    @Test
    @DisplayName("Test invalid CineMenuUserRequestDto with invalid email")
    void testInvalidCineMenuUserRequestDtoWithInvalidEmail() {
        // Given
        String name = "John Doe";
        String username = "johndoe";
        String email = "johndoexample.com";
        String password = "password123";
        String confirmationPassword = "password123";

        // When
        CineMenuUserRequestDto requestDto = new CineMenuUserRequestDto(
                name, username, email, password, confirmationPassword);

        // Then
        Set<ConstraintViolation<CineMenuUserRequestDto>> violations = validator.validate(requestDto);
        Assertions.assertEquals(1, violations.size());
    }

}