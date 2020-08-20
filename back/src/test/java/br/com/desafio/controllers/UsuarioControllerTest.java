package br.com.desafio.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import br.com.desafio.models.dtos.UsuarioAuthDTO;
import br.com.desafio.models.dtos.NovoUsuarioDTO;
import br.com.desafio.services.UsuarioService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.hamcrest.core.IsNot;
import org.hamcrest.text.IsBlankString;

import com.google.gson.Gson;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
public class UsuarioControllerTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private UsuarioService service;

	private final String NOME_USUARIO = "teste";
	private final String EMAIL_USUARIO = "teste@teste";
	private final String SENHA = Base64Utils.encodeToString("senha".getBytes());

	@Test
	@DisplayName("Teste criar novo usuário")
	public void testarCriacaoUsuario() throws Exception {
		NovoUsuarioDTO usuario = new NovoUsuarioDTO("Usuário de Testes", NOME_USUARIO, EMAIL_USUARIO, SENHA);

		mvc.perform(post("/usuario/novo").content(new Gson().toJson(usuario)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Teste login com nome de usuário")
	public void testarLoginNomeUsuario() throws Exception {
		var usuario = new UsuarioAuthDTO(NOME_USUARIO, null, SENHA);
		novoUsuario();

		mvc.perform(post("/usuario/login").content(new Gson().toJson(usuario)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(IsNot.not(IsBlankString.blankOrNullString())));
	}

	@Test
	@DisplayName("Teste login com email do usuário")
	public void testarLoginEmailUsuario() throws Exception {
		var usuario = new UsuarioAuthDTO(null, EMAIL_USUARIO, SENHA);
		novoUsuario();

		mvc.perform(post("/usuario/login").content(new Gson().toJson(usuario)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(IsNot.not(IsBlankString.blankOrNullString())));
	}

	@Test
	@DisplayName("Teste login usuario não cadastrado")
	public void testarLoginErrado() throws Exception {
		var usuario = new UsuarioAuthDTO(null, "teste@", SENHA);
		novoUsuario();

		mvc.perform(post("/usuario/login").content(new Gson().toJson(usuario)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Teste login com senha errada")
	public void testarSenhaErrada() throws Exception {
		var usuario = new UsuarioAuthDTO(null, EMAIL_USUARIO, "teste");
		novoUsuario();

		mvc.perform(post("/usuario/login").content(new Gson().toJson(usuario)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	private void novoUsuario() {
		var senha = Base64Utils.encodeToString("senha".getBytes());
		service.criarUsuario(new NovoUsuarioDTO("Usuário de Testes", NOME_USUARIO, EMAIL_USUARIO, senha));
	}
}