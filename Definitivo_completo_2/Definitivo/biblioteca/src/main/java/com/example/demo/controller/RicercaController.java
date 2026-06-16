package com.example.demo.controller;

import com.example.demo.repository.AutoreRepository;
import com.example.demo.repository.LibroRepository;
import com.example.demo.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ricerca")
public class RicercaController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutoreRepository autoreRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @GetMapping
    public Map<String, Object> ricerca(@RequestParam String q) {

        Map<String, Object> risultati = new HashMap<>();

        risultati.put("libri", libroRepository.ricercaGlobale(q));
        risultati.put("autori", autoreRepository.ricercaGlobale(q));
        risultati.put("utenti", utenteRepository.ricercaGlobale(q));

        return risultati;
    }
}