package com.example.demo.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prestiti")
public class Prestito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestito")
    private Long idPrestito;

    @ManyToOne
    @JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "id_copia", nullable = false)
    private CopiaLibro copiaLibro;

    @Column(name = "data_inizio")
    private LocalDate dataInizio;

    @Column(name = "data_scadenza")
    private LocalDate dataScadenza;

    @Column(name = "data_restituzione")
    private LocalDate dataRestituzione;

    public Prestito() {}

    // Getter e Setter
    public Long getIdPrestito() { return idPrestito; }
    public void setIdPrestito(Long idPrestito) { this.idPrestito = idPrestito; }
    public Utente getUtente() { return utente; }
    public void setUtente(Utente utente) { this.utente = utente; }
    public CopiaLibro getCopiaLibro() { return copiaLibro; }
    public void setCopiaLibro(CopiaLibro copiaLibro) { this.copiaLibro = copiaLibro; }
    public LocalDate getDataInizio() { return dataInizio; }
    public void setDataInizio(LocalDate dataInizio) { this.dataInizio = dataInizio; }
    public LocalDate getDataScadenza() { return dataScadenza; }
    public void setDataScadenza(LocalDate dataScadenza) { this.dataScadenza = dataScadenza; }
    public LocalDate getDataRestituzione() { return dataRestituzione; }
    public void setDataRestituzione(LocalDate dataRestituzione) { this.dataRestituzione = dataRestituzione; }
}