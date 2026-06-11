package com.example.demo.controller;

import com.example.demo.entity.Utente;
import com.example.demo.repository.PrestitoRepository;
import com.example.demo.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map; // Importato per gestire i messaggi JSON puliti

@RestController
@RequestMapping("/utenti")
@CrossOrigin(origins = "*") // Va benissimo per lo sviluppo locale!
public class UtenteController {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PrestitoRepository prestitoRepository;

    // GET tutti gli utenti
    @GetMapping
    public List<Utente> ottieniTutti() {
        return utenteRepository.findAll();
    }

    // GET singolo utente per ID
    @GetMapping("/{id}")
    public ResponseEntity<?> ottieniPerId(@PathVariable Integer id) {
        return utenteRepository.findById(id)
                .map(u -> ResponseEntity.ok((Object) u))
                .orElse(ResponseEntity.badRequest().body(Map.of("message", "Utente con ID " + id + " non trovato.")));
    }

    // POST crea nuovo utente
    @PostMapping
    public ResponseEntity<?> creaNuovoUtente(@RequestBody Utente utente) {
        if (utente.getNome() == null || utente.getNome().isBlank() ||
                utente.getCognome() == null || utente.getCognome().isBlank() ||
                utente.getEmail() == null || utente.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Errore: Nome, cognome ed email sono obbligatori."));
        }
        if (utenteRepository.existsByEmail(utente.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Errore: Email già registrata nel sistema."));
        }
        if (utente.getTelefono() != null && !utente.getTelefono().isBlank()
                && utenteRepository.existsByTelefono(utente.getTelefono())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Errore: Numero di telefono già registrato."));
        }
        return ResponseEntity.ok(utenteRepository.save(utente));
    }

    // DELETE utente
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminaUtente(@PathVariable Integer id) {
        if (!utenteRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Errore: Utente con ID " + id + " non trovato."));
        }

        // ATTENZIONE: Verifica che nel tuo PrestitoRepository esista esattamente questo metodo!
        // Se la colonna si chiama "utente_id", in JPA si usa "existsByUtenteId" o "existsByUtente_Id"
        boolean haPrestitiAttivi = prestitoRepository.existsByUtenteIdAndDataRestituzioneIsNull(id);
        if (haPrestitiAttivi) {
            return ResponseEntity.badRequest().body(Map.of("message", "Impossibile eliminare: l'utente ha prestiti attivi in corso."));
        }

        utenteRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Utente eliminato con successo."));
    }

    // GET verifica identità per prestito (ID + nome + cognome devono combaciare)
    @GetMapping("/verifica")
    public ResponseEntity<?> verificaIdentita(
            @RequestParam Integer id,
            @RequestParam String nome,
            @RequestParam String cognome) {
        return utenteRepository.findById(id)
                .map(u -> {
                    if (u.getNome().equalsIgnoreCase(nome.trim()) && u.getCognome().equalsIgnoreCase(cognome.trim())) {
                        return ResponseEntity.ok((Object) u);
                    } else {
                        return ResponseEntity.badRequest().body(
                                Map.of("message", "Errore: I dati inseriti non corrispondono all'utente con ID " + id + ". Prestito negato.")
                        );
                    }
                })
                .orElse(ResponseEntity.badRequest().body(Map.of("message", "Errore: Nessun utente trovato con ID " + id + ". L'utente deve essere registrato per poter effettuare un prestito.")));
    }
}