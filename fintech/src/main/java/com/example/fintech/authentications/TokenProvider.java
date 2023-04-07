package com.example.fintech.authentications;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenProvider {

    @Autowired
    TokenKeyProvider tokenKeyProvider;

    @Value("${authentication.token.jwt.expire}")
    private int tokenTimeout;

    @Value("${authentication.token.jwt.oauth-expire}")
    private int oauthTimeout;

    @SuppressWarnings("unused")
    private static final Logger loggers = LoggerFactory.getLogger(TokenProvider.class);

    @SneakyThrows
    public UserPrincipal getUserPrincipalFromJWT(String token) {
        Claims claims = this.decodeJWT(token);
        @SuppressWarnings("unchecked")
        List<GrantedAuthority> authorities = ((List<String>) claims.get("roles")).stream().map((scope) -> new SimpleGrantedAuthority(scope)).collect(Collectors.toList());

        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setAuthorities(authorities);
        userPrincipal.setId(claims.getId());
        userPrincipal.setName((String) claims.get("name"));
        userPrincipal.setEmail((String) claims.get("email"));
        userPrincipal.setId(claims.getSubject());
        return userPrincipal;
    }

    public Claims decodeJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(tokenKeyProvider.getPublicKey())
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    @SneakyThrows
    public String issueToken(UserPrincipal userPrincipal)  {
        Claims claims = Jwts.claims().setSubject(userPrincipal.getId());
        claims.put("roles",userPrincipal.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));
        claims.put("name", userPrincipal.getName());
        claims.put("email", userPrincipal.getEmail());
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date(System.currentTimeMillis() + (tokenTimeout * 1000)));

        String token = Jwts.builder()
                .setClaims(claims)
                //.setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.RS256, tokenKeyProvider.getPrivateKey())
                .compressWith(CompressionCodecs.GZIP)
                .compact();

       return token;
    }

    @SneakyThrows
    public String oauthToken(UserPrincipal userPrincipal)  {

        Claims claims = Jwts.claims().setSubject(userPrincipal.getId());
        claims.put("roles",userPrincipal.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));
        claims.put("name", userPrincipal.getName());
        claims.put("email", userPrincipal.getEmail());
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date(System.currentTimeMillis() + (oauthTimeout * 1000)));

        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.RS256, tokenKeyProvider.getPrivateKey())
                .compressWith(CompressionCodecs.GZIP)
                .compact();

        return token;
    }
}
