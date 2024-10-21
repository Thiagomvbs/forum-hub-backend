package br.com.forum_hub.domain.usuario;

import br.com.forum_hub.domain.perfil.DadosPerfil;
import br.com.forum_hub.domain.perfil.PerfilNome;
import br.com.forum_hub.domain.perfil.PerfilRepository;
import br.com.forum_hub.infra.email.EmailService;
import br.com.forum_hub.infra.exception.RegraDeNegocioException;
import br.com.forum_hub.infra.seguranca.HierarquiaService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PerfilRepository perfilRepository;
    private final HierarquiaService hierarquiaService;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, EmailService emailService, PerfilRepository perfilRepository, HierarquiaService hierarquiaService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.perfilRepository = perfilRepository;
        this.hierarquiaService = hierarquiaService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailIgnoreCaseAndVerificadoTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    public Usuario buscarPeloNomeUsuario(String nomeUsuario){
        return usuarioRepository.findByEmailIgnoreCaseAndVerificadoTrueAndAtivoTrue(nomeUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado: " + nomeUsuario));
    }

    @Transactional
    public Usuario cadastrar(DadosCadastroUsuario dados) {
         var senhaEncriptografada= passwordEncoder.encode(dados.senha());

         var perfil = perfilRepository.findByNome(PerfilNome.ESTUDANTE);
         var usuario = new Usuario(dados, senhaEncriptografada, perfil);

         emailService.enviarEmailVerificacao(usuario);
         return  usuarioRepository.save(usuario);
    }

    @Transactional
    public void verificarEmail(String codigo) {
        var usuario = usuarioRepository.findByToken(codigo)
                .orElseThrow(() -> new RegraDeNegocioException("Código de verificação inválido"));
        usuario.verificar();
    }

    @Transactional
    public Usuario editarPerfil(DadosEdicaoUsuario dados, Usuario logado) {
        var usuario = usuarioRepository.findById(logado.getId()).orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));
        usuario.atualizarInformacoes(dados);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(DadosAlteracaoSenha dados, Usuario logado) {
        if (!passwordEncoder.matches(dados.senhaAtual(), logado.getPassword())) {
            throw new RegraDeNegocioException("Senha atual incorreta");
        }
        if (!dados.novaSenha().equals(dados.novaSenhaConfirmacao())) {
            throw new RegraDeNegocioException("A nova senha e a confirmação de senha não correspondem");
        }
        String senhaCriptografada = passwordEncoder.encode(dados.novaSenha());
        logado.alterarSenha(senhaCriptografada);
        usuarioRepository.save(logado);
    }

    @Transactional
    public void desativarConta(Usuario logado) {
        logado.desativar();
    }

    @Transactional
    public Usuario adicionarPerfil(Long id, DadosPerfil dados) {
        var usuario = usuarioRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));
        var perfil = perfilRepository.findByNome(dados.perfilNome());
        usuario.adicionarPerfil(perfil);
        return usuario;
    }

    public Usuario removerPerfil(Long id, DadosPerfil dados) {
        var usuario = usuarioRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));
        var perfil = perfilRepository.findByNome(dados.perfilNome());
        usuario.removerPerfil(perfil);
        return usuario;
    }

    @Transactional
    public void reativarUsuario(Long id){
        var usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.reativar();
    }
}
