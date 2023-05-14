package org.Sid.dao;

import java.util.Optional;


import org.Sid.entities.Compte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompteRepository extends JpaRepository<Compte,String>{
	@Query("select cp from Compte cp")
	public Page<Compte> listCompte(Pageable pageable);
	
	public Optional<Compte> findById(String codeCompte);
}
