package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "autori")
public class Autore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autore")
    private Integer id;

    private String nome;
    private String cognome;

    @Column(name = "data_nascita")
    private LocalDate dataNascita;

    @Column(name = "data_morte")
    private LocalDate dataMorte;

    // Relazione per vedere i libri dell'autore.
    // JsonIgnoreProperties evita che Java entri in un ciclo infinito tra Libro e Autore
    @OneToMany(mappedBy = "autore", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("autore")
    private List<Libro> libri;

    // Costruttori
    public Autore() {}

    // Getter e Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public LocalDate getDataNascita() { return dataNascita; }
    public void setDataNascita(LocalDate dataNascita) { this.dataNascita = dataNascita; }

    public LocalDate getDataMorte() { return dataMorte; }
    public void setDataMorte(LocalDate dataMorte) { this.dataMorte = dataMorte; }

    public List<Libro> getLibri() { return libri; }
    public void setLibri(List<Libro> libri) { this.libri = libri; }
}