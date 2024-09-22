package br.insper.prova.jogador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JogadorController {

    @Autowired
    private JogadorService jogadorService;

    @GetMapping("/jogadores")
    @ResponseBody
    public List<Jogador> listar() {
        return jogadorService.listar();
    }

    @PostMapping("/jogadores")
    @ResponseStatus(HttpStatus.CREATED)
    public Jogador salvar(@RequestBody Jogador jogador) {
        return jogadorService.salvar(jogador);
    }

    @PutMapping("/jogadores/{idJogador}/time/{idTime}")
    public Jogador addJogadorTime(@PathVariable String idJogador, @PathVariable Integer idTime) {
        return jogadorService.addJogadorTime(idJogador, idTime);
    }
}
