package tasks;

import java.util.Scanner;

public abstract class Task {
    private String name;

    private String definition;

    private String creatorUserName;

    public Task(String name, String definition, String creatorUserName){
        this.name = name;
        this.definition = definition;
        this.creatorUserName = creatorUserName;
    }

    public String getName(){
        return name;
    }

    public String getDefinition(){
        return definition;
    }

    public String getCreatorUserName(){
        return creatorUserName;
    }

    public void setDefinition(String definition){
        this.definition = definition;
    }

    public abstract String getDetails();

    public abstract String toFileString();

    public abstract void updateExtraFields(Scanner scanner);

}
