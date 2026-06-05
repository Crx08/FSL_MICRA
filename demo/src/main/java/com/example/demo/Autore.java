package com.example.demo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity // Dice a Spring che questa classe è una tabella del database
@Table(name = "autori") // Specifica che la tabella si chiama "autori"
public class Autore {

    @Id // Chiave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    @Column(name = "id_autore")
    private Long idAutore;

    private String nome;
    private String cognome;

    @Column(name = "data_nascita")
    private LocalDate dataNascita; // Il tipo DATE di SQL diventa LocalDate in Java

    @Column(name = "data_morte")
    private LocalDate dataMorte;

    // Costruttore vuoto obbligatorio per JPA
    public Autore() {}

    // Getter e Setter (Servono a Spring per leggere e scrivere i dati)
    public Long getIdAutore() { return idAutore; }
    public void setIdAutore(Long idAutore) { this.idAutore = idAutore; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public LocalDate getDataNascita() { return dataNascita; }
    public void setDataNascita(LocalDate dataNascita) { this.dataNascita = dataNascita; }
    public LocalDate getDataMorte() { return dataMorte; }
    public void setDataMorte(LocalDate dataMorte) { this.dataMorte = dataMorte; }
}