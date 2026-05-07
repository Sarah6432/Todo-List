package main

import spock.lang.Specification
import spock.lang.Subject
import java.time.LocalDateTime

class GerenciadorTarefasSpec extends Specification {

    @Subject
    GerenciadorTarefas gerenciador = new GerenciadorTarefas()

    def "Deve criar uma nova tarefa com sucesso e configurar alarme"() {
        given: "Um objeto Tarefa mockado simulando dados vindos do frontend"
        def nome = "Estudar Java"
        def descricao = "Revisar Spock Framework"
        def data = LocalDateTime.now().plusDays(1)
        def prioridade = 5
        def categoria = "Estudos"
        def status = "todo"
        def alarmes = [60]
        def novaTarefa = new Tarefa(nome, descricao, data, prioridade, categoria, status, alarmes)

        when: "O controlador solicita a adição da tarefa ao gerenciador"
        gerenciador.adicionar(novaTarefa)

        then: "A lista de tarefas deve conter exatamente 1 item com os dados corretos"
        gerenciador.tarefas.size() == 1
        gerenciador.tarefas[0].nome == "Estudar Java"
        gerenciador.tarefas[0].alarmesMinutos[0] == 60
    }

    def "Deve remover uma tarefa existente pelo nome"() {
        given: "Que existe uma tarefa cadastrada"
        gerenciador.adicionar(new Tarefa("Limpar Casa", "", LocalDateTime.now(), 1, "Casa", "todo", []))

        when: "O comando de remoção é enviado pelo cliente"
        boolean removido = gerenciador.remover("Limpar Casa")

        then: "O resultado deve ser verdadeiro e a lista deve ficar vazia"
        removido
        gerenciador.tarefas.size() == 0
    }

    def "Deve editar os dados de uma tarefa e atualizar o status para 'done'"() {
        given: "Uma tarefa inicial no sistema"
        gerenciador.adicionar(new Tarefa("Academia", "Treino A", LocalDateTime.now(), 3, "Saúde", "todo", []))

        when: "O controller envia novos parâmetros para edição"
        boolean editado = gerenciador.editar("Academia", "Treino A", 3, "Saúde", "done", [15])

        then: "A tarefa deve refletir as alterações mockadas"
        editado
        with(gerenciador.tarefas[0]) {
            status == "done"
            alarmesMinutos[0] == 15
        }
    }

    def "Deve rebalancear a lista priorizando tarefas de nível mais alto"() {
        given: "Dados de tarefas com diferentes prioridades"
        def tarefaBaixa = new Tarefa("Baixa", "Desc", LocalDateTime.now(), 1, "Geral", "todo", [])
        def tarefaAlta = new Tarefa("Alta", "Desc", LocalDateTime.now(), 5, "Geral", "todo", [])

        when: "As tarefas são adicionadas em qualquer ordem"
        gerenciador.adicionar(tarefaBaixa)
        gerenciador.adicionar(tarefaAlta)

        then: "A primeira tarefa da lista (topo) deve ser a de maior prioridade"
        gerenciador.tarefas.first().nome == "Alta"
    }
}