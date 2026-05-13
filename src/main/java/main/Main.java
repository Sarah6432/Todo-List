package main;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Main {
    private static final GerenciadorTarefas gerenciador = new GerenciadorTarefas(new NotificadorView());
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        gerenciador.iniciarMonitoramento();

        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                processarOpcao(opcao);
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número válido.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\nGerenciador de Tarefas: ");
        System.out.println("1. Nova Tarefa | 2. Listar Tudo | 3. Remover");
        System.out.println("4. Filtrar    | 5. Editar      | 0. Sair");
        System.out.print("Escolha: ");
    }

    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> cadastrar();
            case 2 -> listar(gerenciador.getTarefas());
            case 3 -> deletar();
            case 4 -> filtrar();
            case 5 -> editarTarefa();
            case 0 -> System.out.println("Saindo...");
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void cadastrar() {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            String desc = pedirDescricao();
            LocalDateTime dataHora = lerData();
            int prio = pedirPrioridade();
            String cat = pedirCategoria();
            StatusTarefa status = lerStatus();
            List<Integer> alarmes = lerAlarmes();

            gerenciador.adicionar(new Tarefa(nome, desc, dataHora, prio, cat, status, alarmes));
            System.out.println("Sucesso! Tarefa adicionada.");

        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data inválido.");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    private static void editarTarefa() {
        System.out.print("Nome da tarefa que deseja editar: ");
        String nome = scanner.nextLine();

        try {
            String desc = pedirDescricao();
            int prio = pedirPrioridade();
            String cat = pedirCategoria();
            StatusTarefa status = lerStatus();
            List<Integer> alarmes = lerAlarmes();

            if (gerenciador.editar(nome, desc, prio, cat, status, alarmes)) {
                System.out.println("Tarefa atualizada com sucesso!");
            } else {
                System.out.println("Erro: Tarefa não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao editar: Dados inválidos.");
        }
    }

    private static String pedirDescricao() {
        System.out.print("Descrição: ");
        return scanner.nextLine();
    }

    private static int pedirPrioridade() {
        System.out.print("Prioridade (1-5): ");
        return Integer.parseInt(scanner.nextLine());
    }

    private static String pedirCategoria() {
        System.out.print("Categoria: ");
        return scanner.nextLine();
    }

    private static LocalDateTime lerData() {
        System.out.print("Data/Hora (dd/MM/yyyy HH:mm): ");
        return LocalDateTime.parse(scanner.nextLine(), FORMATADOR);
    }

    private static StatusTarefa lerStatus() {
        System.out.print("Status (TODO, DOING, DONE): ");
        try {
            return StatusTarefa.valueOf(scanner.nextLine().toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            System.out.println("Status inválido. Definindo como TODO por padrão.");
            return StatusTarefa.TODO;
        }
    }

    private static List<Integer> lerAlarmes() {
        System.out.print("Antecedência dos alarmes em minutos (ex: 10, 20 ou 0 para nenhum): ");
        String input = scanner.nextLine();

        if (input.equals("0") || input.isBlank()) {
            return Collections.emptyList();
        }

        return Arrays.stream(input.split(","))
                .map(s -> Integer.parseInt(s.trim()))
                .toList();
    }

    private static void listar(List<Tarefa> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private static void deletar() {
        System.out.print("Nome da tarefa para remover: ");
        String nome = scanner.nextLine();
        if (gerenciador.remover(nome)) {
            System.out.println("Removida com sucesso.");
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }

    private static void filtrar() {
        try {
            System.out.println("Filtrar por: 1. Categoria | 2. Status | 3. Prioridade");
            int tipo = Integer.parseInt(scanner.nextLine());
            System.out.print("Busca: ");
            String busca = scanner.nextLine();
            listar(gerenciador.filtrar(tipo, busca));
        } catch (Exception e) {
            System.out.println("Erro ao filtrar.");
        }
    }
}