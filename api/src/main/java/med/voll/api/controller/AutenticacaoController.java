package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.domain.usuarios.DadosAutenticacao;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired // Não da pra injetar ele diretamente precisa ensinar o spring a fazer isso
    private AuthenticationManager manager;

    @Autowired
    private TokenServico tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
        var authenticationtoken = new UsernamePasswordAuthenticationToken(dados.login(),dados.senha());
        var authentication = manager.authenticate(authenticationtoken);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

}
