import java.time.LocalDate;

public class Tarefa {
    private String nome;
    private String descricao;
    private LocalDate dataTermino;
    private int prioridade;
    private String categoria;
    private String status;

    public Tarefa(String nome, String descricao, LocalDate dataTermino, int prioridade, String categoria, String status) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataTermino = dataTermino;
        this.prioridade = prioridade;
        this.categoria = categoria;
        this.status = status;
    }

    // Getters para filtros e exibição
    public String getNome() { return nome; }
    public int getPrioridade() { return prioridade; }
    public String getCategoria() { return categoria; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("Nome: %-15s | Prioridade: %d | Status: %-8s | Fim: %s | Cat: %s",
                nome, prioridade, status, dataTermino, categoria);
    }
}