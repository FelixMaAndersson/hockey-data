package se.yrgo.client;

import se.yrgo.domain.Player;

public class SalaryManager {

    private final Player player;
    private int salary;
    private int meanRating;

    public SalaryManager() {
        this.player = new Player();
    }
}
