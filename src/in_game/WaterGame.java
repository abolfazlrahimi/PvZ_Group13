package in_game;

import model.Chamanzan;

import java.util.ArrayList;

public class WaterGame extends GameDay {

    public WaterGame(String name, String password) {
        super(name, password);
        this.chamanzans = new ArrayList<>();
        this.chamanzans.add(new Chamanzan(this.yard));
        this.chamanzans.add(new Chamanzan( this.yard));
        this.chamanzans.add(new Chamanzan( this.yard));
        this.chamanzans.add(new Chamanzan( this.yard));

        this.cards = new ArrayList<>();


    }
}
