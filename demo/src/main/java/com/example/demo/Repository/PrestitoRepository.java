package com.example.demo.Repository;

import com.example.demo.Prestito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestitoRepository extends JpaRepository<Prestito, Long> {
}