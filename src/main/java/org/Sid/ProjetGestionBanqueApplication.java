package org.Sid;

import java.util.Date;
import java.util.Random;

import org.Sid.dao.ClientRepository;
import org.Sid.dao.CompteRepository;
import org.Sid.dao.OperationRepository;
import org.Sid.entities.Clients;
import org.Sid.entities.Compte;
import org.Sid.entities.CompteCourant;
import org.Sid.entities.CompteEpargne;
import org.Sid.entities.Operations;
import org.Sid.entities.Retrait;
import org.Sid.entities.Versement;
import org.Sid.service.IBanqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProjetGestionBanqueApplication implements CommandLineRunner{
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	CompteRepository compteRepository;
	@Autowired
	OperationRepository operationRepository;
	@Autowired
	IBanqueService ibanqueService ;
	public static void main(String[] args) {
     SpringApplication.run(ProjetGestionBanqueApplication.class, args);

		
	}

	@Override
	public void run(String... args) throws Exception {
		
		 /* Clients c1 = clientRepository.save(new Clients("John","John@gmail.com"));
		  Clients c2 = clientRepository.save(new Clients("Alexandre","alexander@gmail.com")); 
		  Compte cp1 =compteRepository.save(new CompteCourant("c1", new Date(), 9000, c1, 6000));
		  Compte cp2 = compteRepository.save(new CompteEpargne("c2", new Date(), 6000,c2, 5.5)); 
		  operationRepository.save(new Versement(new Date(), 9000, cp1 ));
		  operationRepository.save(new Versement(new Date(), 6000, cp1 ));
		  operationRepository.save(new Versement(new Date(), 2300, cp1 ));
		  operationRepository.save(new Retrait(new Date(), 9000, cp1 ));
		  
		  operationRepository.save(new Versement(new Date(), 2300, cp2 ));
		  operationRepository.save(new Versement(new Date(), 400, cp2 ));
		  operationRepository.save(new Versement(new Date(), 2300, cp2 ));
		  operationRepository.save(new Retrait(new Date(), 3000, cp2 ));
		  
		  ibanqueService.Verser("c1", 1111111);*/
		 
		
	}

}
