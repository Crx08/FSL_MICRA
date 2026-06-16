package com.example.demo.repository;

import com.example.demo.entity.CopiaLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CopiaLibroRepository extends JpaRepository<CopiaLibro, Integer> {
    List<CopiaLibro> findByLibro_CodiceIsbn(String codiceIsbn);
}
