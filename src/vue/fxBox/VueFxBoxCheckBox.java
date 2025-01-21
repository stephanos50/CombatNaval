/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue.fxBox;

import javafx.scene.control.CheckBox;

/**
 *
 * @author stefanos
 */
public class VueFxBoxCheckBox {
    private CheckBox box ;
    public VueFxBoxCheckBox(double x, double y){
        box = new CheckBox();
        box.layoutXProperty().set(x);
        box.layoutYProperty().set(y);
    }
    public CheckBox getBox() {
        return box;
    }
    
       
}
