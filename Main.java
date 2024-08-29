import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static ToDoManager toDoManager = new ToDoManager();
    private static final String FILE_NAME = "toDos.csv";
    public static void  main(String[] args){
        toDoManager.loadToDosFromFile(FILE_NAME);
        Scanner scanner = new Scanner(System.in);


        new Thread(() ->{
            while (true){
                toDoManager.checkAllAlarms();

                try {

                    Thread.sleep(60000);

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();

        while (true){
            System.out.println("===== TODO List Menu =====");
            System.out.println("1. Adicionar Tarefa");
            System.out.println("2. Remover Tarefa");
            System.out.println("3. Atualizar Tarefa");
            System.out.println("4. Listar Tarefas");
            System.out.println("5. Filtrar Tarefas por Categoria");
            System.out.println("6. Filtrar Tarefas por Prioridade");
            System.out.println("7. Filtrar Tarefas por Status");
            System.out.println("8. Filtrar Tarefas por Data");
            System.out.println("9. Contar Tarefas por Status");
            System.out.println("10. Configurar Alarme para Tarefa");
            System.out.println("11. Salvar Tarefas");
            System.out.println("12. Sair");
            System.out.print("Escolha uma opção: ");
            int option = scanner.nextInt();
            scanner.nextLine();



            switch (option) {
                case 1:
                    addToDo(scanner);
                    break;
                case 2:
                    deleteToDo(scanner);
                    break;
                case 3:
                    updateToDo(scanner);
                    break;
                case 4:
                    listToDos();
                    break;
                case 5:
                    filterToDosByCategory(scanner);
                    break;
                case 6:
                    filterToDosByPriority(scanner);
                    break;
                case 7:
                    filterToDosByStatus(scanner);
                    break;
                case 8:
                    filterToDosByDate(scanner);
                    break;
                case 9:
                    countToDosByStatus();
                    break;
                case 10:
                    configureAlarm(scanner);
                    break;
                case 11:
                    toDoManager.saveToDosToFile(FILE_NAME);
                    System.out.println("Tarefas salvas com sucesso!");
                    break;
                case 12:
                    toDoManager.saveToDosToFile(FILE_NAME);
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }


    private static void configureAlarm(Scanner scanner) {
        System.out.print("Nome da Tarefa para Configurar Alarme: ");
        String name = scanner.nextLine();

        System.out.print("Data e Hora do Alarme (yyyy-MM-dd HH:mm): ");
        String alarmTimeInput = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime alarmTime;

        try {
            alarmTime = LocalDateTime.parse(alarmTimeInput, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data e hora inválido. Por favor, use o formato yyyy-MM-dd HH:mm.");
            return;
        }

        for (ToDo toDo : toDoManager.listToDos()) {
            if (toDo.getName().equalsIgnoreCase(name)) {
                toDo.addAlarm(alarmTime);
                System.out.println("Alarme configurado com sucesso!");
                return;
            }
        }

        System.out.println("Tarefa não encontrada.");
    }


    private static void addToDo(Scanner scanner) {
        System.out.print("Nome: ");
        String name = scanner.nextLine();
        System.out.print("Descrição: ");
        String description = scanner.nextLine();
        System.out.print("Data de Término (yyyy-mm-dd): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Prioridade (1-5): ");
        int priorityLevel = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Categoria: ");
        String category = scanner.nextLine();
        System.out.print("Status (ToDo, Doing, Done): ");
        String status = scanner.nextLine();

        ToDo toDo = new ToDo(name, description, endDate, priorityLevel, category, status);
        toDoManager.addToDo(toDo);
        System.out.println("Tarefa adicionada com sucesso!");
    }


    private static void deleteToDo(Scanner scanner) {
        System.out.print("Nome da Tarefa a Remover (ou pressione Enter para cancelar): ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println("Operação cancelada.");
            return;
        }

        toDoManager.deleteToDo(name);
        System.out.println("Tarefa removida com sucesso!");
    }

    private static void updateToDo(Scanner scanner) {
        System.out.print("Nome da Tarefa a Atualizar (ou pressione Enter para cancelar): ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println("Operação cancelada.");
            return;
        }

        System.out.print("Nova Descrição (ou pressione Enter para cancelar): ");
        String description = scanner.nextLine();
        if (description.isEmpty()) {
            System.out.println("Operação cancelada.");
            return;
        }

        System.out.print("Nova Data de Término (yyyy-mm-dd) (ou pressione Enter para cancelar): ");
        String endDateInput = scanner.nextLine();
        if (endDateInput.isEmpty()) {
            System.out.println("Operação cancelada.");
            return;
        }

        LocalDate endDate;
        try {
            endDate = LocalDate.parse(endDateInput);
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida. Operação cancelada.");
            return;
        }

        System.out.print("Nova Prioridade (1-5) (ou pressione Enter para cancelar): ");
        String priorityInput = scanner.nextLine();
        if (priorityInput.isEmpty()) {
            System.out.println("Operação cancelada.");
            return;
        }

        int priorityLevel;
        try {
            priorityLevel = Integer.parseInt(priorityInput);
            if (priorityLevel < 1 || priorityLevel > 5) {
                System.out.println("Prioridade inválida. Operação cancelada.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Número inválido. Operação cancelada.");
            return;
        }

        System.out.print("Nova Categoria (ou pressione Enter para cancelar): ");
        String category = scanner.nextLine();
        if (category.isEmpty()) {
            System.out.println("Operação cancelada.");
            return;
        }

        System.out.print("Novo Status (ToDo, Doing, Done) (ou pressione Enter para cancelar): ");
        String status = scanner.nextLine();
        if (status.isEmpty()) {
            System.out.println("Operação cancelada.");
            return;
        }

        ToDo updatedToDo = new ToDo(name, description, endDate, priorityLevel, category, status);
        toDoManager.updateToDo(name, updatedToDo);
        System.out.println("Tarefa atualizada com sucesso!");
    }

    private static void listToDos() {
        List<ToDo> toDos = toDoManager.listToDos();
        toDos.forEach(System.out::println);
    }

    private static void filterToDosByCategory(Scanner scanner) {
        System.out.print("Categoria: ");
        String category = scanner.nextLine();

        List<ToDo> toDos = toDoManager.listToDosByCategory(category);
        toDos.forEach(System.out::println);
    }

    private static void filterToDosByPriority(Scanner scanner) {
        System.out.print("Prioridade: ");
        int priority = scanner.nextInt();
        scanner.nextLine();

        List<ToDo> toDos = toDoManager.listToDosByPriority(priority);
        toDos.forEach(System.out::println);
    }

    private static void filterToDosByStatus(Scanner scanner) {
        System.out.print("Status: ");
        String status = scanner.nextLine();

        List<ToDo> toDos = toDoManager.listToDosByStatus(status);
        toDos.forEach(System.out::println);
    }

    private static void filterToDosByDate(Scanner scanner) {
        System.out.print("Data (yyyy-mm-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        List<ToDo> toDos = toDoManager.filterToDosByDate(date);
        toDos.forEach(System.out::println);
    }

    private static void countToDosByStatus() {
        toDoManager.countToDosByStatus();
    }




}
