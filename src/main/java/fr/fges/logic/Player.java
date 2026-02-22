package fr.fges.logic;

public class Player {
    private final String name;
    private int points = 0;
    private int wins = 0;

    public Player(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public int getPoints() { return points; }
    public int getWins() { return wins; }

    public void addWin() {
        this.points += 3;
        this.wins++;
    }

    public void addDefeat() {
        this.points += 1; // 1 point de participation
    }
}