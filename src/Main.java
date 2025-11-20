import java.time.format.DateTimeFormatter;
import java.util.*;
import tasks.*;
import java.time.*;

public class Main {
    public static void main(String[] args) {

        // Create Scanner to read user input from console
        Scanner scanner = new Scanner(System.in);

        // Ask user for their username (creatorUserName for tasks)
        System.out.println("Enter your username: ");
        String currentUserName = scanner.nextLine();

        // Create TaskManager which holds all tasks in memory (and will load/save from file)
        TaskManager taskManager = new TaskManager();

        // Load tasks from file (so previously saved tasks are available)
        taskManager.loadFromFile();

        boolean exit = false;

        // Main program loop – keeps running until user chooses 0 (Exit)
        while(!exit){
            // Print the menu options
            printMenu();

            System.out.println("Choose option: ");
            // Read user choice as String then convert to int
            int choice = Integer.parseInt(scanner.nextLine());

            // Decide what to do based on user choice
            switch (choice) {
                case 1:
                    // 1. Create (save) a new task
                    handleCreateTask(scanner, taskManager, currentUserName);
                    break;
                case 2:
                    // 2. Show list of all tasks (their names)
                    handleListTasks(taskManager);
                    break;
                case 3:
                    // 3. Update an existing task
                    handleUpdateTask(scanner, taskManager);
                    break;
                case 4:
                    // 4. Delete a specific task
                    handleDeleteTask(scanner, taskManager);
                    break;
                case 5:
                    // 5. View details of a specific task
                    handleViewTask(scanner, taskManager);
                    break;
                case 0:
                    // 0. Exit the application
                    System.out.println("Exiting... Bye!");
                    exit = true;
                    break;
                default:
                    // Any other number is invalid
                    System.out.println("Invalid option, please try again.");
            }

        }

        // Close scanner before program ends (good practice)
        scanner.close();
    }

    // Prints the main menu options to the user
    private static void printMenu(){
        System.out.println();
        System.out.println("==== Task Management System ====");
        System.out.println("1. Save task");
        System.out.println("2. Take all tasks");
        System.out.println("3. Update specific task");
        System.out.println("4. Delete specific task");
        System.out.println("5. Take specific task");
        System.out.println("0. Exit");
        System.out.println("================================");
    }

    /**
     * Handles creating a new task:
     * - asks for name, definition, type
     * - depending on type, asks for extra fields
     * - creates the correct Task object (Basic / Limited / Repeatable)
     * - adds it to TaskManager and saves to file
     */
    private static void handleCreateTask(Scanner scanner, TaskManager taskManager, String currentUserName){
        System.out.println("Enter task name: ");
        String name = scanner.nextLine();

        // Check if a task with this name already exists (name must be unique)
        Task existing = taskManager.getTask(name);
        if(existing != null){
            System.out.println("Task with this name already exists");
            return; // stop creating
        }

        System.out.println("Enter task definition: ");
        String definition = scanner.nextLine();

        // Ask user to choose task type
        System.out.println("Choose task type: ");
        System.out.println("1. Basic");
        System.out.println("2. Limited (with deadline)");
        System.out.println("3. Repeatable");

        int typeChoice = Integer.parseInt(scanner.nextLine());

        // Will hold the new Task object we create
        Task newTask = null;

        // Create different task type depending on choice
        switch (typeChoice) {
            case 1:
                // Basic task – only name, definition, creator
                newTask = new BasicTask(name, definition, currentUserName);
                break;
            case 2:
                // Limited task – needs a deadline
                System.out.println("Enter deadline (dd/MM/yyyy HH:mm): ");
                String deadlineText = scanner.nextLine();

                try{
                    // Parse user input into LocalDateTime using given pattern
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    LocalDateTime deadline = LocalDateTime.parse(deadlineText, formatter);

                    newTask = new LimitedTimeTask(name, definition, currentUserName, deadline);
                }catch(Exception e){
                    // If parsing fails, we show error and do not create the task
                    System.out.println("Invalid date format. Task not created.");
                    return;
                }
                break;
            case 3:
                // Repeatable task – needs how many times and when
                System.out.println("How many times to do this task? ");
                int timesToDo = Integer.parseInt(scanner.nextLine());

                System.out.println("When (for example: daily, monthly, ...)?");
                String when = scanner.nextLine();

                newTask = new RepeatableTask(name, definition, currentUserName, timesToDo, when);
                break;
            default:
                // Any wrong number → cancel creation
                System.out.println("Invalid type. Task not created.");
                return;
        }

        // Try to add the new task to TaskManager
        boolean added = taskManager.addTask(newTask);
        if(!added){
            System.out.println("Could not add task.");
            return;
        }

        // Save all tasks to file after adding new one
        taskManager.saveToFile();
        System.out.println("Task added successfully.");

    }

    /**
     * Handles listing all task names.
     * Uses TaskManager.getAllTasks() and prints each task name.
     */
    private static void handleListTasks(TaskManager taskManager){
        List<Task> tasks = taskManager.getAllTasks();

        // If there are no tasks, show message and stop
        if(tasks.isEmpty()){
            System.out.println("No tasks available.");
            return;
        }

        System.out.println("List of tasks: ");
        // Print only task names
        for(Task task : tasks){
            System.out.println(task.getName());
        }

    }

    /**
     * Handles updating an existing task:
     * - asks for task name
     * - lets user choose to update definition or type-specific fields
     * - calls setters or updateExtraFields on the Task
     * - saves changes to file
     */
    private static void handleUpdateTask(Scanner scanner, TaskManager taskManager){
        System.out.println("Enter task name to update: ");
        String name = scanner.nextLine();

        // Find task by name
        Task task = taskManager.getTask(name);
        if(task == null){
            System.out.println("Task not found.");
            return;
        }

        // Let user choose what to update
        System.out.println("What do you want to update? ");
        System.out.println("1. Definition");
        System.out.println("2. Type-specific field (deadline / times / when)");
        int updateChoice = Integer.parseInt(scanner.nextLine());

        if(updateChoice == 1){
            // Update definition (common field for all tasks)
            System.out.println("Enter new definition: ");
            String newDefinition = scanner.nextLine();
            task.setDefinition(newDefinition);
        }else if(updateChoice == 2){
            // Update type-specific fields.
            // This assumes Task class (or subclasses) have an updateExtraFields(Scanner) method.
            task.updateExtraFields(scanner);
        }else{
            System.out.println("Invalid option.");
            return;
        }

        // Save updated tasks to file
        taskManager.saveToFile();
        System.out.println("Task updated successfully.");

    }

    /**
     * Handles deleting a task:
     * - asks for task name
     * - calls TaskManager.deleteTask()
     * - saves tasks to file if deleted
     */
    private static void handleDeleteTask(Scanner scanner, TaskManager taskManager){
        System.out.println("Enter Task name to delete: ");
        String name = scanner.nextLine();

        // Try deleting task by name
        boolean deleted = taskManager.deleteTask(name);
        if(!deleted){
            System.out.println("Could not delete task.");
            return;
        }

        // Save after deletion
        taskManager.saveToFile();
        System.out.println("Task deleted successfully.");

    }

    /**
     * Handles viewing one specific task:
     * - asks for task name
     * - fetches that task from TaskManager
     * - prints its details using getDetails()
     */
    private static void handleViewTask(Scanner scanner, TaskManager taskManager){
        System.out.println("Enter task name to view: ");
        String name = scanner.nextLine();

        // Get task by name
        Task task = taskManager.getTask(name);
        if(task == null){
            System.out.println("Task not found.");
            return;
        }

        System.out.println("Task details: ");
        // Polymorphic call – each subclass has its own getDetails() implementation
        System.out.println(task.getDetails());

    }
}

