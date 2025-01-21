/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue.fxBox;

import javafx.scene.control.Button;

/**
 *
 * @author stefanos
 */
public class VueFxGameOverButtom {
    private Button button;
    
    public VueFxGameOverButtom(String string , double x, double y, double taille){
        button = new Button(string);
        button.layoutXProperty().set(x);
        button.layoutYProperty().set(y);
        button.prefWidthProperty().set(taille);
        
    }

    public Button getButton() {
        return button;
    }
    
}
