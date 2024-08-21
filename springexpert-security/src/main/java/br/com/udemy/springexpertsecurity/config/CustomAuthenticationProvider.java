package br.com.udemy.springexpertsecurity.config;

import br.com.udemy.springexpertsecurity.domain.entity.Usuario;
import br.com.udemy.springexpertsecurity.domain.security.CustomAuthentication;
import br.com.udemy.springexpertsecurity.domain.security.IdentificacaoUsuario;
import br.com.udemy.springexpertsecurity.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String login = authentication.getName();
        String senha = authentication.getCredentials().toString();

        Usuario usuario = usuarioService.obterUsuarioComPermissoes(login);
        if (usuario != null) {
            boolean senhasBatem = passwordEncoder.matches(senha, usuario.getSenha());
            if (senhasBatem) {
                IdentificacaoUsuario identificacaoUsuario = new IdentificacaoUsuario(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getLogin(),
                        usuario.getPermissoes());
                return new CustomAuthentication(identificacaoUsuario);
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
