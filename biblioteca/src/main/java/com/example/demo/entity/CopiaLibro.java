package com.example.demo.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "copie_libri")
public class CopiaLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_copia")
    private Long idCopia;

    @ManyToOne
    @JoinColumn(name = "codice_isbn", nullable = false)
    private Libro libro;

    @Column(name = "stato_conservazione")
    private String statoConservazione = "       ";

    private Boolean disponibile = true;

    public CopiaLibro() {}

    // Getter e Setter
    public Long getIdCopia() { return idCopia; }
    public void setIdCopia(Long idCopia) { this.idCopia = idCopia; }
    public Libro getLibro() { return libro; }
    public void setLibro(Libro libro) { this.libro = libro; }
    public String getStatoConservazione() { return statoConservazione; }
    public void setStatoConservazione(String statoConservazione) { this.statoConservazione = statoConservazione; }
    public Boolean getDisponibile() { return disponibile; }
    public void setDisponibile(Boolean disponibile) { this.disponibile = disponibile; }
}