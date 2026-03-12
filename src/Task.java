import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Task {

    private int id;
    private static int lastId = 0;
    private Status status;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /*
    * ToDo - Alterar formatação de data para DD/MM/YYYY*/
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    public void markTodo(){
        this.status = Status.TODO;
    }

    public void markInProgress(){
        this.status = Status.IN_PROGRESS;
    }

    public void markDone (){
        this.status = Status.DONE;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getLastId() {
        return lastId;
    }

    public static void setLastId(int lastId) {
        Task.lastId = lastId;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getId (){
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public Task(int id, Status status, String description, String createdAt, String updatedAt) {
        this.id = ++lastId;
        this.status = Status.TODO;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }

}
