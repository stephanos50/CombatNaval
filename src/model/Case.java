/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author stefanos
 */
public class Case implements Jouable  {
    private Contenue cont;
    private boolean jouable;
    
    
    public Case(){
        this.cont = new Eau();
        this.jouable = true;
    }
    
    public void setCont(Contenue cont) {
        this.cont = cont;
    }
    
    public Contenue getCont() {
        return cont;
    }
    
    public void contenueestAjouter(Contenue cont){
        setCont(cont);
    }
   

    @Override
    public boolean getJouable() {
        return jouable;
    }

    @Override
    public void setJouable(boolean jouable) {
        this.jouable = jouable;
    }
    
    
}
