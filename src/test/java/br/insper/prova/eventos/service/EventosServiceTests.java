package br.insper.prova.eventos.service;

import br.insper.prova.eventos.Eventos;
import br.insper.prova.eventos.EventosRepository;
import br.insper.prova.eventos.EventosService;
import br.insper.prova.usuarios.RetornarUsuariosDTO;
import br.insper.prova.usuarios.UsuariosService;
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
public class EventosServiceTests {

    @InjectMocks
    private EventosService eventosService;

    @Mock
    private EventosRepository eventosRepository;

    @Mock
    private UsuariosService usuariosService;


    @Test
    public void TestCadastrarEventoComPessoasUsuarioExiste() {
        RetornarUsuariosDTO usuario = new RetornarUsuariosDTO();
        usuario.setCpf("12345678901");

        Eventos eventos = new Eventos();
        eventos.setNome("Festa");
        eventos.setDescricao("Insper");
        eventos.setCpfCriador(usuario.getCpf());
        eventos.setMaxConvidados(10);


        ArrayList<String> pessoas = new ArrayList<>();
        pessoas.add("12345678901");
        eventos.setPessoas(pessoas);

        ResponseEntity<RetornarUsuariosDTO> responseEntity = new ResponseEntity<>(usuario, HttpStatus.OK);
        Mockito.when(usuariosService.getUsuario(Mockito.anyString())).thenReturn(responseEntity);

        Mockito.when(eventosRepository.save(Mockito.any(Eventos.class))).thenReturn(eventos);

        Eventos retorno = eventosService.salvar(eventos);

        Assertions.assertNotNull(retorno);
        Assertions.assertEquals("Festa", retorno.getNome());
        Assertions.assertEquals("Insper", retorno.getDescricao());
        Assertions.assertEquals("12345678901", retorno.getCpfCriador());
        Assertions.assertEquals(10, retorno.getMaxConvidados());
        Assertions.assertEquals(pessoas, retorno.getPessoas());
    }

    @Test
    public void testListarEventosSemFiltro() {
        Eventos eventos = new Eventos();
        eventos.setNome("Festa");
        eventos.setDescricao("Insper");
        eventos.setCpfCriador("12345678901");
        eventos.setMaxConvidados(10);
        eventos.setPessoas(new ArrayList<>());

        List<Eventos> eventos1 = new ArrayList<>();
        eventos1.add(eventos);

        Mockito.when(eventosRepository.findAll()).thenReturn(eventos1);

        List<Eventos> resultado = eventosService.listar(null);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals("Festa", resultado.get(0).getNome());
        Assertions.assertEquals("Insper", resultado.get(0).getDescricao());
        Assertions.assertEquals(eventos.getPessoas(), resultado.get(0).getPessoas());
    }

    @Test
    public void testListarEventosComFiltro() {
        Eventos eventos = new Eventos();
        eventos.setNome("Festa");
        eventos.setDescricao("Insper");
        eventos.setCpfCriador("12345678901");
        eventos.setMaxConvidados(10);
        eventos.setPessoas(new ArrayList<>());

        List<Eventos> eventosFiltrados = new ArrayList<>();
        eventosFiltrados.add(eventos);

        Mockito.when(eventosRepository.findByNome("Festa")).thenReturn(eventosFiltrados);

        List<Eventos> resultado = eventosService.listar("Festa");

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals("Festa", resultado.get(0).getNome());
        Assertions.assertEquals("Insper", resultado.get(0).getDescricao());
        Assertions.assertEquals("12345678901", resultado.get(0).getCpfCriador());
        Assertions.assertEquals(10, resultado.get(0).getMaxConvidados());
        Assertions.assertEquals(new ArrayList<>(), resultado.get(0).getPessoas());
    }

    @Test
    public void addUsuarioEvento() {
        Eventos eventos = new Eventos();
        eventos.setNome("Festa");
        eventos.setId("1");
        eventos.setDescricao("Insper");
        eventos.setCpfCriador("12345678901");
        eventos.setMaxConvidados(10);
        eventos.setPessoas(new ArrayList<>());

        RetornarUsuariosDTO usuario = new RetornarUsuariosDTO();
        usuario.setCpf("12345678901");

        ResponseEntity<RetornarUsuariosDTO> responseEntity = new ResponseEntity<>(usuario, HttpStatus.OK);
        Mockito.when(usuariosService.getUsuario(Mockito.anyString())).thenReturn(responseEntity);

        Mockito.when(eventosRepository.findById("1")).thenReturn(java.util.Optional.of(eventos));

        eventosService.addUsuariosEventos("1", "12345678901");

        Assertions.assertEquals(1, eventos.getPessoas().size());
    }
}
