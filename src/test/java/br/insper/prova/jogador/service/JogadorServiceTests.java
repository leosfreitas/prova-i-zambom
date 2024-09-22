package br.insper.prova.jogador.service;

import br.insper.prova.jogador.Jogador;
import br.insper.prova.jogador.JogadorRepository;
import br.insper.prova.jogador.JogadorService;
import br.insper.prova.time.RetornarTimeDTO;
import br.insper.prova.time.TimeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class JogadorServiceTests {

    @InjectMocks
    private JogadorService jogadorService;

    @Mock
    private JogadorRepository jogadorRepository;

    @Mock
    private TimeService timeService;


    @Test
    public void TestCadastrarJogadorComTime() {
        Jogador jogador = new Jogador();
        jogador.setNome("Matheus Pereira");
        jogador.setIdade(26);

        ArrayList<String> times = new ArrayList<>();
        times.add("CRU");
        jogador.setTimes(times);

        Mockito.when(jogadorRepository.save(Mockito.any(Jogador.class))).thenReturn(jogador);

        Jogador retorno = jogadorService.salvar(jogador);

        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(26, retorno.getIdade());
        Assertions.assertEquals("Matheus Pereira", retorno.getNome());
        Assertions.assertEquals(times, retorno.getTimes());
    }

    @Test
    public void TestCadastrarJogadorTimeNull() {
        Jogador jogador = new Jogador();
        jogador.setNome("Matheus Pereira");
        jogador.setIdade(26);
        jogador.setTimes(null);

        ArrayList<String> times = new ArrayList<>();

        Mockito.when(jogadorRepository.save(Mockito.any(Jogador.class))).thenReturn(jogador);

        Jogador retorno = jogadorService.salvar(jogador);

        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(26, retorno.getIdade());
        Assertions.assertEquals("Matheus Pereira", retorno.getNome());
        Assertions.assertEquals(times, retorno.getTimes());
    }


    @Test
    public void testListarJogadores() {
        Jogador jogador = new Jogador();
        jogador.setNome("Matheus Pereira");
        jogador.setIdade(26);

        ArrayList<String> times = new ArrayList<>();
        times.add("CRU");
        jogador.setTimes(times);

        List<Jogador> jogadores = new ArrayList<>();
        jogadores.add(jogador);

        Mockito.when(jogadorRepository.findAll()).thenReturn(jogadores);

        List<Jogador> resultado = jogadorService.listar();

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals("Matheus Pereira", resultado.getFirst().getNome());
        Assertions.assertEquals(26, resultado.getFirst().getIdade());
        Assertions.assertEquals(times, resultado.getFirst().getTimes());
    }

    @Test
    public void addJogadorTime() {
        Jogador jogador = new Jogador();
        jogador.setId("1");
        jogador.setTimes(new ArrayList<String>());

        Mockito.when(jogadorRepository.findById("1")).thenReturn(java.util.Optional.of(jogador));

        RetornarTimeDTO timeDTO = new RetornarTimeDTO();
        ResponseEntity<RetornarTimeDTO> responseEntity = new ResponseEntity<>(timeDTO, HttpStatus.OK);
        Mockito.when(timeService.getTime(1)).thenReturn(responseEntity);

        jogadorService.addJogadorTime("1", 1);

        Assertions.assertEquals(1, jogador.getTimes().size());
    }

    @Test
    public void addJogadorTimeJogadorNaoEncontrado() {
        Mockito.when(jogadorRepository.findById("1")).thenReturn(java.util.Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> {
            jogadorService.addJogadorTime("1", 1);
        });
    }


    @Test
    public void addJogadorTimeTimeNaoEncontrado() {
        Jogador jogador = new Jogador();
        jogador.setId("1");
        jogador.setTimes(new ArrayList<String>());

        Mockito.when(jogadorRepository.findById("1")).thenReturn(java.util.Optional.of(jogador));

        RetornarTimeDTO timeDTO = new RetornarTimeDTO();
        ResponseEntity<RetornarTimeDTO> responseEntity = new ResponseEntity<>(timeDTO, HttpStatus.NOT_FOUND);
        Mockito.when(timeService.getTime(1)).thenReturn(responseEntity);

        Assertions.assertThrows(RuntimeException.class, () -> {
            jogadorService.addJogadorTime("1", 1);
        });
    }

}
