/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue.fxBox;

import control.ControlerFx;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;              
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

/**
 *
 * @author stefanos
 */
public class VueFxBox extends Pane{
    private boolean aleatoire = false; 
    private boolean mine = false;
    private final ControlerFx control;
    private Button btn;
    private TextField a1;
    private TextField a2;
    private TextField taille;
    private Label placer;
    private Label modeDebug;
    private Label dim;
    private CheckBox alea;
    private CheckBox debug;
    

    public VueFxBox(Stage stage,ControlerFx ctrl) {
        
        control = ctrl;
        setup();
        box();
        bouton();
        
        stage.setTitle(" Welcom ");
        stage.setScene(new Scene(this, 600, 400));
        stage.resizableProperty().set(true);
        stage.show();
    }
    
    private void setup(){
        a1 = new VueFxBoxTextField("Armee A1",84.0,59.0,150.0).getTexte();
        a2 = new VueFxBoxTextField("Armee A2",301.0,59.0,150.0).getTexte();
        placer = new VueFxBoxLabel(" Placement manuel : ",100.0,233.0).getLable();
        modeDebug = new VueFxBoxLabel(" Mode Debug  ",100.0,270.0).getLable();
        dim = new VueFxBoxLabel(" Dimmension: ",100.0,167.0).getLable();
        debug = new VueFxBoxCheckBox(301.0,275.0).getBox();
        alea = new VueFxBoxCheckBox(301.0,241.0).getBox();
        taille = new InputNumber();
        taille.layoutXProperty().set(301.0);
        taille.layoutYProperty().set(171.0);
        taille.prefWidthProperty().set(50.0);
        btn = new Button("Jouer");
        getStylesheets().add("vue/css/Fxml.css");
    }
    
    private void box(){
         alea.setOnMouseClicked(e -> {
            aleatoire = !aleatoire;
            mine = false;
        });
         debug.setOnMouseClicked(e->{
             mine = !mine;
         });
         
    }
    
    private void bouton(){
        btn.layoutXProperty().set(299.0);
        btn.layoutYProperty().set(309.0);
        
        btn.setOnAction(e -> {
            
            if (!taille.getText().isEmpty()) {
                switchToVueFx(a1.getText(),a2.getText(),aleatoire,mine,Integer.valueOf(taille.getText()));
            }
            else {
                System.out.println("je suis du txte");
                taille.requestFocus(); // Laisse le focus au TextField
            }
        });
        
        btn.setAlignment(Pos.CENTER);
        getChildren().addAll(a1,a2,placer,modeDebug,alea,debug,dim,taille,btn);
        setPadding(new Insets(20));
        
    }
   
    /* Passer à la fenêtre Principale vers le Controler */
    private void switchToVueFx(String nomA1, String nomA2,boolean aleatoire,boolean mine, int size) {
        int taille = tailleMaximum(size);
        
        control.switchToVueFx(nomA1,nomA2,aleatoire,mine,taille);
    }
    private int tailleMaximum(int taille){
        if(taille > 26){  // taille maixmum
            taille = 26;
        }
        else if(taille < 5){ // taille minimum
            taille = 5;
        }
        return taille;
    }
    
    // Un TextField qui n'accepte que des saisies de nombre entiers naturels
    private class InputNumber extends TextField {
        InputNumber() {
            super("5");
            setAlignment(Pos.CENTER);
            setMaxWidth(150);
            installListeners();
        }
        
        private void installListeners() {
            // N'accepte que les chiffres
            textProperty().addListener((obs, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    setText(oldValue);
                }
            });
            // Capture du Enter pour valider saisie
            setOnKeyPressed(ke -> {
                if (ke.getCode().equals(KeyCode.ENTER) && !getText().isEmpty()) {
                    switchToVueFx(a1.getText(),a2.getText(),aleatoire,mine,Integer.valueOf(taille.getText()));
                }
            });            
        }
        
        
        

    }
    
    
}
