/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.util.HashSet;
import java.util.Set;

public class DeplacementDeuxCases {
    private boolean controleur;
    private int ix;
    private int iy;
    private int h;
    
    private Set<Position> listPetit;
    public DeplacementDeuxCases(int taille,int x, int y,boolean controleur) {
        verifierControleur( controleur);
        this.controleur = controleur;
        this.listPetit = new HashSet<>();
        positionBougerFx(taille,x,y);
    }
    private void verifierControleur(boolean controleur){
        if(controleur){
            ix = 0;
            iy = 0;
            h =1;
        }
        else {
            ix = 1;
            iy = 1;
            h=2;
        }
    }

    private void positionBougerFx(int taille,int x,int y)  {
       
        /* x 0 0 ou   1 1  */
        if( x == ix && y == iy  ){
            
            ajouterLesPosition( x+2, y ); // droite
            ajouterLesPosition( taille-2, y ); // gauche
            ajouterLesPosition( x ,taille-2 ); // haut
            ajouterLesPosition( x ,y+2); // bas
        }
        /* x 1 1 ou 2 2 */
         else if(x == h && y == h ){
            
            ajouterLesPosition( x+2, y ); // droite
            ajouterLesPosition( taille-1, y ); // gauche
            ajouterLesPosition( x , taille-1 ); // haut
            ajouterLesPosition( x, y+2); // bas
        }
          /* 0 1 ou 1 2  */
        else if(x == ix && y == h ){
             
            ajouterLesPosition( x+2, y ); // droite
            ajouterLesPosition( taille-2, y ); // gauche
            ajouterLesPosition( x , taille-1 ); // haut
            ajouterLesPosition( x, y+2); // bas
        }
         /* 1 0 ou 2 1 */
        else if(x == h && y == iy ){
            
            ajouterLesPosition( x+2, y ); // droite
            ajouterLesPosition( taille-1, y ); // gauche
            ajouterLesPosition( x , taille-2 ); // haut
            ajouterLesPosition( x, y+2); // bas
        }
        /* 0 taille-1 ou 1 taille-1*/
        else if(x == ix && y == (taille-1) ){
           
            ajouterLesPosition( x+2, y ); // droite
            ajouterLesPosition( taille-2, y ); // gauche
            ajouterLesPosition( x , y-2 ); // haut
            ajouterLesPosition( x, h); // bas
        }
        /* 0 taille-2 ou 1 taille-2 */
            
        else if(x == ix && y == (taille-2) ){
            ajouterLesPosition( x+2, y ); // droite
            ajouterLesPosition( taille-2, y ); // gauche
            ajouterLesPosition( x , y-2 ); // haut
            ajouterLesPosition( x, iy); // bas
        }
       
        
        /* taille-2 1 ou taille-2 0 */
        else if(x == taille-2 && y == iy ){
            ajouterLesPosition( ix, y ); // droite
            ajouterLesPosition( x-2, y ); // gauche
            ajouterLesPosition( x , taille-2 ); // haut
            ajouterLesPosition( x, y+2); // bas
        }
        /* taille-2 1 */
        else if(x == taille-2 && y == taille-1 ){
            ajouterLesPosition( ix, y ); // droite
            ajouterLesPosition( x-2, y ); // gauche
            ajouterLesPosition( x , y-2 ); // haut
            ajouterLesPosition( x, h); // bas
        }
       
         /* 1 taille-1 ou 2 taille-2 */
        else if(x == h && y == taille-1 ){
            ajouterLesPosition( x+2, y ); // droite
            ajouterLesPosition( taille-1, y ); // gauche
            ajouterLesPosition( x , y-2 ); // haut
            ajouterLesPosition( x, h); // bas
        }
        
        /* 10 1 */
        else if(x == taille-1 && y == iy){
            ajouterLesPosition( h, y); // droite
            ajouterLesPosition( x-2, y ); // gauche
            ajouterLesPosition( x , taille-2 ); // haut
            ajouterLesPosition( x , y+2 ); // bas
        }
        /* 10 1 */
        else if(x == taille-1 && y == h){
            ajouterLesPosition( h, y); // droite
            ajouterLesPosition( x-2, y ); // gauche
            ajouterLesPosition( x , taille-1 ); // haut
            ajouterLesPosition( x , y+2 ); // bas
        }
        /* 10 10 */
        else if(x == taille-1 && y == taille-1){
            ajouterLesPosition( h, y ); // droite
            ajouterLesPosition( x-2, y ); // gauche
            ajouterLesPosition( x , y-2 ); // haut
            ajouterLesPosition( x , h );  // bas
        }
        /* 10 10 */
        else if(x == taille-1 && y == taille-2){
            ajouterLesPosition( h, y ); // droite
            ajouterLesPosition( x-2, y ); // gauche
            ajouterLesPosition( x , y-2 ); // haut
            ajouterLesPosition( x , iy );  // bas
        }
        // ############ PAUSE 
        /*  X est à  1 ou 0 */
        else if( x == ix ){
            ajouterLesPosition( x+2, y); // droite
            ajouterLesPosition( taille-2, y ); // gauche
            ajouterLesPosition( x, y-2); // haut
            ajouterLesPosition( x, y+2); // bas
        }
        /* Y est à 1 ou 0 */
        else if(y == iy){
            ajouterLesPosition( x+2, y ); // droite
            ajouterLesPosition( x-2, y ); // gauche
            ajouterLesPosition( x, taille-2 ); // haut
            ajouterLesPosition( x, y+2 ); // bas
        }
        /*  X est à 10 */
        else if( x == taille-1 ){
            ajouterLesPosition( h, y); // droite
            ajouterLesPosition( x-2, y ); // gauche
            ajouterLesPosition( x, y-2); // haut
            ajouterLesPosition( x, y+2); // bas
        }
        /* Y est à 10 */
        else if( y == taille-1 ){
            ajouterLesPosition( x+2, y); // droite
            ajouterLesPosition( x-2, y ); // gauche
            ajouterLesPosition( x, y-2); // haut
            ajouterLesPosition( x, h); // bas
        }
        else if(x == ix && y == iy){
            ajouterLesPosition( x+2, y ); // droite
            ajouterLesPosition( taille-1, y); // gauche
            ajouterLesPosition( x, taille-1); // haut
            ajouterLesPosition( x, y+2 ); // bas
           
        }
        else if(x == taille-2 && y == taille-2){
            ajouterLesPosition( ix, y ); // droite
            ajouterLesPosition( x-2, y); // gauche
            ajouterLesPosition( x, y-2); // haut
            ajouterLesPosition( x, iy ); // bas
           
        }
        else if(x == taille-2 && y == h){
            ajouterLesPosition( ix, y ); // droite
            ajouterLesPosition( x-2, y); // gauche
            ajouterLesPosition( x, taille-1); // haut
            ajouterLesPosition( x, y+2 ); // bas
           
        }
        else if(x == 2 && y == taille-2){
            ajouterLesPosition( x+2, y ); // droite
            ajouterLesPosition( taille-1, y); // gauche
            ajouterLesPosition( x, y-2); // haut
            ajouterLesPosition( x, iy ); // bas
        }
        else if(x == h){
            ajouterLesPosition( x+2, y ); // droite
            ajouterLesPosition( taille-1, y); // gauche
            ajouterLesPosition( x, y-2); // haut
            ajouterLesPosition( x, y+2 ); // bas
        }
        else if(y == h){
            ajouterLesPosition( x+2, y ); // droite
            ajouterLesPosition( x-2, y); // gauche
            ajouterLesPosition( x, taille-1); // haut
            ajouterLesPosition( x, y+2 ); // bas
        }
        else if(x == taille-2){
            ajouterLesPosition( ix, y ); // droite
            ajouterLesPosition( x-2, y); // gauche
            ajouterLesPosition( x, y-2); // haut
            ajouterLesPosition( x, y+2 ); // bas
        }
        else if(y == taille-2){
            ajouterLesPosition( x+2, y ); // droite
            ajouterLesPosition( x-2, y); // gauche
            ajouterLesPosition( x, y-2); // haut
            ajouterLesPosition( x, iy ); // bas
        }
        else  {
            ajouterLesPosition( x+2, y); // droite
            ajouterLesPosition( x-2, y ); // gauche
            ajouterLesPosition( x, y-2 ); // haut
            ajouterLesPosition( x, y+2 ); // bas
        }
        listPetit.addAll(new DeplacementUneCase(taille,x,y,controleur).getList());
    }
    
   
    private  void ajouterLesPosition(int x, int y){
        listPetit.add(new Position( x,y )); // droite
    }

    public Set<Position> getList() {
        return listPetit;
    }
    
}
   
    
    

    


