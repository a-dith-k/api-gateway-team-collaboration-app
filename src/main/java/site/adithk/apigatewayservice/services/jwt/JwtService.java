package site.adithk.apigatewayservice.services.jwt;


import io.jsonwebtoken.JwtException;

import java.security.Key;
import java.util.Map;


public interface JwtService {


	void validateToken(String token)throws JwtException;

	boolean isTokenExpired(String token);
} 