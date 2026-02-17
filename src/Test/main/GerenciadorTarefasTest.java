/*
 * Autor: Sarah Lima
 * data: 09/02/2026
 * Desafio: (K1-T5) - Criando testes unitários para o TODO List
 */

package Test.main;

import main.GerenciadorTarefas;
import main.Tarefa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
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
        Tarefa nova = new Tarefa("Estudar Java", "Revisar Junit", LocalDateTime.now().plusDays(1), 5, "Estudos", "todo", List.of(60));

        System.out.print("Criando " + nova.getNome());
        gerenciador.adicionar(nova);

        Assertions.assertEquals(1, gerenciador.getTarefas().size());
        assertEquals(60, gerenciador.getTarefas().getFirst().getAlarmesMinutos().getFirst());
        System.out.println(" criada com sucesso!");
    }

    @Test
    void testeRemoverTarefa() {
        System.out.println("Teste de remoção de tarefa:");
        gerenciador.adicionar(new Tarefa("Limpar Casa", "", LocalDateTime.now(), 1, "Casa", "todo", List.of()));

        System.out.print("Removendo Limpar Casa ");
        boolean removido = gerenciador.remover("Limpar Casa");

        assertTrue(removido);
        Assertions.assertEquals(0, gerenciador.getTarefas().size());
        System.out.println(" removida com sucesso!");
    }

    @Test
    void testeEditarTarefa() {
        System.out.println("Teste de edição:");
        Tarefa t = new Tarefa("Academia", "Treino A", LocalDateTime.now(), 3, "Saúde", "todo", List.of());
        gerenciador.adicionar(t);

        System.out.print("Editando " + t.getNome() + " para status 'done' ");

        boolean editado = gerenciador.editar("Academia", "Treino A", 3, "Saúde", "done", List.of(15));

        assertTrue(editado);
        Assertions.assertEquals("done", gerenciador.getTarefas().getFirst().getStatus());
        Assertions.assertEquals(15, gerenciador.getTarefas().getFirst().getAlarmesMinutos().getFirst());
        System.out.println(" editada com sucesso!");
    }

    @Test
    void testePrioridadeRebalanceamento() {
        System.out.println("Teste de rebalanceamento por prioridade:");
        gerenciador.adicionar(new Tarefa("Baixa", "Desc", LocalDateTime.now(), 1, "Geral", "todo", List.of()));
        gerenciador.adicionar(new Tarefa("Alta", "Desc", LocalDateTime.now(), 5, "Geral", "todo", List.of()));

        assertEquals("Alta", gerenciador.getTarefas().getFirst().getNome());
        System.out.println(" rebalanceamento OK!");
    }
}