package main;

public class FiltroPorCategoria implements FiltroStrategy{
    @Override
    public boolean test(Tarefa tarefa, String busca) {
        return tarefa.getCategoria().equalsIgnoreCase(busca);
    }
}
