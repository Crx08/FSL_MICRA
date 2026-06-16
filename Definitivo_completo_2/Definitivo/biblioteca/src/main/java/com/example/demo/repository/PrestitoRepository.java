package com.example.demo.repository;

import com.example.demo.entity.Prestito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestitoRepository extends JpaRepository<Prestito, Integer> {

    // Controlla se l'utente ha prestiti attivi (non restituiti)
    boolean existsByUtente_IdAndDataRestituzioneIsNull(Integer idUtente);

    // Controlla se una copia è attualmente in prestito attivo
    boolean existsByCopiaLibro_IdCopiaAndDataRestituzioneIsNull(Integer idCopia);

    // Controlla se un libro (tramite ISBN) ha copie in prestito attivo
    boolean existsByCopiaLibro_Libro_CodiceIsbnAndDataRestituzioneIsNull(String codiceIsbn);

    // Controlla se un autore ha libri con prestiti attivi
    boolean existsByCopiaLibro_Libro_Autore_IdAndDataRestituzioneIsNull(Integer idAutore);
}
