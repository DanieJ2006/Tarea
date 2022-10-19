package com.idat.ec1.Jose.Jacaycucho.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idat.ec1.Jose.Jacaycucho.model.Bodega;
import com.idat.ec1.Jose.Jacaycucho.repository.BodegaRepository;
@Service
public class BodegaServiceImpl implements BodegaService {
	
	@Autowired
	private BodegaRepository repository;
	
	@Override
	public List<Bodega> lista() {
		return repository.findAll();
	}

}
