package br.com.desafio.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.desafio.exceptions.DesafioNotFoundException;
import br.com.desafio.models.documents.Evento;
import br.com.desafio.models.dtos.EventoDTO;
import br.com.desafio.repositories.EventoCustomRepo;
import br.com.desafio.repositories.EventoRepo;

@Service
public class EventoService {
	@Autowired
	private EventoRepo eventoRepo;

	@Autowired
	private EventoCustomRepo repo;

	public EventoDTO buscarEventoId(String id) {
		if (id == null) return null;

		var evento = eventoRepo.findById(id).orElse(null);
		
		return EventoDTO.fromEvento(evento);
	}

	/**
	 * Busca um lista do tipo Page<EventoDTO> de acordo com filtro, definições de páginas e
	 * ordenação
	 * 
	 * @param nome
	 * @param data
	 * @param nomeUsuario
	 * @param pagina
	 * @param totalItens
	 * @param campoOrdem
	 * @param decrecente
	 * @return
	 */
	public Page<EventoDTO> buscarEventos(String nome, Date inicio, Date fim, String nomeUsuario, int pagina, int totalItens, String campoOrdem, Boolean decrecente) {
		campoOrdem = StringUtils.hasText(campoOrdem) ? campoOrdem : "data";
		decrecente = decrecente != null ? decrecente : false;
		Sort sort = Sort.by(!decrecente ? Direction.ASC : Direction.DESC, campoOrdem);
		Pageable pageable = PageRequest.of(pagina, totalItens, sort);

		Page<Evento> pagesEventos = repo.buscaPorFiltros(nome, inicio, fim, nomeUsuario, pageable);

		return pagesEventos.map(EventoDTO::fromEvento);
	}

	public EventoDTO salvarEvento(EventoDTO evento){
		if (evento == null) return null;
		
		return EventoDTO.fromEvento(eventoRepo.save(evento.toEvento()));
	}

	public void deletarEvento(String id) {
		if (id == null) return;
		if (buscarEventoId(id) == null) throw new DesafioNotFoundException("Evento não encontrado!");

		eventoRepo.deleteById(id);
	}
}