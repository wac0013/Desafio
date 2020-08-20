package br.com.desafio.models.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Document(collection = "usuario")
@Getter 
@Builder
@EqualsAndHashCode @ToString
public class Usuario {
    @Id
    private String id;

    @Setter
    private String nome;

    @Indexed(unique=true)
    @Setter @NotNull
    private String nomeUsuario;

    @Indexed(unique=true)
    @Setter @NotNull
    private String email;

    @Setter @NotNull
    private String senhaHash;
}