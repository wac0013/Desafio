package br.com.desafio.utils.jwt;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.desafio.config.ConfigProperties;
import br.com.desafio.exceptions.DesafioException;
import org.springframework.util.StringUtils;

@Component
public class JWTUtils {

	@Autowired
	private ConfigProperties prop;

	/**
	 * Cria token para o email fornecido como parâmetro
	 * @param eamil
	 * @return
	 * @throws DesafioException
	 */
	public String criarToken(String eamil) {
		String token = "";
		Date dataExpiracao = new Date(System.currentTimeMillis() + prop.getJwt().getValidateTime());

		try {
			Algorithm algorithm = Algorithm.HMAC256(prop.getJwt().getSecret());
			token = JWT.create()
				.withIssuer(eamil)
				.withExpiresAt(dataExpiracao)
				.sign(algorithm);
		} catch (JWTCreationException e) {
			throw new DesafioException("Não foi possível gerar token", e);
		}
		return token;
	}

	/**
	 * Valida token fornecido, retornando <strong>true</strong> para tokens válidos e 
	 * <strong>false</strong> para tokens inválidos
	 * @param token
	 * @return
	 */
	public boolean validarToken(String token) {
		if (!StringUtils.hasText(token)) return false;

		try {
			Algorithm algorithm = Algorithm.HMAC256(prop.getJwt().getSecret());
			JWTVerifier verifier = JWT.require(algorithm).build();
			verifier.verify(token);
		} catch (TokenExpiredException e) {
			throw new DesafioException("Token expirado", e);
		} catch (JWTVerificationException e){
			throw new DesafioException("Não foi possível verificar token", e);
		}

		return true;
	}

	public String getUsuarioFromToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(prop.getJwt().getSecret());
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(token);
			return jwt.getIssuer();
		} catch (JWTVerificationException e){
			throw new DesafioException("Não foi possível verificar token", e);
		}
	}
}
