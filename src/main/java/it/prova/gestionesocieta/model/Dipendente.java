package it.prova.gestionesocieta.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "dipendente")
public class Dipendente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "cognome")
	private String cognome;
	@Column(name = "dataassunzione")
	private Date dataAssunzione;
	@Column(name = "redditoannuolordo")
	private int redditoAnnuoLordo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "societa_id",nullable = false)
	private Societa societa;
	
	public Dipendente() {
		// TODO Auto-generated constructor stub
	}
	public Dipendente(String nome, String cognome, Date dataAssunzione, int redditoAnnuoLordo, Societa societa) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.dataAssunzione = dataAssunzione;
		this.redditoAnnuoLordo = redditoAnnuoLordo;
		this.societa = societa;
	}
	public Dipendente(Long id, String nome, String cognome, Date dataAssunzione, int redditoAnnuoLordo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.dataAssunzione = dataAssunzione;
		this.redditoAnnuoLordo = redditoAnnuoLordo;
	}
	public Dipendente(String nome, String cognome, Date dataAssunzione, int redditoAnnuoLordo) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.dataAssunzione = dataAssunzione;
		this.redditoAnnuoLordo = redditoAnnuoLordo;
	}
	
	public Dipendente(String nome, Societa societa) {
		super();
		this.nome = nome;
		this.societa = societa;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public Date getDataAssunzione() {
		return dataAssunzione;
	}
	public void setDataAssunzione(Date dataAssunzione) {
		this.dataAssunzione = dataAssunzione;
	}
	public int getRedditoAnnuoLordo() {
		return redditoAnnuoLordo;
	}
	public void setRedditoAnnuoLordo(int redditoAnnuoLordo) {
		this.redditoAnnuoLordo = redditoAnnuoLordo;
	}
	public Societa getSocieta() {
		return societa;
	}
	public void setSocieta(Societa societa) {
		this.societa = societa;
	}
	
	


}
