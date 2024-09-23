package br.insper.prova.eventos;

import br.insper.prova.usuarios.RetornarUsuariosDTO;
import br.insper.prova.usuarios.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventosService {

    @Autowired
    private EventosRepository eventosRepository;

    @Autowired
    private UsuariosService usuariosService;

    public Eventos salvar(Eventos eventos) {
        eventos.setId(UUID.randomUUID().toString());

        ResponseEntity<RetornarUsuariosDTO> usuario = usuariosService.getUsuario(eventos.getCpfCriador());
        if (usuario.getStatusCode().is2xxSuccessful()) {
            RetornarUsuariosDTO usuarioDTO = usuario.getBody();
            eventos.setCpfCriador(usuarioDTO.getCpf());
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }

        ArrayList<String> pessoas = eventos.getPessoas();
        if (pessoas == null) {
            pessoas = new ArrayList<>();
        }

        eventos.setPessoas(pessoas);
        return eventosRepository.save(eventos);
    }



    public List<Eventos> listar(String nome) {
        if (nome == null || nome.isEmpty()) {
            return eventosRepository.findAll();
        } else {
            return eventosRepository.findByNome(nome);
        }
    }

    public Eventos addUsuariosEventos(String id, String cpf) {
        Optional<Eventos> op = eventosRepository.findById(id);
        if (op.isEmpty()) {
            throw new RuntimeException("Evento não encontrado");
        }
        if (op.get().getPessoas().size() == op.get().getMaxConvidados()) {
            throw new RuntimeException("Evento lotado");
        }
        Eventos eventos = op.get();
        ResponseEntity<RetornarUsuariosDTO> usuario = usuariosService.getUsuario(cpf);
        if (usuario.getStatusCode().is2xxSuccessful()) {
            RetornarUsuariosDTO usuarioDTO = usuario.getBody();
            ArrayList<String> pessoas = eventos.getPessoas();
            pessoas.add(usuarioDTO.getCpf());
            eventos.setPessoas(pessoas);
            return eventosRepository.save(eventos);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }
}
