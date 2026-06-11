package com.example.demo.repository;

import com.example.demo.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, String> {
    List<Libro> findByTitoloContainingIgnoreCase(String titolo);
    boolean existsByTitoloIgnoreCase(String titolo);
    boolean existsByAutore_Id(Integer idAutore);
}
