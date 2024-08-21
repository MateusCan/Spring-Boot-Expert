package br.com.udemy.springexpertsecurity.domain.service;

import br.com.udemy.springexpertsecurity.domain.entity.Grupo;
import br.com.udemy.springexpertsecurity.domain.entity.Usuario;
import br.com.udemy.springexpertsecurity.domain.entity.UsuarioGrupo;
import br.com.udemy.springexpertsecurity.domain.repository.GrupoRepository;
import br.com.udemy.springexpertsecurity.domain.repository.UsuarioGrupoRepository;
import br.com.udemy.springexpertsecurity.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final GrupoRepository grupoRepository;
    private final UsuarioGrupoRepository usuarioGrupoRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario salvar(Usuario usuario, List<String> grupos) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        usuarioRepository.save(usuario);

        List<UsuarioGrupo> listaUsuarioGrupo = grupos.stream().map(nomeGrupo -> {
            Optional<Grupo> possivelGrupo = grupoRepository.findByNome(nomeGrupo);
            if(possivelGrupo.isPresent()) {
                Grupo grupo = possivelGrupo.get();
                return new UsuarioGrupo(usuario,grupo);
            }
            return null;
        }).filter(grupo -> grupo!=null).collect(Collectors.toList());
        usuarioGrupoRepository.saveAll(listaUsuarioGrupo);
        return usuario;
    }

    public Usuario obterUsuarioComPermissoes(String login){
        Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(login);

        if (usuarioOptional.isEmpty()) {
            return null;}

        Usuario usuario = usuarioOptional.get();
        List<String> permissoes = usuarioGrupoRepository.findPermissoesByUsuario(usuario);
        usuario.setPermissoes(permissoes);
        return usuario;
    }
}
