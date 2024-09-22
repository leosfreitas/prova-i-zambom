package br.insper.prova.time;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TimeService {

    public ResponseEntity<RetornarTimeDTO> getTime(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(
                "http://localhost:8082/time/" + id,
                RetornarTimeDTO.class);
    }
}
