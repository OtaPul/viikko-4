package main;

import java.io.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Syötä pelaajan nimi: ");
        String playerName = scanner.nextLine();
        Player player = new Player(playerName);
        Cave cave = new Cave(player);

        while (true) {
            System.out.println("1) Lisää luolaan hirviö");
            System.out.println("2) Listaa hirviöt");
            System.out.println("3) Hyökkää hirviöön");
            System.out.println("4) Tallenna peli");
            System.out.println("5) Lataa peli");
            System.out.println("0) Lopeta peli");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Anna hirviön tyyppi: ");
                    String type = scanner.nextLine();
                    System.out.print("Anna hirviön elämän määrä numerona: ");
                    int health = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    cave.addMonster(new Monster(type, health));
                    break;
                case 2:
                    cave.listMonsters();
                    break;
                case 3:
                    System.out.println("Valitse hirviö, johon hyökätä:");
                    cave.listMonsters();
                    if (!cave.getMonsters().isEmpty()) {
                        int monsterIndex = scanner.nextInt() - 1;
                        scanner.nextLine(); // Consume newline
                        if (monsterIndex >= 0 && monsterIndex < cave.getMonsters().size()) {
                            Monster target = cave.getMonsters().get(monsterIndex);
                            cave.getPlayer().attack(target);
                            if (target.getHealth() <= 0) {
                                cave.removeMonster(target);
                            }
                        } else {
                            System.out.println("Virheellinen valinta!");
                        }
                    }
                    break;
                case 4:
                    System.out.print("Anna tiedoston nimi, johon peli tallentaa: ");
                    String saveFileName = scanner.nextLine();
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFileName))) {
                        oos.writeObject(cave);
                        System.out.println("Peli tallennettiin tiedostoon " + saveFileName + ".");
                    } catch (IOException e) {
                        System.out.println("Tallennus epäonnistui: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.print("Anna tiedoston nimi, josta peli ladataan: ");
                    String loadFileName = scanner.nextLine();
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(loadFileName))) {
                        cave = (Cave) ois.readObject();
                        player = cave.getPlayer();
                        System.out.println("Peli ladattu tiedostosta " + loadFileName + ". Tervetuloa takaisin, " + player.getName() + ".");
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Lataus epäonnistui: " + e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Peli päättyy. Kiitos pelaamisesta!");
                    return;
                default:
                    System.out.println("Virheellinen valinta!");
            }
        }
    }
}
