package it.prova.gestionesocieta.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import it.prova.gestionesocieta.model.Dipendente;


public interface DipendenteRepository extends CrudRepository<Dipendente, Long>,QueryByExampleExecutor <Dipendente> {
		
	Dipendente findFirst1BySocieta_DataFondazioneBeforeOrderByDataAssunzioneAsc(Date dataInput);
	
	

}
