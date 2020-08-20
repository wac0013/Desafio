package br.com.desafio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter @Setter
public class ConfigProperties {

	private JWT jwt = new JWT();

	@Getter @Setter
	public static class JWT {
		/**
		 * Chave de segurança para uso do JWT
		 */
		private String secret = "CH4V3$uP3$3Cr3T4";
		/**
		 * Tempo de expiração para token JWT em milisegundos <br>
		 * <strong>Default:</strong> 1000 * 60 * 15 = 900.000 milisegundos ou 15 minutos
		 */
		private int validateTime = 1000 * 60 * 15;
	}
}
