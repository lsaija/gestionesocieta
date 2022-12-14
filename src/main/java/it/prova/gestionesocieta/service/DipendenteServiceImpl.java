package it.prova.gestionesocieta.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.model.Societa;
import it.prova.gestionesocieta.repository.DipendenteRepository;


@Service
public class DipendenteServiceImpl implements DipendenteService{
	@Autowired
	private DipendenteRepository dipendenteRepository;

	@Transactional
	public void inserisciNuovo(Dipendente dipendenteInstance) {
		dipendenteRepository.save(dipendenteInstance);
	}

	@Transactional
	public void aggiorna(Dipendente dipendenteInstance) {
		dipendenteRepository.save(dipendenteInstance);
	}
	
	@Transactional(readOnly = true)
	public Dipendente cercaDipendePiuAnzianoConSocietaConDataFondazioneMinoreDi(Date dataInput) {
		return dipendenteRepository.findFirst1BySocieta_DataFondazioneBeforeOrderByDataAssunzioneAsc(dataInput);
	}
}
