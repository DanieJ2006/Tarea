package com.idat.ec1.Jose.Jacaycucho.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.idat.ec1.Jose.Jacaycucho.model.Bodega;
import com.idat.ec1.Jose.Jacaycucho.service.BodegaService;

@RestController
@RequestMapping("/bodega/v1")
public class BodegaController {
	
	@Autowired
	private BodegaService service;
	
	@RequestMapping(path = "/listar", method = RequestMethod.GET)
	public ResponseEntity<List<Bodega>> getBodega(){
		return new ResponseEntity<List<Bodega>>(service.lista(), HttpStatus.OK);
	}

}
