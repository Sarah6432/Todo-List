import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class GerenciadorTarefas {
    private List<Tarefa> tarefas = new ArrayList<>();

    public void adicionar(Tarefa t) {
        tarefas.add(t);
        rebalancear();
    }

    private void rebalancear() {
        tarefas.sort(Comparator.comparingInt(Tarefa::getPrioridade).reversed());
    }

    public boolean remover(String nome) {
        return tarefas.removeIf(t -> t.getNome().equalsIgnoreCase(nome));
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public List<Tarefa> verificarAlarmes() {
        LocalDateTime agora = LocalDateTime.now();
        return tarefas.stream()
                .filter(t -> t.isAlarmeAtivo() && !t.getStatus().equalsIgnoreCase("done"))
                .filter(t -> {
                    Duration d = Duration.between(agora, t.getDataHora());
                    return !d.isNegative() && d.toHours() < 2;
                })
                .collect(Collectors.toList());
    }

    public List<Tarefa> filtrar(int tipo, String busca) {
        return switch (tipo) {
            case 1 -> tarefas.stream().filter(t -> t.getCategoria().equalsIgnoreCase(busca)).toList();
            case 2 -> tarefas.stream().filter(t -> t.getStatus().equalsIgnoreCase(busca)).toList();
            case 3 -> tarefas.stream().filter(t -> String.valueOf(t.getPrioridade()).equals(busca)).toList();
            default -> tarefas;
        };
    }
}