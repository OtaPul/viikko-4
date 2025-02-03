package main;

import java.io.Serializable;
import java.util.ArrayList;

public class Cave implements Serializable {
    private Player player;
    private ArrayList<Monster> monsters;

    public Cave(Player player) {
        this.player = player;
        this.monsters = new ArrayList<>();
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    public void listMonsters() {
        if (monsters.isEmpty()) {
            System.out.println("Luola on tyhjä.");
        } else {
            for (int i = 0; i < monsters.size(); i++) {
                monsters.get(i).printInfo(i + 1);
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void removeMonster(Monster monster) {
        monsters.remove(monster);
    }
}
