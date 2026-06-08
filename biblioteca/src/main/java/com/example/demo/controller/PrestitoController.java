package com.example.demo.controller;

import com.example.demo.entity.CopiaLibro;
import com.example.demo.entity.Prestito;
import com.example.demo.repository.CopiaLibroRepository;
import com.example.demo.repository.PrestitoRepository;
import com.example.demo.repository.UtenteRepository;
import com.example.demo.entity.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/prestiti")
public class PrestitoController {

    @Autowired
    private PrestitoRepository prestitoRepository;

    @Autowired
    private CopiaLibroRepository copiaLibroRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    // Endpoint GET: http://localhost:8080/prestiti (Vede la lista)
    @GetMapping
    public List<Prestito> ottieniTutti() {
        return prestitoRepository.findAll();
    }

    // NUOVO Endpoint POST Complesso: http://localhost:8080/prestiti/attiva
    @GetMapping("/attiva")
    public String attivaPrestito(@RequestParam Integer idUtente, @RequestParam Integer idCopia) {

        // 1. Cerchiamo l'utente e la copia nel database
        Utente utente = utenteRepository.findById(idUtente).orElse(null);
        CopiaLibro copia = copiaLibroRepository.findById(idCopia).orElse(null);

        // Controlli di sicurezza
        if (utente == null || copia == null) {
            return "Errore: Utente o Copia del libro non trovati!";
        }

        if (!copia.getDisponibile()) {
            return "Errore: La copia richiesta con ID " + idCopia + " è già in prestito a qualcun altro!";
        }

        // 2. Modifichiamo lo stato della copia (ora diventa occupata)
        copia.setDisponibile(false);
        copiaLibroRepository.save(copia); // Aggiorna il database

        // 3. Creiamo il record del prestito con le date automatiche
        Prestito nuovoPrestito = new Prestito();
        nuovoPrestito.setUtente(utente);
        nuovoPrestito.setCopiaLibro(copia);
        nuovoPrestito.setDataInizio(LocalDate.now());
        nuovoPrestito.setDataScadenza(LocalDate.now().plusDays(30));

        prestitoRepository.save(nuovoPrestito);

        return "Prestito registrato con successo a " + utente.getNome() + "! Scadenza il: " + nuovoPrestito.getDataScadenza();
    }

    @GetMapping("/restituisci")
    public String restituisciLibro(@RequestParam Integer idPrestito) {

        // 1. Cerchiamo il prestito nel database
        Prestito prestito = prestitoRepository.findById(idPrestito).orElse(null);

        if (prestito == null) {
            return "Errore: Prestito non trovato!";
        }

        if (prestito.getDataRestituzione() != null) {
            return "Questo libro è già stato restituito in data: " + prestito.getDataRestituzione();
        }

        // 2. Recuperiamo la copia del libro  e la rimettiamo disponibile
        CopiaLibro copia = prestito.getCopiaLibro();
        copia.setDisponibile(true);
        copiaLibroRepository.save(copia); // Aggiorna lo stato della copia

        // 3. Impostiamo la data di restituzione a oggi
        prestito.setDataRestituzione(LocalDate.now());
        prestitoRepository.save(prestito);

        return "Restituzione completata con successo per il libro: '" + copia.getLibro().getTitolo() + "'!";
    }
}