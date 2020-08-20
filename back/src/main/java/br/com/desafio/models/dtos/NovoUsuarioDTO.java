package br.com.desafio.models.dtos;

import java.io.Serializable;

import br.com.desafio.models.documents.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class NovoUsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private String nomeUsuario;
	private String email;
	private String senhaHash;

	public Usuario toUsuario() {
		return Usuario.builder()
			.nome(nome)
			.nomeUsuario(nomeUsuario)
			.email(email)
			.senhaHash(senhaHash)
			.build();
	}
}