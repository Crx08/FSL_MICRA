package com.example.demo.controller;

import com.example.demo.entity.CopiaLibro;
import com.example.demo.entity.Libro;
import com.example.demo.repository.AutoreRepository;
import com.example.demo.repository.CopiaLibroRepository;
import com.example.demo.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libri")
@CrossOrigin(origins = "*")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutoreRepository autoreRepository;

    @Autowired
    private CopiaLibroRepository copiaLibroRepository;

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
        if (libro.getTitolo() == null || libro.getTitolo().isBlank()) {
            return ResponseEntity.badRequest().body("Errore: Il titolo del libro è obbligatorio.");
        }
        if (libro.getCodiceIsbn() == null || libro.getCodiceIsbn().isBlank()) {
            return ResponseEntity.badRequest().body("Errore: Il codice ISBN è obbligatorio.");
        }
        if (libro.getAutore() == null || libro.getAutore().getId() == null) {
            return ResponseEntity.badRequest().body("Errore: L'ID autore è obbligatorio.");
        }
        if (!autoreRepository.existsById(libro.getAutore().getId())) {
            return ResponseEntity.badRequest().body("Errore: Nessun autore trovato con ID " + libro.getAutore().getId());
        }
        if (libroRepository.existsByTitoloIgnoreCase(libro.getTitolo())) {
            return ResponseEntity.badRequest().body("Errore: Un libro con questo titolo è già presente nel catalogo.");
        }
        if (libroRepository.existsById(libro.getCodiceIsbn())) {
            return ResponseEntity.badRequest().body("Errore: Questo codice ISBN è già associato a un altro libro.");
        }
        return ResponseEntity.ok(libroRepository.save(libro));
    }

    // POST inserisci copie per un libro
    @PostMapping("/{isbn}/copie")
    public ResponseEntity<?> inserisciCopie(@PathVariable String isbn,
                                             @RequestParam(defaultValue = "1") int quantita,
                                             @RequestParam(defaultValue = "Buono") String stato) {
        if (!libroRepository.existsById(isbn)) {
            return ResponseEntity.badRequest().body("Errore: Nessun libro trovato con ISBN " + isbn);
        }
        if (quantita < 1 || quantita > 50) {
            return ResponseEntity.badRequest().body("Errore: La quantità deve essere tra 1 e 50.");
        }
        Libro libro = libroRepository.findById(isbn).get();
        for (int i = 0; i < quantita; i++) {
            CopiaLibro copia = new CopiaLibro();
            copia.setLibro(libro);
            copia.setDisponibile(true);
            copia.setStatoConservazione(stato);
            copiaLibroRepository.save(copia);
        }
        return ResponseEntity.ok("Aggiunte " + quantita + " copie (" + stato + ") per il libro: " + libro.getTitolo());
    }

    // GET elenco copie di un libro
    @GetMapping("/{isbn}/copie")
    public ResponseEntity<?> ottieniCopie(@PathVariable String isbn) {
        if (!libroRepository.existsById(isbn)) {
            return ResponseEntity.badRequest().body("Nessun libro trovato con ISBN " + isbn);
        }
        return ResponseEntity.ok(copiaLibroRepository.findByLibro_CodiceIsbn(isbn));
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> eliminaLibro(@PathVariable String isbn) {
        if (!libroRepository.existsById(isbn)) {
            return ResponseEntity.badRequest().body("Errore: Nessun libro trovato con ISBN: " + isbn);
        }
        try {
            libroRepository.deleteById(isbn);
            return ResponseEntity.ok("Libro rimosso dal catalogo con successo.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Errore: Impossibile eliminare. Assicurati che non ci siano copie o prestiti attivi.");
        }
    }
}
