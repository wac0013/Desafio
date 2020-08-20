package br.com.desafio.models.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UsuarioAuthDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nome;
    private String email;
    private String senhaHash;
}