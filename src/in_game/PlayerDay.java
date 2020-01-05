package in_game;

import model.Shop;
import model.Sun;
import model.Zombie;

import java.util.ArrayList;

public class PlayerDay extends Player {

    playerCondition playerCondition = in_game.playerCondition.PLAYING;

    public in_game.playerCondition getPlayerCondition() {
        return playerCondition;
    }

    public void setPlayerCondition(in_game.playerCondition playerCondition) {
        this.playerCondition = playerCondition;
    }

    private int turnLastZombieKilled = 0;

    public int getTurnLastZombieKilled() {
        return turnLastZombieKilled;
    }

    public void setTurnLastZombieKilled(int turnLastZombieKilled) {
        this.turnLastZombieKilled = turnLastZombieKilled;
    }

    /**Handling suns:*/
    private int turnServed = 0;
    private Sun suns;//must init with 2

    public void addSuns(int s) {
        int currentSuns = this.suns.getSunStore();
        this.suns.setSunStore(s - currentSuns);
    }

    public int getSuns() {
        return suns.getSunStore();
    }

    public int getTurnServed() {
        return turnServed;
    }

    public void setTurnServed(int turnServed) {
        this.turnServed = turnServed;
    }

    /**Handling Zombies:*/
    public void setZombies(ArrayList<Zombie> zombies) {
        this.zombies = zombies;
    }

    /**Handling waves:*/
    private int wavesOfAttack;

    public int getWavesOfAttack() {
        return wavesOfAttack;
    }

    public void decreaseWaveOfAttack(){
        wavesOfAttack--;
    }

    public int getValidNumOfPlants() {
        return 7;
    }

    public int getValidNumOfCards() {
        int VALID_NUM_OF_CARDS = 7;
        return VALID_NUM_OF_CARDS;
    }


    public int getNumOfPlants() {
        return plants.size();
    }

    public PlayerDay(String name, String password) {
        super(name, password);
        this.cards.add(Shop.getAllPlants().get(0));
        this.cards.add(Shop.getAllPlants().get(1));
        this.cards.add(Shop.getAllPlants().get(10));
        this.cards.add(Shop.getAllPlants().get(7));
        this.cards.add(Shop.getAllPlants().get(19));
        this.cards.add(Shop.getAllPlants().get(8));
        this.cards.add(Shop.getAllPlants().get(21));
        this.suns = new Sun(2);
        wavesOfAttack = 3;
        turn = 0;
    }

}
