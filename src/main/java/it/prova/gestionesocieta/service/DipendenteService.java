package it.prova.gestionesocieta.service;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;

import it.prova.gestionesocieta.model.Dipendente;

public interface DipendenteService {
	
	public void inserisciNuovo(Dipendente dipendenteInstance);
	public void aggiorna(Dipendente dipendenteInstance);
	

}
