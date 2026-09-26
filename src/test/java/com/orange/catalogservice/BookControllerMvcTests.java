package com.orange.catalogservice;

import com.orange.catalogservice.domain.Book;
import com.orange.catalogservice.domain.BookNotFoundException;
import com.orange.catalogservice.domain.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BookControllerMvcTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookService bookService;

    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn = "73737313940";
        given(bookService.viewBookDetails(isbn))
            .willThrow(BookNotFoundException.class);
        mockMvc
            .perform(get("/books/" + isbn))
            .andExpect(status().isNotFound());
    }

    @Test
    void whenPostBookThenShouldReturn201() throws Exception {
        var book = new Book("9898674321", "Panama Papers", "Obermayer", 12.5);

        mockMvc
            .perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
            .andExpect(status().isCreated());
    }

    @Test
    void whenPutBookThenShouldReturn200() throws Exception {
        var initialBook = new Book("0266823491", "Das Kapital, 3 atome", "Karl Marx", 60.5);
        var updatedBook = new Book("0266823491", "Das Kapital, 3 atome", "Karl Marx", 50.5);

        given(bookService.editBookDetails(eq(initialBook.isbn()), any(Book.class)))
            .willReturn(updatedBook);

        mockMvc
            .perform(put("/books/{isbn}", initialBook.isbn())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBook)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.price").value(50.5));
    }

    @Test
    void whenDeleteBookThenShouldReturn204() throws Exception {
        var book = new Book("9876321498", "France History", "Marco Russo", 12.25);

        mockMvc
            .perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
            .andExpect(status().isCreated());

        mockMvc
            .perform(delete("/books/{isbn}", book.isbn()))
            .andExpect(status().is2xxSuccessful());
    }
}
