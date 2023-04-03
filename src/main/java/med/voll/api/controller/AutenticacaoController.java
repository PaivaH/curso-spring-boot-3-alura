package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioDto;
import med.voll.api.infra.security.TokenJWTDto;
import med.voll.api.infra.security.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/signup")
    public ResponseEntity efeturLogin(@RequestBody @Valid UsuarioDto dto) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());

        Authentication authentication = manager.authenticate(authToken);

        var JWTToken = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJWTDto(JWTToken));
    }

    // @PostMapping("/signin")
    // public ResponseEntity criarConta(@RequestBody @Valid UsuarioDto dto) {
    //     UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());

    //     Authentication authentication = manager.authenticate(authToken);

    //     var JWTToken = tokenService.gerarToken((Usuario) authentication.getPrincipal());

    //     return ResponseEntity.ok(new TokenJWTDto(JWTToken));
    // }
}
