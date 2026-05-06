import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Task {

    private int id;
    private String description;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = Status.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public Status getStatus() { return status; }

    public void updateDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateStatus(Status status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public String toJson() {
        return String.format(
                "{\"id\":%d,\"description\":\"%s\",\"status\":\"%s\",\"createdAt\":\"%s\",\"updatedAt\":\"%s\"}",
                id,
                escape(description),
                status.toJson(),
                createdAt.format(FORMATTER),
                updatedAt.format(FORMATTER)
        );
    }

    public static Task fromJson(String json) {
        Map<String, String> map = parse(json);

        Task t = new Task(
                Integer.parseInt(map.get("id")),
                map.get("description")
        );

        t.status = Status.fromString(map.get("status"));
        t.createdAt = LocalDateTime.parse(map.get("createdAt"), FORMATTER);
        t.updatedAt = LocalDateTime.parse(map.get("updatedAt"), FORMATTER);

        return t;
    }

    private static Map<String, String> parse(String json) {
        Map<String, String> map = new HashMap<>();

        json = json.substring(1, json.length() - 1); // remove {}

        String[] pairs = json.split(",");

        for (String pair : pairs) {
            String[] kv = pair.split(":", 2);

            String key = kv[0].replace("\"", "").trim();
            String value = kv[1].replace("\"", "").trim();

            map.put(key, value);
        }

        return map;
    }

    private String escape(String s) {
        return s.replace("\"", "\\\"");
    }
}