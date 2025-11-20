package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class LimitedTimeTask extends Task{

    private String type;

    private LocalDateTime deadline;

    public LimitedTimeTask(String name, String definition, String creatorUserName, LocalDateTime deadline){
        super(name, definition, creatorUserName);
        this.deadline = deadline;
        this.type = "Limited";
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getDeadline(){
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline){
        this.deadline = deadline;
    }

    @Override
    public String getDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedDeadline = deadline.format(formatter);

        return getName() + ", "
                + getDefinition() + ", "
                + getCreatorUserName() + ", "
                + formattedDeadline;
    }


    @Override
    public String toFileString() {
        //Type|name|definition|creatorUserName|deadline
        return getType() + "|" + getName() + "|" +getDefinition() + "|" +getCreatorUserName() + "|" + deadline;
    }

    @Override
    public void updateExtraFields(Scanner scanner) {
        System.out.println("Enter new deadline (dd/MM/yyyy HH:mm): ");
        String deadlineText = scanner.nextLine();
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime newDeadline = LocalDateTime.parse(deadlineText, formatter);
            setDeadline(newDeadline);
            System.out.println("Deadline updated.");
        }catch(Exception e){
            System.out.println("Invalid date format.");
        }
    }
}
