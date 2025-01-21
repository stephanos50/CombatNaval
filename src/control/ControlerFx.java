/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;




import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Armee;
import model.Jeu;
import model.Mer;
import model.Position;
import vue.VueFx;
import vue.fxBox.VueFxBox;
/**
 *
 * @author stefanos
 */
public class ControlerFx extends Application{
    private boolean nouveau = false;
    private Random random = new Random();
    private ControlerBuilder builder;
    private Stage stage;
    private Jeu jeu;
    private int size;
    private boolean etatDeplacementBateau; 
    private boolean armeeUn;
    private boolean armeeDeux;
    private boolean bouger;
    private boolean tirer;
    private String nomA1;
    private String nomA2;
    
    
    

    public boolean isArmeeDeux() {
        return armeeDeux;
    }
    public boolean isArmeeUn() {
        return armeeUn;
    }
    public void setArmeeUn(boolean armeeUn) {
        this.armeeUn = armeeUn;
    }
    public void setArmeeDeux(boolean armeeDeux) {
        this.armeeDeux = armeeDeux;
    }
    public void setTirer(boolean tire){
        tirer = tire;
    }
    public void setBouger(boolean bouge){
        bouger = bouge;
    }
    public boolean isBouger() {
        return bouger;
    }
    public boolean isTirer() {
        return tirer;
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        new VueFxBox(stage,this);
    }
    public static void main(String[] args) {
        launch(args);
    }
    
    private VueFx vueFx(Stage stage, int taille, Armee a1, Armee a2){
         return new VueFx(stage,taille,a1,a2,this);
    }

    public ControlerBuilder getControlerBuilder() {
        return builder;
    }
    
    public boolean choixControler(){
        if(builder == null){
            return true;
        }
        return false;
    }
   
    /* si valeur est à vrai on construit le builder  */
    public void switchToVueFx(String nomA1, String nomA2, boolean aleatoire,boolean mine,int taille){
        VueFx.setMine(mine);
        this.nomA1 = nomA1;
        this.nomA2 = nomA2;
        this.size = taille+1;
        if(aleatoire){
            this.builder = new ControlerBuilder(stage,size,this);
            this.builder.constuireLeBuilder(size, nomA1, nomA2);
        }
        else {
            initialiserJeu(size);
            jeu.addObserver(vueFx(stage,size,a1(), a2()));
            jeu.setChangedAndNotify();
            if(nouveau){
                nouveau = false;
            }
        }
    }
    
    /* Des que la construction est terminée c'est à dire   */
    /* La mer , les armées , les bateaux placés et pour finir les mines placées */
    /* Je passe le Director à Jeu */
    public void donnerLeBuilder(){
        jeu = this.builder.getD().getJeu();
        builder = null;
        jeu.addObserver(vueFx(stage,size,a1(), a2()));
        jeu.setChangedAndNotify();
        verifier();
    }
    
    /* Verifie les booleans des deux armées apres une nouvelle partie */
    private void verifier(){
       if(!tirer){
            tirer = true;
            bouger = false;
        }
        if(!armeeUn && armeeDeux){
            armeeUn = true;
            armeeDeux = false;
        }
        nouveau = false;
    }
    
    public boolean obtenirA1(int x, int y){
        return getA1().obtenir(new Position(x,y));
    }
    public boolean obtenirA2(int x, int y){
        return getA2().obtenir(new Position(x,y));
    }
    
    public Stage getStage() {
        return stage;
    }
    public Armee getA1(){
        return jeu.getA1();
    }
    public Armee getA2(){
        return jeu.getA2();
    }
    // Quand l'utilisateur clique sur une case vide
    public boolean waterBoxClicked(int x, int y) {
        Position p = new Position(x,y);
        boolean rencontrerMine = false;
        if(etatDeplacementBateau) {
            if(jeu.verifierPosition(p)){
                if(jeu.deplacerBateau(p)){
                    rencontrerMine = true;
                    jeu.setChangedAndNotify();
                }
                else {
                    jeu.setChangedAndNotify();
                }
                etatDeplacementBateau = false;
                
            }
        }
        return rencontrerMine;
    }

    /* Récupère le tir pour calculer toutes les positions */
    private Set<Position> tirer(Armee a, int x, int y){
        Set<Position> positionTirer = new HashSet<>();
        int t = jeu.tire(a,x,y);
        if(t == 1){
            positionTirer = new PorterUn(getMer().getTaille(), x, y,1).getList();
            jeu.tirerSurLesBateaux(a,positionTirer);
        }
        else if(t == 2){
            positionTirer = new PorterDeux(getMer().getTaille(),x,y,1).getList();
            jeu.tirerSurLesBateaux(a,positionTirer);
        }
        return positionTirer;
    }
    public int ix(){
        return Position.getIx();
    }
    public int iy(){
        return Position.getIy();
    }
    private Set<Position> tirerSurBateau(Armee a, int x, int y){
        Set<Position> pos = tirer(a,x,y);
        tirer = !tirer;
        return pos;
    }
    
    public void notifier(){
        jeu.setChangedAndNotify();
    }
    private void bougerUnBateau(int x, int y){
        jeu.sauvegarder(x, y);
        jeu.setChangedAndNotify();
        etatDeplacementBateau = true;
        
    }
    
    public void bouger(int x, int y){
        if(armeeUn){
            bougerUnBateau(x,y);
        }
        else if(armeeDeux){
            bougerUnBateau(x,y);
        }
    }
    
    
    // Quand l'utilisateur clique sur un bateau
    public Set<Position> tirer(int x, int y) {
        Set<Position> pos = new HashSet<>();
        if(armeeUn){
            jeu.sauvegarder(x, y);
            pos = tirerSurBateau(a1(),x,y);
        }
        else if(armeeDeux){
            jeu.sauvegarder(x, y);
            pos = tirerSurBateau(a2(),x,y);
        }
        return pos;
    }
    
    /* Renvoi les positions jouable d'un bâteau pour afficher*/
    public Set<Position> positionsJouablesPourUnBateau(int x, int y){
        Set<Position> position = new HashSet<>();
        if(!nouveau){
            position.addAll(jeu.positionsJouablesPourUnBateau(x, y));
        }
       
        return position;
    }
    /* Taille */
    private void taille(int taille){
        size = taille;
    }
    
    /*  Je demande la Mer*/
    private Mer getMer(){
        return jeu.getMer();
    }
     /* Je demande l'armée un  */
    private Armee a1(){
        return jeu.getA1();
    }
    /* Je demande l'arméee deux */
    private Armee a2(){
        return jeu.getA2();
    }
    
    public void mer(int taille){
        jeu = new Jeu(taille,nomA1,nomA2);
        
    }
    /* Envoie une position aléatoire  */
    private void donnerPositions(Armee a) {
        int cpt = 0;
        while(cpt < 3){
            int x = getX();
            int y = getY();
            if(jeu.positionAleatoire(a,new Position(x,y),cpt)){
                ++cpt;
            } 
        }
        
    }
    /* Tant que une position de A1 est idendique à A2 */
    /* Je vide la liste de A2 et donne nouvelle position */
    private void verifierLesDeuxListes(){
        while(!checkLists()){
            if(jeu.viderList(a2())){
                donnerPositions(a2());
            }
            checkLists();
        }
    }
    /* Donner l'ensemble des positions pour les les mines */
    private void minePosition(){
        jeu.ajouterMine(new PosMines().set(getMer().getTaille(),jeu.positionsDeToutLesBateaux()));
    }
    /* Je verifie que les deux listes des armées ont bien  des positions différentes */
    private boolean  checkLists(){
        return jeu.checkLists(a1(), a2());
    }
    /* Renvoie une position aléatoire en X */
    private  int getX(){
        int x = random.nextInt(size-1)+1;
        return x;
    }
    /* Renvoie une position aléatoire en Y */
    private  int  getY(){
        int y = random.nextInt(size-1)+1;
        return y;
    }
    /* Les étapes pour initialiser le jeu  */
    private void initialiserJeu(int taille){
        taille(taille);
        mer(taille);
        donnerPositions(a1());
        donnerPositions(a2());
//        checkLists();
        verifierLesDeuxListes();
        minePosition();
        
    }
    
    public void zero(){
        verifier();
        
    }

}
