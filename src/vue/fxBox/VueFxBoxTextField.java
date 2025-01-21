/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue.fxBox;

import javafx.scene.control.TextField;

/**
 *
 * @author stefanos
 */
public class VueFxBoxTextField {
    private TextField texte;
    public VueFxBoxTextField(String nom, double x, double y, double taille){
        texte = new TextField(nom);
        texte.layoutXProperty().set(x);
        texte.layoutYProperty().set(y);
        texte.prefWidthProperty().set(taille);
    }
    public TextField getTexte() {
        return texte;
    }
    
}
