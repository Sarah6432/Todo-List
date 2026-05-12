package main;

public enum StatusTarefa {
    TODO("A fazer"),
    DOING("Em andamento"),
    DONE("Concluído");

    private final String descricao;

    StatusTarefa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
