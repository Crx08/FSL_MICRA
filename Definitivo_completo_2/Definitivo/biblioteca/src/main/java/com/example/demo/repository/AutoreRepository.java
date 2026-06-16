package com.example.demo.repository;

import com.example.demo.entity.Autore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface AutoreRepository extends JpaRepository<Autore, Integer> {
    boolean existsById(Integer id);

    // Ricerca per nome o cognome (case insensitive)
    List<Autore> findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(String nome, String cognome);
    @Query("""
    SELECT a
    FROM Autore a
    WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :q, '%'))
       OR LOWER(a.cognome) LIKE LOWER(CONCAT('%', :q, '%'))
    """)
        List<Autore> ricercaGlobale(@Param("q") String q);
}