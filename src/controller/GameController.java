package controller;

import in_game.DynamicDay;
import in_game.Game;
import in_game.GameDay;
import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.*;

import java.util.*;


public class GameController{
    final private double FRAMES_PER_SECOND = 60.0;
    private DayYard dayYard;
    private GameDay gameDay;
    private Stage stage;
    private Timer timer;


    public GameController(DayYard dayYard, Stage initStage) {
        this.dayYard = dayYard;
        this.gameDay = (GameDay)dayYard.getGame();
        this.stage = initStage;
    }

    /**
     * initialize timer
     */
    public void initialize() {
        this.startTimer();

    }

    /**
     * run starterTimer
     */
    private void startTimer() {
        this.timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                updateAnimation();
            }
        };

        long frameTimeInMilliseconds = (long)(1);
        this.timer.schedule(timerTask,0,frameTimeInMilliseconds);
    }


    private void updateAnimation() {
        /*
        ArrayList<Zombie> listOfZombies = dayYard.getGame().getZombies();

        for (shootingPlant shootingPlant: shootingPlants){
            listOfPeas.addAll(shootingPlant.getPeaBullets());
        }*/
        for (Plant plant:gameDay.getPlants()){
            plant.action(gameDay);
        }
        for (Zombie zombie:gameDay.getZombies()){
            zombie.action(gameDay);
        }
        for(Chamanzan chamanzan:gameDay.getChamanzans()){
            chamanzan.action(gameDay);
        }
        ArrayList<Plant> listOfPlants = dayYard.getGame().getPlants();
        ArrayList<shootingPlant> shootingPlants = new ArrayList<>();
        for (Plant plant: listOfPlants){
            if (plant instanceof shootingPlant){
                shootingPlants.add((shootingPlant)plant);
            }
        }

        ArrayList<PeaBullet> listOfPeas = new ArrayList<>();
        for (shootingPlant shootingPlant: shootingPlants){
            listOfPeas.addAll(shootingPlant.getPeaBullets());
        }

        for(PeaBullet peaBullet:listOfPeas){
            peaBullet.action(gameDay);
        }


        runFight(listOfPlants, listOfZombies, listOfPeas);
        for (Pea pea: listOfPeas) {
            pea.step();
        }
        // Update plant animation
        for (Plant plant : listOfPlants) {
            plant.step();
        }
        // Update Zombie animation
        for (Zombie zombie : enemy.getZombies()) {
            zombie.step();
        }
    }


    private ArrayList<Plant> checkPlants(){
        ArrayList<Plant> listOfPlants = player.getPlants();
        return listOfPlants;
    }

    /** Get list of peas*/
    private ArrayList<Pea> checkPeas(){
        ArrayList<Pea> listOfPeas = player.getPeas();
        return listOfPeas;
    }

    /** Get list of zombies*/
    private ArrayList<Zombie> checkZombies(){
        ArrayList<Zombie> listOfZombies = enemy.getZombies();
        return listOfZombies;
    }

    /** Simulate the fight between plants and zombies and update animation accordingly*/
    private void runFight(ArrayList<Plant> plants, ArrayList<Zombie> zombies, ArrayList<PeaBullet> peas){
        boolean plantDie = false;
        // this is the list containing the zombies blocked by plants

        DynamicDay dynamicDay = new DynamicDay(this.game);
        ArrayList<Zombie> blockZombie = new  ArrayList<Zombie>();
        for (Iterator<Zombie> iterator2 = zombies.iterator(); iterator2.hasNext(); ) {
            Zombie zombie = iterator2.next();
            if (zombie.getX() <= 1){
                this.zombieWin = true;
            }
            for (Iterator<Plant> iterator = plants.iterator(); iterator.hasNext(); ) {
                Plant plant = iterator.next();
                int plantRow = plant.getRow();
                int plantColumn = plant.getColumn();
                int zombieRow = zombie.getRow();
                int zombieColumn = zombie.getColumn();

                // if the plant is a peashooter, we should consider the pea hits the zombie
                if (plant.getName().equals("peashooter")) {
                    for (Iterator<Pea> iterator3 = peas.iterator(); iterator3.hasNext(); ) {
                        Pea pea = iterator3.next();
                        int peaRow = pea.getRow();
                        int peaX = (int) Math.round(pea.getImagePositionX());
                        // If pea hit the zombie, reduce zombie's health value and remove the pea
                        // we calculate their real position instead of column to make the animation more
                        // realistic
                        if (plantRow == peaRow && peaRow == zombieRow && peaX == (int)zombie.getImagePositionX()) {
                            pea.removeImage();
                            int zombieHealth = zombie.getHealth();
                            int plantPower = plant.getPower();
                            zombie.setHealth(zombieHealth - plantPower);
                            iterator3.remove();
                            if (zombie.getHealth() <= 0) {
                                zombie.removeImage();
                                iterator2.remove();
                            }
                        }
                        // remove the pea if it is out of the image
                        if (pea.getImagePositionX() > 1000) {
                            iterator3.remove();
                        }
                    }
                }
                // if the zombie is blocked, it should eat the plant
                if (plantRow == zombieRow && plantColumn == zombieColumn) {
                    zombie.setSpeed(0);
                    blockZombie.add(zombie);
                    int plantHealth = plant.getHealth();
                    int zombiePower = zombie.getPower();
                    plant.setHealth(plantHealth-zombiePower);
                    if (plant.getHealth() < 50) {
                        if (plant.getHealth() <= 0){
                            plantDie = true;
                        }
                        zombie.setSpeed(zombie.getISpeed());
                    }
                }
                if (plantDie == true){
                    plant.removeImage();
                    iterator.remove();
                }
                plantDie = false;
            }

        }
        // If player has killed all zombie in the list, show message that player has won.
        if (enemy.getZombies().isEmpty()){
            this.playerWin = true;
        }
        checkIfEnd();
    }

    /** check if we win the game*/
    public void checkIfEnd(){
        if (this.playerWin) {
            String result = "You win the Game";
            EndGame game = new EndGame(result);
            try {
                this.timer.cancel();
                game.start(game.getEndStage());
                this.stage.close();
            } catch (Exception e) {

            }
        }
        if (this.zombieWin){
            System.out.println("here");
            String result = "You lose the Game";
            EndGame game = new EndGame(result);
            try{
                this.timer.cancel();
                game.start(game.getEndStage());
                this.stage.close();
            } catch (Exception e){

            }

        }
    }


    @Override
    public void handle(KeyEvent keyEvent) {

    }
}