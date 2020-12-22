package com.sw1tech.app.user;

import static com.sw1tech.app.configs.SecurityConstants.HEADER_STRING;
import static com.sw1tech.app.configs.SecurityConstants.SECRET;
import static com.sw1tech.app.configs.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sw1tech.app.exceptions.ResourceNotFoundException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class UserAuthorizationFilter extends BasicAuthenticationFilter {


	public UserAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException  {
		String header = req.getHeader(HEADER_STRING);
		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}

		try {
			//doValidaToken(req, res, chain);
			UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (JwtException e) {
			throw new ResourceNotFoundException(e.getMessage());
		} finally {
			chain.doFilter(req, res);
		}


/* 		if (doValidaToken(req, chain) != ""){
			chain.doFilter(req, res);
			return;
		};
*/
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);

		if (token != null) {
			Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET.getBytes())
			.parseClaimsJws(token.replace(TOKEN_PREFIX, ""));

			String user = claims.getBody().getSubject();

			var authoritie =  AuthorityUtils
				.commaSeparatedStringToAuthorityList(claims.getBody().get("authorities").toString());

			Date dhExpiracao = claims.getBody().getExpiration();
			if (dhExpiracao.before(new Date())) {
				return null;
			};

			if (user != null) {
				UsernamePasswordAuthenticationToken _usr = new UsernamePasswordAuthenticationToken(user, null, authoritie);
				return _usr;
			}								

			return null;
		}
		return null;
	}
/*
	private void doValidaToken(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String token = req.getHeader(HEADER_STRING).replace(TOKEN_PREFIX, "");
	//	String error = "";
		Jwts.parser().setSigningKey(SECRET.getBytes())
		.parseClaimsJws(token.replace(TOKEN_PREFIX, ""));

 		try {
		} catch (UnsupportedJwtException e) {
			//throw new AcessoNegadoException("Token não suportado.!");
			error = "Token não suportado.!";
		}catch (SignatureException e) {
			//throw new AcessoNegadoException("Assinatura não suportada.!");
			error = "Assinatura não suportada.!";
		}catch (MalformedJwtException e) {
			//throw new AcessoNegadoException("Token mal informado.!");
			error = "Token mal informado.!";
		}catch (ExpiredJwtException e) {
			//throw new RuntimeException(e);
			error = "Token expirado .!";
			//AcessoNegadoException("Token expirado .!");
		}
		if (error != ""){
			chain.doFilter(req, res);
			//throw new AcessoNegadoException(error);
		}
 }*/

}
