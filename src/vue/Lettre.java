/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;
/**
 *
 * @author stefanos
 */
public class Lettre {
    private  String string;
    
   
    
    /* Renvoie le caractere de la lettre   */
    public  String  caractere(int x){
        for(Alphabet l : Alphabet.values()){
            if(l.ordinal() == x){
                string = l.name();
            }
        }
        return string;
    }
    /* Renvoie le caractere de la lettre   */
    public  String  nombre(int x){
        for(Alphabet l : Alphabet.values()){
            if(l.ordinal() == x){
                string = l.toString();
            }
        }
        return string;
    }
}
