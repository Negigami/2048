package modele;

import java.util.Random;

public class Task implements Runnable {
    private Direction direction;
    private int precision;
    private Jeu jeu;
    private int average;

    public Task(Direction direction, int precision, Jeu jeu) {
        this.direction = direction;
        this.precision = precision;
        this.jeu = jeu;
        this.average = 0;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getAverage() {
        return average;
    }

    public void run() {
        try {
            for (int i = 0; i < precision; i++) {
                Jeu j = new Jeu(jeu);
                j.move(direction);
                while (!j.isGameOver()) {
                    Random rnd = new Random();
                    int r = rnd.nextInt(4);
                    switch (r) {
                        case 0 : j.move(Direction.haut);
                        case 1 : j.move(Direction.bas);
                        case 2 : j.move(Direction.gauche);
                        case 3 : j.move(Direction.droite);
                    }
                }
                average += j.getScore();
            }
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }
}
