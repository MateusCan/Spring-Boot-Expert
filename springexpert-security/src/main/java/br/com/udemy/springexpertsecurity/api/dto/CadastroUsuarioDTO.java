package br.com.udemy.springexpertsecurity.api.dto;

import br.com.udemy.springexpertsecurity.domain.entity.Usuario;
import lombok.Data;

import java.util.List;

@Data
public class CadastroUsuarioDTO {
    private Usuario usuario;
    private List<String> permissoes;
}
