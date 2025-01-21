/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;


/**
 *
 * @author stefanos
 */
public class BatGrand extends Bateau {

    public BatGrand(Position p) {
        super(p);
        this.resistance = 2;
        this.integriter = 100;
    }
    @Override
    public String getType() {
        return "Big";
    }
    @Override
    public int tire() {
        int tir = 0;
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 1) + 1) + 1;
        if (randomNum <= 20 && randomNum >= 1) {
            tir = 0;
        } else if (randomNum <= 50 && randomNum >= 21) {
            tir = 1;
        } else if (randomNum <= 100 && randomNum >= 51){
            tir = 2;
        }
        return tir;
    }
}
