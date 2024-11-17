package com.forDragon.BookCatalog.repository;

import com.forDragon.BookCatalog.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    Optional<Author> findByName(String name);

    @Query("SELECT a FROM Author a WHERE a.birthYear <= :givenYear AND a.deathYear >= :givenYear")
    List<Author> authorAliveByGivenYear(int givenYear );
}
