package br.com.desafio.models.dtos;

import java.io.Serializable;

import br.com.desafio.models.documents.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String nome;
	private String email;

	public Usuario toUsuario() {
		return Usuario.builder()
					.nome(nome)
					.email(email)
					.id(id)
					.build();
	}

	public static UsuarioDTO fromUsuario(Usuario usuario) {
		if (usuario == null) return null;

		return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
	}
}