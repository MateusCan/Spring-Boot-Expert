package br.com.udemy.springexpert.security.jwt;

import br.com.udemy.springexpert.VendasApplication;
import br.com.udemy.springexpert.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    private Key assinatura;

    @PostConstruct //Faz com que seja chamado o método logo após injetar as dependencias
    public void init(){
        byte[] chaveDecodificada = Base64.getDecoder().decode(chaveAssinatura);
        assinatura = Keys.hmacShaKeyFor(chaveDecodificada);
    }

    public String gerarToken(Usuario usuario) {
        long expString = Long.parseLong(expiracao);
        LocalDateTime expiracao = LocalDateTime.now().plusSeconds(expString);
        Date data =  Date.from(expiracao.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(usuario.getLogin())
                .setExpiration(data)
                .signWith(assinatura)
                .compact();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(assinatura)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValido(String token) {
        try{
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data = dataExpiracao.toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();
            return LocalDateTime.now().isBefore(data);
        }catch(Exception e){
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        return obterClaims(token).getSubject();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class, args);
        JwtService service = context.getBean(JwtService.class);
        Usuario usuario = Usuario.builder().login("fulano").build();
        String token = service.gerarToken(usuario);
        System.out.println(token);

        boolean isTokenValido = service.tokenValido(token);
        System.out.println("Token valido? "+isTokenValido);
        System.out.println(service.obterLoginUsuario(token));
    }
}
