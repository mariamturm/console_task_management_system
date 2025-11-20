import java.time.format.DateTimeFormatter;
import java.util.*;
import tasks.*;
import java.time.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String currentUserName = scanner.nextLine();

        TaskManager taskManager = new TaskManager();
        taskManager.loadFromFile();

        boolean exit = false;


        while(!exit){
            printMenu();
            System.out.println("Choose option: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    handleCreateTask(scanner, taskManager, currentUserName);
                    break;
                case 2:
                    handleListTasks(taskManager);
                    break;
                case 3:
                    handleUpdateTask(scanner, taskManager);
                    break;
                case 4:
                    handleDeleteTask(scanner, taskManager);
                    break;
                case 5:
                    handleViewTask(scanner, taskManager);
                    break;
                case 0:
                    System.out.println("Exiting... Bye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }

        }
        scanner.close();
    }

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

    private static void handleCreateTask(Scanner scanner, TaskManager taskManager, String currentUserName){
        System.out.println("Enter task name: ");
        String name = scanner.nextLine();

        Task existing = taskManager.getTask(name);
        if(existing != null){
            System.out.println("Task with this name already exists");
            return;
        }

        System.out.println("Enter task definition: ");
        String definition = scanner.nextLine();

        System.out.println("Choose task type: ");
        System.out.println("1. Basic");
        System.out.println("2. Limited (with deadline)");
        System.out.println("3. Repeatable");

        int typeChoice = Integer.parseInt(scanner.nextLine());

        Task newTask = null;

        switch (typeChoice) {
            case 1:
                newTask = new BasicTask(name, definition, currentUserName);
                break;
            case 2:
                System.out.println("Enter deadline (dd/MM/yyyy HH:mm): ");
                String deadlineText = scanner.nextLine();

                try{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    LocalDateTime deadline = LocalDateTime.parse(deadlineText, formatter);

                    newTask = new LimitedTimeTask(name, definition, currentUserName, deadline);
                }catch(Exception e){
                    System.out.println("Invalid date format. Task not created.");
                    return;
                }
                break;
            case 3:
                System.out.println("How many times to do this task? ");
                int timesToDo = Integer.parseInt(scanner.nextLine());

                System.out.println("When (for example: daily, monthly, ...)?");
                String when = scanner.nextLine();

                newTask = new RepeatableTask(name, definition, currentUserName, timesToDo, when);
                break;
            default:
                System.out.println("Invalid type. Task not created.");
                return;
        }

        boolean added = taskManager.addTask(newTask);
        if(!added){
            System.out.println("Could not add task.");
            return;
        }

        taskManager.saveToFile();
        System.out.println("Task added successfully.");

    }

    private static void handleListTasks(TaskManager taskManager){

    }

    private static void handleUpdateTask(Scanner scanner, TaskManager taskManager){

    }

    private static void handleDeleteTask(Scanner scanner, TaskManager taskManager){

    }

    private static void handleViewTask(Scanner scanner, TaskManager taskManager){

    }
}
