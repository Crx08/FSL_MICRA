package com.example.demo.repository;

import com.example.demo.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);
}