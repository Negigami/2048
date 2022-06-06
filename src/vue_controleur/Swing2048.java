package vue_controleur;

import modele.Case;
import modele.Direction;
import modele.Jeu;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Swing2048 extends JFrame implements Observer {
    private static final int PIXEL_PER_SQUARE = 60;
    // tableau de cases : i, j -> case graphique
    private JLabel[][] tabC;
    private Jeu jeu;


    public Swing2048(Jeu _jeu) {
        jeu = _jeu;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(jeu.getSize() * PIXEL_PER_SQUARE, jeu.getSize() * PIXEL_PER_SQUARE);
        tabC = new JLabel[jeu.getSize()][jeu.getSize()];


        JPanel contentPane = new JPanel(new GridLayout(jeu.getSize(), jeu.getSize()));
        setBackground(null);

        for (int i = 0; i < jeu.getSize(); i++) {
            for (int j = 0; j < jeu.getSize(); j++) {
                Border border = BorderFactory.createLineBorder(Color.darkGray, 5);
                tabC[i][j] = new JLabel();
                tabC[i][j].setBorder(border);
                tabC[i][j].setHorizontalAlignment(SwingConstants.CENTER);


                contentPane.add(tabC[i][j]);

            }
        }
        setContentPane(contentPane);
        ajouterEcouteurClavier();
        rafraichir();

    }




    /**
     * Correspond à la fonctionnalité de Vue : affiche les données du modèle
     */
    private void rafraichir()  {

        SwingUtilities.invokeLater(new Runnable() { // demande au processus graphique de réaliser le traitement
            @Override
            public void run() {
                for (int i = 0; i < jeu.getSize(); i++) {
                    for (int j = 0; j < jeu.getSize(); j++) {
                        Case c = jeu.getCase(i, j);

                        if (c == null) {

                            tabC[i][j].setText("");
                            tabC[i][j].setBackground(Color.WHITE);

                        } else {
                            tabC[i][j].setText(c.getValeur() + "");
                            int val = c.getValeur();
                            Color couleur = new Color(255, 255, 255);
                            switch (val) {
                                case 2: {
                                    couleur = new Color(00, 00, 255);
                                    tabC[i][j].setBackground(couleur);
                                    tabC[i][j].setOpaque(true);
                                    break;
                                }
                                case 4: {
                                    couleur = new Color(200, 231, 255);
                                    tabC[i][j].setBackground(couleur);
                                    tabC[i][j].setOpaque(true);
                                    break;
                                }
                                case 8: {
                                    couleur = new Color(208, 209, 255);
                                    tabC[i][j].setBackground(couleur);
                                    tabC[i][j].setOpaque(true);
                                    break;
                                }
                                case 16: {
                                    couleur = new Color(216, 187, 255);
                                    tabC[i][j].setBackground(couleur);
                                    tabC[i][j].setOpaque(true);
                                    break;
                                }
                                case 32: {
                                    couleur = new Color(222, 170, 255);
                                    tabC[i][j].setBackground(couleur);
                                    tabC[i][j].setOpaque(true);
                                    break;
                                }
                                case 64: {
                                    couleur = new Color(226, 175, 255);
                                    tabC[i][j].setBackground(couleur);
                                    tabC[i][j].setOpaque(true);
                                    break;
                                }
                                case 128: {
                                    couleur = new Color(229, 179, 254);
                                    tabC[i][j].setBackground(couleur);
                                    tabC[i][j].setOpaque(true);
                                }
                                    break;
                                case 256: {
                                    couleur = new Color(236, 188, 253);
                                    tabC[i][j].setBackground(couleur);
                                    tabC[i][j].setOpaque(true);
                                    break;
                                }
                                case 512: {
                                    couleur = new Color(243, 196, 251);
                                    tabC[i][j].setBackground(couleur);
                                    tabC[i][j].setOpaque(true);
                                }
                                    break;
                                case 1024: {
                                    couleur = new Color(255, 203, 242);
                                    tabC[i][j].setBackground(couleur);
                                    tabC[i][j].setOpaque(true);
                                }
                                    break;
                                case 2048: {
                                    couleur = new Color(249, 94, 239);
                                    tabC[i][j].setBackground(couleur);
                                    tabC[i][j].setOpaque(true);
                                    break;
                                }
                                default: {
                                    tabC[i][j].setBackground(couleur);
                                    tabC[i][j].setOpaque(true);
                                }
                            }

                        }



                    }
                }
            }
        });


    }

    /**
     * Correspond à la fonctionnalité de Contrôleur : écoute les évènements, et déclenche des traitements sur le modèle
     */
    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT : jeu.move(Direction.gauche); break;
                    case KeyEvent.VK_RIGHT : jeu.move(Direction.droite); break;
                    case KeyEvent.VK_DOWN : jeu.move(Direction.bas); break;
                    case KeyEvent.VK_UP : jeu.move(Direction.haut); break;
                }
                rafraichir();
            }
        });
    }


    @Override
    public void update(Observable o, Object arg) {
        rafraichir();
    }
}