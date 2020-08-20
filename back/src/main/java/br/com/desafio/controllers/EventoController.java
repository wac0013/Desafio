package br.com.desafio.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import br.com.desafio.enums.DesafioHeaderEnum;
import br.com.desafio.models.dtos.EventoDTO;
import br.com.desafio.services.EventoService;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(path = "evento")
public class EventoController {
	@Autowired
	private EventoService eventoService;

	@PostMapping(path = "criar")
	@ResponseStatus(HttpStatus.CREATED)
	public EventoDTO criarEvento(@RequestBody EventoDTO evento) {
		return eventoService.salvarEvento(evento);
	}

	@PutMapping(path = "salvar")
	public EventoDTO atualizarEvento(@RequestBody EventoDTO evento) {
		return eventoService.salvarEvento(evento);
	}

	@DeleteMapping(path = "excluir")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirEvento(@RequestParam String id) {
		eventoService.deletarEvento(id);
	}

	@GetMapping(path = "buscar-eventos")
	public Page<EventoDTO> buscarEventos(@RequestParam(required = false) String nome, 
		@RequestParam(required = false) Date dataInicio, 
		@RequestParam(required = false) Date dataFim, 
		@RequestParam(required = false) String usuario, 
		HttpServletRequest request) {
		
		var pagina = request.getHeader(DesafioHeaderEnum.PAGINA.getValue()) != null ? Integer.parseInt(request.getHeader(DesafioHeaderEnum.PAGINA.getValue())): 0;
		var totalItens = request.getHeader(DesafioHeaderEnum.TAMANHO_PAGINA.getValue()) != null ?  Integer.parseInt(request.getHeader(DesafioHeaderEnum.TAMANHO_PAGINA.getValue())): 10;
		var campoOrdem = request.getHeader(DesafioHeaderEnum.CAMPO_ORDEM.getValue());
		var decrecente = Boolean.parseBoolean(request.getHeader(DesafioHeaderEnum.DECRESCENTE.getValue()));

		return eventoService.buscarEventos(nome, dataInicio, dataFim, usuario, pagina, totalItens, campoOrdem, decrecente);
	}

	@GetMapping(path = "buscar")
	public  EventoDTO buscarEvento(@RequestParam String id) {
		return eventoService.buscarEventoId(id);
	}
}