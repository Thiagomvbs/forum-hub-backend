package br.com.forum_hub.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import org.aspectj.weaver.ast.Not;

public record DadosAlteracaoSenha(
        @NotBlank
        String senhaAtual,
        @NotBlank
        String novaSenha,
        @NotBlank
        String novaSenhaConfirmacao
) {
}
