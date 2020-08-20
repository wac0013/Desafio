package br.com.desafio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.desafio.utils.jwt.JWTAuthEntryPoint;
import br.com.desafio.utils.jwt.JWTAuthFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JWTAuthEntryPoint naoAutorizadoHandler;

	@Autowired
	private JWTAuthFilter jwtAuthFilter;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/usuario/login").permitAll()
				.antMatchers(HttpMethod.POST, "/usuario/novo").permitAll().antMatchers("/docs/**").permitAll()
				.antMatchers("/").permitAll().antMatchers("/swagger-resources/**").permitAll()
				.antMatchers("/v2/api-docs").permitAll().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(naoAutorizadoHandler).and()
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	}
}