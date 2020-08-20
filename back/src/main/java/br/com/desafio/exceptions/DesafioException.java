package br.com.desafio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class DesafioException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	@Getter
	private String mensagemDetalhada = "";

	public DesafioException(String mensagem) {
		super(mensagem);
	}

	public DesafioException(String mensagem, String detail) {
		super(mensagem);
		mensagemDetalhada = detail;
	}

	public DesafioException(String mensagem, Throwable t) {
		super(mensagem, t);
		
		while (t.getCause() != null) {
			mensagemDetalhada = t.getMessage() + System.lineSeparator() + mensagemDetalhada;
		}
	}
}