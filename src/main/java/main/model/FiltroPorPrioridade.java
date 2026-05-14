package main.model;

public class FiltroPorPrioridade implements FiltroStrategy {
    @Override
    public boolean test(Tarefa tarefa, String busca) {
        return String.valueOf(tarefa.getPrioridade()).equals(busca);
    }
}
