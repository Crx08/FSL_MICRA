package com.example.demo.repository;

import com.example.demo.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);

    // Ricerca per nome o cognome (case insensitive)
    List<Utente> findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(String nome, String cognome);
    @Query("""
SELECT u
FROM Utente u
WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :q, '%'))
   OR LOWER(u.cognome) LIKE LOWER(CONCAT('%', :q, '%'))
   OR LOWER(u.email) LIKE LOWER(CONCAT('%', :q, '%'))
""")
    List<Utente> ricercaGlobale(@Param("q") String q);
}