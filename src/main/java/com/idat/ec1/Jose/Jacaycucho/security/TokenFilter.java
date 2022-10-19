package com.idat.ec1.Jose.Jacaycucho.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class TokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserDetailService detailService;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//el final indica que a esa variable solo se le puede asignar un valor u objeto una única vez
		final String tokenHeader = request.getHeader("Authorization");
		
		String token = null;
		String username = null;
		
		
		if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
			
			token = tokenHeader.substring(7);
			
			try {
				username = tokenUtil.getUsernameFromToken(token);
				
			} catch (IllegalArgumentException e) {
				logger.warn("Token Invalido");
			} catch (ExpiredJwtException e) {
				logger.warn("Token Expirado");
			}
		}else {
			logger.warn("Token Invalido");
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails user = this.detailService.loadUserByUsername(username);
			if(tokenUtil.validateToken(token, user)) {
				UsernamePasswordAuthenticationToken usernameToken = 
						new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
				usernameToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernameToken);
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
