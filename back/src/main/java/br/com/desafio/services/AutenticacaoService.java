package br.com.desafio.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.desafio.exceptions.DesafioNotFoundException;
import br.com.desafio.repositories.UsuarioRepo;
import lombok.AllArgsConstructor;

@Service
public class AutenticacaoService {
	@Autowired
	private UsuarioRepo usuarioRepo;

	public UserDetails getUsuarioDetalhes(String login) {
		if (StringUtils.hasText(login)) {
			var usuario = usuarioRepo.findByNomeUsuarioOrEmail(login, login).findFirst()
					.orElseThrow(() -> new DesafioNotFoundException("Usuário não cadastrado!"));

			return new UserDetailsImp(usuario.getNomeUsuario(), usuario.getSenhaHash());
		} else {
			return null;
		}
	}

	@AllArgsConstructor
	private static class UserDetailsImp implements UserDetails {
		private static final long serialVersionUID = 1L;
		private String nomeUsuario;
		private String senha;

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return new ArrayList<>();
		}

		@Override
		public String getPassword() {
			return senha;
		}

		@Override
		public String getUsername() {
			return nomeUsuario;
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}
		
	}
}