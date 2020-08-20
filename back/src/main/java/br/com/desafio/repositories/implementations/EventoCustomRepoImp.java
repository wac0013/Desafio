package br.com.desafio.repositories.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import br.com.desafio.models.documents.Evento;
import br.com.desafio.repositories.EventoCustomRepo;

@Component
public class EventoCustomRepoImp implements EventoCustomRepo {
	@Autowired
	private MongoTemplate mt;

	@Override
	public Page<Evento> buscaPorFiltros(String nome, Date inicio, Date fim, String nomeUsuario, Pageable pag) {
		final Query query = new Query();
		final List<Criteria> criteria = new ArrayList<>();
		
		if (nome != null) criteria.add(Criteria.where("nome").regex(nome + ".*", "i"));
		if (nomeUsuario != null) criteria.add(Criteria.where("usuario.nome").regex(nome + ".*", "i"));
		if (inicio != null ) criteria.add(Criteria.where("data").gte(inicio));
		if (fim != null ) criteria.add(Criteria.where("data").lte(fim));

		if (pag == null) pag = PageRequest.of(0, 10);
		if (!criteria.isEmpty()) query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
		
		query.with(pag.getSort());
		List<Evento> enventos =  mt.find(query, Evento.class);
		
		return PageableExecutionUtils.getPage(enventos, pag,
				() -> mt.count(query, Evento.class));
	}
    
}