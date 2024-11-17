package com.forDragon.BookCatalog.service;

import com.forDragon.BookCatalog.model.Author;
import com.forDragon.BookCatalog.model.Book;
import com.forDragon.BookCatalog.model.BookData;
import com.forDragon.BookCatalog.repository.AuthorRepository;
import com.forDragon.BookCatalog.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public void saveBook(BookData bookData) {
        Author author;

        if (bookData.authors() != null && !bookData.authors().isEmpty()) {
            String authorName = bookData.authors().get(0).name();

            // Check if the author exists in the database
            Optional<Author> existingAuthor = authorRepository.findByName(authorName);
            author = existingAuthor.orElseGet(() -> {
                Author newAuthor = new Author(bookData.authors().get(0)); // Or new AuthorData(bookData.authors().get(0))
                return authorRepository.save(newAuthor); // Save new author only if not found
            });
        } else {
            throw new IllegalArgumentException("Author data is missing.");
        }

        Book book = new Book(bookData);
        //book.setAuthor(author);  // Set the existing or newly saved author
        bookRepository.save(book);  // Save the book with CascadeType.PERSIST to avoid duplication errors
    }
}
