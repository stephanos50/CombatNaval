/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import javafx.stage.Stage;
import model.Armee;
import model.Position;
import model.builder.Director;
import model.builder.JeuBuilder;
import vue.VueFx;

/**
 *
 * @author stefanos
 */
public class ControlerBuilder {
    
    private Stage stage;
    private int taille;
    private Director director;
    private JeuBuilder builder; 
    private ControlerFx control;
    private boolean armeeUn;
    private boolean armeeDeux;
    
    public ControlerBuilder(Stage stage, int taille, ControlerFx ctrl){
        this.control = ctrl;
        this.stage = stage;
        this.taille = taille;
    }
    public void setArmeeUn(boolean armeeUn) {
        this.armeeUn = armeeUn;
    }
    public void setArmeeDeux(boolean armeeDeux) {
        this.armeeDeux = armeeDeux;
    }
    public boolean isArmeeUn() {
        return armeeUn;
    }
    public boolean isArmeeDeux() {
        return armeeDeux;
    }
    public Director getD() {
        return director;
    }
    public String nomArmeeA1(){
        return directorNomArmeeA1();
    }
    public String nomArmeeA2(){
        return directorNomArmeeA2();
    }
    /* Donne le nom de l'armée Un depuis le Director */
    private String directorNomArmeeA1(){
        return director.nomArmeeA1();
    }
    /* Donne le nom de l'armée Deux depuis le Director */
    private String directorNomArmeeA2(){
        return director.nomArmeeA2();
    }
    private Armee builderArmeeA1(){
        return director.getArmeeA1();
    }
    private Armee builderArmeeA2(){
        return director.getArmeeA2();
    }
    /* Je passe par le Builder */
    /* Je construis en premier la mer , ensuite les deux Armées  */
    protected void constuireLeBuilder(int taille, String a1, String a2){
        director = new Director(); 
        builder = new JeuBuilder()
                .append(taille,a1,a2);
                
        director.setSeaBuilder(builder);
        director.getJeu().addObserver(vueFx(stage,taille,builderArmeeA1(),builderArmeeA2()));
        director.getJeu().setChangedAndNotify();
    }
    /* Construit la vue pour le builder */
    private VueFx vueFx(Stage stage, int taille, Armee a1, Armee a2){
        return new VueFx(stage,taille,a1,a2,control);
    }
     
    public boolean builder(){
        boolean valider = true;
        if(director != null){
            valider = false;
            if(endBuilderBateau()){
                builder.append(new PosMines().set(director.getJeu().getTaille(),director.getJeu().positionsDeToutLesBateaux()));
                valider = true;
            }
        }
        return valider;
    }
    
    public boolean verifierBuilder(){
        return endBuilderBateau();
    }
    /* Si la taille de mes deux listes est égale au nombre de bateaux  */
    /* La constructuion des bateaux est terminée */
    private boolean endBuilderBateau(){
        if(director.getJeu().a1().size() == 3 && director.getJeu().a2().size() == 3){
            return true;
        }
        return false;
    }
    /* on place les bâteaux sur la mer */
    public void placer(Position p, int index){
        if(armeeUn){
            builder.append(builderArmeeA1(), p, index);
        }
        if(armeeDeux){
            builder.append(builderArmeeA2(), p, index);
        }
    }
}
