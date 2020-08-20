package br.com.desafio.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.Builder;
import lombok.Getter;

@ControllerAdvice
@RestController
public class DesafioExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErroDetalhe> handleAllExceptions(Exception ex, WebRequest request) {
		var erroBody = ErroDetalhe.builder()
			.message(ex.getMessage())
			.mensagemDetalhada(ex.getLocalizedMessage())
			.caminho(((ServletWebRequest)request).getRequest().getRequestURI())
			.build();
		return new ResponseEntity<>(erroBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DesafioException.class)
	public final ResponseEntity<ErroDetalhe> handleDesafioExceptions(DesafioException ex, WebRequest request) {
		var erroBody = ErroDetalhe.builder()
			.message(ex.getMessage())
			.mensagemDetalhada(ex.getMensagemDetalhada())
			.caminho(((ServletWebRequest)request).getRequest().getRequestURI())
			.build();
		return new ResponseEntity<>(erroBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DesafioNotFoundException.class)
	public final ResponseEntity<ErroDetalhe> handleDesafioExceptions(DesafioNotFoundException ex, WebRequest request) {
		var erroBody = ErroDetalhe.builder()
			.message(ex.getMessage())
			.mensagemDetalhada(ex.getMensagemDetalhada())
			.caminho(((ServletWebRequest)request).getRequest().getRequestURI())
			.build();
		return new ResponseEntity<>(erroBody, HttpStatus.NOT_FOUND);
	}

	@Getter
	@Builder
	private static class ErroDetalhe {
		private String message;
		private String mensagemDetalhada;
		private String caminho;
		@Builder.Default
		private Date dataHora = new Date();
	}
}