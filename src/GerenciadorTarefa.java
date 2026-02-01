import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorTarefa {
    private List<Tarefa> tarefas = new ArrayList<>();

    public void adicionarTarefa(Tarefa t) {
        tarefas.add(t);
        // Rebalanceamento: Prioridade 5 (maior) fica no topo
        tarefas.sort(Comparator.comparingInt(Tarefa::getPrioridade).reversed());
    }

    public void removerTarefa(String nome) {
        tarefas.removeIf(t -> t.getNome().equalsIgnoreCase(nome));
    }

    public List<Tarefa> listarTodas() { return tarefas; }

    // Filtros
    public List<Tarefa> filtrarPorCategoria(String cat) {
        return tarefas.stream().filter(t -> t.getCategoria().equalsIgnoreCase(cat)).collect(Collectors.toList());
    }

    public List<Tarefa> filtrarPorStatus(String status) {
        return tarefas.stream().filter(t -> t.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
}