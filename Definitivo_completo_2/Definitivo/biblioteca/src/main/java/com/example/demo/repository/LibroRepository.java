package com.example.demo.repository;

import com.example.demo.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface LibroRepository extends JpaRepository<Libro, String> {

    List<Libro> findByTitoloContainingIgnoreCase(String titolo);

    boolean existsByTitoloIgnoreCase(String titolo);

    boolean existsByAutore_Id(Integer idAutore);

    // Usato dalla cancellazione a cascata dell'autore
    List<Libro> findByAutore_Id(Integer idAutore);

    @Query("""
    SELECT l
    FROM Libro l
    LEFT JOIN l.autore a
    WHERE LOWER(l.titolo) LIKE LOWER(CONCAT('%', :q, '%'))
       OR LOWER(l.codiceIsbn) LIKE LOWER(CONCAT('%', :q, '%'))
       OR LOWER(a.nome) LIKE LOWER(CONCAT('%', :q, '%'))
       OR LOWER(a.cognome) LIKE LOWER(CONCAT('%', :q, '%'))
    """)
    List<Libro> ricercaGlobale(@Param("q") String q);
}