/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author stefanos
 */
public class Action {
    private int x; 
    private int y;
 
    public Action(String input){
       action(input);
    }
    private void action(String input){
        x = transofrmerX(input);
        y = transfomerY(input);
    }
    private int transormerCharToInteger(int y ){
        return  (y > 90) ?  y - 97 :  y - 65;
    }
    private  int transfomerY(String s){
        return  transormerCharToInteger(s.charAt(0));
        
    }
    
    private int transofrmerX(String s){
        return  s.charAt(1)-49;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
