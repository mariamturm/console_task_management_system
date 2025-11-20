package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LimitedTimeTask extends Task{

    private LocalDateTime deadline;

    public LimitedTimeTask(String name, String definition, String creatorUserName, LocalDateTime deadline){
        super(name, definition, creatorUserName);
        this.deadline = deadline;
    }

    public LocalDateTime getDeadline(){
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline){
        this.deadline = deadline;
    }

    @Override
    public String getDetails() {
        deadline.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        return getName() + ", " + getDefinition() + ", " + getCreatorUserName() + ", " + deadline;
//        super.getDetails();
//        return deadline.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
