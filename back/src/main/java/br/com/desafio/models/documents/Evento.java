package br.com.desafio.models.documents;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Builder
@Document(collection = "evento")
public class Evento {
	@Id
	private String id;

	@Setter @NotNull
	private String nome;

	@Setter @NotNull
	private Date data;

	@Setter
	private Usuario usuario;
}