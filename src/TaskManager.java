import tasks.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
import java.nio.*;


public class TaskManager {

    //Map<String, Task> tasks = new HashMap<>();
    private Map<String, Task> tasks;

    private String filePath;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.filePath = "tasks.txt";
        //loadFromFile();
    }

    public boolean addTask(Task task){
        if(tasks.containsKey(task.getName())){
            return false;
        }else{
            tasks.put(task.getName(), task);
            //saveToFile();
            return true;
        }

    }

    public Task getTask(String name){
        return tasks.get(name);
        //will return null if it does not exist
    }

    public List<Task> getAllTasks(){
        return new ArrayList<>(tasks.values());
    }

    public boolean deleteTask(String name){
        if(tasks.containsKey(name)){
            tasks.remove(name);
            //saveToFile();
            return true;
        }else{
            return false;
        }
    }

    public List<String> getTaskNames(){
        return new ArrayList<>(tasks.keySet());
    }

    public void loadFromFile() {
        File file = new File(filePath);

        // 1. If file does not exist yet, there is nothing to load
        if (!file.exists()) {
            return;
        }

        // 2. Clear current tasks so we don't duplicate if loadFromFile() is called again
        tasks.clear();

        // 3. Formatter for parsing deadline strings (must match what you use in saveToFile)
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        // Example stored deadline: 2025-01-22T18:00

        try (Scanner myReader = new Scanner(file)) {

            // 4. Read file line by line
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine().trim();

                // skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                // 5. Split the line into parts by "|"
                String[] parts = line.split("\\|");

                // We expect at least TYPE, name, definition, creator
                if (parts.length < 4) {
                    // malformed line, skip it
                    continue;
                }

                String type = parts[0];
                String name = parts[1];
                String definition = parts[2];
                String creatorUserName = parts[3];

                Task task = null;

                // 6. Decide which subclass to create based on TYPE
                switch (type) {
                    case "BASIC":
                        task = new BasicTask(name, definition, creatorUserName);
                        break;

                    case "LIMITED":
                        // parts[4] should contain the deadline string, if it contains then it is repeatable
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
                        // unknown type, skip
                        break;
                }

                // 7. If we successfully created a task, put it in the map
                if (task != null) {
                    tasks.put(name, task);
                }
            }

        } catch (FileNotFoundException e) {
            // Shouldn't happen because we checked exists(), but just in case
            e.printStackTrace();
        }
    }

//    private void saveToFile(){
//        try {
//            File file = new File(filePath);
//            if(file.createNewFile()){
//               for (Task task : tasks.values()){
//                   task.toFileString();
//               }
//
//            }else{
//
//            }
//        }
//    }

    private void saveToFile() {
        File file = new File(filePath);

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            // loop through all tasks and write each as a line
            for (Task task : tasks.values()) {
                writer.println(task.toFileString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
