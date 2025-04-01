package no.hvl.dat107;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(schema = "oblig3", name = "avdeling")
public class Avdeling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avdeling_id")
    private int id;

    @Column(name = "navn", nullable = false)
    private String navn;

    @OneToOne
    @JoinColumn(name = "sjef_id", referencedColumnName = "ansatt_id")
    private Ansatt sjef;

    @OneToMany(mappedBy = "avdeling", cascade = CascadeType.ALL)
    private List<Ansatt> ansatte;

    public Avdeling() {}

    public Avdeling(String navn, Ansatt sjef) {
        this.navn = navn;
        this.sjef = sjef;
    }

    public int getId() { return id; }
    public String getNavn() { return navn; }
    public Ansatt getSjef() { return sjef; }
    public List<Ansatt> getAnsatte() { return ansatte; }
    
    public void setSjef(Ansatt sjef) { this.sjef = sjef; }

    @Override
    public String toString() {
        return "Avdeling{" +
                "ID=" + id +
                ", Navn='" + navn + '\'' +
                ", Sjef=" + (sjef != null ? sjef.getFornavn() + " " + sjef.getEtternavn() : "Ingen") +
                '}';
    }
}