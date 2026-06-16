package com.example.demo.controller;

import com.example.demo.entity.Autore;
import com.example.demo.entity.CopiaLibro;
import com.example.demo.entity.Libro;
import com.example.demo.repository.AutoreRepository;
import com.example.demo.repository.CopiaLibroRepository;
import com.example.demo.repository.LibroRepository;
import com.example.demo.repository.PrestitoRepository;
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

    @Autowired
    private CopiaLibroRepository copiaLibroRepository;

    @Autowired
    private PrestitoRepository prestitoRepository;

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

    // DELETE autore — elimina a cascata libri e copie, bloccato se ci sono prestiti attivi
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminaAutore(@PathVariable Integer id) {
        if (!autoreRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Errore: Autore con ID " + id + " non trovato.");
        }

        // Blocca se uno qualsiasi dei suoi libri ha prestiti attivi
        boolean haPrestitiAttivi = prestitoRepository.existsByCopiaLibro_Libro_Autore_IdAndDataRestituzioneIsNull(id);
        if (haPrestitiAttivi) {
            return ResponseEntity.badRequest().body(
                "Impossibile eliminare: uno o più libri di questo autore sono attualmente in prestito.");
        }

        // Elimina tutte le copie dei libri dell'autore
        List<Libro> libriAutore = libroRepository.findByAutore_Id(id);
        for (Libro libro : libriAutore) {
            List<CopiaLibro> copie = copiaLibroRepository.findByLibro_CodiceIsbn(libro.getCodiceIsbn());
            copiaLibroRepository.deleteAll(copie);
        }

        // Elimina tutti i libri dell'autore
        libroRepository.deleteAll(libriAutore);

        // Elimina l'autore
        autoreRepository.deleteById(id);
        return ResponseEntity.ok("Autore, libri e copie associati eliminati con successo.");
    }
}
