package org.Sid.dao;

import org.Sid.entities.Clients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Clients, Long> {
	@Query("select c from Clients c")
	public Page<Clients> listClients(Pageable pageable);
	
	public Clients findByEmail(String email);
}
