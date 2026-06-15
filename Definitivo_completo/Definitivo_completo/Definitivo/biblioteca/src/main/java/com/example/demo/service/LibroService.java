package com.example.demo.service;

import com.example.demo.entity.Libro;
import com.example.demo.repository.LibroRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LibroService {

    private final LibroRepository repository;

    public LibroService(LibroRepository repository) {
        this.repository = repository;
    }

    public List<Libro> findAll() {
        return repository.findAll();
    }

    public Libro findById(String isbn) {
        return repository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Libro non trovato con ISBN: " + isbn));
    }

    public Libro save(Libro libro) {
        return repository.save(libro);
    }

    public Libro update(String isbn, Libro nuovoLibro) {
        Libro libro = findById(isbn);
        libro.setTitolo(nuovoLibro.getTitolo());
        libro.setAutore(nuovoLibro.getAutore());
        libro.setSala(nuovoLibro.getSala());
        libro.setScaffale(nuovoLibro.getScaffale());
        libro.setRipiano(nuovoLibro.getRipiano());
        return repository.save(libro);
    }

    public void delete(String isbn) {
        repository.deleteById(isbn);
    }
}
