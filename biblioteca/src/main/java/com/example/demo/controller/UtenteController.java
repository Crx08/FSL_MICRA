package com.example.demo.controller;

import com.example.demo.entity.Utente;
import com.example.demo.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    @Autowired
    private UtenteRepository utenteRepository;

    // Endpoint per salvare un nuovo utente nel DB
    @PostMapping
    public Utente creaNuovoUtente(@RequestBody Utente utente) {
        return utenteRepository.save(utente);
    }
}