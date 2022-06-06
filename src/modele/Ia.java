package modele;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ia {
    private Jeu jeu;
    private int nbProc;
    private int precision;

    public Ia(Jeu jeu, int nbProc) {
        this.jeu = jeu;
        this.nbProc = nbProc;
    }

    public void MonteCarlo() {
        while (!jeu.isGameOver()) {

            Jeu j1 = new Jeu(jeu);
            Jeu j2 = new Jeu(jeu);
            Jeu j3 = new Jeu(jeu);
            Jeu j4 = new Jeu(jeu);

            Task t1 = new Task(Direction.haut, precision, j1);
            Task t2 = new Task(Direction.bas, precision, j2);
            Task t3 = new Task(Direction.gauche, precision, j3);
            Task t4 = new Task(Direction.droite, precision, j4);

            ExecutorService pool = Executors.newFixedThreadPool(4);

            pool.execute(t1);
            pool.execute(t2);
            pool.execute(t3);
            pool.execute(t4);

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
        }
    }
}
