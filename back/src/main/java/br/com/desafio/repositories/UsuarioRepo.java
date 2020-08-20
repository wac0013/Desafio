package br.com.desafio.repositories;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.desafio.models.documents.Usuario;

@Repository
public interface UsuarioRepo extends MongoRepository<Usuario, String>{
    
    Stream<Usuario> findByEmail(String email);

    Stream<Usuario> findByNomeUsuario(String userName);

    Stream<Usuario> findByNomeUsuarioOrEmail(String userName, String email);

}