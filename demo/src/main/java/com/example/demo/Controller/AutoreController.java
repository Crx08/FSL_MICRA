package com.example.demo.Controller;

import com.example.demo.Autore;
import com.example.demo.Repository.AutoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/autori")
public class AutoreController {

    @Autowired // Collega automaticamente il magazziniere del database
    private AutoreRepository autoreRepository;

    // 1. Tutti gli autori  http://localhost:8080/autori
    @GetMapping
    public List<Autore> ottieniTutti() {
        return autoreRepository.findAll();
    }

    // 2. Aggiungere un autore (PostMapping)
    @PostMapping
    public Autore aggiungiAutore(@RequestBody Autore nuovoAutore) {
        return autoreRepository.save(nuovoAutore);
    }
}