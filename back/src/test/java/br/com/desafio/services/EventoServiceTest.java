package br.com.desafio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Base64Utils;

import br.com.desafio.models.documents.Evento;
import br.com.desafio.models.documents.Usuario;
import br.com.desafio.models.dtos.EventoDTO;
import br.com.desafio.models.dtos.UsuarioDTO;
import br.com.desafio.repositories.EventoRepo;
import br.com.desafio.repositories.UsuarioRepo;

@AutoConfigureDataMongo
@SpringBootTest
@ActiveProfiles("test")
public class EventoServiceTest {

	@Autowired
	private EventoService service;

	@Autowired
	private EventoRepo repo;

	@Autowired
	private UsuarioRepo usuarioRepo;

	private final String NOME_USUARIO = "teste";
	private final String EMAIL_USUARIO = "teste@teste";
	private final String SENHA = Base64Utils.encodeToString("senha".getBytes());

	private final String NOME_EVENTO = "Evento Teste";

	@Test
	@DisplayName("Testar criação de eventos")
	public void criarEvento() {
		Usuario usuario = novoUsuario();
		EventoDTO evento = service
				.salvarEvento(new EventoDTO(null, "Teste", new Date(), UsuarioDTO.fromUsuario(usuario)));

		assertNotNull(evento, "evento não cadastrado");
		assertNotNull(evento.getId(), "ID do evento não esperado");
		assertEquals(evento.getNome(), "Teste", "Nome do evento não esperado");
		assertEquals(evento.getUsuario(), UsuarioDTO.fromUsuario(usuario), "usuario não esperado");
	}

	@Test
	@DisplayName("Testar atualizar de eventos")
	public void atualizarEvento() {
		final String nomeTeste = "Teste 1";
		Evento evento = novoEvento();
		evento.setNome(nomeTeste);
		EventoDTO eventoAtualizado = service.salvarEvento(EventoDTO.fromEvento(evento));

		assertNotNull(eventoAtualizado, "evento não cadastrado");
		assertNotNull(eventoAtualizado.getId(), "ID do evento não esperado");
		assertEquals(eventoAtualizado.getId(), evento.getId(), "ID do evento não esperado");
		assertEquals(eventoAtualizado.getNome(), nomeTeste, "Nome do evento não esperado");
	}

	@Test
	@DisplayName("Testar excluir eventos")
	public void excluirEvento() {
		Evento evento = novoEvento();
		service.deletarEvento(EventoDTO.fromEvento(evento).getId());
		evento = repo.findById(evento.getId()).orElse(null);

		assertNull(evento, "evento não excluído");
	}

	@Test
	@DisplayName("Testar buscar eventos por id")
	public void buscarEventoID() {
		Evento evento = novoEvento();
		EventoDTO dto = service.buscarEventoId(evento.getId());

		assertNotNull(dto, "evento não localizado");
		assertEquals(dto.getId(), evento.getId(), "ID do evento não esperado");
	}

	@Test
	@DisplayName("Testar buscar eventos por filtros")
	public void buscarEventoFiltros() {
		Evento evento = novoEvento();
		Page<EventoDTO> dto = service.buscarEventos(NOME_EVENTO, null, null, null, 0, 5, null, null);

		assertNotNull(dto, "eventos não localizado");
		assertNotNull(dto.get().findFirst().orElse(null), "eventos não localizado");
		assertEquals(dto.get().findFirst().orElse(null), EventoDTO.fromEvento(evento), "ID do evento não esperado");

		dto = service.buscarEventos("oto evento", null, null, null, 0, 5, null, null);

		assertNotNull(dto, "eventos não localizado");
		assertNull(dto.get().findFirst().orElse(null), "eventos não localizado");
	}

	private Evento novoEvento() {
		return repo.insert(Evento.builder().nome(NOME_EVENTO).data(new Date()).usuario(novoUsuario()).build());
	}

	private Usuario novoUsuario() {
		return usuarioRepo.insert(Usuario.builder().email(EMAIL_USUARIO).nome(NOME_USUARIO).senhaHash(SENHA).build());
	}
}