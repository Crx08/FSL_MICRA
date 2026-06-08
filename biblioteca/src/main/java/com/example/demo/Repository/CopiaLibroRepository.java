package com.example.demo.Repository;

import com.example.demo.Entity.CopiaLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopiaLibroRepository extends JpaRepository<CopiaLibro, Long> {
}