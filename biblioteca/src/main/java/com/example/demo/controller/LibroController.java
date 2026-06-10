package com.example.demo.controller;

import com.example.demo.entity.Libro;
import com.example.demo.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/libri")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping
    public List<Libro> ottieniTuttiILibri() {
        return libroRepository.findAll();
    }

    @GetMapping("/ricerca")
    public List<Libro> cercaLibroPerTitolo(@RequestParam String titolo) {
        return libroRepository.findByTitoloContainingIgnoreCase(titolo);
    }

    @PostMapping
    public ResponseEntity<?> inserisciNuovoLibro(@RequestBody Libro libro) {
        // 1. Controllo se il titolo esiste già
        if (libroRepository.existsByTitoloIgnoreCase(libro.getTitolo())) {
            return ResponseEntity.badRequest().body("Errore: Un libro con questo titolo è già presente nel catalogo!");
        }

        // 2. Controllo se l'ISBN esiste già visto che fa da Chiave Primaria
        if (libroRepository.existsById(libro.getCodiceIsbn())) {
            return ResponseEntity.badRequest().body("Errore: Questo codice ISBN è già associato a un altro libro!");
        }

        Libro libroSalvato = libroRepository.save(libro);
        return ResponseEntity.ok(libroSalvato);
    }

    // Il PathVariable ora riceve una String (l'isbn) e non un Integer
    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> eliminaLibro(@PathVariable String isbn) {
        if (!libroRepository.existsById(isbn)) {
            return ResponseEntity.badRequest().body("Errore: Nessun libro trovato con l'ISBN inserito: " + isbn);
        }

        try {
            libroRepository.deleteById(isbn);
            return ResponseEntity.ok().body("Libro rimosso dal catalogo con successo!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Errore: Impossibile eliminare il libro. Assicurati che non ci siano copie o prestiti attivi collegati.");
        }
    }
}