package com.example.demo.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "libri")
public class Libro {

    @Id
    @Column(name = "codice_isbn", length = 20, nullable = false)
    private String codiceIsbn;

    @Column(nullable = false)
    private String titolo;

    @Column(name = "anno_pubblicazione")
    private Integer annoPubblicazione;

    @ManyToOne(fetch = FetchType.EAGER) // Carica sempre i dati dell'autore
    @JoinColumn(name = "id_autore", nullable = false)
    @JsonIgnoreProperties({"libri"}) // Evita cicli infiniti se il tuo Autore ha una lista di Libri
    private Autore autore;

    private String sala;
    private String scaffale;
    private String ripiano;

    // Costruttore vuoto richiesto da JPA
    public Libro() {}

    // Costruttore completo per comodità
    public Libro(String codiceIsbn, String titolo, Integer annoPubblicazione, Autore autore) {
        this.codiceIsbn = codiceIsbn;
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.autore = autore;
    }

    // Getter e Setter
    public String getCodiceIsbn() { return codiceIsbn; }
    public void setCodiceIsbn(String codiceIsbn) { this.codiceIsbn = codiceIsbn; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public Integer getAnnoPubblicazione() { return annoPubblicazione; }
    public void setAnnoPubblicazione(Integer annoPubblicazione) { this.annoPubblicazione = annoPubblicazione; }

    public Autore getAutore() { return autore; }
    public void setAutore(Autore autore) { this.autore = autore; }

    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }

    public String getScaffale() { return scaffale; }
    public void setScaffale(String scaffale) { this.scaffale = scaffale; }

    public String getRipiano() { return ripiano; }
    public void setRipiano(String ripiano) { this.ripiano = ripiano; }
}