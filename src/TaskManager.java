import java.io.BufferedWriter;
import java.nio.file.*;
import java.util.*;

public class TaskManager {

    private static final String FILE = "tasks.json";


    private List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();

        try {
            Path path = Path.of(FILE);

            if (!Files.exists(path)) {
                Files.createFile(path);
                return tasks;
            }

            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                if (!line.isBlank()) {
                    tasks.add(Task.fromJson(line));
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao carregar tarefas.");
        }

        return tasks;
    }

    private void saveTask(List<Task> tasks) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(FILE))) {

            for (Task task : tasks) {
                writer.write(task.toJson());
                writer.newLine();
            }

        } catch (Exception e) {
            System.out.println("Erro ao salvar tarefas.");
        }
    }

    public void addTask(String description) {
        List<Task> tasks = loadTasks();
        int nextId = generateId(tasks);

        tasks.add(new Task(nextId, description));
        saveTask(tasks);

        System.out.println("Tarefa adicionada.");
    }

    public void updateTask(int id, String description) throws Exception {
        List<Task> tasks = loadTasks();
        Task task = findTasks(tasks, id);

        task.updateDescription(description);
        saveTask(tasks);

        System.out.println("Tarefa atualizada.");
    }

    public void deleteTask(int id) {
        List<Task> tasks = loadTasks();
        tasks.removeIf(t -> t.getId() == id);
        saveTask(tasks);

        System.out.println("Tarefa removida.");
    }

    public void markTask(int id, Status status) throws Exception {
        List<Task> tasks = loadTasks();
        Task task = findTasks(tasks, id);

        task.updateStatus(status);
        saveTask(tasks);

        System.out.println("Status atualizado.");
    }

    public void listTasks(Optional<Status> filter) {
        List<Task> tasks = loadTasks();

        if (tasks.isEmpty()) {
            System.out.println("Nenhuma tarefa.");
            return;
        }

        tasks.stream()
                .filter(t -> filter.map(f -> t.getStatus() == f).orElse(true))
                .forEach(t ->
                        System.out.printf("[%s] %d - %s%n",
                                t.getStatus().toJson(),
                                t.getId(),
                                t.getDescription())
                );
    }


    private Task findTasks(List<Task> tasks, int id) throws Exception {
        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new Exception("Tarefa não encontrada"));
    }

    private int generateId(List<Task> tasks) {
        return tasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0) + 1;
    }
}