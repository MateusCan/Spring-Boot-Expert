package br.com.udemy.springexpert.service.impl;

import br.com.udemy.springexpert.domain.entity.Usuario;
import br.com.udemy.springexpert.domain.repository.UsuarioRepository;
import br.com.udemy.springexpert.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    @Lazy
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public UserDetails authenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhas = encoder.matches(usuario.getSenha(), user.getPassword());
        if(senhas){
            return user;
        }
        throw new SenhaInvalidaException("Senha invalida");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));

        String[] roles = usuario.isAdmin() ? new String[] { "ADMIN" } : new String[] { "USER" };

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }
}
