package com.orange.catalogservice;


import com.orange.catalogservice.domain.Book;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class BookValidationTests {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        var book = new Book("1234567890", "Programmer en C", "Mathieu Nebra", 35.);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    void whenIsbnDefinedButIncorrectThenValidationFails() {
        var book = new Book("1254", "Design Data Intensive Applications", "Kleppman", 60.);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        Assertions.assertThat(violations).isNotEmpty();
        Assertions.assertThat(violations.iterator().next().getMessage())
            .isEqualTo("The ISBN format must be valid.");
    }

    @Test
    void whenPriceIsNegativeThenValidationFails() {
        var book = new Book("1254367890", "Test", "Martin Fowler", -65.);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        Assertions.assertThat(violations).isNotEmpty();
        Assertions.assertThat(violations.iterator().next().getMessage())
            .isEqualTo("The book price must be greater than zero.");
        Assertions.assertThat(book.price()).isNegative();
    }

    @Test
    public void whenBookTitleIsNotDefinedThenTestFails() {
        var book = new Book("9876543210", "", "Alain Duno", 42.);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        Assertions.assertThat(violations).isNotEmpty();
        Assertions.assertThat(book.title()).isEmpty();
        Assertions.assertThat(violations.iterator().next().getMessage())
            .isEqualTo("The book title must be defined.");
    }
}
