/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.builder;

import java.util.List;
import java.util.Set;
import model.Armee;
import model.Jeu;
import model.Position;

/**
 *
 * @author stefanos
 */
public class JeuBuilder extends Builder {

    @Override
    public JeuBuilder append(int taille, String a1, String a2) {
        this.buildJeu(taille,a1,a2);
        return this;
    }

    @Override
    public JeuBuilder append(Armee a, Position p,int index) {
        a.ajouterBateau(p,index);
        return this;
    }
    @Override
    public JeuBuilder append(List<Position> mine) {
        this.jeu.ajouterMine(mine);
        return this;
    }

    public Jeu getJeu() {
        return jeu;
    }
}
