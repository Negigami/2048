package modele;

import vue_controleur.Swing2048;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Ia {
    private Jeu jeu;
    private int nbProc;
    private Swing2048 swing;
    private int precision;

    public Ia(Jeu jeu, int nbProc, int precision, Swing2048 swing) {
        this.jeu = jeu;
        this.nbProc = nbProc;
        this.precision = precision;
        this.swing = swing;
    }

    public void MonteCarlo() throws InterruptedException {
        while (!jeu.isGameOver()) {

            Jeu j1 = new Jeu(jeu);
            Jeu j2 = new Jeu(jeu);
            Jeu j3 = new Jeu(jeu);
            Jeu j4 = new Jeu(jeu);

            Task t1 = new Task(Direction.haut, precision, j1);
            Task t2 = new Task(Direction.bas, precision, j2);
            Task t3 = new Task(Direction.gauche, precision, j3);
            Task t4 = new Task(Direction.droite, precision, j4);

            ExecutorService pool = Executors.newFixedThreadPool(nbProc);

            pool.execute(t1);
            pool.execute(t2);
            pool.execute(t3);
            pool.execute(t4);

            pool.shutdown();
            pool.awaitTermination(2, TimeUnit.SECONDS);

            Task selectedTask = t1;
            if (selectedTask.getAverage() < t2.getAverage()) {
                selectedTask = t2;
            }
            if (selectedTask.getAverage() < t3.getAverage()) {
                selectedTask = t3;
            }
            if (selectedTask.getAverage() < t4.getAverage()) {
                selectedTask = t4;
            }

            jeu.move(selectedTask.getDirection());
            swing.rafraichir();
        }
    }
}
