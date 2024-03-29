package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Random;

import static modele.Direction.haut;
import static modele.Direction.bas;
import static modele.Direction.gauche;
import static modele.Direction.droite;




public class Jeu extends Observable {

    private Case[][] tabCases;
    private static Random rnd = new Random();
    private HashMap<Case, Point> map = new HashMap<Case, Point>();

    private boolean gameOver;

    private int score;

    public Jeu(int size) {
        tabCases = new Case[size][size];
        gameOver = false;
        score = 0;
        rnd();
    }

    public Jeu(Jeu jeu) {
        this.tabCases = new Case[jeu.getSize()][jeu.getSize()];
        this.gameOver = jeu.isGameOver();
        this.score = jeu.getScore();

        for (int i = 0; i < jeu.tabCases.length; i++) {
            for (int j = 0; j < jeu.tabCases.length; j++) {
                if (jeu.tabCases[i][j] != null) {
                    Case c = new Case(jeu.tabCases[i][j].getValeur());
                    Point p = new Point(i, j);
                    this.tabCases[i][j] = c;
                    this.map.put(c, p);
                }
            }
        }
    }

    public HashMap<Case, Point> getMap() {
        return map;
    }

    public Case[][] getTabCases() {
        return tabCases;
    }

    public int getSize() {
        return tabCases.length;
    }

    public Case getCase(int i, int j) {
        return tabCases[i][j];
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }

    public Case getNeighbour(Direction d, Case c) {
        Point p = map.get(c);
        int x = p.getX();
        int y = p.getY();
        switch (d) {
            case haut -> {
                if (x - 1 >= 0) {
                    x -= 1;
                }
                break;
            }

            case droite -> {
                if (y + 1 < tabCases.length) {
                    y += 1;
                }
                break;
            }

            case gauche -> {
                if (y - 1 >= 0) {
                    y -= 1;
                }
                break;
            }

            case bas -> {
                if (x + 1 < tabCases.length) {
                    x += 1;
                }
                break;
            }
        }
        return tabCases[x][y];
    }

    public void move(Direction d) {

        switch (d) {
            case bas -> {
                for (int i = tabCases.length - 1; i >= 0; i--) {
                    for (int j = tabCases[i].length - 1; j >= 0; j--) {
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
            case droite -> {
                for (int i = tabCases.length - 1; i >= 0; i--) {
                    for (int j = tabCases[i].length - 1; j >= 0; j--) {
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
                    case droite -> {
                        tabCases[pC.getX()][pC.getY() + 1] = c;
                        tabCases[pC.getX()][pC.getY()] = null;
                        map.get(c).setY(pC.getY() + 1);
                        break;
                    }
                    case gauche -> {
                        tabCases[pC.getX()][pC.getY() - 1] = c;
                        tabCases[pC.getX()][pC.getY()] = null;
                        map.get(c).setY(pC.getY() - 1);
                        break;
                    }
                    case haut -> {
                        tabCases[pC.getX() - 1][pC.getY()] = c;
                        tabCases[pC.getX()][pC.getY()] = null;
                        map.get(c).setX(pC.getX() - 1);
                        break;
                    }
                    case bas -> {
                        tabCases[pC.getX() + 1][pC.getY()] = c;
                        tabCases[pC.getX()][pC.getY()] = null;
                        map.get(c).setX(pC.getX() + 1);
                        break;
                    }
                }
            } else {
                canMerge = false;
                c.setValeur(c.getValeur() * 2);
                score += c.getValeur();
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

        boolean gameOver = false;
        if (points.size() == 0) {
            gameOver = gameOver();
        }

        if (points.size() != 0) {
            int r1 = rnd.nextInt(points.size());
            Case newCase = new Case((rnd.nextInt(2) + 1) * 2);
            map.put(newCase, points.get(r1));
            tabCases[points.get(r1).getX()][points.get(r1).getY()] = newCase;
        }
    }

    public boolean gameOver() {
        gameOver = true;
        Case vHaut = null;
        Case vBas = null;
        Case vGauche = null;
        Case vDroite = null;

        for(int i=0 ; i < tabCases.length ; i++) {
            for(int j=0 ; j < tabCases.length ; j++) {
                Case c = tabCases[i][j];
                if (i > 0) {
                     vHaut = getNeighbour(haut, c);
                    if (c.getValeur() == vHaut.getValeur() && c != vHaut) {
                        this.gameOver = false;
                    }
                }
                if (i < tabCases.length -1) {
                    vBas = getNeighbour(bas, c);
                    if (c.getValeur() == vBas.getValeur() && c != vBas) {
                        this.gameOver = false;
                    }
                }
                if (j > 0) {
                    vGauche = getNeighbour(gauche, c);
                    if (c.getValeur() == vGauche.getValeur() && c != vGauche) {
                        this.gameOver = false;
                    }
                }
                if (j < tabCases.length -1) {
                    vDroite = getNeighbour(droite, c);
                    if (c.getValeur() == vDroite.getValeur() && c != vDroite) {
                        this.gameOver = false;
                    }
                }
            };
        };
        return gameOver;
    }

    public void rnd() {
        new Thread() { // permet de libérer le processus graphique ou de la console
            public void run() {
                int r;

                for (int i = 0; i < tabCases.length; i++) {
                    for (int j = 0; j < tabCases.length; j++) {
                        r = rnd.nextInt(10);
                        Point p = new Point(i, j);

                        switch (r) {
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
                            default:
                                tabCases[i][j] = null;
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
