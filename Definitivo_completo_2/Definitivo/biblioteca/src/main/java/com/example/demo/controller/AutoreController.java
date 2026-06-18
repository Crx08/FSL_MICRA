package com.example.demo.controller;

import com.example.demo.entity.Autore;
import com.example.demo.entity.CopiaLibro;
import com.example.demo.entity.Libro;
import com.example.demo.entity.Prestito;
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
    @PutMapping("/{id}")
    public ResponseEntity<?> aggiornaAutore(
            @PathVariable Integer id,
            @RequestBody Autore autore) {

        return autoreRepository.findById(id)
                .map(a -> {
                    a.setNome(autore.getNome());
                    a.setCognome(autore.getCognome());
                    a.setDataNascita(autore.getDataNascita());
                    a.setDataMorte(autore.getDataMorte());
                    return ResponseEntity.ok((Object) autoreRepository.save(a));
                })
                .orElse(ResponseEntity.badRequest()
                        .body((Object) ("Autore con ID " + id + " non trovato.")));
    }

    // DELETE autore — elimina a cascata prestiti storici, copie e libri
    // Bloccato se uno dei libri ha prestiti attivi
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminaAutore(@PathVariable Integer id) {
        if (!autoreRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Errore: Autore con ID " + id + " non trovato.");
        }
        // Blocca se uno qualsiasi dei libri dell'autore ha prestiti attivi
        boolean haPrestitiAttivi = prestitoRepository.existsByCopiaLibro_Libro_Autore_IdAndDataRestituzioneIsNull(id);
        if (haPrestitiAttivi) {
            return ResponseEntity.badRequest().body(
                    "Impossibile eliminare: uno o più libri di questo autore sono attualmente in prestito.");
        }
        // Elimina prestiti storici, copie e libri dell'autore
        List<Prestito> prestitiStorici = prestitoRepository.findByCopiaLibro_Libro_Autore_Id(id);
        prestitoRepository.deleteAll(prestitiStorici);

        List<Libro> libriAutore = libroRepository.findByAutore_Id(id);
        for (Libro libro : libriAutore) {
            List<CopiaLibro> copie = copiaLibroRepository.findByLibro_CodiceIsbn(libro.getCodiceIsbn());
            copiaLibroRepository.deleteAll(copie);
        }
        libroRepository.deleteAll(libriAutore);
        autoreRepository.deleteById(id);
        return ResponseEntity.ok("Autore, libri e copie associati eliminati con successo.");
    }
}