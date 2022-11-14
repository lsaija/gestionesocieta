package it.prova.gestionesocieta.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesocieta.exception.SocietaConDipendentiException;
import it.prova.gestionesocieta.model.Societa;
import it.prova.gestionesocieta.repository.SocietaRepository;


@Service
public class SocietaServiceImpl implements SocietaService{
	@Autowired
	private SocietaRepository societaRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void inserisciNuovo(Societa societaInstance) {
		societaRepository.save(societaInstance);
	}

	@Override
	public List<Societa> findByExample(Societa example)throws Exception {
		String query = "select s from Societa s where s.id = s.id";
		
		if (StringUtils.isNotBlank(example.getRagioneSociale())) 
			query += " and s.ragioneSociale like '%" + example.getRagioneSociale() +"%'";
		if (StringUtils.isNotBlank(example.getIndirizzo())) 
			query += " and s.indirizzo like '%" + example.getIndirizzo() + "%'";
		
		if (example.getDataFondazione() != null) 
			query +=" and s.dataFondazione >= " + "'" + example.getDataFondazione().toInstant() + "'";
		
		return entityManager.createQuery(query,Societa.class).getResultList();	
	
	}

	@Transactional
	public void removeConEccezione(Societa societaInstance) {
		/*Long idSocietaToRemove=societaInstance.getId();
		if(societaInstance.getDipendenti().isEmpty())
			throw new SocietaConDipendentiException("Societa con id: " + idSocietaToRemove + " ha ancora dei dipendenti collegati.");
		societaRepository.delete(societaInstance);*/
		TypedQuery<Societa> query=entityManager.createQuery("select s from Societa s join fetch s.dipendenti d where s.id=:id", Societa.class).setParameter("id", societaInstance.getId());
		if(!query.getResultList().isEmpty()) {
			throw new SocietaConDipendentiException("Societa con almeno un dipendente");
		}

		societaRepository.delete(societaInstance);

	}
	
	public void aggiorna(Societa societa) {
		societaRepository.save(societa);
	}
}
