package com.idat.ec1.Jose.Jacaycucho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idat.ec1.Jose.Jacaycucho.dto.UsuarioDTOResponse;
import com.idat.ec1.Jose.Jacaycucho.dto.UsuarioDTORquest;
import com.idat.ec1.Jose.Jacaycucho.security.TokenUtil;
import com.idat.ec1.Jose.Jacaycucho.security.UserDetailService;


@RestController
public class UsuarioController {
	
	@Autowired
	private TokenUtil tokenUtil;
	@Autowired
	private UserDetailService detailService;
	
	@RequestMapping(path = "/creartoken", method = RequestMethod.POST)
	public ResponseEntity<?> crearToken(@RequestBody UsuarioDTORquest request){
		UserDetails user = detailService.loadUserByUsername(request.getUsuario());
		return ResponseEntity.ok(new UsuarioDTOResponse(tokenUtil.generateToken(user.getUsername())));
		
	}
	
	
	
}
