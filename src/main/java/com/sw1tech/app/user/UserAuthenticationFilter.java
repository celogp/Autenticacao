package com.sw1tech.app.user;

import static com.sw1tech.app.configs.SecurityConstants.EXPIRATION_TIME;
import static com.sw1tech.app.configs.SecurityConstants.HEADER_STRING;
import static com.sw1tech.app.configs.SecurityConstants.SECRET;
import static com.sw1tech.app.configs.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public UserAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
												HttpServletResponse res) throws AuthenticationException {
		try {
			UserEntity creds = new ObjectMapper()
					.readValue(req.getInputStream(), UserEntity.class);
					
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							creds.getUsername(),
							creds.getPassword(),
							new ArrayList<>())

			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req,
											HttpServletResponse res,
											FilterChain chain,
											Authentication auth) throws IOException, ServletException {

		StringBuilder sbRoles = new StringBuilder();
		var roles = ((User) auth.getPrincipal()).getAuthorities();
		roles.forEach( (role) -> {
			sbRoles.append(role.getAuthority());
			sbRoles.append(",");
		});
										
		Claims claims = Jwts.claims().setSubject(((User) auth.getPrincipal()).getUsername());
		
		claims.put("authorities", sbRoles);
		String token = Jwts.builder()
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
				.compact();

		res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}
}