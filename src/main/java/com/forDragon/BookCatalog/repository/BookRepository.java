package com.forDragon.BookCatalog.repository;

import com.forDragon.BookCatalog.model.Author;
import com.forDragon.BookCatalog.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {

    @Query("SELECT a FROM Book b JOIN b.author a WHERE a.name ILIKE %:authorName%")
    List<Author> getAuthorByName(String authorName);

    List<Book> findByLanguage(String language);

    List<Book> findTop10ByOrderByDownloadCountDesc();

//    Optional<Serie> findByTituloContainingIgnoreCase(String nombreSerie);

//    List<Serie> findTop5ByOrderByEvaluacionDesc();
//    List<Serie> findByGenero(Categoria categoria);
//    @Query("select s from Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.evaluacion >= :evaluacion")
//    List<Serie> seriesPorTemporadaYEvaluacion(int totalTemporadas, double evaluacion);
//    // List<Serie> findByTotalTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(int totalTemporadas, Double evaluacion);
//    @Query("SELECT s FROM Serie s " + "JOIN s.episodios e " + "GROUP BY s " + "ORDER BY MAX(e.fechaDeLanzamiento) DESC LIMIT 5")
//    List<Serie> lanzamientosMasRecientes();
//    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numeroTemporada")
//    List<Episodio> obtenerTemporadasPorNumero(Long id, Long numeroTemporada);
}
