public class TaskCliApp {
    public static void main(String[] args) {

    Task task1 = new Task(1, Status.TODO, "teste", "05/03/2026", "05/03/2026" );
    Task task2 = new Task(2, Status.DONE, "teste2", "05/03/2026", "05/03/2026" );

        System.out.println(task1.getDescription());
        System.out.println(task1.getId());
        System.out.println(task1.getStatus());
        System.out.println("==============================");
    task1.markDone();
        System.out.println(task1.getDescription());
        System.out.println(task1.getId());
        System.out.println(task1.getStatus());


    }
}