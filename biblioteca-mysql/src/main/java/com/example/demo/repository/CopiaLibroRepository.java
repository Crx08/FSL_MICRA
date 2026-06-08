package com.example.demo.repository;

import com.example.demo.entity.CopiaLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopiaLibroRepository extends JpaRepository<CopiaLibro, Long> {
}