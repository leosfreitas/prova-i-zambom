package br.insper.prova.jogador;

import br.insper.prova.time.RetornarTimeDTO;
import br.insper.prova.time.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JogadorService {

    @Autowired
    private JogadorRepository jogadorRepository;

    @Autowired
    private TimeService timeService;

    public Jogador salvar(Jogador jogador) {
        jogador.setId(UUID.randomUUID().toString());

        ArrayList<String> times = jogador.getTimes();
        if (times == null) {
            times = new ArrayList<>();
        }

        jogador.setTimes(times);
        return jogadorRepository.save(jogador);
    }

    public List<Jogador> listar() {
        return jogadorRepository.findAll();
    }

    public Jogador addJogadorTime(String id, Integer idTime) {
        Optional<Jogador> op = jogadorRepository.findById(id);
        if (op.isEmpty()) {
            throw new RuntimeException("Jogador não encontrado");
        }
        Jogador jogador = op.get();
        ResponseEntity<RetornarTimeDTO> time = timeService.getTime(idTime);
        if (time.getStatusCode().is2xxSuccessful()) {
            RetornarTimeDTO timeDTO = time.getBody();
            ArrayList<String> times = jogador.getTimes();
            times.add(timeDTO.getIdentificador());
            jogador.setTimes(times);
            return jogadorRepository.save(jogador);
        } else {
            throw new RuntimeException("Time não encontrado");
        }
    }
}
