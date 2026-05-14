package main;

import java.time.LocalDateTime;
import java.util.*;

public class GerenciadorTarefas {
    private final List<Tarefa> tarefas = new ArrayList<>();
    private final Set<String> alarmesDisparados = new HashSet<>();
    private final Notificador notificador;

    public GerenciadorTarefas(Notificador notificador) {
        this.notificador = notificador;
    }

    public void adicionar(Tarefa tarefa) {
        tarefas.add(tarefa);
        ordenarPorPrioridade();
    }

    private void ordenarPorPrioridade() {
        tarefas.sort(Comparator.comparingInt(Tarefa::getPrioridade).reversed());
    }

    public boolean remover(String nome) {
        return tarefas.removeIf(t -> t.getNome().equalsIgnoreCase(nome));
    }

    public List<Tarefa> getTarefas() {
        return Collections.unmodifiableList(tarefas);
    }

    public void iniciarMonitoramento() {
        Thread threadAlarme = new Thread(this::processarMonitoramento);
        threadAlarme.setDaemon(true);
        threadAlarme.start();
    }

    private void processarMonitoramento() {
        while (true) {
            LocalDateTime agora = LocalDateTime.now();
            tarefas.stream()
                    .filter(t -> !t.estaConcluida())
                    .forEach(t -> verificarEAlertar(t, agora));

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void verificarEAlertar(Tarefa tarefa, LocalDateTime agora) {
        long minutosRestantes = tarefa.calcularMinutosRestantes(agora);

        for (Integer antecedencia : tarefa.getAlarmesMinutos()) {
            String chaveAlarme = tarefa.getNome() + "_" + antecedencia;

            if (deveDispararAlarme(minutosRestantes, antecedencia, chaveAlarme)) {
                notificador.enviarAlerta(String.format("A tarefa '%s' expira em %d minutos!", tarefa.getNome(), minutosRestantes));
                alarmesDisparados.add(chaveAlarme);
            }
        }
    }

    private boolean deveDispararAlarme(long minutosRestantes, int antecedencia, String chaveAlarme) {
        return minutosRestantes <= antecedencia
                && minutosRestantes > 0
                && !alarmesDisparados.contains(chaveAlarme);
    }

    public List<Tarefa> filtrar(FiltroStrategy estrategia, String busca) {
        return tarefas.stream()
                .filter(t -> estrategia.test(t, busca))
                .toList();
    }

    private boolean atendeAoFiltro(Tarefa t, int tipo, String busca) {
        return switch (tipo) {
            case 1 -> t.getCategoria().equalsIgnoreCase(busca);
            case 2 -> t.getStatus().name().equalsIgnoreCase(busca);
            case 3 -> String.valueOf(t.getPrioridade()).equals(busca);
            default -> true;
        };
    }

    public boolean editar(String nome, String novaDesc, int novaPrio, String novaCat, StatusTarefa novoStatus, List<Integer> novosAlarmes) {
        for (Tarefa t : tarefas) {
            if (t.getNome().equalsIgnoreCase(nome)) {
                t.setDescricao(novaDesc);
                t.setPrioridade(novaPrio);
                t.setCategoria(novaCat);
                t.setStatus(novoStatus);
                t.setAlarmesMinutos(novosAlarmes);
                ordenarPorPrioridade();
                return true;
            }
        }
        return false;
    }
}