import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private static GerenciadorTarefa gerenciador = new GerenciadorTarefa();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        dispararAlarmesIniciais();

        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1 -> cadastrar();
                    case 2 -> listar(gerenciador.getTarefas());
                    case 3 -> deletar();
                    case 4 -> filtrar();
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro na entrada. Certifique-se de usar o formato correto.");
            }
        }
    }

    private static void dispararAlarmesIniciais() {
        List<Tarefa> proximas = gerenciador.verificarAlarmes();
        if (!proximas.isEmpty()) {
            System.out.println("\n🔔 --- ALARMES ATIVOS (Tarefas próximas do fim) ---");
            proximas.forEach(t -> System.out.println("AVISO: A tarefa '" + t.getNome() + "' expira em menos de 2 horas!"));
            System.out.println("---------------------------------------------------\n");
        }
    }

    private static void exibirMenu() {
        System.out.println("1. Nova Tarefa | 2. Listar Tudo | 3. Remover | 4. Filtrar | 0. Sair");
        System.out.print("Escolha: ");
    }

    private static void cadastrar() {
        try {
            System.out.print("Nome: "); String nome = scanner.nextLine();
            System.out.print("Descrição: "); String desc = scanner.nextLine();
            System.out.print("Data/Hora (dd/MM/yyyy HH:mm): ");
            LocalDateTime dh = LocalDateTime.parse(scanner.nextLine(), fmt);
            System.out.print("Prioridade (1-5): "); int prio = Integer.parseInt(scanner.nextLine());
            System.out.print("Categoria: "); String cat = scanner.nextLine();
            System.out.print("Status (todo/doing/done): "); String status = scanner.nextLine();
            System.out.print("Ativar Alarme? (s/n): "); boolean alarme = scanner.nextLine().equalsIgnoreCase("s");

            gerenciador.adicionar(new Tarefa(nome, desc, dh, prio, cat, status, alarme));
            System.out.println("Sucesso! Lista rebalanceada por prioridade.");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar. Verifique o formato da data/hora.");
        }
    }

    private static void listar(List<Tarefa> lista) {
        if (lista.isEmpty()) System.out.println("Vazio.");
        else lista.forEach(System.out::println);
    }

    private static void deletar() {
        System.out.print("Nome da tarefa para remover: ");
        if (gerenciador.remover(scanner.nextLine())) System.out.println("Removida.");
        else System.out.println("Não encontrada.");
    }

    private static void filtrar() {
        System.out.println("Filtrar por: 1. Categoria | 2. Status | 3. Prioridade");
        int tipo = Integer.parseInt(scanner.nextLine());
        System.out.print("Busca: ");
        String busca = scanner.nextLine();
        listar(gerenciador.filtrar(tipo, busca));
    }
}