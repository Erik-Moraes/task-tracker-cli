import java.util.Optional;

public class TaskCliApp {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();


        if (args.length == 0) {
            help();
            return;
        }

        String command = args [0];

        try {
            switch (command) {

                case "add" -> manager.addTask(args[1]);

                case "update" ->
                        manager.updateTask(Integer.parseInt(args[1]), args[2]);

                case "delete" ->
                        manager.deleteTask(Integer.parseInt(args[1]));

                case "mark-in-progress" ->
                        manager.markTask(Integer.parseInt(args[1]), Status.IN_PROGRESS);

                case "mark-done" ->
                        manager.markTask(Integer.parseInt(args[1]), Status.DONE);

                case "list" -> {
                    if (args.length == 1) {
                        manager.listTasks(Optional.empty());
                    } else {
                        manager.listTasks(Optional.of(Status.fromString(args[1])));
                    }
                }

                default -> help();
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    private static void help() {
        System.out.println("""
        Commands:
        add "description"
        update ID "description"
        delete ID
        mark-in-progress ID
        mark-done ID
        list
        list todo|done|in-progress
        """);
    }
}