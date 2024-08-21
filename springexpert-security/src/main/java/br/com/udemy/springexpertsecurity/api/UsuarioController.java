package br.com.udemy.springexpertsecurity.api;

import br.com.udemy.springexpertsecurity.api.dto.CadastroUsuarioDTO;
import br.com.udemy.springexpertsecurity.domain.entity.Usuario;
import br.com.udemy.springexpertsecurity.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/incluir-usuario")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> save(@RequestBody CadastroUsuarioDTO body) {
        Usuario usuarioSalvo = usuarioService.salvar(body.getUsuario(), body.getPermissoes());
        return ResponseEntity.ok(usuarioSalvo);
    }
}
