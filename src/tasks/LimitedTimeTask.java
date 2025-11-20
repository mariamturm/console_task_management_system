package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
}
