package br.insper.prova.eventos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventosController {

    @Autowired
    private EventosService eventosService;

    @GetMapping("/eventos")
    @ResponseBody
    public List<Eventos> listar(@RequestParam(required = false) String nome) {
        return eventosService.listar(nome);
    }

    @PostMapping("/eventos")
    @ResponseStatus(HttpStatus.CREATED)
    public Eventos salvar(@RequestBody Eventos eventos) {
        return eventosService.salvar(eventos);
    }

    @PutMapping("/eventos/{idEvento}/usuario/{cpf}")
    public Eventos addJogadorTime(@PathVariable String idEvento, @PathVariable String cpf) {
        return eventosService.addUsuariosEventos(idEvento, cpf);
    }
}
