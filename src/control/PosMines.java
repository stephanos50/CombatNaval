/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.Position;

/**
 *
 * @author stefanos
 */
public class PosMines implements PosMine {
    private List<Position> list = new ArrayList<>();
    private Random random = new Random();
    private int size;
    
    @Override
    public List<Position> set(int taille,List<Position> bateau) {
        size = ((taille-1)*(taille-1)); 
        for(int i = 1; i < size; ++i){
            if(random(random)){
                Position p = new Position(random.nextInt(taille-1)+1, random.nextInt(taille-1)+1);
                if (!verifierPosition(p,bateau)){
                    list.add(p);
                }
            }
        }
        return list;
    }
    /* Vérifie si la position est égale à une position de bateau */
    private boolean verifierPosition(Position p, List<Position> bateau){
        boolean egale = false;
        for(Position pos : bateau){
            if(pos.equals(p)){
                egale = true;
            }
        }
        return egale;
    }

    /* Pour chaque case renvoie un random vrai ou false */
    private boolean random(Random random){
        boolean present = false;
        int nbr = random.nextInt((100 - 1) + 1);
        if(nbr <= 10 && nbr >= 1){
            present = true;
        } 
        return present;
        
    }

    
    
}
    

