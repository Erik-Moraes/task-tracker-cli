import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class TaskStorage {

    private static final String FILE_NAME = "tasks.json";


    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();

        File file = new File(FILE_NAME);
        if (!file.exists()) {
           return new ArrayList<>();
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
            // Variáveis para construir a task atual
            Task currentTask = null;

            for (String line : lines) {
                line = line.trim();

                if (line.contains("\"id\":")) {
                    currentTask = new Task();
                    currentTask.setId(extractNumber(line));
                }

                // Extrai description
                else if (line.contains("\"description\":") && currentTask != null) {
                    currentTask.setDescription(extractString(line));
                }

                // Extrai status
                else if (line.contains("\"status\":") && currentTask != null) {
                    currentTask.setStatus((Status.valueOf(extractString(line))));
                }

                // Extrai createdAt
                else if (line.contains("\"createdAt\":") && currentTask != null) {
                    currentTask.setCreatedAt(extractString(line));
                }

                // Extrai updatedAt (última propriedade da task)
                else if (line.contains("\"updatedAt\":") && currentTask != null) {
                    currentTask.setUpdatedAt(extractString(line));
                    // Task completa! Adiciona na lista
                    tasks.add(currentTask);
                    currentTask = null;
                }
            }

        } catch (IOException e) {
            System.err.println("Fail to read the file: " + e.getMessage());
        }

        return tasks;
    }

    public void saveTasks(List<Task> tasks, int nextId) {
        StringBuilder json = new StringBuilder();

        json.append("{\n");
        json.append("  \"tasks\": [\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            json.append("    {\n");
            json.append("      \"id\": ").append(task.getId()).append(",\n");
            json.append("      \"description\": \"").append(escapeJson(task.getDescription())).append("\",\n");
            json.append("      \"status\": \"").append(task.getStatus()).append("\",\n");
            json.append("      \"createdAt\": \"").append(task.getCreatedAt()).append("\",\n");
            json.append("      \"updatedAt\": \"").append(task.getUpdatedAt()).append("\"\n");
            json.append("    }");

            if (i < tasks.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("  ],\n");
        json.append("  \"nextId\": ").append(nextId).append("\n");
        json.append("}\n");

        try {
            Files.write(Paths.get(FILE_NAME), json.toString().getBytes());
        } catch (IOException e) {
            System.err.println("Fail to save: " + e.getMessage());
        }
    }


    // ========================================
    //          LER NEXT ID
    // ========================================

    public int getNextId() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return 1;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));

            for (String line : lines) {
                if (line.contains("\"nextId\":")) {
                    return extractNumber(line);
                }
            }

        } catch (IOException e) {
            System.err.println("❌ Erro ao ler nextId: " + e.getMessage());
        }

        return 1; // Valor padrão
    }

    // ========================================
    //          MÉTODOS AUXILIARES
    // ========================================

    // Extrai número de uma linha como: "id": 123,
    private int extractNumber(String line) {
        // Remove tudo exceto números
        String numbers = line.replaceAll("[^0-9]", "");

        if (numbers.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(numbers);
    }

    // Extrai string de uma linha como: "description": "Buy groceries",
    private String extractString(String line) {
        // Encontra o primeiro "
        int firstQuote = line.indexOf('"', line.indexOf(':'));

        // Encontra o último " (antes da vírgula ou fim)
        int lastQuote = line.lastIndexOf('"');

        if (firstQuote != -1 && lastQuote != -1 && lastQuote > firstQuote) {
            return line.substring(firstQuote + 1, lastQuote);
        }

        return "";
    }

    private String escapeJson(String text) {
        if (text == null) {
            return "";
        }

        return text.replace("\\", "\\\\")  // Barra invertida
                .replace("\"", "\\\"")  // Aspas
                .replace("\n", "\\n")   // Nova linha
                .replace("\r", "\\r")   // Retorno de carro
                .replace("\t", "\\t");  // Tab
    }

    // Cria arquivo JSON vazio
//    private void createEmptyFile() {
//        List<Task> emptyList = new ArrayList<>();
//        saveTasks(emptyList, 1);
//    }
}




