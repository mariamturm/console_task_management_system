package tasks;

public class RepeatableTask extends Task {
    private int howManyTimes;

    private String when;

    public RepeatableTask(String name, String definition, String creatorUserName, int howManyTimes, String when){
        super(name, definition, creatorUserName);
        this.howManyTimes = howManyTimes;
        this.when = when;
    }

    public int getHowManyTimes(){
        return howManyTimes;
    }

    public String getWhen(){
        return when;
    }

    public void setHowManyTimes(int howManyTimes){
        this.howManyTimes = howManyTimes;
    }

    public void setWhen(String when){
        this.when = when;
    }

    @Override
    public String getDetails() {
        return getName() + ", " + getDefinition() + ", " + getCreatorUserName() + ", " + howManyTimes + ", " + when;
    }
}
