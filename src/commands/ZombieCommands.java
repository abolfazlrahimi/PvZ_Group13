package commands;
import model.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public abstract class ZombieCommands {
    public static ArrayList<LoginCommand> allCommand = new ArrayList<>();
    public Pattern pattern;
    String input;
    Menu menu;

    ZombieCommands(String input, Menu menuPtr) {
        this.input = input;
        this.menu = menuPtr;
    }

    public static void createCommands(String input, Menu menuPtr) {

        allCommand.add(new CreateAccount(input, menuPtr));
    }

    abstract public void action(Menu menuPtr);


}