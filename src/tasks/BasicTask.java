package tasks;

public class BasicTask extends Task {

    public BasicTask(String name, String definition, String creatorUserName){
        super(name, definition, creatorUserName);
    }

    @Override
    public String getDetails() {
        return getName() + ", " + getDefinition() + ", " + getCreatorUserName();
    }
}
