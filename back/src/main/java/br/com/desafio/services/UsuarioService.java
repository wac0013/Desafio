package br.com.desafio.services;

import br.com.desafio.models.dtos.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafio.exceptions.DesafioException;
import br.com.desafio.exceptions.DesafioNotFoundException;
import br.com.desafio.models.documents.Usuario;
import br.com.desafio.models.dtos.NovoUsuarioDTO;
import br.com.desafio.models.dtos.UsuarioAuthDTO;
import br.com.desafio.repositories.UsuarioRepo;
import br.com.desafio.utils.jwt.JWTUtils;

@Service
public class UsuarioService {
    @Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private UsuarioRepo usuarioRepo;

	public String autenticar(final UsuarioAuthDTO auth) {
		Usuario usuario;

		if (auth == null) {
			throw new DesafioException("Usuário não informado!");
		} else if (auth.getNome() != null && !auth.getNome().isBlank()) {
			usuario = usuarioRepo.findByNomeUsuario(auth.getNome())
					.findFirst()
					.orElseThrow(() -> new DesafioNotFoundException("Usuário não cadastrado!"));
		} else {
			usuario = usuarioRepo.findByEmail(auth.getEmail())
					.findFirst()
					.orElseThrow(() -> new DesafioNotFoundException("Usuário não cadastrado!"));
		}

		if (!usuario.getSenhaHash().equals(auth.getSenhaHash())) throw new DesafioException("Senha incorreta");

		return jwtUtils.criarToken(usuario.getEmail());
	}

	public void criarUsuario(NovoUsuarioDTO usuario) {
		usuarioRepo.insert(usuario.toUsuario());
	}

	public UsuarioDTO getUsuarioPorToken(String token) {
		String login = jwtUtils.getUsuarioFromToken(token);
		Usuario usuario = usuarioRepo.findByNomeUsuarioOrEmail(login, login).findFirst().orElse(null);
		return UsuarioDTO.fromUsuario(usuario);
	}
}