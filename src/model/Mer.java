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
public class Mer  {
    private Case [][] mer ;
    private   int TAILLE = 5; 
    /* Constructeur pour FX */
    public Mer(int taille){
        this.TAILLE = taille;
        this.mer = new Case [TAILLE][TAILLE] ;
        for(int i = 0; i < taille; ++i){
            for(int j = 0; j < taille ; ++j){
                mer[i][j] = new Case();
            }
        }
    }
    /* Constructeur */
    public Mer(){
        this.mer = new Case [TAILLE][TAILLE] ;
        for(int i = 0; i < TAILLE; ++i){
            for(int j = 0; j < TAILLE; ++j){
                mer[i][j] = new Case();
            }
        }
    }
    
   
    
    public Position getPosition(int x, int y){
        return new Position(x,y);
    }
   
    /* Je supprime le contenu qui est une mine */
    public boolean mineEstSupprimer(Position p){
        mer[p.x][p.y].setCont(new Eau());
        return true;
    }
    
    public void ajouterContenue(int x, int y,Contenue cont){
        mer[x][y].contenueestAjouter(cont);
    }
  
    public void setMer(Case[][] mer) {
        this.mer = mer;
    }
    
    public void modifierContenue(Position p, Contenue cont){
        mer[p.x][p.y].setCont(cont);
    }
    
    
    /* Si la mer contient une mine Classic true si non false pour mineatomic */
    public boolean contienrTypeMine(Position p){
        boolean type = false;
        if(mer[p.x][p.y].getCont() instanceof MineClassic){
            type = true;
        }
        return type;
    }
    
    /* Si en position x et y il existe une mine je renvoie TRUE */
     public boolean contienrMine(Position p){
        boolean type = false;
        if(mer[p.x][p.y].getCont() instanceof Mine){
            type = true;
        }
        return type;
        
    }
    

    public Case[][] getMer() {
        return mer;
    }

    public int getTaille() {
        return TAILLE;
    }
}
    


