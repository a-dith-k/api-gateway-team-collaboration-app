package site.adithk.apigatewayservice.services.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.util.Date;




@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

	private static final long EXPIRATION_TIME=1000*60*60*24*5L;
//	private static final SecretKey key = Jwts.SIG.HS384.key().build();

	private static final String SECRET_KEY="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

	private SecretKey getSignKey(){
//		return key;
		byte[] keyBites= Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBites);
	}

	@Override
	public void validateToken(String token) throws JwtException {
			JwtParser parser=Jwts.parser().verifyWith(getSignKey()).build();
//			if(isTokenExpired(token))
//					throw new JwtException("Token expired");

			Jws<Claims> claims=parser.parseSignedClaims(token);
	}

	public boolean isTokenExpired(String token) {
			JwtParser parser=Jwts.parser().verifyWith(getSignKey()).build();
		Jws<Claims> claims=parser.parseSignedClaims(token);
		return claims.getPayload().getExpiration().before(new Date(System.currentTimeMillis()));
	}


}
