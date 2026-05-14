package main.model;

public interface FiltroStrategy {
    boolean test(Tarefa tarefa, String busca);
}
