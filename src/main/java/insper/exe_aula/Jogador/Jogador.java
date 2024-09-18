package insper.exe_aula.Jogador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Document(collection = "jogadores")
public class Jogador {
    
    @Id
    private String id;
    private String nome;
    private int idade;
    private List<String> times;

    public Jogador(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
        this.times = new ArrayList<>();
    }

    // Getters e Setters
}

