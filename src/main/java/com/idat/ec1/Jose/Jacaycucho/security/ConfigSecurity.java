package com.idat.ec1.Jose.Jacaycucho.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration//esto es para indicar que esta clase es de tipo configuracin
@EnableWebSecurity//esto es para activar los metodos de seguridad
@EnableGlobalMethodSecurity(prePostEnabled = true)//sirve para aperturar todos los metodos de seguridad
public class ConfigSecurity extends WebSecurityConfigurerAdapter{
	
	//esto es para spring 2.6.7
	
	@Autowired
	private UserDetailService detailService;
	@Autowired
	private TokenFilter filter;
	@Autowired
	private EntryPoint entryPoint;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		
		auth.userDetailsService(detailService).passwordEncoder(encriptado());
//		auth.inMemoryAuthentication()
//		.withUser("daniel")
//		.password(encriptado().encode( "123456"))
//		.roles("ADMIN");
//		auth.inMemoryAuthentication()
//		.withUser("daniel2")
//		.password(encriptado().encode( "123456"))
//		.roles("USER");
	}

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//		.antMatchers("/producto/v1/listar").permitAll()
//		.antMatchers("/producto/v1/**").access("hasRole('ADMIN')")
//		.and().httpBasic().and().csrf().disable();
		
		http.authorizeHttpRequests()
		.antMatchers("/creartoken")
		.permitAll().anyRequest()
		.authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(entryPoint)
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
		.csrf().disable();
		
	}
	
	
	
	
//	esto es para spring 2.7.3
	
	//crear usuario en memoria
//	@Bean
//	public UserDetailsService detailsService() {
//		//se crea una instancia
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		manager.createUser(
//				User.withUsername("daniel")
//				.password(encriptado().encode("123456"))
//				.roles("ADMIN")
//				.build());
//		
//		return manager;
//	}
//	
//	
	//metodo para encriptar la contraseña
	@Bean//esto se coloca para guardarlo en el contenedor de spring , ya que se puede usar en otro lado
	public PasswordEncoder encriptado() {
		return new BCryptPasswordEncoder();
	}
//	
//	//esto delimitar el acceso al servicio(end point) de acuerdo a los roles
//	
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//		
//		http.authorizeRequests()
//			.antMatchers("/producto/v1/*")
//			.access("hasRole('ADMIN')")
//			.and()
//			.httpBasic()
//			.and()
//			.csrf()
//			.disable();
//		return http.build();
//	}
//	
	

	
	

	

}
