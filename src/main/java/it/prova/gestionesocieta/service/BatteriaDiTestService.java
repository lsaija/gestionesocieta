package it.prova.gestionesocieta.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.exception.SocietaConDipendentiException;
import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.model.Societa;

@Service
public class BatteriaDiTestService {
	@Autowired
	private SocietaService societaService;

	@Autowired
	private DipendenteService dipendeteService;

	public void testInserisciNuovaSocieta() {
		Long nowInMillisecondi = new Date().getTime();

		Societa nuovaSocieta = new Societa("Societa", "via bella", new Date());
		if (nuovaSocieta.getId() != null)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: transient object con id valorizzato");
		// salvo
		societaService.inserisciNuovo(nuovaSocieta);
		if (nuovaSocieta.getId() == null || nuovaSocieta.getId() < 1)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: inserimento fallito");

		System.out.println("testInserisciNuovaSocieta........OK");
	}

	public void testFindByExempleSocieta() throws Exception {
		String ragioneSociale = "";
		String indirizzo = "";
		Date dataFondazione = new SimpleDateFormat("yyyy-MM-dd").parse("1900-01-01");
		Societa nuovaSocieta = new Societa(ragioneSociale, indirizzo, dataFondazione);
		if (nuovaSocieta.getId() != null)
			throw new RuntimeException("testFindByExempleSocieta...failed: transient object con id valorizzato");
		// salvo
		societaService.inserisciNuovo(nuovaSocieta);
		if (nuovaSocieta.getId() == null || nuovaSocieta.getId() < 1)
			throw new RuntimeException("testFindByExempleSocieta...failed: inserimento fallito");

		if (societaService.findByExample(nuovaSocieta).size() != 2)
			throw new RuntimeException("testFindByExempleSocieta...failed: non tutti gli elementi sono stati trovati");

		System.out.println("testFindByExempleSocieta........OK");
	}

	public void testRemoveConEccezioneVaInRollback() throws Exception {
		Societa nuovaSocieta = new Societa("Societa", "via bella", new Date());

		Societa nuovaSocieta2 = new Societa("Societa", "via bella", new Date());

		societaService.inserisciNuovo(nuovaSocieta);
		societaService.inserisciNuovo(nuovaSocieta2);

		Dipendente dipendente1 = new Dipendente("carlo", nuovaSocieta);
		dipendeteService.inserisciNuovo(dipendente1);
		nuovaSocieta.getDipendenti().add(dipendente1);
		societaService.aggiorna(nuovaSocieta);

		if (nuovaSocieta.getId() == null || nuovaSocieta.getId() < 1 || nuovaSocieta2.getId() == null
				|| nuovaSocieta2.getId() < 1)
			throw new RuntimeException("testRemoveConEccezioneVaInRollback...failed: inserimento fallito");

		try {
			societaService.removeConEccezione(nuovaSocieta);
			societaService.removeConEccezione(nuovaSocieta2);

		} catch (SocietaConDipendentiException e) {
			e.printStackTrace();

		}

		if (nuovaSocieta == null || nuovaSocieta.getId() == null)
			throw new RuntimeException(
					"testRemoveConEccezioneVaInRollback...failed: cancellazione avvenuta senza rollback");

		System.out.println("testRemoveConEccezioneVaInRollback........OK");
	}

	public void testInserisciDipendente() {
		Societa nuovaSocieta = new Societa("Societa", "via bella", new Date());
		societaService.inserisciNuovo(nuovaSocieta);

		Dipendente dipendente1 = new Dipendente("carlo", "magno", new Date(), 10000);
		dipendente1.setSocieta(nuovaSocieta);

		dipendeteService.inserisciNuovo(dipendente1);
		if (dipendente1.getId() == null || dipendente1.getId() < 1)
			throw new RuntimeException("testInserisciDipendente...failed: inserimento fallito");

		System.out.println("testInserisciDipendente........OK");
	}

	public void testAggiornaDipendente() {
		Societa nuovaSocieta = new Societa("Societa", "via bella", new Date());
		societaService.inserisciNuovo(nuovaSocieta);

		Dipendente dipendente1 = new Dipendente("carlo", "magno", new Date(), 10000);
		dipendente1.setSocieta(nuovaSocieta);

		dipendeteService.inserisciNuovo(dipendente1);
		if (dipendente1.getId() == null || dipendente1.getId() < 1)
			throw new RuntimeException("testAggiornaDipendente...failed: inserimento fallito");

		String nomeNuovo = "Alessandro";
		dipendente1.setNome(nomeNuovo);
		dipendeteService.aggiorna(dipendente1);
		if (!dipendente1.getNome().equals("Alessandro"))
			throw new RuntimeException("testAggiornaDipendente...failed: Aggornamento fallito");

		System.out.println("testAggiornaDipendente........OK");
	}

	public void testCercaTuttiISocietaConDipendentiConRalMaggioreDi() {
		Long nowInMillisecondi = new Date().getTime();

		// test
		int ralToCheck = 30000;

		IntStream.range(1, 5).forEach(i -> {
			int ralToSet = i % 2 == 0 ? 30000 : 50000;
			Societa nuovaSocieta = new Societa("Societa", "via bella", new Date());
			societaService.inserisciNuovo(nuovaSocieta);
			dipendeteService
					.inserisciNuovo(new Dipendente("Mario" + i, "Rossi" + i, new Date(), ralToSet, nuovaSocieta));
			dipendeteService
					.inserisciNuovo(new Dipendente("Anto" + i, "Bianchi" + i, new Date(), ralToSet, nuovaSocieta));
		});

		List<Societa> risultatiAttesi = societaService
				.cercaTuttiISocietaConDipendentiConredditoAnnuoLordoMaggioreDi(ralToCheck);
		if (risultatiAttesi.size() != 2)
			throw new RuntimeException(
					"testCercaTuttiISocietaConDipendentiConRalMaggioreDi...failed: non sono il numero previsto");

		List<Dipendente> listaDipendentiDaSocieta = risultatiAttesi.stream()
				.flatMap(societa -> societa.getDipendenti().stream()).collect(Collectors.toList());

		if (listaDipendentiDaSocieta.size() != 4)
			throw new RuntimeException(
					"testCercaTuttiISocietaConDipendentiConRalMaggioreDi...failed: gli dipendenti non sono il numero previsto");

		System.out.println("testCercaTuttiISocietaConDipendentiConRalMaggioreDi........OK");
	}

	public void testCercaDipendePiuAnzianoConSocietaConDataFondazioneMinoreDi() throws ParseException {
		

		// test
		Date dataToCheck = new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01");

		Societa nuovaSocieta;
		nuovaSocieta = new Societa("Societa", "via bella", new SimpleDateFormat("yyyy-MM-dd").parse("1800-01-01"));
		societaService.inserisciNuovo(nuovaSocieta);
		dipendeteService.inserisciNuovo(new Dipendente("Mario", "Rossi",
				new SimpleDateFormat("yyyy-MM-dd").parse("1800-04-01"), 300, nuovaSocieta));
		dipendeteService.inserisciNuovo(new Dipendente("Anto", "Bianchi", new Date(), 66, nuovaSocieta));

		Dipendente risultatoAtteso = dipendeteService
				.cercaDipendePiuAnzianoConSocietaConDataFondazioneMinoreDi(dataToCheck);
		if (risultatoAtteso == null)
			throw new RuntimeException(
					"testCercaDipendePiuAnzianoConSocietaConDataFondazioneMinoreDi...failed: non ?? il numero previsto");

		if (!risultatoAtteso.getNome().equals("Mario") || !risultatoAtteso.getCognome().equals("Rossi"))
			throw new RuntimeException(
					"testCercaDipendePiuAnzianoConSocietaConDataFondazioneMinoreDi...failed: non ?? il risultato corretto");

		System.out.println("testCercaDipendePiuAnzianoConSocietaConDataFondazioneMinoreDi........OK");
	}

}
