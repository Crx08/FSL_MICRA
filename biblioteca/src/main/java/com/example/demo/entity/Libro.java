package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "libri")
public class Libro {

    @Id
    @Column(name = "codice_isbn", length = 20)
    private String codiceIsbn; // Usiamo l'ISBN (String) come chiave primaria

    private String titolo;

    // RELAZIONE: Molti libri appartengono a un solo Autore
    @ManyToOne
    @JoinColumn(name = "id_autore", nullable = false) // Collega la chiave esterna SQL
    private Autore autore;

    private String sala;
    private String scaffale;
    private String ripiano;

    public Libro() {}



    // Getter e Setter
    public String getCodiceIsbn() { return codiceIsbn; }
    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public Autore getAutore() { return autore; }
    public void setAutore(Autore autore) { this.autore = autore; }
    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }
    public String getScaffale() { return scaffale; }
    public void setScaffale(String scaffale) { this.scaffale = scaffale; }
    public String getRipiano() { return ripiano; }
    public void setRipiano(String ripiano) { this.ripiano = ripiano; }
}