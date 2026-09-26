package com.orange.catalogservice;

import com.orange.catalogservice.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
public class BookJsonTests {

    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        var book = new Book("9682347169", "AWS Security", "Nuno Maduro", 9.90);
        var jsonContent = json.write(book);
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
            .isEqualTo(book.isbn());
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.title")
            .isEqualTo(book.title());
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.author")
            .isEqualTo(book.author());
        Assertions.assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
            .isEqualTo(book.price());
    }

    @Test
    void testDeserialize() throws Exception {
        var content = """
            {
                "isbn": "9682347169",
                "title": "AWS Security",
                "author": "Nuno Maduro",
                "price": 9.90
            }
            """;
        Assertions.assertThat(json.parse(content))
            .usingRecursiveComparison()
            .isEqualTo(new Book("9682347169", "AWS Security", "Nuno Maduro", 9.90));
    }
}
