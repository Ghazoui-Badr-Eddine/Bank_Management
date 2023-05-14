package org.Sid.service;

import org.Sid.entities.Clients;
import org.springframework.data.domain.Page;

public interface IClientService {
	
	public Page<Clients> listClients(int page,int size);
	public Clients getClientByCodeClient(long code);
}
