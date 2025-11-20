import java.util.*;
import tasks.*;

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
