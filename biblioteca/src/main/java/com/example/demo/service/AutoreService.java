package com.example.demo.service;

import com.example.demo.entity.Autore;
import com.example.demo.repository.AutoreRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AutoreService {

    private final AutoreRepository repository;

    public AutoreService(AutoreRepository repository) {
        this.repository = repository;
    }

    public List<Autore> findAll() {
        return repository.findAll();
    }

    public Autore findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autore non trovato con id: " + id));
    }

    public Autore save(Autore autore) {
        return repository.save(autore);
    }

    public Autore update(Long id, Autore nuovoAutore) {
        Autore autore = findById(id);
        autore.setNome(nuovoAutore.getNome());
        autore.setCognome(nuovoAutore.getCognome());
        autore.setDataNascita(nuovoAutore.getDataNascita());
        autore.setDataMorte(nuovoAutore.getDataMorte());
        return repository.save(autore);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
