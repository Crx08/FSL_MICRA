package com.example.demo.controller;

import com.example.demo.entity.CopiaLibro;
import com.example.demo.entity.Prestito;
import com.example.demo.entity.Utente;
import com.example.demo.repository.CopiaLibroRepository;
import com.example.demo.repository.PrestitoRepository;
import com.example.demo.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/prestiti")
@CrossOrigin(origins = "*")
public class PrestitoController {

    @Autowired
    private PrestitoRepository prestitoRepository;

    @Autowired
    private CopiaLibroRepository copiaLibroRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    // GET tutti i prestiti
    @GetMapping
    public List<Prestito> ottieniTutti() {
        return prestitoRepository.findAll();
    }

    // POST attiva prestito - con verifica identità (ID + nome + cognome)
    @PostMapping("/attiva")
    public ResponseEntity<?> attivaPrestito(
            @RequestParam Integer idUtente,
            @RequestParam String nomeUtente,
            @RequestParam String cognomeUtente,
            @RequestParam Integer idCopia) {

        // 1. Verifica identità utente: tutti e tre i campi devono combaciare
        Utente utente = utenteRepository.findById(idUtente).orElse(null);
        if (utente == null) {
            return ResponseEntity.badRequest().body(
                "Errore: Nessun utente trovato con ID " + idUtente + ". L'utente deve essere registrato per effettuare un prestito.");
        }
        if (!utente.getNome().equalsIgnoreCase(nomeUtente.trim()) ||
            !utente.getCognome().equalsIgnoreCase(cognomeUtente.trim())) {
            return ResponseEntity.badRequest().body(
                "Errore: Nome o cognome non corrispondono all'utente con ID " + idUtente + ". Prestito negato.");
        }

        // 2. Verifica copia disponibile
        CopiaLibro copia = copiaLibroRepository.findById(idCopia).orElse(null);
        if (copia == null) {
            return ResponseEntity.badRequest().body("Errore: Copia con ID " + idCopia + " non trovata.");
        }
        if (!copia.getDisponibile()) {
            return ResponseEntity.badRequest().body(
                "Errore: La copia ID " + idCopia + " è già in prestito. Scegli un'altra copia disponibile.");
        }

        // 3. Segna la copia come non disponibile
        copia.setDisponibile(false);
        copiaLibroRepository.save(copia);

        // 4. Crea il prestito
        Prestito nuovoPrestito = new Prestito();
        nuovoPrestito.setUtente(utente);
        nuovoPrestito.setCopiaLibro(copia);
        nuovoPrestito.setDataInizio(LocalDate.now());
        nuovoPrestito.setDataScadenza(LocalDate.now().plusDays(30));
        prestitoRepository.save(nuovoPrestito);

        return ResponseEntity.ok("Prestito attivato con successo per " + utente.getNome() + " " + utente.getCognome() +
            "! Scadenza: " + nuovoPrestito.getDataScadenza());
    }

    // POST restituisci
    @PostMapping("/restituisci")
    public ResponseEntity<?> restituisciLibro(@RequestParam Integer idPrestito) {
        Prestito prestito = prestitoRepository.findById(idPrestito).orElse(null);
        if (prestito == null) {
            return ResponseEntity.badRequest().body("Errore: Prestito con ID " + idPrestito + " non trovato.");
        }
        if (prestito.getDataRestituzione() != null) {
            return ResponseEntity.badRequest().body("Questo prestito è già stato restituito il " + prestito.getDataRestituzione());
        }
        CopiaLibro copia = prestito.getCopiaLibro();
        copia.setDisponibile(true);
        copiaLibroRepository.save(copia);
        prestito.setDataRestituzione(LocalDate.now());
        prestitoRepository.save(prestito);
        return ResponseEntity.ok("Restituzione completata per il libro: '" + copia.getLibro().getTitolo() + "'!");
    }
}
