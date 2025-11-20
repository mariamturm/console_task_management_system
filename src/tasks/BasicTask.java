package tasks;

public class BasicTask extends Task {

    private String type;


    public BasicTask(String name, String definition, String creatorUserName){
        super(name, definition, creatorUserName);
        this.type = "Basic";
    }

    public String getType() {
        return type;
    }

    @Override
    public String getDetails() {
        return getName() + ", " + getDefinition() + ", " + getCreatorUserName();
    }

    @Override
    public String toFileString() {
        //type|name|definition|creatorUserName
        return getType() + "|" + getName() + "|" +getDefinition() + "|" +getCreatorUserName();

    }
}
