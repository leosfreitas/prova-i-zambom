package br.insper.prova.usuarios;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UsuariosService {

    public ResponseEntity<RetornarUsuariosDTO> getUsuario(String cpf) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(
                "http://184.72.80.215:8080/usuario/"+cpf,
                RetornarUsuariosDTO.class);
    }
}
