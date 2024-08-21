package br.com.udemy.springexpertsecurity.api;

import br.com.udemy.springexpertsecurity.domain.entity.Grupo;
import br.com.udemy.springexpertsecurity.domain.repository.GrupoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
@RequiredArgsConstructor
public class GrupoController {

    private final GrupoRepository grupoRepository;

    @PostMapping("/incluir-grupo")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Grupo> salvar(@RequestBody Grupo grupo) {
        grupoRepository.save(grupo);
        return ResponseEntity.ok(grupo);
    }

    @GetMapping("/listar-grupo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Grupo>> listar() {
        return ResponseEntity.ok(grupoRepository.findAll());
    }
}
