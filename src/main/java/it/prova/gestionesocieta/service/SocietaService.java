package it.prova.gestionesocieta.service;

import java.util.List;

import it.prova.gestionesocieta.model.Societa;


public interface SocietaService {
	public void inserisciNuovo(Societa societaInstance);
	
	public List<Societa> findByExample(Societa example)throws Exception;
	
	public void removeConEccezione(Societa societaInstance) throws Exception;
	
	public void aggiorna(Societa societa);
}
