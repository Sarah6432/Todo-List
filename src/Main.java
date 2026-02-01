import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    private static List<Tarefa> listaTarefas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        int opcao = 0;
        do {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1 -> adicionarTarefa();
                    case 2 -> listarTarefas(listaTarefas);
                    case 3 -> removerTarefa();
                    case 4 -> filtrarTarefas();
                    case 0 -> System.out.println("Encerrando aplicação...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: Entrada inválida.");
            }
        } while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println("\n=== ZG-HERO TODO LIST ===");
        System.out.println("1. Criar Tarefa");
        System.out.println("2. Listar Tudo");
        System.out.println("3. Remover Tarefa");
        System.out.println("4. Filtrar por Categoria/Prioridade/Status");
        System.out.println("0. Sair");
        System.out.print("Escolha: ");
    }

    private static void adicionarTarefa() {
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("Descrição: "); String desc = scanner.nextLine();
        System.out.print("Data Término (dd/mm/aaaa): ");
        LocalDate data = LocalDate.parse(scanner.nextLine(), formatter);
        System.out.print("Prioridade (1-5): "); int prio = Integer.parseInt(scanner.nextLine());
        System.out.print("Categoria: "); String cat = scanner.nextLine();
        System.out.print("Status (todo, doing, done): "); String status = scanner.nextLine();

        Tarefa nova = new Tarefa(nome, desc, data, prio, cat, status);
        listaTarefas.add(nova);


        listaTarefas.sort(Comparator.comparingInt(Tarefa::getPrioridade).reversed());
        System.out.println("Tarefa adicionada e lista rebalanceada!");
    }


    private static void listarTarefas(List<Tarefa> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
        } else {
            System.out.println("\n--- Lista de Atividades ---");
            lista.forEach(System.out::println);
        }
    }


    private static void removerTarefa() {
        System.out.print("Digite o nome exato da tarefa para remover: ");
        String nome = scanner.nextLine();
        boolean removido = listaTarefas.removeIf(t -> t.getNome().equalsIgnoreCase(nome));
        if (removido) System.out.println("Tarefa removida com sucesso!");
        else System.out.println("Tarefa não encontrada.");
    }


    private static void filtrarTarefas() {
        System.out.println("Filtrar por: 1. Categoria | 2. Prioridade | 3. Status");
        int f = Integer.parseInt(scanner.nextLine());
        List<Tarefa> filtrada = new ArrayList<>();

        if (f == 1) {
            System.out.print("Digite a categoria: ");
            String busca = scanner.nextLine();
            filtrada = listaTarefas.stream().filter(t -> t.getCategoria().equalsIgnoreCase(busca)).toList();
        } else if (f == 2) {
            System.out.print("Digite a prioridade (1-5): ");
            int busca = Integer.parseInt(scanner.nextLine());
            filtrada = listaTarefas.stream().filter(t -> t.getPrioridade() == busca).toList();
        } else if (f == 3) {
            System.out.print("Digite o status: ");
            String busca = scanner.nextLine();
            filtrada = listaTarefas.stream().filter(t -> t.getStatus().equalsIgnoreCase(busca)).toList();
        }
        listarTarefas(filtrada);
    }
}