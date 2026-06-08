package com.example.demo.service;

import com.example.demo.entity.CopiaLibro;
import com.example.demo.entity.Prestito;
import com.example.demo.entity.Utente;
import com.example.demo.repository.CopiaLibroRepository;
import com.example.demo.repository.PrestitoRepository;
import com.example.demo.repository.UtenteRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class PrestitoService {

    private final PrestitoRepository prestitoRepository;
    private final CopiaLibroRepository copiaLibroRepository;
    private final UtenteRepository utenteRepository;

    public PrestitoService(PrestitoRepository prestitoRepository,
                           CopiaLibroRepository copiaLibroRepository,
                           UtenteRepository utenteRepository) {
        this.prestitoRepository = prestitoRepository;
        this.copiaLibroRepository = copiaLibroRepository;
        this.utenteRepository = utenteRepository;
    }

    public List<Prestito> findAll() {
        return prestitoRepository.findAll();
    }

    public Prestito findById(Long id) {
        return prestitoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestito non trovato con id: " + id));
    }

    public String attiva(Long idUtente, Long idCopia) {
        Utente utente = utenteRepository.findById(idUtente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con id: " + idUtente));
        CopiaLibro copia = copiaLibroRepository.findById(idCopia)
                .orElseThrow(() -> new RuntimeException("Copia non trovata con id: " + idCopia));

        if (!copia.getDisponibile()) {
            return "Errore: La copia con ID " + idCopia + " e' gia' in prestito!";
        }

        copia.setDisponibile(false);
        copiaLibroRepository.save(copia);

        Prestito prestito = new Prestito();
        prestito.setUtente(utente);
        prestito.setCopiaLibro(copia);
        prestito.setDataInizio(LocalDate.now());
        prestito.setDataScadenza(LocalDate.now().plusDays(30));
        prestitoRepository.save(prestito);

        return "Prestito registrato per " + utente.getNome() + "! Scadenza: " + prestito.getDataScadenza();
    }

    public String restituisci(Long idPrestito) {
        Prestito prestito = findById(idPrestito);

        if (prestito.getDataRestituzione() != null) {
            return "Libro gia' restituito il: " + prestito.getDataRestituzione();
        }

        CopiaLibro copia = prestito.getCopiaLibro();
        copia.setDisponibile(true);
        copiaLibroRepository.save(copia);

        prestito.setDataRestituzione(LocalDate.now());
        prestitoRepository.save(prestito);

        return "Restituzione completata per: '" + copia.getLibro().getTitolo() + "'";
    }

    public void delete(Long id) {
        prestitoRepository.deleteById(id);
    }
}
