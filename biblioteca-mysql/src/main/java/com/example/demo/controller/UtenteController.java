package com.example.demo.controller;

import com.example.demo.entity.Utente;
import com.example.demo.service.UtenteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    private final UtenteService service;

    public UtenteController(UtenteService service) {
        this.service = service;
    }

    // GET http://localhost:8080/utenti
    @GetMapping
    public List<Utente> getAll() {
        return service.findAll();
    }

    // GET http://localhost:8080/utenti/1
    @GetMapping("/{id}")
    public Utente getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST http://localhost:8080/utenti
    @PostMapping
    public Utente create(@RequestBody Utente utente) {
        return service.save(utente);
    }

    // PUT http://localhost:8080/utenti/1
    @PutMapping("/{id}")
    public Utente update(@PathVariable Long id, @RequestBody Utente utente) {
        return service.update(id, utente);
    }

    // DELETE http://localhost:8080/utenti/1
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Utente con id " + id + " eliminato.";
    }
}
