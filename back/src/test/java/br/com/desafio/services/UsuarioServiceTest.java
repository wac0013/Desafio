package br.com.desafio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Base64Utils;

import br.com.desafio.models.documents.Usuario;
import br.com.desafio.models.dtos.NovoUsuarioDTO;
import br.com.desafio.models.dtos.UsuarioAuthDTO;
import br.com.desafio.repositories.UsuarioRepo;

@AutoConfigureDataMongo
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@Autowired
	private UsuarioService service;

	@Autowired
	private UsuarioRepo repo;

	private final String NOME_USUARIO = "teste";
	private final String EMAIL_USUARIO = "teste@teste";
	private final String SENHA = Base64Utils.encodeToString("senha".getBytes());

	@Test
	@DisplayName("Testar criação de usuários")
	public void criarUsuario() {
		novoUsuario();
		Usuario usuario = buscarUsuario();

		assertNotNull(usuario, "usuário não cadastrado");
		assertNotNull(usuario.getId(), "ID do usuário não esperado");
		assertEquals(usuario.getNomeUsuario(), NOME_USUARIO, "Nome de usuário não esperado");
	}

	@Test
	@DisplayName("Testar autenticacao por email")
	public void autenticarEmail() {
		novoUsuario();
		var token = service.autenticar(new UsuarioAuthDTO(null, EMAIL_USUARIO, SENHA));

		assertNotNull(token, "token não pode ser criado");
		assertFalse(token.isBlank());
	}

	@Test
	@DisplayName("Testar autenticacao por nome de usuário")
	public void autenticarNomeUsuario() {
		novoUsuario();
		var token = service.autenticar(new UsuarioAuthDTO(NOME_USUARIO, null, SENHA));

		assertNotNull(token, "token não pode ser criado");
		assertFalse(token.isBlank());
	}

	private void novoUsuario() {
		service.criarUsuario(new NovoUsuarioDTO("Usuário de Testes", NOME_USUARIO, EMAIL_USUARIO, SENHA));
	}

	private Usuario buscarUsuario() {
		return repo.findByNomeUsuario(NOME_USUARIO).findFirst().orElse(null);
	}
}