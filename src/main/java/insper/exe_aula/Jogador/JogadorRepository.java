package insper.exe_aula.Jogador;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface JogadorRepository extends MongoRepository<Jogador, String> {
    Optional<Jogador> findByNome(String nome);
}

