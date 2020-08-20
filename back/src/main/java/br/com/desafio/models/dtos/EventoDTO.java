package br.com.desafio.models.dtos;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.desafio.models.documents.Evento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String nome;
	//@JsonFormat(pattern = "dd/MM/yyyy")
	private Date data;
	private UsuarioDTO usuario;

	public Evento toEvento() {
		return Evento.builder().nome(nome).usuario(usuario.toUsuario()).id(id).data(data).build();
	}

	public static EventoDTO fromEvento(Evento evento) {
		if (evento == null)
			return null;

		return new EventoDTO(evento.getId(), evento.getNome(), evento.getData(),
				UsuarioDTO.fromUsuario(evento.getUsuario()));
	}
}