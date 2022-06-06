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
                jeu.move(direction);
                while (!jeu.isGameOver()) {
                    Random rnd = new Random();
                    int r = rnd.nextInt(4);
                    switch (r) {
                        case 0 : jeu.move(Direction.haut);
                        case 1 : jeu.move(Direction.bas);
                        case 2 : jeu.move(Direction.gauche);
                        case 3 : jeu.move(Direction.droite);
                    }
                }
                average += jeu.getScore();
            }
            average /= precision;
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }
}
