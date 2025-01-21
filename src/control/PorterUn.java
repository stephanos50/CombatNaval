/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.HashSet;
import java.util.Set;
import model.Position;
/**
 *
 * @author 0106olomelcenco
 */
public class PorterUn {
    private  Set<Position> list = new HashSet<>();
    
    public  PorterUn(int taille, int x, int y, int z){
        
       
        int max = taille-1;
        int a = z;
        int b = z;
        int x_depart = x;
        int y_depart = y;
      
        if (x == z) {
            x_depart = max;
            a = x_depart;
        } else {
            x_depart = x_depart - 1;
            a = x_depart;
        }
        if (y == z) {
            y_depart = max;
            b = y_depart;
        } else {
            y_depart = y_depart - 1;
            b = y_depart;
        }
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (b > max) {
                    b = z;
                    list.add(new Position(a,b));
                    b = b + 1;
                    
                } else {
                    list.add(new Position(a,b));
                    b = b + 1;
                    
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

    public  Set<Position> getList() {
        return list;
    }

    public void setList(Set<Position> list) {
        this.list = list;
    }
    
    
}
