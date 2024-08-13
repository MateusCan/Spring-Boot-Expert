package br.com.udemy.springexpert.controller;

import br.com.udemy.springexpert.domain.entity.Usuario;
import br.com.udemy.springexpert.dto.CredenciaisDTO;
import br.com.udemy.springexpert.dto.TokenDTO;
import br.com.udemy.springexpert.exception.SenhaInvalidaException;
import br.com.udemy.springexpert.security.jwt.JwtService;
import br.com.udemy.springexpert.service.impl.UsuarioServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("inclui-usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvarUsuario(@RequestBody @Valid Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioServiceImpl.salvar(usuario);
    }

    @PostMapping("auth")
    public TokenDTO authenticar(@RequestBody CredenciaisDTO credenciaisDTO){
        try{
            Usuario usuario = Usuario.builder()
                    .login(credenciaisDTO.getLogin())
                    .senha(credenciaisDTO.getSenha())
                    .build();

            UserDetails usuarioAuthenticado = usuarioServiceImpl.authenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        }catch(UsernameNotFoundException | SenhaInvalidaException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
