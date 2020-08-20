package br.com.desafio.repositories;

import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.desafio.models.documents.Evento;

@Repository
public interface EventoRepo extends MongoRepository<Evento, String>{

}