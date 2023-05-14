package org.Sid.service;

import org.Sid.dao.ClientRepository;
import org.Sid.entities.Clients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientService implements IClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	@Override
	public Page<Clients> listClients(int page, int size) {
		return clientRepository.listClients(PageRequest.of(page, size));
	}

	@Override
	public Clients getClientByCodeClient(long code) {
		return clientRepository.findById(code).get();
	}

}
