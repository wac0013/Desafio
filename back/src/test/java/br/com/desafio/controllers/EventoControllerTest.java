package br.com.desafio.controllers;

import org.hamcrest.core.IsNot;
import org.hamcrest.text.IsBlankString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import br.com.desafio.models.dtos.UsuarioAuthDTO;
import br.com.desafio.models.dtos.UsuarioDTO;
import br.com.desafio.repositories.UsuarioRepo;
import br.com.desafio.models.dtos.EventoDTO;
import br.com.desafio.models.dtos.NovoUsuarioDTO;
import br.com.desafio.services.EventoService;
import br.com.desafio.services.UsuarioService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
public class EventoControllerTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private EventoService service;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepo usuarioRepo;

	private String token;
	private UsuarioDTO usuarioTeste;

	private final String NOME_USUARIO = "teste";
	private final String EMAIL_USUARIO = "teste@teste";
	private final String SENHA = Base64Utils.encodeToString("senha".getBytes());

	@BeforeEach
	void criarToken() {
		novoUsuario();
		usuarioTeste = UsuarioDTO.fromUsuario(usuarioRepo.findByEmail(EMAIL_USUARIO).findFirst().orElse(null));
		token = usuarioService.autenticar(new UsuarioAuthDTO(NOME_USUARIO, null, SENHA));
	}

	@Test
	@DisplayName("Teste criar novo evento")
	public void testarCriacaoEvento() throws Exception {
		EventoDTO evento = new EventoDTO(null, "Evento Teste", new Date(), usuarioTeste);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create();

		mvc.perform(post("/evento/criar").content(gson.toJson(evento)).contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)).andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Teste atualizar evento")
	public void testarAtualizarEvento() throws Exception {
		EventoDTO evento = novoEvento();
		evento.setNome("Evento Atualizado");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create();

		mvc.perform(put("/evento/salvar").content(gson.toJson(evento)).contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)).andExpect(status().isOk())
				.andExpect(content().string(IsNot.not(IsBlankString.blankOrNullString())));
	}

	@Test
	@DisplayName("Teste excluir evento")
	public void testarExcluirEvento() throws Exception {
		EventoDTO evento = novoEvento();

		mvc.perform(delete("/evento/excluir").param("id", evento.getId()).header(HttpHeaders.AUTHORIZATION,
				"Bearer " + token)).andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("Teste consulta de evento por id")
	public void testarConsultaEvento() throws Exception {
		EventoDTO evento = novoEvento();

		mvc.perform(
				get("/evento/buscar").param("id", evento.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(status().isOk()).andExpect(content().string(IsNot.not(IsBlankString.blankOrNullString())));
	}

	@Test
	@DisplayName("Teste regra de acesso")
	public void testarNaoAutorizado() throws Exception {
		EventoDTO evento = novoEvento();

		mvc.perform(get("/evento/buscar").param("id", evento.getId())).andExpect(status().isUnauthorized());
	}

	private void novoUsuario() {
		usuarioService.criarUsuario(new NovoUsuarioDTO("Usuário de Testes", NOME_USUARIO, EMAIL_USUARIO, SENHA));
	}

	private EventoDTO novoEvento() {
		return service.salvarEvento(new EventoDTO(null, "Evento Teste", new Date(), usuarioTeste));
	}
}