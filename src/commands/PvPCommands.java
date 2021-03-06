package commands;

import commands.Menu.Menu;
import commands.Menu.PlayMenu;
import commands.Menu.PvPMenu;
import in_game.*;
import model.Card;
import model.Plant;
import model.Zombie;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PvPCommands {
    public static ArrayList<PvPCommands> allCommand = new ArrayList<>();
    public Pattern pattern;
    String input;
    Menu menu;

    PvPCommands(String input, Menu menuPtr) {
        this.input = input;
        this.menu = menuPtr;
    }

    public static void createCommands(String input, Menu menuPtr) {
        allCommand = new ArrayList<>();
        allCommand.add(new EndTurnPvP(input, menuPtr));
        allCommand.add(new PlantPvPDay(input, menuPtr));
        allCommand.add(new PutPvP(input, menuPtr));
        allCommand.add(new Ready(input, menuPtr));
        allCommand.add(new Ready(input, menuPtr));
        allCommand.add(new RemovePvPDay(input, menuPtr));
        allCommand.add(new SelectPvPDay(input, menuPtr));
        allCommand.add(new ShowHandPvPDay(input, menuPtr));
        allCommand.add(new ShowHandPvPZombie(input, menuPtr));
        allCommand.add(new ShowHandPvPZombie(input, menuPtr));
        allCommand.add(new ShowLanesPvP(input, menuPtr));
        allCommand.add(new ShowLawnPvP(input, menuPtr));
        allCommand.add(new ShowLawnPvPZombie(input, menuPtr));
        allCommand.add(new StartPvP(input, menuPtr));
    }

    abstract public Menu action(Menu menuPtr, PvPGame pvpGame);

}

class ShowHandPvPDay extends PvPCommands {
    ShowHandPvPDay(String input, Menu menuPtr) {
        super(input, menuPtr);
        pattern = Pattern.compile("show hand", Pattern.CASE_INSENSITIVE);

    }

    @Override
    public Menu action(Menu menuPtr, PvPGame pvpGame) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            DynamicPVP dynamicPVP = new DynamicPVP(pvpGame);
            Dynamic.printer(pvpGame.getCards(), "Names", "SunsTheyNeed");
            System.out.println("all the suns you need for current plants: " + dynamicPVP.demandingSuns());
        }
        return menuPtr;
    }
}


class SelectPvPDay extends PvPCommands {
    SelectPvPDay(String input, Menu menuPtr) {
        super(input, menuPtr);
        pattern = Pattern.compile("select (.)+", Pattern.CASE_INSENSITIVE);

    }

    @Override
    public Menu action(Menu menuPtr, PvPGame pvpGame) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String cardName = matcher.group(1);
            //does the player has this card?
            Plant wantedPlant = Dynamic.findPlant(cardName);
            if (wantedPlant == null) {
                InvalidPrompt invalidPrompt = () -> {
                    System.out.println("INVALID CARD NAME!");
                };
                invalidPrompt.action();
            } else {
                DynamicPVP dynamicPVP = new DynamicPVP(pvpGame);
                Card wantedCard = Dynamic.findPlant(wantedPlant);
                if (wantedCard == null) {
                    InvalidPrompt invalidPrompt = () -> {
                        System.out.println("CARD NOT ON YOUR LIST!");
                    };
                    invalidPrompt.action();
                    return menuPtr;
                }
                if (dynamicPVP.canIChoose(wantedCard))
                    wantedCard.setSelect(true);
                else {
                    InvalidPrompt invalidPrompt = () -> {
                        System.out.println("YOU DON'T HAVE ENOUGH SUNS!");
                    };
                    invalidPrompt.action();
                }
            }
        }
        return menuPtr;
    }
}


class PlantPvPDay extends PvPCommands {
    PlantPvPDay(String input, Menu menuPtr) {
        super(input, menuPtr);
        pattern = Pattern.compile("Plant ((.),(.))+");

    }

    @Override
    public Menu action(Menu menuPtr, PvPGame pvpGame) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            Card card = Dynamic.findSelectedCard(pvpGame);
            if (card == null) {
                InvalidPrompt invalidPrompt = () -> {
                    System.out.println("NO CARD IS SELECTED!");
                };
                invalidPrompt.action();
            } else {
                String num1 = matcher.group(2);
                String num2 = matcher.group(3);
                try {
                    int x = Integer.parseInt(num1);
                    int y = Integer.parseInt(num2);
                    Plant plant = Dynamic.findPlant(Dynamic.findPlant(card));
                    if ((x >= 0) && (x <= 2) && (y >= 0) && (y <= 5)) {
                        Dynamic.setPlantPosition(x, y, plant, pvpGame);
                    } else {
                        InvalidPrompt invalidPrompt = () -> {
                            System.out.println("FIRST INTEGER MUST SATISFY:");
                            System.out.println("0 <= x <= 2");
                            System.out.println("SECOND INTEGER MUST SATISFY:");
                            System.out.println("0 <= Y <= 5.");
                        };
                        invalidPrompt.action();
                    }

                } catch (NumberFormatException e) {
                    System.out.println("PLEASE ENTER TWO INTEGERS ALIKE:");
                    System.out.println("INT,INT AFTER Plant. THE FIRST INTEGER");
                    System.out.println("MUST BE A NUMBER BETWEEN 0 AND 18 AND");
                    System.out.println("THE SECOND INTEGER IS A NUMBER BETWEEN 0 AND 5!");
                }


            }
        }

        return menuPtr;


    }
}


class RemovePvPDay extends PvPCommands {
    RemovePvPDay(String input, Menu menuPtr) {
        super(input, menuPtr);
        pattern = Pattern.compile("remove ((.),(.))+", Pattern.CASE_INSENSITIVE);

    }

    @Override
    public Menu action(Menu menuPtr, PvPGame pvpGame) {
        Matcher matcher = pattern.matcher(input.toLowerCase());
        if (matcher.matches()) {
            String num1 = matcher.group(2);
            String num2 = matcher.group(3);
            try {
                int x = Integer.parseInt(num1);
                int y = Integer.parseInt(num2);
                if ((x >= 0) && (x <= 18) && (y >= 0) && (y <= 5)) {
                    Plant plant = Graphic.findPlant(x, y);
                    if (plant == null) {
                        InvalidPrompt invalidPrompt = () -> {
                            System.out.println("THERE IS NO PLANT THERE!");
                        };
                        invalidPrompt.action();
                    } else {
                        Graphic.remove(x, y);
                        pvpGame.removePlant(plant);
                    }
                } else {
                    InvalidPrompt invalidPrompt = () -> {
                        System.out.println("PLEASE ENTER TWO INTEGERS ALIKE:");
                        System.out.println("INT,INT AFTER Plant. THE FIRST INTEGER");
                        System.out.println("MUST BE A NUMBER BETWEEN 0 AND 18 AND");
                        System.out.println("THE SECOND INTEGER IS A NUMBER BETWEEN 0 AND 5!");
                    };
                    invalidPrompt.action();
                }

            } catch (NumberFormatException e) {
                System.out.println("PLEASE ENTER TWO INTEGERS ALIKE:");
                System.out.println("INT,INT AFTER Plant. THE FIRST INTEGER");
                System.out.println("MUST BE A NUMBER BETWEEN 0 AND 18 AND");
                System.out.println("THE SECOND INTEGER IS A NUMBER BETWEEN 0 AND 5!");
            }

        }
        return menuPtr;
    }
}


class ShowLawnPvP extends PvPCommands {
    ShowLawnPvP(String input, Menu menuPtr) {
        super(input, menuPtr);
        pattern = Pattern.compile("show lawn", Pattern.CASE_INSENSITIVE);

    }

    @Override
    public Menu action(Menu menuPtr, PvPGame pvpGame) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            Dynamic.ShowLawnPrinter(pvpGame.getPlants(), pvpGame.getZombies(), "life", "Coordinate");
        }
        return menuPtr;
    }
}


class Ready extends PvPCommands {
    Ready(String input, Menu menuPtr) {
        super(input, menuPtr);
        pattern = Pattern.compile("Ready");
    }

    @Override
    public Menu action(Menu menuPtr, PvPGame pvpGame) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            pvpGame.setReady(true);

        }
        return menuPtr;
    }
}


class ShowHandPvPZombie extends PvPCommands {
    ShowHandPvPZombie(String input, Menu menuPtr) {
        super(input, menuPtr);
        pattern = Pattern.compile("show hand", Pattern.CASE_INSENSITIVE);

    }

    @Override
    public Menu action(Menu menuPtr, PvPGame pvpGame) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches() && pvpGame.isReady()) {
            Dynamic.printer(pvpGame.getCards(), "Names", "SunsTheyNeed");
        }
        return menuPtr;
    }
}


class ShowLanesPvP extends PvPCommands {
    ShowLanesPvP(String input, Menu menuPtr) {
        super(input, menuPtr);
        pattern = Pattern.compile("Show lanes", Pattern.CASE_INSENSITIVE);

    }

    @Override
    public Menu action(Menu menuPtr, PvPGame pvpGame) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches() && pvpGame.isReady()) {
            DynamicPVP dynamicPVP = new DynamicPVP(pvpGame);
            dynamicPVP.showLanePrinter();
        }
        return menuPtr;
    }
}


class PutPvP extends PvPCommands {
    PutPvP(String input, Menu menuPtr) {
        super(input, menuPtr);
        pattern = Pattern.compile("Put ((.),(.))+", Pattern.CASE_INSENSITIVE);

    }

    @Override
    public Menu action(Menu menuPtr, PvPGame pvpGame) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches() && pvpGame.isReady()) {
            String zombieName = matcher.group(2);
            String rowNumber = matcher.group(3);
            try {
                int row = Integer.parseInt(rowNumber);
                DynamicPVP dynamicPVP = new DynamicPVP(pvpGame);
                Card card = dynamicPVP.findCard(pvpGame, zombieName);
                if (card == null) {
                    System.out.println("You don't have such a zombie or the zombie name is invalid");
                } else {
                    Zombie zombie = Dynamic.findZombie(card);
                    if (zombie == null) {
                        System.out.println("Such a zombie doesn't exist on your list!");
                    } else {
                    }

                }
            } catch (NumberFormatException e) {
                System.out.println("PLEASE ENTER AN INTEGER AS THE LAST INPUT OF PUT COMMAND");
            }
        }
        return menuPtr;
    }
}


class StartPvP extends PvPCommands {
    StartPvP(String input, Menu menuPtr) {
        super(input, menuPtr);
        pattern = Pattern.compile("start", Pattern.CASE_INSENSITIVE);

    }

    @Override
    public Menu action(Menu menuPtr, PvPGame pvpGame) {
        Matcher matcher = pattern.matcher(input.toLowerCase());
        if (matcher.matches()) {
            pvpGame.setStart(true);
        }
        return menuPtr;
    }
}


class ShowLawnPvPZombie extends PvPCommands {
    ShowLawnPvPZombie(String input, Menu menuPtr) {
        super(input, menuPtr);
        pattern = Pattern.compile("Showlawn", Pattern.CASE_INSENSITIVE);

    }

    @Override
    public Menu action(Menu menuPtr, PvPGame pvpGame) {
        Matcher matcher = pattern.matcher(input.toLowerCase());
        if (matcher.matches()) {
            Dynamic.ShowLawnPrinter(pvpGame.getPlants(), pvpGame.getZombies(), "life", "Coordinate");
        }
        return menuPtr;
    }
}


class EndTurnPvP extends PvPCommands {
    EndTurnPvP(String input, Menu menuPtr) {
        super(input, menuPtr);
        pattern = Pattern.compile("End turn", Pattern.CASE_INSENSITIVE);

    }

    @Override
    public Menu action(Menu menuPtr, PvPGame pvpGame) {
        Matcher matcher = pattern.matcher(input.toLowerCase());
        if (matcher.matches()) {
            DynamicPVP dynamicPVP = new DynamicPVP(pvpGame);
            if (dynamicPVP.hasGameEnded()){
                System.out.println("Game is finished!");
                if (pvpGame.getGamePvPCondition() == GamePvPCondition.PLANTWINNIG)
                    System.out.println("Plants won!!!!!!");
                else
                    System.out.println("Zombies Won!!!");
                menuPtr = new PlayMenu();
            }
            else if (dynamicPVP.isWaveFinished()){
                System.out.println("Wave "+ pvpGame.getWaveCounter() + "finished and the winner of the wave are:");
                if (pvpGame.getWaveCondition() == GamePvPCondition.PLANTWINNIG){
                    System.out.println("Plants");
                }
                else{
                    System.out.println("Plants");
                }
                menuPtr = new PvPMenu();
            }
            else if(pvpGame.isStart()){
                dynamicPVP.goOn();
            }
            else{
                System.out.println("you haven't started the game!");
                menuPtr = new PvPMenu();
            }
        }
        return  menuPtr;
    }
}
