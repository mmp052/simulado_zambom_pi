package insper.exe_aula.Jogador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jogadores")
public class JogadorController {

    @Autowired
    private JogadorService jogadorService;

    // Rota para salvar um novo jogador
    @PostMapping
    public ResponseEntity<Jogador> criarJogador(@RequestBody Jogador jogador) {
        Jogador jogadorSalvo = jogadorService.salvarJogador(jogador);
        return ResponseEntity.ok(jogadorSalvo);
    }

    // Rota para listar todos os jogadores
    @GetMapping
    public List<Jogador> listarJogadores() {
        return jogadorService.listarTodosJogadores();
    }

    // Rota para adicionar um time ao jogador
    @PutMapping("/{id}/adicionar-time")
    public ResponseEntity<String> adicionarTime(@PathVariable String id, @RequestBody AdicionarTimeDTO adicionarTimeDTO) {
        String resultado = jogadorService.adicionarTimeAoJogador(id, adicionarTimeDTO.getTimeId());

        if (resultado.equals("Jogador não encontrado.") || resultado.equals("Time não encontrado na API de Campeonato.")) {
            return ResponseEntity.badRequest().body(resultado);
        }

        return ResponseEntity.ok(resultado);
    }
}
