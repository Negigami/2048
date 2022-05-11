package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Random;

public class Jeu extends Observable {

    private Case[][] tabCases;
    private static Random rnd = new Random(4);
    private HashMap<Case, Point> map = new HashMap<Case, Point>();

    public Jeu(int size) {
        tabCases = new Case[size][size];
        rnd();
    }

    public int getSize() {
        return tabCases.length;
    }

    public Case getCase(int i, int j) {
        return tabCases[i][j];
    }

    public Case getNeighbour(Direction d, Case c) {
        Point p = map.get(c);
        switch (d) {
            case haut -> {
                if (p.y - 1 >= 0) {
                    p.y -= 1;
                }
                break;
            }

            case bas -> {
                if (p.y + 1 < tabCases.length) {
                    p.y += 1;
                }
                break;
            }

            case gauche -> {
                if (p.x - 1 >= 0) {
                    p.x -= 1;
                }
                break;
            }

            case droite -> {
                if (p.x + 1 < tabCases.length) {
                    p.x += 1;
                }
                break;
            }
        }
        return tabCases[p.x][p.y];
    }

    public void move(Direction d) {

        switch (d) {
            case droite -> {
                for (int i = tabCases.length - 1; i > 0; i--) {
                    for (int j = tabCases[i].length - 1; j > 0; j--) {
                        if(tabCases[i][j] != null) {
                            moveCase(d, tabCases[i][j]);
                        }
                    }
                }
                break;
            }
            case gauche -> {
                for (int i = 0; i < tabCases.length; i++) {
                    for (int j = 0; j < tabCases[i].length; j++) {
                        if(tabCases[i][j] != null) {
                            moveCase(d, tabCases[i][j]);
                        }
                    }
                }
                break;
            }
            case bas -> {
                for (int i = tabCases.length - 1; i > 0; i--) {
                    for (int j = tabCases[i].length - 1; j > 0; j--) {
                        if(tabCases[i][j] != null) {
                            moveCase(d, tabCases[i][j]);
                        }
                    }
                }
                break;
            }
            case haut -> {
                for (int i = 0; i < tabCases.length; i++) {
                    for (int j = 0; j < tabCases[i].length; j++) {
                        if(tabCases[i][j] != null) {
                            moveCase(d, tabCases[i][j]);
                        }
                    }
                }
                break;
            }
        }

        addCase();
    }

    public void moveCase(Direction d, Case c) {
        Case v = getNeighbour(d, c);
        boolean canMerge = true;
        while ((v == null || v.getValeur() == c.getValeur()) && canMerge && v != c) {

            Point pC = map.get(c);
            if (v == null) {
                switch (d) {
                    case bas -> {
                        tabCases[pC.getX()][pC.getY() + 1] = c;
                        map.get(c).setY(pC.getY() + 1);
                        break;
                    }
                    case haut -> {
                        tabCases[pC.getX()][pC.getY() - 1] = c;
                        map.get(c).setY(pC.getY() - 1);
                        break;
                    }
                    case gauche -> {
                        tabCases[pC.getX() - 1][pC.getY()] = c;
                        map.get(c).setX(pC.getX() - 1);
                        break;
                    }
                    case droite -> {
                        tabCases[pC.getX() + 1][pC.getY()] = c;
                        map.get(c).setX(pC.getX() + 1);
                        break;
                    }
                }
            } else {
                canMerge = false;
                c.setValeur(c.getValeur() * 2);
                Point pV = map.get(v);
                map.put(c, pV);
                tabCases[pV.getX()][pV.getY()] = c;
                tabCases[pC.getX()][pC.getY()] = null;
            }

            v = getNeighbour(d, c);
        }
    }

    public void addCase() {
        ArrayList<Point> points = new ArrayList<Point>();
        for (int i = 0; i < tabCases.length; i++) {
            for (int j = 0; j < tabCases.length; j++) {
                if (tabCases[i][j] == null) {
                    points.add(new Point(i, j));
                }
            }
        }

        int r1 = rnd.nextInt(points.size());
        Case newCase = new Case((rnd.nextInt(2) + 1) * 2);
        map.put(newCase, points.get(r1));
        tabCases[points.get(r1).getX()][points.get(r1).getY()] = newCase;
    }

    public void rnd() {
        new Thread() { // permet de libérer le processus graphique ou de la console
            public void run() {
                int r;

                for (int i = 0; i < tabCases.length; i++) {
                    for (int j = 0; j < tabCases.length; j++) {
                        r = rnd.nextInt(3);
                        Point p = new Point(i, j);

                        switch (r) {
                            case 0:
                                tabCases[i][j] = null;
                                break;
                            case 1:
                                Case c2 = new Case(2);
                                tabCases[i][j] = c2;
                                map.put(c2, p);
                                break;
                            case 2:
                                Case c4 = new Case(4);
                                tabCases[i][j] = c4;
                                map.put(c4, p);
                                break;
                        }
                    }
                }
            }

        }.start();


        setChanged();
        notifyObservers();


    }

}
