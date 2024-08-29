import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ToDoManager {
    private List<ToDo> toDos;
    public ToDoManager() { this.toDos = new ArrayList<>();}


    public void addToDo(ToDo toDo){
        toDos.add(toDo);
        toDos.sort(Comparator.comparingInt(ToDo::getPriorityLevel).reversed());
    }


    public void deleteToDo(String name){
        toDos.removeIf(toDo -> toDo.getName().equalsIgnoreCase(name));
    }

    public void updateToDo(String name, ToDo updatedToDo) {
        for (int i = 0; i < toDos.size(); i++) {
            if (toDos.get(i).getName().equalsIgnoreCase(name)) {
                toDos.set(i, updatedToDo);
                toDos.sort(Comparator.comparingInt(ToDo::getPriorityLevel).reversed());
                break;
            }
        }
    }

    public List<ToDo> listTasks() {
        return new ArrayList<>(toDos);
    }

    public List<ToDo> listToDosByCategory(String category) {
        return toDos.stream()
                .filter(toDo -> toDo.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<ToDo> listToDosByPriority(int priority) {
        return toDos.stream()
                .filter(toDo -> toDo.getPriorityLevel() == priority)
                .collect(Collectors.toList());
    }

    public List<ToDo> listToDosByStatus(String status) {
        return toDos.stream()
                .filter(toDo -> toDo.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    public List<ToDo> filterToDosByDate(LocalDate date) {
        return toDos.stream()
                .filter(toDo -> toDo.getEndDate().equals(date))
                .collect(Collectors.toList());
    }

    public void countToDosByStatus() {
        long todoCount = toDos.stream().filter(toDo -> toDo.getStatus().equalsIgnoreCase("ToDo")).count();
        long doingCount = toDos.stream().filter(toDo -> toDo.getStatus().equalsIgnoreCase("Doing")).count();
        long doneCount = toDos.stream().filter(toDo -> toDo.getStatus().equalsIgnoreCase("Done")).count();

        System.out.println("ToDo: " + todoCount);
        System.out.println("Doing: " + doingCount);
        System.out.println("Done: " + doneCount);
    }

    public void checkAllAlarms() {
        for (ToDo toDo : toDos) {
            toDo.checkAlarms();
        }
    }

    public void saveToDosToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (ToDo toDo : toDos) {
                StringBuilder sb = new StringBuilder();
                sb.append(toDo.getName()).append(",");
                sb.append(toDo.getDescription()).append(",");
                sb.append(toDo.getEndDate()).append(",");
                sb.append(toDo.getPriorityLevel()).append(",");
                sb.append(toDo.getStatus()).append(",");

                List<LocalDateTime> alarms = toDo.getAlarms();
                for (int i = 0; i < alarms.size(); i++) {
                    sb.append(alarms.get(i).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    if (i < alarms.size() - 1) {
                        sb.append(";");
                    }
                }

                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar as tarefas: " + e.getMessage());
        }
    }

    public void loadToDosFromFile(String filename) {
        if (!Files.exists(Paths.get(filename))) {
            System.out.println("Arquivo " + filename + " não encontrado. Nenhuma tarefa foi carregada.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String name = parts[0];
                    String description = parts[1];
                    LocalDate endDate = LocalDate.parse(parts[2]);
                    int priorityLevel = Integer.parseInt(parts[3]);
                    String category = parts[4];
                    String status = parts[5];

                    ToDo toDo = new ToDo(name, description, endDate, priorityLevel, category, status);

                    if (parts.length == 7) {
                        String[] alarmParts = parts[6].split(";");
                        for (String alarm : alarmParts) {
                            LocalDateTime alarmTime = LocalDateTime.parse(alarm, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                            toDo.addAlarm(alarmTime);
                        }
                    }

                    toDos.add(toDo);
                }
            }

            toDos.sort(Comparator.comparingInt(ToDo::getPriorityLevel).reversed());

        } catch (IOException e) {
            System.err.println("Erro ao carregar as tarefas: " + e.getMessage());
        }
    }
}











