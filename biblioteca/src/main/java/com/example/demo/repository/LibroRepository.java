package com.example.demo.repository;

import com.example.demo.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, String> {
    // Usiamo String come secondo parametro perché la chiave primaria di Libro (l'ISBN) è una Stringa!
}