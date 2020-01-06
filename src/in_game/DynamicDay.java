package in_game;

import model.*;

import java.util.ArrayList;
import java.util.Random;

public class DynamicDay extends Dynamic {
    GameDay playerDay;

    public DynamicDay(GameDay playerDay) {
        this.playerDay = playerDay;
    }

    /**
     * Show Hand Commands:
     */

    public int demandingSuns() {
        /**the output indicates the number of suns player needs,so
         * if the output < 0, it means that we need "output" suns.
         * if the output > 0, it means we have extra "output" suns.
         */
        int allSunsPlayerHas = playerDay.getSuns();
        int allSunsPlayerMustHave = 0;
        for (Plant plant : playerDay.getPlants()) {
            allSunsPlayerMustHave = allSunsPlayerMustHave + plant.getSun();
        }
        int allSunsPlayerNeeds = allSunsPlayerMustHave - allSunsPlayerHas;
        return allSunsPlayerNeeds;
    }

    /**
     * Select Commands:
     */

    public boolean canIChoose(GameDay playerDay, Card card) {
        Plant plant = findPlant(card);
        int numberOfExtraSuns = demandingSuns();
        return (numberOfExtraSuns >= plant.getSun());
    }

    /**
     * Adding Suns Randomly:
     */
    public void addSunRandomly() {
        int n = MathFunctions.getRandomNumber(2, 5);
        int turn = playerDay.getTurn();
        int lastTurnServed = playerDay.getTurnServed();
        if (turn - lastTurnServed > 1) {
            playerDay.addSuns(n);
        } else if (turn - lastTurnServed == 1) {
            int rnd = MathFunctions.getRandomNumber(0, 1);
            if (rnd == 1) {
                playerDay.addSuns(n);
            }
        }
    }

    /**
     * Set and choose Random Zombies:
     */
    public ArrayList<Zombie> chooseAndSetRandomZombies() {
        Random rand = new Random();
        ArrayList<Zombie> randomZombies = new ArrayList<>();
        int numberOfZombies = MathFunctions.getRandomNumber(4, 10);
        for (int i = 0; i < numberOfZombies; i++) {
            int randomIndex = rand.nextInt(Shop.getZombies().size());
            randomZombies.add((Zombie) Shop.getZombies().get(randomIndex));
        }
        playerDay.setZombies(randomZombies);
        return randomZombies;
    }

    /**
     * Find a plant given its name:
     */


    public void attack() {
        ArrayList<Zombie> zombies = chooseAndSetRandomZombies();
        playerDay.setZombies(zombies);
    }

    /**
     * Do what is needed to do to end a turn:
     */
    public boolean hasGameEnded() {
        if (playerDay.getWavesOfAttack() >= 3) {
            playerDay.setGameCondition(GameCondition.WINNER);
            return true;
        } else if (playerDay.getPlants().size() == 0 && playerDay.getWavesOfAttack() < 3 && playerDay.getWavesOfAttack() != 0) {
            playerDay.setGameCondition(GameCondition.LOSER);
            return true;
        }
        return false;
    }

    public boolean hasWaveEnded(){
        return playerDay.getZombies().size()==0;
    }

    public boolean canIStartTheNextAttack() {
        if (playerDay.getTurn() - playerDay.getTurnLastZombieKilled() >= 7) {
            return true;
        } else if (playerDay.getTurn() == 3) {
            return true;
        }
        return false;

    }

    public void endTurn() {
        for (Plant plant:playerDay.getPlants()){
            if(plant.getLife() <= 0){
                Dynamic.removePlant(plant.getXCoordinate(), plant.getYCoordinate(), playerDay);
            }
        }
        for (Zombie zombie:playerDay.getZombies()){
            if(zombie.getLife() <= 0 || ((zombie.getX() <= -1) && zombie.getY() <= -1)){
                playerDay.getZombies().remove(zombie);
            }
        }

        addSunRandomly();
        if (playerDay.getZombies().size() == 0 && playerDay.getTurn() != 0) {
            playerDay.setTurnLastZombieKilled(playerDay.getTurn());
        }
        if (canIStartTheNextAttack()) {
            attack();
        }
        playerDay.addTurn();
        for (Zombie zombie:playerDay.getZombies()){
            zombie.action(playerDay);
        }
        for (Plant plant:playerDay.getPlants()){
            plant.action(playerDay);
        }
        for (Chamanzan chamanzan:playerDay.getChamanzans()){
            chamanzan.action(playerDay);
        }
        for (PeaBullet peaBullet:playerDay.getPeaBullets()){
            peaBullet.action(playerDay);
        }


    }

}



