package insper.exe_aula.Jogador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import insper.exe_aula.campeonato.CampeonatoClient;

@Service
public class JogadorService {

    @Autowired
    private JogadorRepository jogadorRepository;

    @Autowired
    private CampeonatoClient campeonatoClient;

    // Salvar novo jogador
    public Jogador salvarJogador(Jogador jogador) {
        return jogadorRepository.save(jogador);
    }

    // Listar todos os jogadores
    public List<Jogador> listarTodosJogadores() {
        return jogadorRepository.findAll();
    }

    // Adicionar um time ao jogador
    public String adicionarTimeAoJogador(String jogadorId, String timeId) {
        Optional<Jogador> jogadorOptional = jogadorRepository.findById(jogadorId);

        if (jogadorOptional.isEmpty()) {
            return "Jogador não encontrado.";
        }

        Jogador jogador = jogadorOptional.get();

        // Verifica se o time existe na API do Campeonato
        boolean timeExiste = campeonatoClient.timeExiste(timeId);
        if (!timeExiste) {
            return "Time não encontrado na API de Campeonato.";
        }

        // Adiciona o time à lista do jogador
        jogador.getTimes().add(timeId);
        jogadorRepository.save(jogador);

        return "Time adicionado ao jogador com sucesso.";
    }
}

