package br.com.desafio.controllers;

import br.com.desafio.models.dtos.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import br.com.desafio.models.dtos.NovoUsuarioDTO;
import br.com.desafio.models.dtos.UsuarioAuthDTO;
import br.com.desafio.services.UsuarioService;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(path = "usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService authService;

	@PostMapping(path = "login")
	public String autenticar(@RequestBody UsuarioAuthDTO auth) {
		return authService.autenticar(auth);
	}

	@PostMapping(path = "novo")
	@ResponseStatus(HttpStatus.CREATED)
	public void criarUsuario(@RequestBody NovoUsuarioDTO usuario) {
		authService.criarUsuario(usuario);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO criarUsuario(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		token = StringUtils.hasText(token) && token.split(" ").length > 1 ? token.split(" ")[1]: token;
		return authService.getUsuarioPorToken(token);
	}
}
