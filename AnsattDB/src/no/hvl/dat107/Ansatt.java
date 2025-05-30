package no.hvl.dat107;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(schema = "oblig3", name = "ansatt")
public class Ansatt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ansatt_id")
	private int id;

	@Column(name = "brukernavn", unique = true, nullable = false)
	private String brukernavn;

	@Column(name = "fornavn", nullable = false)
	private String fornavn;

	@Column(name = "etternavn", nullable = false)
	private String etternavn;

	@Column(name = "ansettelsesdato", nullable = false)
	private LocalDate ansettelsesdato;

	@Column(name = "stilling", nullable = false)
	private String stilling;

	@Column(name = "manedslonn", nullable = false)
	private int manedslonn;

	@ManyToOne
	@JoinColumn(name = "avdeling_id")
	private Avdeling avdeling;

	@OneToMany(mappedBy = "ansatt", cascade = CascadeType.ALL)
	private List<Prosjektdeltagelse> prosjektdeltagelser;

	public Ansatt() {
	}

	public Ansatt(String brukernavn, String fornavn, String etternavn, LocalDate ansettelsesdato, String stilling,
			int manedslonn, Avdeling avdeling) {
		this.brukernavn = brukernavn;
		this.fornavn = fornavn;
		this.etternavn = etternavn;
		this.ansettelsesdato = ansettelsesdato;
		this.stilling = stilling;
		this.manedslonn = manedslonn;
		this.avdeling = avdeling;
	}

	public int getId() {
		return id;
	}

	public String getBrukernavn() {
		return brukernavn;
	}

	public String getFornavn() {
		return fornavn;
	}

	public String getEtternavn() {
		return etternavn;
	}

	public LocalDate getAnsettelsesdato() {
		return ansettelsesdato;
	}

	public String getStilling() {
		return stilling;
	}

	public int getManedslonn() {
		return manedslonn;
	}

	public Avdeling getAvdeling() {
		return avdeling;
	}

	public void setStilling(String stilling) {
		this.stilling = stilling;
	}

	public void setManedslonn(int manedslonn) {
		this.manedslonn = manedslonn;
	}

	public void setAvdeling(Avdeling avdeling) {
		this.avdeling = avdeling;
	}

	@Override
	public String toString() {
		return "Ansatt{" + "ID=" + id + ", Brukernavn='" + brukernavn + '\'' + ", Navn='" + fornavn + " " + etternavn
				+ '\'' + ", Stilling='" + stilling + '\'' + ", Lønn=" + manedslonn + ", Avdeling="
				+ (avdeling != null ? avdeling.getNavn() : "Ingen") + '}';
	}
}