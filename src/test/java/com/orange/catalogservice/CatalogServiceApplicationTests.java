package com.orange.catalogservice;

import com.orange.catalogservice.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWebTestClient
class CatalogServiceApplicationTests {
    private final WebTestClient webTestClient;

    @Autowired
    public CatalogServiceApplicationTests(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @Test
    void whenPostRequestThenBookCreated() {
        var expectedBook = new Book("1236549870", "Modern Java in Action", "Mario Fusco", 55.);

        this.webTestClient
            .post()
            .uri("/books")
            .bodyValue(expectedBook)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Book.class).value(actualBook -> {
                Assertions.assertThat(actualBook).isNotNull();
                Assertions.assertThat(actualBook.isbn())
                    .isEqualTo(expectedBook.isbn());
            });
    }

    @Test
    void whenGetRequestThenBookReturned() {
        var expectedBook = new Book("1266338877", "Modern C# in Action", "Andre Rop", 60.);

        // 1. Arrange: Send a POST request to make sure the book actually exists first
        this.webTestClient
            .post()
            .uri("/books")
            .bodyValue(expectedBook)
            .exchange()
            .expectStatus().isCreated();

        this.webTestClient
            .get()
            .uri("/books/{isbn}", expectedBook.isbn())
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    void whenGetRequestAllBooksReturned() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("9685324187", "C++", "Bjarne Stroustrup", 60.0));
        books.add(new Book("7896541110", "Distilled SQL", "Martin Fowler", 30.0));
        books.add(new Book("6489321732", "Prisoners of geography", "Tim Marshal", 15.5));

//        for (Book book: books) {
//            this.webTestClient
//                .post()
//                .uri("/books")
//                .bodyValue(book)
//                .exchange()
//                .expectStatus().isCreated();
//        }

        books.forEach(book -> this.webTestClient
            .post()
            .uri("/books")
            .bodyValue(book)
            .exchange()
            .expectStatus().isCreated());

        this.webTestClient
            .get()
            .uri("/books")
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    void whenPutRequestThenBookUpdated() {
        var initalBook = new Book("9689643211", "Azure Security", "Paul Gartner", 36.);
        var updatedBook = new Book("9689643211", "Azure Security", "Paul Gartner", 30.);

        this.webTestClient
            .post()
            .uri("/books")
            .bodyValue(initalBook)
            .exchange()
            .expectStatus().isCreated();

        this.webTestClient
            .put()
            .uri("/books/{isbn}", initalBook.isbn())
            .bodyValue(updatedBook)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Book.class)
            .value(actualBook -> Assertions.assertThat(
                actualBook.price()).isEqualTo(updatedBook.price())
            );
    }

    @Test
    void whenDeleteRequestThenBookDeleted() {
        this.webTestClient
            .delete()
            .uri("/books/{isbn}", "1266338877")
            .exchange()
            .expectStatus().isNoContent();
    }
}
