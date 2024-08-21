package br.com.udemy.springexpertsecurity.config;

import br.com.udemy.springexpertsecurity.domain.security.CustomAuthentication;
import br.com.udemy.springexpertsecurity.domain.security.IdentificacaoUsuario;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SenhaMasterAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var login = authentication.getName();
        var password = authentication.getCredentials().toString();

        String loginMaster = "master";
        String senhaMaster = "@321";

        if(login.equals(loginMaster) && password.equals(senhaMaster)) {
            IdentificacaoUsuario identificacaoUsuario = new IdentificacaoUsuario(
                    "Sou Master",
                    "Master",
                    loginMaster,
                    List.of("ADMIN"));
            return new CustomAuthentication(identificacaoUsuario);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
