package insper.exe_aula;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import insper.exe_aula.Jogador.Jogador;
import insper.exe_aula.Jogador.JogadorRepository;
import insper.exe_aula.Jogador.JogadorService;
import insper.exe_aula.campeonato.CampeonatoClient;

class JogadorServiceTests {

    @Mock
    private JogadorRepository jogadorRepository;

    @Mock
    private CampeonatoClient campeonatoClient;

    @InjectMocks
    private JogadorService jogadorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarJogador() {
        Jogador jogador = new Jogador("Neymar", 29);
        jogador.setId("1");

        when(jogadorRepository.save(any(Jogador.class))).thenReturn(jogador);

        Jogador resultado = jogadorService.salvarJogador(jogador);

        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
        assertEquals("Neymar", resultado.getNome());
        assertEquals(29, resultado.getIdade());
        verify(jogadorRepository).save(jogador);
    }

    @Test
    void testListarTodosJogadores() {
        Jogador jogador1 = new Jogador("Neymar", 29);
        jogador1.setId("1");

        Jogador jogador2 = new Jogador("Messi", 34);
        jogador2.setId("2");

        List<Jogador> jogadores = Arrays.asList(jogador1, jogador2);

        when(jogadorRepository.findAll()).thenReturn(jogadores);

        List<Jogador> resultado = jogadorService.listarTodosJogadores();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Neymar", resultado.get(0).getNome());
        assertEquals("Messi", resultado.get(1).getNome());
        verify(jogadorRepository).findAll();
    }

    @Test
    void testAdicionarTimeAoJogador_JogadorNaoEncontrado() {
        when(jogadorRepository.findById("1")).thenReturn(Optional.empty());

        String resultado = jogadorService.adicionarTimeAoJogador("1", "timeId");

        assertEquals("Jogador não encontrado.", resultado);
        verify(jogadorRepository).findById("1");
        verifyNoInteractions(campeonatoClient);
    }

    @Test
    void testAdicionarTimeAoJogador_TimeNaoEncontrado() {
        Jogador jogador = new Jogador("Neymar", 29);
        jogador.setId("1");

        when(jogadorRepository.findById("1")).thenReturn(Optional.of(jogador));
        when(campeonatoClient.timeExiste("timeId")).thenReturn(false);

        String resultado = jogadorService.adicionarTimeAoJogador("1", "timeId");

        assertEquals("Time não encontrado na API de Campeonato.", resultado);
        verify(jogadorRepository).findById("1");
        verify(campeonatoClient).timeExiste("timeId");
        verifyNoMoreInteractions(jogadorRepository);
    }

    @Test
    void testAdicionarTimeAoJogador_Sucesso() {
        Jogador jogador = new Jogador("Neymar", 29);
        jogador.setId("1");

        when(jogadorRepository.findById("1")).thenReturn(Optional.of(jogador));
        when(campeonatoClient.timeExiste("timeId")).thenReturn(true);
        when(jogadorRepository.save(any(Jogador.class))).thenReturn(jogador);

        String resultado = jogadorService.adicionarTimeAoJogador("1", "timeId");

        assertEquals("Time adicionado ao jogador com sucesso.", resultado);
        assertTrue(jogador.getTimes().contains("timeId"));
        verify(jogadorRepository).findById("1");
        verify(campeonatoClient).timeExiste("timeId");
        verify(jogadorRepository).save(jogador);
    }
}
