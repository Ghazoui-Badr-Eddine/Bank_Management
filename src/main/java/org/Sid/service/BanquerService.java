package org.Sid.service;

import java.util.Date;
import java.util.Optional;

import org.Sid.dao.ClientRepository;
import org.Sid.dao.CompteRepository;
import org.Sid.dao.OperationRepository;
import org.Sid.entities.Clients;
import org.Sid.entities.Compte;
import org.Sid.entities.CompteCourant;
import org.Sid.entities.Operations;
import org.Sid.entities.Retrait;
import org.Sid.entities.Versement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.core.net.server.Client;

@Service
@Transactional
public class BanquerService implements IBanqueService {
	@Autowired
	OperationRepository operationRepository;
	@Autowired
	CompteRepository compteRepository;
	@Autowired
	ClientRepository clientRepository;
	@Override
	public Compte consulterCPTE(String CodeCPTE) {
		Compte cpte ;
		try
		{
			cpte = compteRepository.findById(CodeCPTE).get();
			return cpte;
		}
		catch(Exception e)
		{
			throw new  RuntimeException("Compte introuvable");
		}
	}

	@Override
	public void Verser(String CodeCPTE, double Montant) {
		// TODO Auto-generated method stub
		Compte cpte =consulterCPTE(CodeCPTE);
	    Versement v =new Versement(new Date(), Montant, cpte);
	    operationRepository.save(v);
	    
	    cpte.setSolde(Montant+cpte.getSolde());
	    compteRepository.save(cpte);
	    
		
	}

	@Override
	public void retirer(String CodeCPTE, double Montant) {
		// TODO Auto-generated method stub
		Compte cpte =consulterCPTE(CodeCPTE);
		double facilite=0 ;
		if(cpte instanceof CompteCourant)
			facilite=((CompteCourant) cpte).getDecouvert();
		
		
		
		if((facilite+cpte.getSolde())<Montant)
			throw new RuntimeException("Solde insuffisant");
		
	    Retrait r =new Retrait(new Date(), Montant, cpte);
	    operationRepository.save(r);
	    cpte.setSolde(cpte.getSolde()-Montant);
	    compteRepository.save(cpte);
		
	}
		


	@Override
	public Page<Operations> ListeOperation(String CodeCPTE, int page, int size) {
		// TODO Auto-generated method stub
		return operationRepository.listeOperations(CodeCPTE, PageRequest.of(page,size));
	}

	@Override
	public void virement(String CodeCPTE1, String CodeCPTE2,double montant) {
		// TODO Auto-generated method stub
		if(CodeCPTE1.equals(CodeCPTE2))
			throw new RuntimeException("vous ne pouvez pas effectuer un virement pour le meme compte");
		retirer(CodeCPTE1, montant);
		Verser(CodeCPTE2,montant);
	}

	

	@Override
	public Clients ajouter(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
