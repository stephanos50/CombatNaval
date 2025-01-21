/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.builder;

import model.Armee;
import model.Jeu;

/**
 *
 * @author stefanos
 */
public class Director {
    private JeuBuilder seaBuilder;
    
    public Jeu getJeu(){
        return this.seaBuilder.getJeu();
    }
    public Armee getArmeeA1(){
        return this.seaBuilder.getJeu().getA1();
    }
    public Armee getArmeeA2(){
        return this.seaBuilder.getJeu().getA2();
    }
    public String nomArmeeA1(){
        return this.seaBuilder.getJeu().nomArmeeA1();
    }
    public String nomArmeeA2(){
        return this.seaBuilder.getJeu().nomArmeeA2();
    }
    public int taille(){
        return this.seaBuilder.getJeu().getTaille();
    }
    
    public void setSeaBuilder(JeuBuilder seaBuilder) {
        this.seaBuilder = seaBuilder;
    }
    
}
