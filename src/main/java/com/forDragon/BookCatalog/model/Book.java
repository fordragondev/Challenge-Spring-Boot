package com.forDragon.BookCatalog.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String title;
    private String language;
    private Integer downloadCount;
    @ManyToOne(cascade = CascadeType.PERSIST,  fetch = FetchType.LAZY)
    private Author author;

    public Book() {
    }

    public Book(BookData bookData) {
        this.title = bookData.title();
        if (bookData.languages() != null && !bookData.languages().isEmpty()) {
            this.language = bookData.languages().get(0);
        }
        this.downloadCount = bookData.downloadCount();
        if (bookData.authors() != null && !bookData.authors().isEmpty()) {
            this.author = new Author(bookData.authors().get(0));
        }
    }

    @Override
    public String toString() {
        return  "titulo='" + title + '\'' +
                ", idioma='" + language + '\'' +
                ", descargas='" + downloadCount + '\'';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }
}
