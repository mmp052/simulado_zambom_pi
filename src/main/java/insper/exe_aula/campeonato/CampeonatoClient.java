package insper.exe_aula.campeonato;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CampeonatoClient {

    private static final String CAMPEONATO_API_URL = "http://localhost:8080/time/";

    public boolean timeExiste(String timeId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = CAMPEONATO_API_URL + timeId;

        try {
            // Faz a requisição para verificar se o time existe
            ResponseEntity<Void> response = restTemplate.getForEntity(url, Void.class);

            // Verifica se a resposta foi OK (200)
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            // Caso aconteça algum erro na requisição, assumimos que o time não existe
            return false;
        }
    }
}
