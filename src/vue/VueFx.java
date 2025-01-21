/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import vue.fxBox.GameOver;
import control.ControlerFx;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Armee;
import model.BatGrand;
import model.Bateau;
import model.Case;

import model.Eau;
import model.Jeu;
import model.Mer;
import model.MineAtomic;
import model.MineClassic;
import model.Position;


public class VueFx extends GridPane implements Observer {
    private final HBox hbox = new HBox();
    private final BorderPane pane = new BorderPane();
    private GridPane gridA1 = new GridPane();
    private GridPane gridA2 = new GridPane();
    private final ControlerFx control;
    private BoxView bat;
    private Case [][]cases ;
    private Set<Position> positionTirer;
    private VBox box;
    private Button message;
    private Button annuler;
    private boolean classic;
    private boolean atomic;
    private final  int SIZE;
    private boolean bloquer;
    private static int pos_X = 0;
    private static int pos_Y = 0;
    private final String nomA1;
    private final String nomA2;
    private boolean armeeA1; 
    private boolean armeeA2;
    
    private boolean gameOver;
    private boolean isBuilder;
    private static final int tirer = 0;
    private int positionX;
    private int positionY;
    private static boolean mine;
    private String shoot;
    private int longeur;
    private int largeur;
    private int index;
    private TableView<EtatArmee> table;
   
    public VueFx(Stage stage,int taille,Armee a1, Armee a2, ControlerFx ctrl) {
        classic = mine;
        atomic = mine;
        this.nomA1 = a1.getArmee().getNom();
        this.nomA2 = a2.getArmee().getNom();
        control = ctrl;
        SIZE = taille;
        pane.setStyle("-fx-background-color: #336699;");
        donnerDimenssion(taille);
        top(nomA1, nomA2);
        center(this); 
        choixDeLaVue();
        afficherLeNomDeArmee();
        setSizeConstraints(this,SIZE);
        stage.setScene(new Scene(pane, longeur, largeur,true, SceneAntialiasing.DISABLED));
        stage.setTitle("Batail Naval");
        stage.show();
    }
    /* Modifie la taille de la scene en fonction de la taille  */
    private void donnerDimenssion(int taille){
        longeur = 900;
        largeur = 500;
        if(taille > 15){
            longeur = 1200;
            largeur = 700;
        }
        if(taille > 20){
            longeur = 1400;
            largeur = 700;
        }
        
    }
    public static void setMine(boolean mine) {
        VueFx.mine = mine;
    }
    /* Je verifie que le nom appartient à JeuBuilder ou à Jeu */
    private void afficherLeNomDeArmee(){
        if(control.getControlerBuilder() == null)
            message();
        if(control.getControlerBuilder() != null) {
            messageSelectionnerUnBateau(nomA1);
        }
    }
    // Pour que chaque ligne et chaque colonne soit dimensionnée
    private void setSizeConstraints(GridPane g,int SIZE) {
        int taille = 100;
        if(SIZE > 10){
            taille = 125;
        }
        for (int i = 0; i < SIZE; ++i) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(taille / SIZE);
            g.getColumnConstraints().add(cc);
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(taille / SIZE);
            g.getRowConstraints().add(rc);
        }        
    }
    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Jeu){
            Jeu jeu = (Jeu) o;
            Mer mer = jeu.getMer();
            getChildren().clear();
            this.cases = mer.getMer();
            armeeA(jeu.getA1().getList());
            armeeB(jeu.getA2().getList());
            for(int i = 0; i < mer.getTaille(); ++i){
                for(int j = 0; j < mer.getTaille(); ++j){
                    if(j == 0){ afficherAlphabet(i); }
                    else if(i == 0){ afficherNumero(j); }
                    else if(bateauList(jeu.a1(),i,j)){
                        armeeA1 = true;
                        if(estGrand(jeu.getA1().getList(),i,j)){
                            add(new BoatGrand(i, j), i, j);
                        } else {
                           add(new BoatPetit(i, j), i, j);
                        }
                        armeeA1 = false;
                    }  
                    else if( bateauList(jeu.a2(),i,j)){
                        armeeA2 = true;
                        if(estGrand(jeu.getA2().getList(),i,j)){
                            add(new BoatGrand(i, j), i, j);
                        } else {
                            add(new BoatPetit(i, j), i, j);
                        }
                        armeeA2 = false;
                    }
                    else if(!cases[i][j].getJouable()){
                        add(new Radioactive(i, j), i, j);
                    }
                    else if(cases[i][j].getCont() instanceof Eau){
                        add(new WaterView(i, j), i, j);
                    }
                    else if(cases[i][j].getCont() instanceof MineClassic && classic){
                        add(new MineView(i, j), i, j);
                    }
                    else if(cases[i][j].getCont() instanceof MineAtomic && atomic){
                        if(!classic){
                            if(i == positionX && j == positionY){
                               add(new MineAtomicView(i, j), i, j);
                            } else {
                                add(new WaterView(i, j), i, j);
                            }
                        } else if(classic) {
                            add(new MineAtomicView(i, j), i, j);
                        } else {
                            add(new WaterView(i, j), i, j);
                        }
                    } else {
                        add(new WaterView(i, j), i, j);
                    }
                }
            }
            if(control.isBouger()){
                afficherDepalcement();
            }
            else if(!control.isTirer() && isBuilder){
                if(!positionTirer.isEmpty()){
                    afficherTire();
                }
            }
        }
    }
    /* Je récupère les positions jouables de controleur pour les afficher */
    private void afficherDepalcement(){
        Set<Position> pos = control.positionsJouablesPourUnBateau(control.ix(), control.iy());
        for(Position p : pos){
            if(cases[p.getX()][p.getY()].getJouable()){
                add(new MoveView(p.getX(), p.getY()), p.getX(), p.getY());
            }
        }
        
    }
    /* Je récupère les positions des tires  pour les afficher */
    private void afficherTire(){
        if(isBuilder){
            for(Position p : positionTirer){
               add(new ShootView(p.getX(), p.getY()), p.getX(), p.getY());
            }
        }
    }
    /* La vue d'une lettre  */
    private class Alphabet extends LettreView{
        public Alphabet(int x, int y){
           getStyleClass().add(new Lettre().caractere(x-1));
        }
    }
    /* Affiche les nombres de 1 à ... */
    private class Nombre extends NombreView{
        public Nombre(int x, int y){
           getStyleClass().add(new Lettre().nombre(y-1));
        }
    }
    
    /* BULDER */
    /* Affiche les messages lorqu'on utilise le placement manuelle des bateau  */
    private void afficherLesMessage(){
        if(control.getControlerBuilder().isArmeeUn()){
            messageSelectionnerUnBateau(nomA1);
        }
        else if(control.getControlerBuilder().isArmeeDeux() ){
            messageSelectionnerUnBateau(nomA2);
        }
    }
    /* La vue va afficher  */
    private void choixDeLaVue(){
        if(control.choixControler()){
            control.setTirer(true);
            control.setArmeeUn(true);
            isBuilder = true;
            message();
        }
        else {
            control.getControlerBuilder().setArmeeUn(true);
            messageSelectionnerUnBateau(nomA1);
        }
    }
    /* BULDER */
    /* Supprime le bateau placer à gauche ou à droite */
    /* Ajoute le bateau sur la mère */
    private void placerBateauSurLaCase(BoxView b , BoxView courant){
        if(pos_X == 0 || pos_Y == 0){} // ne rien faire
         
        else {
            add(b,pos_X,pos_Y);
            getChildren().remove(courant);
            control.getControlerBuilder().placer(new Position(pos_X, pos_Y),index);
            modifier();
            afficherLesMessage();
        }
    }
    /* Modier les boolean des armmes  */ 
    private void modifier(){
         boolean a1 = control.getControlerBuilder().isArmeeUn();
         boolean a2 = control.getControlerBuilder().isArmeeDeux();
         control.getControlerBuilder().setArmeeUn(!a1);
         control.getControlerBuilder().setArmeeDeux(!a2);
     }
    /* switch le boolean des armées */
    private void commutateurArmee(){
        control.setArmeeUn(!control.isArmeeUn());
        control.setArmeeDeux(!control.isArmeeDeux());
        message();
    }
    // La vue d'une "case" Mine
    private class MineView extends BoxView {
        public MineView(int x, int y) {
            getStyleClass().add("classic");
        }
    }
    // La vue d'une "case" Atomic
    private class MineAtomicView extends BoxView {
        public MineAtomicView(int x, int y) {
            getStyleClass().add("atomic");
        }
    }
    // La vue d'une "case" Radioactive
    private class Radioactive extends BoxView {
        public Radioactive(int x, int y) {
            getStyleClass().add("shoot");
        }
    }
    // La vue d'une "case" Tirer 
    private class ShootView extends BoxView {
        public ShootView(int x, int y) {
            getStyleClass().add("shoot");
        }
    }
    // La vue d'une "case" Bouger  
    private class MoveView extends BoxView {
        public MoveView(int x, int y) {
            getStyleClass().add("move");
            this.setOnMouseReleased(e -> {
                actionSurMoveView(x,y);
            });
        }
    }
    /* Verifie que la case est de type Atomic, Classic avant une action */
    private void actionSurMoveView(int x, int y){
        commutateurAction();
        if(estClassic(x,y)){
            actionMoveView(x, y);
            afficherMessagerMine(x,y,Message.supprimer);
            
        }
        else if(estAtomic(x,y)){
            actionMoveView(x, y);
            afficherMessagerMine(x,y,Message.dinamic);
        }
        else {
            commutateurArmee();
            actionMoveView(x, y);
        }
    }
    
    private void actionMoveView(int x, int y){
        control.waterBoxClicked(x, y);
    }
    
    /* Affiche une message lors d'une rencontre avec une mine */
    private void afficherMessagerMine(int x, int y, Message msg){
        bottom(new VueFxMessage(donnerLeNomDeLarmee(), msg).getL());
        add(new MineView(x, y), x, y);
        afficherCalssic();
    }
    /* Patiente 2 secondes avant de passer à la suite */
    private void afficherCalssic(){
         Timeline timeline  = new Timeline(
            new KeyFrame(Duration.millis(500), b -> {
                control.notifier();
                commutateurArmee();
                
            })
        ); timeline.play();
    }
    /* Vérifie que la case à une mine de type atomic */
    private boolean estAtomic(int x, int y){
         return (cases[x][y].getCont() instanceof MineAtomic );
    }
    private boolean estClassic(int x, int y){
        boolean classic = false;
         if ((cases[x][y].getCont() instanceof MineClassic )){
             classic = true;
         }
         return classic;
    }
    // La vue d'une "case" Eau  
    private class WaterView extends BoxView {
        public WaterView(int x, int y) {
           
            getStyleClass().add("eau");
            this.setOnMouseClicked(e->{
                bloquer(x,y,this);
                if(bateauEnConstruction() && demanderLeControleurUtiliser()){ // fin de la construction du builder
                   control.donnerLeBuilder();
                }
            });
        }
    }
    /* Lors du palcement manuel des bâteaux  */
    private void bloquer(int x, int y, BoxView that ){
        if(bloquer){ // reserver pour le builder 
            bottom(new VueFxMessage(donnerLeNomDeLarmeeBuilder(),Message.cases).getL());
            pos_X = x;
            pos_Y = y;
            bloquer = false;
            placerBateauSurLaCase(bat,that);
        }
    }
     /* renvoie le nom de l'armée d'apres le boolean des deux armées */
    private String donnerLeNomDeLarmeeBuilder(){
        String nom = null;
        nom = control.getControlerBuilder().isArmeeUn() ? nomA1 : nomA2;
        return nom;
    }
    
    /* Image pour diffencier les grand bateaux des petits */
    private boolean estGrand(List<Bateau> l,int a, int b ){
        for(int i = 0; i < l.size(); ++i){
            if(l.get(i).getPosition().equals(new Position(a,b))){
                return (l.get(i) instanceof BatGrand);
            }
        }
        return false;
    }
    /* Vérifie que la position existe dans une liste de bateau */
    private  boolean bateauList(List<Bateau> l, int a, int b){
        for(int i = 0; i < l.size(); ++i){
            if(l.get(i).getPosition().equals(new Position(a,b))){
                    return true;
            }
        }
        return false;
    }

   private void afficherAlphabet(int i){
       add(new Alphabet(i, 0), i, 0);
   }
   private void afficherNumero(int j){
       add(new Nombre(0, j), 0, j);
   }
    

   /* Cliquer sur un petit bateau armée  */
    private class BoatPetit extends BoxView{
        public BoatPetit(int x, int y) {
            getStyleClass().add(donnerLesImagesDesBateaux(this));
            this.setOnMouseClicked((e)->{ 
               if(!verifierLecControleur()){ // je suis builder 
                    actionSurPlacementManuel(this,getStyleClass());
                }
            });
            this.setOnMousePressed(e -> {
                if(verifierLecControleur()){ // je ne suis plus builder 
                    commencerLesActions(x,y);
                }
            });
            
        }
    }
    
    // Cliquer sur un Grand Bateau 
    private class BoatGrand extends BoxView {
        public BoatGrand(int x, int y) {
            getStyleClass().add(donnerLesImagesDesBateaux(this));
            this.setOnMouseClicked((e)->{ 
                if(!verifierLecControleur()){ // je suis builder 
                    actionSurPlacementManuel(this,getStyleClass());
                }
            }); 
            this.setOnMousePressed(e -> {
                if(verifierLecControleur()){ // je ne suis plus builder 
                    commencerLesActions(x,y);
                }
            }); 
           
        } 
    }
    private void actionSurPlacementManuel(BoxView that, ObservableList<String> styleClass){
        if(control.getControlerBuilder().isArmeeUn() && that.getLayoutX() == 0.0){
            if(styleClass.contains("a1_petit")){
                index = 1;
                recupererLeBateau(that);
            } else if(styleClass.contains("a1_grand")){
                index = 0;
                recupererLeBateau(that);
            }
            
        }  
        else if(control.getControlerBuilder().isArmeeDeux() && that.getLayoutX() == 0.0){
            if(styleClass.contains("a2_petit")){
                index = 1;
                recupererLeBateau(that);
            } else if(styleClass.contains("a2_grand")){
                index = 0;
                recupererLeBateau(that);
            }
           
        }  
    }
    private void attendre(int x, int y){
        Timeline timeline  = new Timeline(
            new KeyFrame(Duration.millis(500), b -> {
               commencerLesActions(x,y);
            })
        );
        timeline.play();
    }
    /* Commence l'action pour tirer  */
    private void commencerLesActions(int x, int y){
        if(control.isArmeeUn() && !control.isArmeeDeux()){
            if(control.obtenirA1(x, y)) { action(x,y); }
        }
        if(control.isArmeeDeux() && !control.isArmeeUn()){
            if(control.obtenirA2(x, y)) { action(x,y); }
        }
    }
    /* Vérifie sur quel image on a cliqué */
    private String donnerLesImagesDesBateaux(BoxView that){
        String image = null;
        if(that instanceof BoatPetit){
            image = (armeeA1) ? "a1_petit" : "a2_petit";
        }
        if(that instanceof BoatGrand){
            image = (armeeA1) ? "a1_grand" : "a2_grand";
        }
        return image;
    }
    
    /* Vérifie que l object controleurBuilder existe */
    private boolean verifierLecControleur(){
        return (control.getControlerBuilder() == null);
    }
    /* demande quel controleur est utilisé */
    private boolean demanderLeControleurUtiliser(){
        if(control.choixControler()){
            return false;
        }
        return true;
    }   
    /* verifier si la construction des bateaux est terminée */
    private boolean bateauEnConstruction(){
        if(demanderLeControleurUtiliser()){
            return control.getControlerBuilder().builder();
        } return true;
    }
    
    /* Je verifie la position en X à gauche et à droite du GridPane */
    private boolean  verifierLaPosition(BoxView that){
        final double x = that.layoutXProperty().getValue();
        return ( x == 0.0);
    }
    /* Récupere le bateau et mais bloquer à true */
    private void recupererLeBateau(BoxView that){
        if(verifierLaPosition(that)){
            bat = that;
            bloquer = true; // empêche une action lorqu'on clik sur l'eau
            bottom(new VueFxMessage(donnerLeNomDeLarmeeBuilder(),Message.cases).getL());
        }
    }
    /* Les evenements lorqu'on click sur un bateau d'une armée */
    private void action(int x, int y){
        if(control.isTirer()){
            actionMousePresser(x,y);
            attendre(x,y);
        } else{
            actionMouseRelacher(x,y);
        }
    }
    private void actionMousePresser(int x, int y){
        positionTirer = control.tirer(x, y);
        donnerLeTire(positionTirer);
        donnerLeMessageTirer(Message.shoot,shoot);
        control.notifier();
    }
    private void donnerLeTire(Set<Position> tires){
        if(tires.isEmpty()){
            shoot = "0";
        } else {
            shoot = (tires.size()>11) ? "2" : "1";
        }
    }
    private void actionMouseRelacher(int x, int y){
        if(control.isBouger()){
            donnerLeMessage(Message.bouger);
            control.bouger(x, y);
        } else if(!control.isBouger()){
            donnerLeMessage(Message.deplacerBateau);
            actionPasserTour();
            tirerDevientTrue();
        }
    }
    /* Crée un boutton pour le message  */
    private void donnerLeMessageTirer(Message msg, String t){
        bottom(new VueFxMessage(donnerLeNomDeLarmee(),msg,t).getL());
    }
    /* Crée un boutton pour le message  */
    private void donnerLeMessage(Message msg){
        bottom(new VueFxMessage(donnerLeNomDeLarmee(),msg).getL());
    }
    private void tirerDevientTrue(){
        control.setTirer(true);
        control.notifier();
        commutateurAction();
    }
   
    private void actionPasserTour(){
        message.setOpacity(1);
        message.setOnMouseClicked(e->{
            passerSonTour();
        });
    }
    private void passerSonTour(){
        control.setBouger(false);
        control.setTirer(true);
        commutateurArmee();
        control.notifier();
    }
    /* switch l'action tirer et bouger au départ du jeu tirer est à true utilisé pour les messages et les actions */
    private void commutateurAction(){
        if(control.isTirer()){
            commuterLesActionsTirerBouger();
        }
        else if(control.isBouger()){
            commuterLesActionsTirerBouger();
        }
    }
    private void commuterLesActionsTirerBouger(){
        control.setTirer(!control.isTirer());
        control.setBouger(!control.isBouger());
    }
    /* Envoie les messages tirer ou bonger */
    private void message(){
        if(control.isTirer() && !gameOver){
            donnerLeMessage(Message.tirer);
        }
        else if(control.isBouger() && !gameOver){
            donnerLeMessage(Message.bouger);
        }
    }
    
    
    /* renvoie le nom de l'armée d'apres le boolean des deux armées */
    private String donnerLeNomDeLarmee(){
        String nom = null;
        nom = control.isArmeeUn() ? nomA1 : nomA2;
        return nom;
    }
    /* Envoie le message "choisir un bateau" */
    private void messageSelectionnerUnBateau(String nom){
        bottom(new VueFxMessage(nom,Message.bateau).getL());
    }
    // La vue d'une "case"
    private abstract class BoxView extends Pane {
        public BoxView() { getStylesheets().add("vue/css/BoxView.css"); }
    }
    // La vue d'une "case"
    private abstract class LettreView extends Pane {
        public LettreView() { getStylesheets().add("vue/css/LettreView.css"); }
    }
    // La vue d'une "case"
    private abstract class NombreView extends Pane {
        public NombreView() { getStylesheets().add("vue/css/NombreView.css"); }
    }
    private void top(String a, String b){
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(600);
        hbox.setStyle("-fx-background-color: #336699;");
        donnerCouleurAuNom(a,b);
        pane.setTop(hbox);
    }
    private void donnerCouleurAuNom(String a, String b){
        Text a1 = newText(a);
        Text a2 = newText(b);
        a1.setFill(Color.BLUE);
        a2.setFill(Color.RED);
        hbox.getChildren().addAll(a1, a2);
    }
    private Text newText(String a){
        Text t = new Text(a);
        t.setFont(new Font(20));
        return t;
    }
    private void center(Pane that){
        pane.setCenter(that);
        that.setPadding(new Insets(5, 5, 5, 5));
    }
    /* TavleView pour l'armee A1  */
    private void armeeA(List<Bateau> a){
        if(demanderLeControleurUtiliser()){
           imageBateauA1();
        } else {
            construireTableViewA1(a);
        }
        if(a.isEmpty() && isBuilder){
            isBuilder = false;
            arreterJeux(nomA2);
        }
    }
    /* N'est pas correct mais fonctionne */
    private void arreterJeux(String nom){
        Timeline timeline  = new Timeline(
            new KeyFrame(Duration.millis(500), b -> { 
                arreterLeJeu(nom); 
            })
        ); timeline.play();
    }
    private void construireTableViewA1(List<Bateau> a){
        table = table(a);
        table.getStylesheets().add("vue/css/TableA1.css");
        pane.setMargin(table, new Insets(10, 10, 10, 10));
        pane.setLeft(table);
    }
    /* TableView pour l'Armée A2 */
    private void armeeB(List<Bateau> b){
        if(demanderLeControleurUtiliser()){
            imageBateauA2();
        } else {
           construireTableViewA2(b);
        }
        if(b.isEmpty() && isBuilder){
            isBuilder = false;
            arreterJeux(nomA1);
        }
    }
    private TableView table(List<Bateau> list){
        table =  new VueTableView(list).getTable();
        return table;
    }
    private void construireTableViewA2(List<Bateau> b){
        table = table(b);
        table.getStylesheets().add("vue/css/TableA2.css");
        pane.setMargin(table, new Insets(10, 10, 10, 10));
        pane.setRight(table);
    }
    private void arreterLeJeu(String nom){
       
        gameOver = true;
        pane.getChildren().clear();
        pane.setCenter(new GameOver(control.getStage(),control,nom));
        pane.setPadding(new Insets(5, 5, 5, 5));
        
    }
    private void bottom(Button label){
        message = new Button(Message.passerTour.toString());
        message(message);
        label(label);
        donnerCouleurBouton(label);
        box = new VBox(label,message);
        pane.setBottom(box);
    }
    /* Boutton affiche message tirer bouger deplacer */
    private void label(Button b){
        b.setPrefWidth(2000.00);
        b.setPrefHeight(27.0);
        b.setDisable(true);
        b.opacityProperty().set(1.0);
    }
    /* Boutton passer son tour */
    private void message(Button b){
        b.setOpacity(0);
        b.setPrefWidth(2000.00);
        b.setPrefHeight(27.0);
        b.setFont(new Font(18));
    }
    private void donnerCouleurBouton(Button label){
        if(control.getControlerBuilder() != null){
            boolean a1 = control.getControlerBuilder().isArmeeUn();
            donnerLaCouleurButton(label, a1);
        }
        else {
            boolean a1 = control.isArmeeUn();
            donnerLaCouleurButton(label,a1);
        }
    }
    /* Donne la couleur au boutton de Vue Builder */
    private void donnerLaCouleurButton(Button label, boolean a1){
        String couleur = null;
        if(a1){
            couleur = "-fx-font: 20 arial; -fx-base: #1E7FCB;";
            donneCouleur(label, couleur);
        }
        else {
            couleur = "-fx-font: 20 arial; -fx-base: #DE2916;";
            donneCouleur(label, couleur);
        }
    }
    private void donneCouleur(Button label, String couleur){
        label.setStyle(couleur);
        message.setStyle(couleur);
    }
    private void construireBateau(GridPane grid){
        ajouterDansGridPAne(grid,new BoatGrand(0,0),0,0);
        ajouterDansGridPAne(grid,new BoatPetit(0,0),0,1);
        ajouterDansGridPAne(grid,new BoatPetit(0,0),0,2);
    }
    private void ajouterDansGridPAne(GridPane grid,BoxView boat, int x, int y){
        grid.add(boat,x,y);
    }
    private void imageBateauA1(){
        armeeA1 = true;
        initialiserGridPane(gridA1);
        construireBateau(gridA1);
        pane.setLeft(gridA1);
        armeeA1 = false;
    }
    private void imageBateauA2(){
        armeeA2 = true;
        initialiserGridPane(gridA2);
        construireBateau(gridA2);
        pane.setRight(gridA2);
        armeeA2 = false;
    }
    private void initialiserGridPane(GridPane grid){
        grid.setMinWidth(100);
        setSizeConstraints(grid,4);
        BorderPane.setMargin(grid, new Insets(50, 50, 50, 50));
    }
}
    
    
    
    
    
    
    
    
    
    
    
   
    

