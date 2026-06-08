package com.example.demo.Controller;

import com.example.demo.Repository.UtenteRepository;
import com.example.demo.Entity.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/utenti")
public class UtenteController {
    @Autowired
    private UtenteRepository utenteRepository;

    @GetMapping
    public List<Utente> ottieniTutti() { return utenteRepository.findAll(); }

    @PostMapping
    public Utente creaNuovoUtente(@RequestBody Utente nuovoUtente) {
        return utenteRepository.save(nuovoUtente);
    }
}