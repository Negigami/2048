package modele;

import javax.swing.*;
import java.awt.*;

public class Case extends JPanel {
    private int valeur;

    private Jeu j;

    private Color couleur;

    public Case(int _valeur) {
        valeur = _valeur;

    }

    public int getValeur() {
        return valeur;
    }
    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

}
