package com.example.demo.controller;

import com.example.demo.entity.Autore;
import com.example.demo.repository.AutoreRepository;
import com.example.demo.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autori")
@CrossOrigin(origins = "*")
public class AutoreController {

    @Autowired
    private AutoreRepository autoreRepository;

    @Autowired
    private LibroRepository libroRepository;

    // GET tutti gli autori
    @GetMapping
    public List<Autore> ottieniTutti() {
        return autoreRepository.findAll();
    }

    // GET singolo autore per ID
    @GetMapping("/{id}")
    public ResponseEntity<?> ottieniPerId(@PathVariable Integer id) {
        return autoreRepository.findById(id)
            .map(a -> ResponseEntity.ok((Object) a))
            .orElse(ResponseEntity.badRequest().body("Autore con ID " + id + " non trovato."));
    }

    // POST inserisci autore
    @PostMapping
    public ResponseEntity<?> aggiungiAutore(@RequestBody Autore nuovoAutore) {
        if (nuovoAutore.getNome() == null || nuovoAutore.getNome().isBlank() ||
            nuovoAutore.getCognome() == null || nuovoAutore.getCognome().isBlank()) {
            return ResponseEntity.badRequest().body("Errore: Nome e cognome dell'autore sono obbligatori.");
        }
        return ResponseEntity.ok(autoreRepository.save(nuovoAutore));
    }

    // DELETE autore
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminaAutore(@PathVariable Integer id) {
        if (!autoreRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Errore: Autore con ID " + id + " non trovato.");
        }
        boolean haLibri = libroRepository.existsByAutore_Id(id);
        if (haLibri) {
            return ResponseEntity.badRequest().body("Impossibile eliminare: esistono libri associati a questo autore.");
        }
        autoreRepository.deleteById(id);
        return ResponseEntity.ok("Autore eliminato con successo.");
    }
}
