package main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class GerenciadorTarefas {
    private List<Tarefa> tarefas = new ArrayList<>();
    private Set<String> alarmesDisparados = new HashSet<>();

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

    public void iniciarMonitoramento() {
        Thread threadAlarme = new Thread(() -> {
            while (true) {
                LocalDateTime agora = LocalDateTime.now();
                for (Tarefa t : tarefas) {
                    if (!t.getStatus().equalsIgnoreCase("done")) {
                        long minutosRestantes = Duration.between(agora, t.getDataHora()).toMinutes();

                        for (Integer antecedencia : t.getAlarmesMinutos()) {
                            String chaveAlarme = t.getNome() + "_" + antecedencia;

                            if (minutosRestantes <= antecedencia && minutosRestantes > 0 && !alarmesDisparados.contains(chaveAlarme)) {
                                System.out.println("\n[ALERTA] A tarefa '" + t.getNome() + "' expira em " + minutosRestantes + " minutos!");
                                alarmesDisparados.add(chaveAlarme);
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        threadAlarme.setDaemon(true);
        threadAlarme.start();
    }

    public List<Tarefa> filtrar(int tipo, String busca) {
        return switch (tipo) {
            case 1 -> tarefas.stream().filter(t -> t.getCategoria().equalsIgnoreCase(busca)).toList();
            case 2 -> tarefas.stream().filter(t -> t.getStatus().equalsIgnoreCase(busca)).toList();
            case 3 -> tarefas.stream().filter(t -> String.valueOf(t.getPrioridade()).equals(busca)).toList();
            default -> tarefas;
        };
    }

    public boolean editar(String nome, String novaDesc, int novaPrio, String novaCat, String novoStatus, List<Integer> novosAlarmes) {
        for (Tarefa t : tarefas) {
            if (t.getNome().equalsIgnoreCase(nome)) {
                t.setDescricao(novaDesc);
                t.setPrioridade(novaPrio);
                t.setCategoria(novaCat);
                t.setStatus(novoStatus);
                t.setAlarmesMinutos(novosAlarmes);
                rebalancear();
                return true;
            }
        }
        return false;
    }
}