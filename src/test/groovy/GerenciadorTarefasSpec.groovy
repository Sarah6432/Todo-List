package main

import main.controller.GerenciadorTarefas
import main.model.FiltroPorCategoria
import main.model.FiltroPorStatus
import main.model.Notificador
import main.model.StatusTarefa
import main.model.Tarefa
import spock.lang.Specification
import spock.lang.Subject
import java.time.LocalDateTime

class GerenciadorTarefasSpec extends Specification {

    Notificador notificadorMock = Mock(Notificador)

    @Subject
    GerenciadorTarefas gerenciador = new GerenciadorTarefas(notificadorMock)

    void "Deve criar uma nova tarefa com sucesso e configurar alarme"() {
        given:
        String nome = "Estudar Java"
        String descricao = "Revisar Spock Framework"
        LocalDateTime data = LocalDateTime.now().plusDays(1)
        int prioridade = 5
        String categoria = "Estudos"
        StatusTarefa status = StatusTarefa.TODO
        ArrayList<Integer> alarmes = [60]
        Tarefa novaTarefa = new Tarefa(nome, descricao, data, prioridade, categoria, status, alarmes)

        when:
        gerenciador.adicionar(novaTarefa)

        then:
        gerenciador.getTarefas().size() == 1
        gerenciador.getTarefas()[0].nome == "Estudar Java"
        gerenciador.getTarefas()[0].status == StatusTarefa.TODO
        gerenciador.getTarefas()[0].alarmesMinutos[0] == 60
    }

    void "Deve remover uma tarefa existente pelo nome"() {
        given:
        gerenciador.adicionar(new Tarefa("Limpar Casa", "", LocalDateTime.now(), 1, "Casa", StatusTarefa.TODO, []))

        when:
        boolean removido = gerenciador.remover("Limpar Casa")

        then:
        removido
        gerenciador.getTarefas().size() == 0
    }

    void "Deve editar os dados de uma tarefa e atualizar o status para 'DONE'"() {
        given:
        gerenciador.adicionar(new Tarefa("Academia", "Treino A", LocalDateTime.now(), 3, "Saúde", StatusTarefa.TODO, []))

        when:
        boolean editado = gerenciador.editar("Academia", "Treino A", 3, "Saúde", StatusTarefa.DONE, [15])

        then:
        editado
        with(gerenciador.getTarefas()[0]) {
            status == StatusTarefa.DONE
            alarmesMinutos[0] == 15
        }
    }

    void "Deve rebalancear a lista priorizando tarefas de nível mais alto"() {
        given:
        Tarefa tarefaBaixa = new Tarefa("Baixa", "Desc", LocalDateTime.now(), 1, "Geral", StatusTarefa.TODO, [])
        Tarefa tarefaAlta = new Tarefa("Alta", "Desc", LocalDateTime.now(), 5, "Geral", StatusTarefa.TODO, [])

        when:
        gerenciador.adicionar(tarefaBaixa)
        gerenciador.adicionar(tarefaAlta)

        then:
        gerenciador.getTarefas().first().nome == "Alta"
    }

    void "Deve acionar o notificador quando uma tarefa atinge o tempo de alerta"() {
        given: "Uma tarefa que vence em 5 minutos com um alarme configurado para 10 minutos"
        LocalDateTime agora = LocalDateTime.now()
        LocalDateTime dataVencimento = agora.plusMinutes(5)

        Tarefa tarefa = new Tarefa(
                "Tarefa Teste",
                "Desc",
                dataVencimento,
                3,
                "Geral",
                StatusTarefa.TODO,
                [10]
        )
        gerenciador.adicionar(tarefa)

        when: "O monitoramento processa a verificação"
        gerenciador.verificarEAlertar(tarefa, agora)

        then: "O método enviarAlerta do notificador DEVE ser chamado exatamente 1 vez"
        1 * notificadorMock.enviarAlerta(_ as String)

        and: "A tarefa deve ser marcada como notificada para não repetir o alarme"
        gerenciador.alarmesDisparados.contains("Tarefa Teste_10")
    }

    void "Deve filtrar tarefas por categoria"() {
        given:
        LocalDateTime data = LocalDateTime.now()
        gerenciador.adicionar(new Tarefa("Tarefa 1", "D", data, 1, "Trabalho", StatusTarefa.TODO, []))
        gerenciador.adicionar(new Tarefa("Tarefa 2", "D", data, 1, "Pessoal", StatusTarefa.TODO, []))

        when:
        List<Tarefa> resultado = gerenciador.filtrar(new FiltroPorCategoria(), "Trabalho")

        then:
        resultado.size() == 1
        resultado[0].categoria == "Trabalho"
    }

    void "Deve filtrar tarefas por status"() {
        given:
        LocalDateTime data = LocalDateTime.now()
        gerenciador.adicionar(new Tarefa("T1", "D", data, 1, "G", StatusTarefa.TODO, []))
        gerenciador.adicionar(new Tarefa("T2", "D", data, 1, "G", StatusTarefa.DONE, []))

        when:
        List<Tarefa> resultado = gerenciador.filtrar(new FiltroPorStatus(), "DONE")

        then:
        resultado.size() == 1
        resultado[0].status == StatusTarefa.DONE
    }
}

