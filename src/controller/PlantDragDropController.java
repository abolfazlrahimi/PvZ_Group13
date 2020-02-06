package controller;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import game.model.*;
import in_game.Account;
import in_game.Game;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TablePositionBase;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.StackPane;
import model.Plant;
import model.Shop;

/**
 *@author YanhanLyu, Lucy Wu
 * Class that controls the behavior of dropping dragged plants
 */
public class PlantDragDropController implements EventHandler<DragEvent> {
    Group root;
    Game game;
    Account account;
    //private Sun sun;

    public PlantDragDropController(Group root, Game game){
        this.root = root;
        this.game=game;
        this.account = account;
    }
    @Override
    public void handle(DragEvent event) {
        int row=1;
        int column=(int)event.getX()-40;
        String type = event.getDragboard().getString();
        StackPane s1 = new StackPane();

        if (event.getX()>=60 && event.getX() <= 780) {
            if (event.getY() >= 135 && event.getY() <= 685) {
                if (event.getY() >= 135 && event.getY() <= 245){
                    row=1;
                } else if (event.getY() < 355) {
                    row=2;
                } else if (event.getY() < 465) {
                    row=3;
                } else if ( event.getY() < 575){
                    row=4;
                } else if (event.getY() < 685) {
                    row=5;
                }

                if (event.getX() >= 60 && event.getX() <= 140){
                    column = 1;
                } else if (event.getX()<=211) {
                    column = 2;
                } else if (event.getX()<=300) {
                    column = 3;
                }else if (event.getX()<=380){
                    column = 4;
                } else if (event.getX()<=460) {
                    column = 5;
                } else if (event.getX()<=540) {
                    column = 6;
                } else if (event.getX()<=620) {
                    column = 7;
                } else if (event.getX()<=700) {
                    column = 8;
                }else if (event.getX()<=780) {
                    column = 9;
                }

                boolean add = true;

                for (Plant plant : game.getPlants()) {
                    if (plant.getYCoordinate() == row && plant.getXCoordinate() == column) {
                        add = false;
                    }
                }

                if (add) {
                    switch (type) {
                        case "peashooter":
                            if (this.game.getSun() >= 100){
                                Plant peashooter = new Peashooter(row, column,root, player);
                                player.addPlants(peashooter);
                                player.setSun(player.getSun() - peashooter.getPrice());
                            }

                            break;
                        case "wallnut":
                            if (this.player.getSun() >= 50) {
                                WallNut wallnut = new WallNut(row, column,root);
                                player.addPlants(wallnut);
                                player.setSun(player.getSun() - wallnut.getPrice());
                            }
                            break;
                        case "sunflower":

                            if (this.player.getSun() >= 50) {
                                Sunflower sunflower = new Sunflower(row, column,root,player);
                                player.addPlants(sunflower);
                                player.setSun(player.getSun() - sunflower.getPrice());
                            }
                            //player.addPlants(star);
                            break;
                    }
                }
            }
        }
        //System.out.println(player.getPlants().size());
    }

}

