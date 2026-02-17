package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Tarefa {
    private final String nome;
    private String descricao;
    private final LocalDateTime dataHora;
    private int prioridade;
    private String categoria;
    private String status;
    private List<Integer> alarmesMinutos;

    public Tarefa(String nome, String descricao, LocalDateTime dataHora, int prioridade, String categoria, String status, List<Integer> alarmesMinutos) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.prioridade = prioridade;
        this.categoria = categoria;
        this.status = status;
        this.alarmesMinutos = new ArrayList<>(alarmesMinutos);
    }

    public String getNome() { return nome; }
    public int getPrioridade() { return prioridade; }
    public String getCategoria() { return categoria; }
    public String getStatus() { return status; }
    public String getDescricao(){ return  descricao; }
    public LocalDateTime getDataHora() { return dataHora; }
    public List<Integer> getAlarmesMinutos() { return alarmesMinutos; }

    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setPrioridade(int prioridade) { this.prioridade = prioridade; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setStatus(String status) { this.status = status; }
    public void setAlarmesMinutos(List<Integer> alarmes) { this.alarmesMinutos = alarmes; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("Nome: %-15s | Prioridade: %d | Status: %-7s | Prazo: %s | Alarmes: %s min",
                nome, prioridade, status, dataHora.format(fmt), alarmesMinutos.toString());
    }
}