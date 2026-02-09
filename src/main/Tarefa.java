package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tarefa {
    private final String nome;
    private String descricao;
    private final LocalDateTime dataHora;
    private int prioridade;
    private String categoria;
    private String status;
    private boolean alarmeAtivo;

    public Tarefa(String nome, String descricao, LocalDateTime dataHora, int prioridade, String categoria, String status, boolean alarmeAtivo) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.prioridade = prioridade;
        this.categoria = categoria;
        this.status = status;
        this.alarmeAtivo = alarmeAtivo;
    }

    public String getNome() { return nome; }
    public int getPrioridade() { return prioridade; }
    public String getCategoria() { return categoria; }
    public String getStatus() { return status; }
    public String getDescricao(){ return  descricao; }
    public LocalDateTime getDataHora() { return dataHora; }
    public boolean isAlarmeAtivo() { return alarmeAtivo; }

    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setPrioridade(int prioridade) { this.prioridade = prioridade; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setStatus(String status) { this.status = status; }
    public void setAlarmeAtivo(boolean alarmeAtivo) { this.alarmeAtivo = alarmeAtivo; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("Nome: %-15s | Prioridade: %d | Status: %-7s | Prazo: %s | Alarme: %s",
                nome, prioridade, status, dataHora.format(fmt), (alarmeAtivo ? "on" : "off"));
    }
}