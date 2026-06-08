package com.example.demo.repository;

import com.example.demo.entity.Prestito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Correggi "integer" in "Integer" (con la I maiuscola)
public interface PrestitoRepository extends JpaRepository<Prestito, Integer> {
}