package main.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tarefa {
    private static final DateTimeFormatter FORMATADOR_EXIBICAO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final String nome;
    private String descricao;
    private final LocalDateTime dataHora;
    private int prioridade;
    private String categoria;
    private StatusTarefa status;
    private List<Integer> alarmesMinutos;

    public Tarefa(String nome, String descricao, LocalDateTime dataHora, int prioridade,
                  String categoria, StatusTarefa status, List<Integer> alarmesMinutos) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.prioridade = prioridade;
        this.categoria = categoria;
        this.status = status;
        this.alarmesMinutos = (alarmesMinutos != null) ? new ArrayList<>(alarmesMinutos) : new ArrayList<>();
    }

    public long calcularMinutosRestantes(LocalDateTime agora) {
        return Duration.between(agora, dataHora).toMinutes();
    }

    public boolean estaConcluida() {
        return StatusTarefa.DONE.equals(this.status);
    }

    public String getNome() {
        return nome;
    }
    public int getPrioridade() {
        return prioridade;
    }
    public String getCategoria() {
        return categoria;
    }
    public StatusTarefa getStatus() {
        return status;
    }
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public List<Integer> getAlarmesMinutos() {
        return Collections.unmodifiableList(alarmesMinutos);
    }

    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setPrioridade(int prioridade) { this.prioridade = prioridade; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setStatus(StatusTarefa status) { this.status = status; }
    public void setAlarmesMinutos(List<Integer> alarmes) {
        this.alarmesMinutos = new ArrayList<>(alarmes);
    }

    @Override
    public String toString() {
        return String.format("Nome: %-15s | Prio: %d | Status: %-12s | Prazo: %s | Alarmes: %s min",
                nome, prioridade, status.getDescricao(), dataHora.format(FORMATADOR_EXIBICAO), alarmesMinutos);
    }
}