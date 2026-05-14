package main;

public class FiltroPorStatus implements FiltroStrategy{
    @Override
    public boolean test(Tarefa tarefa, String busca) {
        return tarefa.getStatus().name().equalsIgnoreCase(busca);
    }
}
