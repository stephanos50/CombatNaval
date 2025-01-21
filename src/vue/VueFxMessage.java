/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
/**
 *
 * @author stefanos
 */
public class VueFxMessage {
    private Button l = new Button();
    
    public VueFxMessage(String nom, Message message){
        l.setText(nom + " " + message );
        l.setFont(new Font(18));
    }
    
    public VueFxMessage(String nom, Message message, String t){
        l.setText(nom + " " + message + " " + t );
        l.setFont(new Font(18));
        
    }
    
    public Button getL() {
        return l;
    }
}
