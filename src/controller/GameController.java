package controller;

import in_game.DynamicDay;
import in_game.Game;
import in_game.GameCondition;
import in_game.GameDay;
import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.*;
import view.EndGame;
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

        for (Plant plant:listOfPlants){
            if (plant.getLife() <= 0){
                gameDay.getPlants().remove(plant);
            }
        }
        ArrayList<Zombie> listOfZombies = gameDay.getZombies();
        for(Zombie zombie:listOfZombies){
            if(zombie.getLife() <= 0){
                gameDay.getZombies().remove(zombie);
            }
        }

        DynamicDay dynamicDay = new DynamicDay(gameDay);
        if(dynamicDay.hasGameEnded()){
            this.endGame();
        }
        else{
             dynamicDay.endTurn();
        }


    }


    /** check if we win the game*/
    public void endGame(){
        if (gameDay.getGameCondition() == GameCondition.WINNER) {
            String result = "You win the Game";
            EndGame endGameShow = new EndGame(result);
            try {
                this.timer.cancel();
                endGameShow.start(endGameShow.getEndStage());
                this.stage.close();
            } catch (Exception ignored) {

            }
        }
        if (gameDay.getGameCondition() == GameCondition.LOSER){
            System.out.println("here");
            String result = "You lose the Game";
            EndGame endGameShow = new EndGame(result);
            try{
                this.timer.cancel();
                endGameShow.start(endGameShow.getEndStage());
                this.stage.close();
            } catch (Exception ignored){

            }

        }
    }



}