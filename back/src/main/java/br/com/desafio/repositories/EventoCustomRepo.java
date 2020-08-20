package br.com.desafio.repositories;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.desafio.models.documents.Evento;

public interface EventoCustomRepo {

	Page<Evento> buscaPorFiltros(String nome, Date dataInicio, Date dataFim, String nomeUsuario, Pageable pag);
}