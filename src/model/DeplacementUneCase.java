/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.util.HashSet;
import java.util.Set;

public class DeplacementUneCase {
    private Set<Position> list;
    private int ix;
    private int iy;
    public DeplacementUneCase(int taille,int x, int y, boolean controleur) {
        verifierControleur( controleur);
        this.list = new HashSet<>();
        positionBouger(taille,x,y);
    }
    private void verifierControleur(boolean controleur){
        if(controleur){
            ix = 0;
            iy = 0;
        }
        else {
            ix = 1;
            iy = 1;
        }
    }
    private void positionBouger(int taille,int x,int y) { 
        /* 1 1   */
        if( x == ix && y == iy  ){
            ajouterLesPosition( x+1, y ); // droite
            ajouterLesPosition( taille-1, y ); // gauche
            ajouterLesPosition( x ,taille-1 ); // haut
            ajouterLesPosition( x ,y+1); // bas
        }
        /* 1 10 */
        else if(x == ix && y == (taille-1) ){
            ajouterLesPosition( x+1, y ); // droite
            ajouterLesPosition( taille-1, y ); // gauche
            ajouterLesPosition( x , y-1 ); // haut
            ajouterLesPosition( x, iy); // bas
        }
        /* 10 1 */
        else if(x == taille-1 && y == iy){
            ajouterLesPosition( ix, y); // droite
            ajouterLesPosition( x-1, y ); // gauche
            ajouterLesPosition( x , taille-1 ); // haut
            ajouterLesPosition( x , y+1 ); // bas
        }
        /* 10 10 */
        else if(x == taille-1 && y == taille-1){
            ajouterLesPosition( ix, y ); // droite
            ajouterLesPosition( x-1, y); // gauche
            ajouterLesPosition( x , y-1 ); // haut
            ajouterLesPosition( x , iy );  // bas
        }
        // #################################################
        /*  X est à  1  */
        else if( x == ix ){
            ajouterLesPosition( x+1, y); // droite
            ajouterLesPosition( taille-1, y ); // gauche
            ajouterLesPosition( x, y-1); // haut
            ajouterLesPosition( x, y+1); // bas
        }
        /* Y est à 1 */
        else if(y == iy){
            ajouterLesPosition( x+1, y ); // droite
            ajouterLesPosition( x-1, y ); // gauche
            ajouterLesPosition( x, taille-1 ); // haut
            ajouterLesPosition( x, y+1 ); // bas
        }
        
        /*  X est à 10 */
        else if( x == taille-1 ){
            ajouterLesPosition( ix, y); // droite
            ajouterLesPosition( x-1, y ); // gauche
            ajouterLesPosition( x, y-1); // haut
            ajouterLesPosition( x, y+1); // bas
        }
        /* Y est à 10 */
        else if( y == taille-1 ){
            ajouterLesPosition( x+1, y); // droite
            ajouterLesPosition( x-1, y ); // gauche
            ajouterLesPosition( x, y-1); // haut
            ajouterLesPosition( x, iy); // bas
        }
        else  {
            ajouterLesPosition( x+1, y); // droite
            ajouterLesPosition( x-1, y ); // gauche
            ajouterLesPosition( x, y-1 ); // haut
            ajouterLesPosition( x, y+1 ); // bas
        }
    }
    private  void ajouterLesPosition(int x, int y){
        list.add(new Position( x,y )); // droite
    }
    public Set<Position> getList() {
        return list;
    }
}
       

    
    
    
    
   

