package br.insper.prova.eventos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventosRepository extends MongoRepository<Eventos, String> {
    List<Eventos> findByNome(String nome);
}
