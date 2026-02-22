package fr.fges.logic;

public class Player {
    private String name;
    private int points;
    private int wins;

    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.wins = 0;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public int getWins() {
        return wins;
    }

    public void addWin() {
        this.points += 3;
        this.wins += 1;
    }

    public void addDefeat() {
        this.points += 1; // 1 point de participation
    }
}