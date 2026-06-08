package com.example.demo.service;

import com.example.demo.entity.Utente;
import com.example.demo.repository.UtenteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UtenteService {

    private final UtenteRepository repository;

    public UtenteService(UtenteRepository repository) {
        this.repository = repository;
    }

    public List<Utente> findAll() {
        return repository.findAll();
    }

    public Utente findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con id: " + id));
    }

    public Utente save(Utente utente) {
        return repository.save(utente);
    }

    public Utente update(Long id, Utente nuovoUtente) {
        Utente utente = findById(id);
        utente.setNome(nuovoUtente.getNome());
        utente.setCognome(nuovoUtente.getCognome());
        utente.setEmail(nuovoUtente.getEmail());
        utente.setTelefono(nuovoUtente.getTelefono());
        return repository.save(utente);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
