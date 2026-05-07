# Task Tracker CLI

A simple command-line task tracker that allows users to manage tasks directly from the terminal. From https://roadmap.sh/projects/task-tracker.

---

# Features

- Add tasks
- Update tasks
- Delete tasks
- Mark tasks as:
  - Todo
  - In Progress
  - Done
- List all tasks
- Filter tasks by status

---

# How It Works

### Clone the repository
```
git clone https://github.com/Erik-Moraes/task-tracker-cli
cd task-tracker-cli
 ```
### Compile

```bash
javac *.java
```

## Run

### Add task

```bash
java TaskCLI add "Study Java"
```

### Update task

```bash
java TaskCLI update 1 "Study Java Streams"
```
### Delete task

```bash
java TaskCLI delete 1
```

### Mark as in progress

```bash
java TaskCLI mark-in-progress 1
```

### Mark as done

```bash
java TaskCLI mark-done 1
```

### List all tasks

```bash
java TaskCLI list
```

### Done

```bash
java TaskCLI list done
```

### Todo

```bash
java TaskCLI list todo
```

### In Progress

```bash
java TaskCLI list in-progress
```

---
