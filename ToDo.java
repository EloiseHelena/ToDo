import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ToDo {

    private String name;
    private String description;
    private LocalDate endDate;
    private int priorityLevel;
    private String category;
    private String status;
    private List<LocalDateTime> alarms;

    public ToDo(String name, String description, LocalDate endDate, int priorityLevel, String category, String status){
        this.name = name;
        this.description = description;
        this.endDate = endDate;
        this.priorityLevel = priorityLevel;
        this.category = category;
        this.status = status;
        this.alarms = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    public int getPriorityLevel() {
        return priorityLevel;
    }
    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }


    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }


    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }


    public List<LocalDateTime> getAlarms() {
        return alarms;
    }

    public void addAlarm(LocalDateTime alarm){
        this.alarms.add(alarm);
    }

    public void checkAlarms(){
        LocalDateTime now = LocalDateTime.now();
        List<LocalDateTime> alarmsToRemove = new ArrayList<>();

        for (LocalDateTime alarm : alarms){
            if (alarm.isBefore(now) || alarm.isEqual(now)){
                System.out.println("Está na hora da tarefa '" + name + "'! Descrição:" + description);
                alarmsToRemove.add(alarm);
            }
        }
        alarms.removeAll(alarmsToRemove);
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "Nome='" + name + '\'' +
                ", Descrição='" + description + '\'' +
                ", Data Final=" + endDate +
                ", Prioridade=" + priorityLevel +
                ", Categoria='" + category + '\'' +
                ", Status='" + status + '\'' +
                ", Alarmes=" + alarms +
                '}';
    }
}


