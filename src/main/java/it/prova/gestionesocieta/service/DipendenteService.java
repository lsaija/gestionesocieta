package it.prova.gestionesocieta.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;

import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.model.Societa;

public interface DipendenteService {
	
	public void inserisciNuovo(Dipendente dipendenteInstance);
	public void aggiorna(Dipendente dipendenteInstance);
	public Dipendente cercaDipendePiuAnzianoConSocietaConDataFondazioneMinoreDi(Date dataInput);
	

}
