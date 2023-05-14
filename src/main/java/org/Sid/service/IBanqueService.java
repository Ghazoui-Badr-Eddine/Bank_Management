package org.Sid.service;

import org.Sid.entities.Clients;
import org.Sid.entities.Compte;
import org.Sid.entities.Operations;
import org.springframework.data.domain.Page;

public interface IBanqueService {

	public Compte consulterCPTE(String CodeCPTE);
	public void Verser(String CodeCPTE,double Montant);
	public void retirer (String CodeCPTE,double Montant);
	public void virement(String CodeCPTE1,String CodeCPTE2,double montant);
	Page<Operations> ListeOperation(String CodeCPTE,int page , int size);
	public Clients ajouter(String email);
}
