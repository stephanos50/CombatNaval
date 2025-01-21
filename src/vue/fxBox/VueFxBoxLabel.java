/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue.fxBox;

import javafx.scene.control.Label;

/**
 *
 * @author stefanos
 */
public class VueFxBoxLabel  {
    private Label lable;
    public VueFxBoxLabel(String titre, double x, double y){
        lable = new Label(titre);
        lable.layoutXProperty().set(x);
        lable.layoutYProperty().set(y);
    }
    public Label getLable() {
        return lable;
    }
    
    
}
