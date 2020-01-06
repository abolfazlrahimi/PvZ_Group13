package model;

import in_game.Game;

public class ShieldZombie extends Zombie {

    int life;

    public ShieldZombie(String name, int life, int speed, int defense) {
        super(life, speed, defense, name);
    }

    public void addLife(int addNumber) {
        this.life = +addNumber;
    }

    @Override
    public void action(Game game) {
        String name = this.getName();
        for (Plant plant : game.getPlants()) {
            switch (name) {
                case ("Newspaper Zombie"):
                    if (this.getX() == plant.getXCoordinate() && this.getY() == plant.getYCoordinate()) {
                        plant.setLife(plant.getLife() - 1);
                    } else {
                        move();
                    }
                    break;
                case ("Target Zombie"):
                    if (this.getX() == plant.getXCoordinate() && this.getY() == plant.getYCoordinate()) {
                        plant.setLife(plant.getLife() - 1);
                    } else {
                        if (plant.getLife() == 0)
                            move();
                    }
                    break;
                case ("Screen Door Zombie"):
                    if (this.getX() == plant.getXCoordinate() && this.getY() == plant.getYCoordinate()) {
                        plant.setLife(plant.getLife() - 1);
                    } else {
                        if (plant.getLife() == 0)
                            move();
                    }
                    break;
                case ("Balloon Zombie"):
                    if (this.getX() == plant.getXCoordinate() && this.getY() == plant.getYCoordinate()) {
                        plant.setLife(plant.getLife() - 1);
                    } else {
                        if (plant.getLife() == 0)
                            move();
                    }
                    break;
            }
        }
    }
}
