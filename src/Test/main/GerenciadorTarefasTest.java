/*
 Autor: Sarah Lima
 * data: 09/02/2026
 * Desafio: (K1-T5) - Criando testes unitários para o
 * TODO List
*/

package Test.main;
import main.GerenciadorTarefas;
import main.Tarefa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class GerenciadorTarefasTest {
    private GerenciadorTarefas gerenciador;

    @BeforeEach
    void setup() {
        gerenciador = new GerenciadorTarefas();
    }

    @Test
    void testeCriarTarefa() {
        System.out.println("Teste de criação de tarefa:");
        Tarefa nova = new Tarefa("Estudar Java", "Revisar Junit", LocalDateTime.now().plusDays(1), 5, "Estudos", "todo", true);

        System.out.print("Criando " + nova.getNome());
        gerenciador.adicionar(nova);

        Assertions.assertEquals(1, gerenciador.getTarefas().size());
        System.out.println("criada com sucesso!");
    }

    @Test
    void testeRemoverTarefa() {
        System.out.println("Teste de remoção de tarefa:");
        gerenciador.adicionar(new Tarefa("Limpar Casa", "", LocalDateTime.now(), 1, "Casa", "todo", false));

        System.out.print("Removendo Limpar Casa ");
        boolean removido = gerenciador.remover("Limpar Casa");

        assertTrue(removido);
        Assertions.assertEquals(0, gerenciador.getTarefas().size());
        System.out.println("removida com sucesso!");
    }

    @Test
    void testeEditarTarefa() {
        System.out.println("Teste de edição:");
        Tarefa t = new Tarefa("Academia", "Treino A", LocalDateTime.now(), 3, "Saúde", "todo", false);
        gerenciador.adicionar(t);

        System.out.print("Editando " + t.getNome() + " para status 'done' ");

        boolean editado = gerenciador.editar("Academia", "Treino A", 3, "Saúde", "done", false);

        assertTrue(editado);
        Assertions.assertEquals("done", gerenciador.getTarefas().getFirst().getStatus());
        System.out.println("editada com sucesso!");
    }
}