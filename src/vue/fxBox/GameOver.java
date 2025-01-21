/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue.fxBox;

import control.ControlerFx;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import vue.fxBox.VueFxBox;
import vue.fxBox.VueFxBoxLabel;
import vue.fxBox.VueFxGameOverButtom;

/**
 *
 * @author stefanos
 */
public class GameOver extends Parent {
    private Button quitter;
    private Button jouer;
    private Label game;
    private Label vainqueur;
    private Stage stage;
    String nom;
    private ControlerFx control;
    
    public GameOver(Stage stage , ControlerFx ctrl, String nom){
        this.control = ctrl;
        this.stage = stage;
        this.nom = nom;
        setup();
        jouer();
        quitter();
        stage.setTitle(" GAME OVER ");
        stage.setScene(new Scene(this, 600, 400));
        stage.resizableProperty().set(true);
        stage.show();
        
    }
    
    private void setup(){
        game = new VueFxBoxLabel(" Game Over ",200.0,89.0).getLable();
        vainqueur = new VueFxBoxLabel( nom + " " + "vous avez gagné  ",120.0,170.0).getLable();
        quitter = new VueFxGameOverButtom("Quitter",174.0,265.0,66.0).getButton();
        jouer = new VueFxGameOverButtom("Jouer",350.0,265.0,66.0).getButton();
        getChildren().addAll(jouer,quitter,game,vainqueur);
        getStylesheets().add("vue/css/GameOver.css");
    
    }
    private void quitter(){
        quitter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.exit(0);
            }
        });
       
    }
    private void jouer(){
         jouer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                control.zero();
                new VueFxBox(stage,control);
            }
        });
    
    }
    
    
}
