import tasks.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;

public class TaskManager {

    // Stores all tasks using task name as key (name must be unique)
    private Map<String, Task> tasks;

    // Location of the file used to save/load tasks
    private String filePath;

    public TaskManager() {
        // Initialize HashMap and set the file path
        this.tasks = new HashMap<>();
        this.filePath = "tasks.txt";
        // loadFromFile();   // loading happens manually in Main
    }

    // Add a new task to the map
    public boolean addTask(Task task){
        // If a task with this name already exists → fail
        if(tasks.containsKey(task.getName())){
            return false;
        }else{
            // Otherwise add it
            tasks.put(task.getName(), task);
            return true;
        }
    }

    // Return a task by name (or null if not found)
    public Task getTask(String name){
        return tasks.get(name);
    }

    // Return all tasks as a List
    public List<Task> getAllTasks(){
        return new ArrayList<>(tasks.values());
    }

    // Delete a task by name
    public boolean deleteTask(String name){
        if(tasks.containsKey(name)){
            tasks.remove(name);
            return true;
        }else{
            return false;
        }
    }

    // Return a list of all task names
    public List<String> getTaskNames(){
        return new ArrayList<>(tasks.keySet());
    }

    // Load tasks from file (tasks.txt)
    public void loadFromFile() {
        File file = new File(filePath);

        // 1. If file does not exist, nothing to load
        if (!file.exists()) {
            return;
        }

        // 2. Clear current tasks to avoid duplicating entries
        tasks.clear();

        // 3. Formatter for parsing LocalDateTime in file
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        // Example expected deadline string format: 2025-01-22T18:00

        try (Scanner myReader = new Scanner(file)) {

            // 4. Read file line-by-line
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine().trim();

                // Skip completely empty lines
                if (line.isEmpty()) {
                    continue;
                }

                // 5. Split by "|" separator
                String[] parts = line.split("\\|");

                // Must have at least type, name, definition, creator
                if (parts.length < 4) {
                    continue; // malformed line → skip
                }

                String type = parts[0];
                String name = parts[1];
                String definition = parts[2];
                String creatorUserName = parts[3];

                // Will store the created Task object
                Task task = null;

                // 6. Decide task type and rebuild the object
                switch (type) {
                    case "BASIC":
                        // BasicTask has no extra fields
                        task = new BasicTask(name, definition, creatorUserName);
                        break;

                    case "LIMITED":
                        // Must have a valid deadline in parts[4]
                        if (parts.length >= 5 && !parts[4].isEmpty()) {
                            LocalDateTime deadline = LocalDateTime.parse(parts[4], formatter);
                            task = new LimitedTimeTask(name, definition, creatorUserName, deadline);
                        }
                        break;

                    case "REPEATABLE":
                        // parts[5] = howManyTimes, parts[6] = when
                        if (parts.length >= 7) {
                            int howManyTimes = Integer.parseInt(parts[5]);
                            String when = parts[6];
                            task = new RepeatableTask(name, definition, creatorUserName, howManyTimes, when);
                        }
                        break;

                    default:
                        // Unknown task type → skip
                        break;
                }

                // 7. If task was correctly reconstructed, save it to the map
                if (task != null) {
                    tasks.put(name, task);
                }
            }

        } catch (FileNotFoundException e) {
            // Should not happen because we checked file.exists(), but kept as safety
            e.printStackTrace();
        }
    }

    // Save all tasks to file by writing each task's toFileString() result
    public void saveToFile() {
        File file = new File(filePath);

        // Use PrintWriter + FileWriter to write lines into tasks.txt
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {

            // For each task, write a single line using its toFileString() method
            for (Task task : tasks.values()) {
                writer.println(task.toFileString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
