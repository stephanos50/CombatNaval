/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.HashSet;
import java.util.Set;
import model.Position;
public class PorterDeux {
    private int h;
    private Set<Position> list = new HashSet<>();
    public PorterDeux(int taille, int x, int y,int z) {
        porterDeux(taille,x, y,z);
    }
    private void porterDeux(int taille, int x, int y, int z) {
        
        int cpt = 0;
        if(z == 0){
            h =1;
        }
        else {
            h=2;
        }
        int max = taille-1;
        
        int a = z;
        int b = z;
        int x_depart = x;
        int y_depart = y;
        
        
        if (x == z) {
            x_depart = max-1;
            a = x_depart;
        } 
         else if( x == h && y == max){
             
            x_depart = max ;
            a = x_depart;
            y_depart = y_depart-2;
            b = y_depart;
        }
        else if(x!=z ) {
            x_depart = x_depart - 2;
            a = x_depart;
        }
        if(x == h && y == z){
            y_depart = max-1;
            b = y_depart;
            x_depart = max ;
            a = x_depart;
        }
        
        if (y == z) {
          
            y_depart = max-1;
            b = y_depart;
        } else if(y == h && x == h ){
         
            y_depart = max;
            b = y_depart;
            x_depart = max;
            a = x_depart;
        }else if( x == h && y == (max-1)){
            
            y_depart =y_depart -2;
            b = y_depart;
            x_depart = max ;
            a = x_depart;
        }
        else if(x == h && y !=1 && y != max){
            
            y_depart = y_depart-2;
            b = y_depart;
            x_depart = max ;
            a = x_depart;
        }
        
        else if(y == h){
            y_depart = max;
            b = y_depart; 
        }else if(y == max && x == h) {
                // on ne fait rien 
        }
        else {
        
            y_depart = y_depart - 2;
            b = y_depart;
        }
        
        for (int i = 0; i < 5   ; i++) {
            for (int j = 0; j < 5; j++) {
                if (b > max) {
                    b = z;
                    list.add(new Position(a,b));
                    b = b + 1;
                    ++cpt;
                } else {
                    list.add(new Position(a,b));
                    b = b + 1;
                    ++cpt;
                }
            }
            if (a >= max) {
                a = z;
            } else {
                a = a + 1;
            }
            b = y_depart;
        }
    }

    public Set<Position> getList() {
        
        return list;
    }

    
      
    
    
}
