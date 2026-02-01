import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tarefa {
    private String nome;
    private String descricao;
    private LocalDateTime dataHora;
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

    // Getters
    public String getNome() { return nome; }
    public int getPrioridade() { return prioridade; }
    public String getCategoria() { return categoria; }
    public String getStatus() { return status; }
    public LocalDateTime getDataHora() { return dataHora; }
    public boolean isAlarmeAtivo() { return alarmeAtivo; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("Nome: %-15s | Prioridade: %d | Status: %-7s | Prazo: %s | Alarme: %s",
                nome, prioridade, status, dataHora.format(fmt), (alarmeAtivo ? "on" : "off"));
    }
}