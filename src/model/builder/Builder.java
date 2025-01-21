/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.builder;

import java.util.List;
import model.Armee;
import model.Jeu;
import model.Position;

/**
 *
 * @author stefanos
 */
public abstract class Builder {
    protected Jeu jeu;

    public Jeu getJeu() {
        return jeu;
    }
    public void buildJeu(int taille, String a1, String a2){
        this.jeu = new Jeu(taille,a1,a2);
    }
    
//    public abstract JeuBuilder append(String a, String b);
    public abstract JeuBuilder append(int taille,String a1, String a2);
    public abstract JeuBuilder append(Armee a, Position p, int index);
    public abstract JeuBuilder append(List<Position> mine);
    
    
}
