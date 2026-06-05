package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/autori") // Tutti gli URL in questo file inizieranno con /autori
public class AutoreController {

    @Autowired // Collega automaticamente il magazziniere del database
    private AutoreRepository autoreRepository;

    // 1. Leggere tutti gli autori (GET http://localhost:8080/autori)
    @GetMapping
    public List<Autore> ottieniTutti() {
        return autoreRepository.findAll();
    }

    // 2. Aggiungere un autore (POST http://localhost:8080/autori)
    @PostMapping
    public Autore aggiungiAutore(@RequestBody Autore nuovoAutore) {
        return autoreRepository.save(nuovoAutore);
    }
}