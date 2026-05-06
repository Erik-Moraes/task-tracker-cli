public enum Status {

   TODO,
   IN_PROGRESS,
   DONE;

    public static Status fromString(String value) {
        return switch (value.toLowerCase()) {
            case "todo" -> TODO;
            case "in-progress" -> IN_PROGRESS;
            case "done" -> DONE;
            default -> throw new IllegalArgumentException("Invalid Status");
        };
    }

    public String toJson() {
        return switch (this) {
            case TODO -> "todo";
            case IN_PROGRESS -> "in-progress";
            case DONE -> "done";
        };
    }
}

