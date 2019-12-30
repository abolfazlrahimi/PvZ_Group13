package commands;
import model.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PlayMenuCommands {
    public static ArrayList<LoginCommand> allCommand = new ArrayList<>();
    public Pattern pattern;
    String input;
    Menu menu;

    PlayMenuCommands(String input, Menu menuPtr) {
        this.input = input;
        this.menu = menuPtr;
    }

    public static void createCommands(String input, Menu menuPtr) {
        allCommand.add(new CreateAccount(input, menuPtr));
    }

    abstract public void action(Menu menuPtr);

}


class DayCommand extends PlayMenuCommands {
    Pattern pattern = Pattern.compile("day");
    DayCommand(String input, Menu menuPtr) {
        super(input, menuPtr);
    }

    @Override
    public void action(Menu menuPtr) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()){
            menuPtr = new PlantCollectionMenu();
            // TODO: 12/29/2019
        }
    }
}



class WaterCommand extends PlayMenuCommands {
    Pattern pattern = Pattern.compile("water");
    WaterCommand(String input, Menu menuPtr) {
        super(input, menuPtr);
    }

    @Override
    public void action(Menu menuPtr) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()){
            menuPtr = new PlantCollectionMenu();
            // TODO: 12/29/2019
        }
    }
}


class RailCommand extends PlayMenuCommands {
    Pattern pattern = Pattern.compile("rail");
    RailCommand(String input, Menu menuPtr) {
        super(input, menuPtr);
    }

    @Override
    public void action(Menu menuPtr) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()){
            menuPtr = new RailMenu();
            // TODO: 12/29/2019
        }
    }
}


class ZombieCommand extends PlayMenuCommands {
    Pattern pattern = Pattern.compile("zombie");
    ZombieCommand(String input, Menu menuPtr) {
        super(input, menuPtr);
    }

    @Override
    public void action(Menu menuPtr) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()){
            menuPtr = new ZombieCollectionMenu();
            // TODO: 12/29/2019
        }
    }
}

class PvPCommand extends PlayMenuCommands {
    Pattern pattern = Pattern.compile("pvp  (\\d)+");
    PvPCommand(String input, Menu menuPtr) {
        super(input, menuPtr);
    }

    @Override
    public void action(Menu menuPtr) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()){
            // TODO: 12/29/2019
        }
    }
}

