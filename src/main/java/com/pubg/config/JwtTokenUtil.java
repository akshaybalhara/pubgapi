package com.pubg.config;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JwtTokenUtil is responsible for performing JWT operations like creation 
 * and validation.It makes use of the io.jsonwebtoken.Jwts for achieving this.
 * 
 * @author Prolifics
 *
 */
@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	/**
	 * secret will be injected by Spring Framework.
	 */
	@Value("${jwt.secret}")
	private String secret;
	/**
	 * tokenValidity will be injected by Spring Framework.
	 */
	@Value("${jwt.token.validity}")
	private long tokenValidity;

	/**
	 * getUsernameFromToken retrieve username from 
	 * specified jwt token
	 * 
	 * @param token
	 * 	<p>The JWT Token to read.</p>
	 * @return
	 * 	<p>The desired Username(Employeed Id in this case).</p>
	 */	
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	/**
	 * getExpirationDateFromToken retrieve expiration date from 
	 * specified jwt token.
	 * 
	 * @param token
	 * 	<p>The JWT Token to read.</p>
	 * @return
	 * 	<p>The desired Expiration Date.</p>
	 */	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	/**
	 * getClaimFromToken retrieves all Claims
	 * from the JWT Token.	
	 * 
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	/**
	 * getAllClaimsFromToken fetches the Claim 
	 * Body. The Secret key will be needed to
	 * get any information from token.
	 * @return
	 * 	<p>The desired Claims Body.</p>
	 */    
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * isTokenExpired checks whether a JWT Token is expired
	 * or is still active.
	 * 
	 * @param token
	 * 	<p>The input JWT Token.</p>
	 * @return
	 * 	<p>Status - true or false.</p>
	 */	
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
		//Rajeev: Possible enhancement to check if the User has logged out and its token is being used.
	}

	/**
	 * generateToken generates a Token for specified
	 * User Details.
	 * 
	 * @param userDetails
	 * 	<p>The Spring Security UserDetails instance.</p>
	 * @return
	 * 	<p>The desired Token.</p>
	 */	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}
	
	/**
	 * generateToken generates a Token for specified
	 * Employee Id.
	 * 
	 * @param employeeId
	 * 	<p>The Employee id of the User.</p>
	 * @return
	 * 	<p>The desired Token.</p>
	 */
	public String generateToken(String employeeId) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, employeeId);
	}
	 
	/**
	 * doGenerateToken generates the Token using the specified Subject
	 * and Claims data.
	 * 
	 * @param claims
	 * 	<p>The map of Claims Objects.</p>
	 * @param subject
	 * 	<p>The Subject for which Token needs to be generated.</p>
	 * @return
	 * 	<p>The desired JWT Token.</p>
	 */
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		//while creating the token -
		//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
		//2. Sign the JWT using the HS512 algorithm and secret key.
		//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
		//   compaction of the JWT to a URL-safe string
		long validityInMillis = tokenValidity * 60 * 60 * 1000; //Hours to msecs
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + validityInMillis))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	/**
	 * validateToken validates if the Token is valid for
	 * the specified User.
	 * 
	 * @param token
	 * 	<p>The JWT Token.</p>
	 * @param userDetails
	 * 	<p>The UserDetails instance.</p>
	 * @return
	 * 	<p>The Status - true or false.</p>
	 */	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}