package com.orange.catalogservice;

import com.orange.catalogservice.domain.Book;
import jakarta.validation.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class BookValidation2Tests {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        var book = new Book("1234567890123", "Orientalism", "Edward Said", 35.75);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    void whenIsbnFieldIsNotDefinedThenValidationFails() {
        var book = new Book("", "Clash of civilisations", "Samuel Huntington", 60.32);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        Assertions.assertThat(violations).hasSize(2);
        Assertions.assertThat(violations)
            .extracting(ConstraintViolation::getMessage)
            .containsExactlyInAnyOrder(
                "The ISBN format must be valid.",
                "The book ISBN must be defined."
            );
    }

    @Test
    void whenPriceFieldIsNegativeThenValidationFails() {
        var book = new Book("9876543210000", "Panama Papers", "Obermayer", 0.0);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(violations.iterator().next().getMessage())
            .isEqualTo("The book price must be greater than zero.");
    }
}
