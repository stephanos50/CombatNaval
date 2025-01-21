/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.Random;
import java.util.Set;
import model.Armee;
import model.Jeu;
import model.Mer;
import model.Position;
import vue.Message;
import vue.Vue;

/**
 *
 * @author 2706starvanitis
 */
public class Controler {

    private Random random = new Random();
    private Set<Position> jouable;
    private static String a1;
    private static String a2;
    private final Jeu jeu;
    private boolean deplacer = false;

    
    private Armee armee;

    public Controler() {
        jeu = new Jeu();
        jeu.addObserver(Vue.getInstance());

    }

    public static void main(String[] args) throws Exception {
        Controler ctrl = new Controler();
        ctrl.lancer();
    }

    private void lancer() throws Exception {
        Vue.ModeDebug("[0-9]", Message.degug.toString());
        String a = Vue.SaisirNom();
        String b = Vue.SaisirNom();
        while (a.equals(b)) {
            Vue.armeeExiste();
            b = Vue.SaisirNom();
        }
        this.a1 = a;
        this.a2 = b;
        initialiser();
    }

    /*  Je demande la Mer*/
    private Mer getMer() {
        return jeu.getMer();
    }
    /* Je demande l'armée un  */
    private Armee a1() {
        return jeu.getA1();
    }
    /* Je demande l'arméee deux */
    private Armee a2() {
        return jeu.getA2();
    }
    /* Renvoie une position aléatoire en X */
    private int getX() {
        return random.nextInt(5);
    }
    /* Renvoie une position aléatoire en Y */
    private int getY() {
        return random.nextInt(5);
    }
    /* Ajouter les armees  */
    private void ajouterArmee(String a1, String a2) {
        jeu.ajouterArmee(a1, a2);
    }
    /* Renvoie au modele les positions des mines */
    private void minePosition() {
        int taille = getMer().getTaille();
        jeu.ajouterMine(new PosMines().set(taille, jeu.positionsDeToutLesBateaux()));
    }
    /* Envoie une position aléatoire  */
    private void donnerPosAleatoire(Armee a) {
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
    private void verifierLesDeuxListes() {
        while (!checkLists()) {
            if (jeu.viderList(a2())) {
                donnerPosAleatoire(a2());
            }
        }
    }

    /* Je verifie que les deux listes des armées ont bien  des positions différentes */
    private boolean checkLists() {
        return jeu.checkLists(a1(), a2());
    }
    private int transormerCharToInteger(int y) {
        return (y > 90) ? y - 97 : y - 65;
    }
    /* Renvoie le char en type  Integer */
    private int transfomerY(String s) {
        return transormerCharToInteger(s.charAt(0));
    }
    /* Renvoie le char en type integer */
    private int transofrmerX(String s) {
        return s.charAt(1) - 49;
    }
    /* Verifie la position du bateau qui bouge */
    private boolean verifierLaPosition(int x, int y){
        return jeu.verifierLaSaisie(new Position(x,y));
    }
    /* Selectionner une postion pour bouger le bateau   */
    private void selectionnerUnePosition(Armee a) {
        if(deplacer){
            Vue.bouger(a.getNom());
            String position = Vue.action();
            int y = transofrmerX(position);
            int x = transfomerY(position);
            if (verifierLaPositionJouableInserer(x, y)) {
               if(jeu.deplacerBateau(new Position(x,y))){
                   jeu.setChangedAndNotify();
                   Vue.rencontrerMine(x,y);
                }
                else {
                     jeu.setChangedAndNotify();
                }
               
            } else {
                verifierPositionDeplacerBateau(x, y);
            }
        }
    }
    /* Si la position n'est pas valide je demande une nouvelle saisie */
    private void verifierPositionDeplacerBateau(int x, int y) {
        while (!verifierLaPositionJouableInserer(x, y)) {
            Vue.positionInvalide();
            String position = Vue.action();
            y = transofrmerX(position);
            x = transfomerY(position);
        }
        if (verifierLaPositionJouableInserer(x, y)) {
            jeu.deplacerBateau(new Position(x,y));
            jeu.setChangedAndNotify();
        }

    }
    /* Je verifie la position inserer par le joueur avec la liste des positions jouable */
    private boolean verifierLaPositionJouableInserer(int x, int y) {
        boolean valide = false;
        int a = Position.getIx();
        int b = Position.getIy();
        if(verifierLaPosition(x,y)){
            valide = false;
        }
        else {
            for (Position p : jouable) {
                if (p.equals(new Position(x,y)) || p.equals(new Position(a,b))){
                    valide = true;
                }
            }
        }
        return valide;
    }
    /* BOUGER */
    /* Selectionner un bateau pour deplacer */
    /* L'utilisateur selectionne la position du bateau qu'il désire bouger */
    private void selectionnerUnBateau(Armee a) {
        String nom = Vue.ModeDebug("[0-9]", a.getNom() + " " + Message.validerdeplacement.toString());
        if(nom.charAt(0) == '0'){
            deplacer = true;
            Vue.choisirUnBateau(a.getNom());
            String position = Vue.action();
            int y = transofrmerX(position);
            int x = transfomerY(position);
            if (jeu.positionAppartientArmee(a,new Position(x,y))) {
                bouger(x,y);
            } else {
                erreurSelectionBateau(a);
            }
        }
        else {
            deplacer = false;
        }
        
    }
    private void erreurSelectionBateau(Armee a){
        int x;
        int y;
        do {
            Vue.choisirUnBateau(a.getNom());
            String position = Vue.action();
            y = transofrmerX(position);
            x = transfomerY(position);
        } while (!jeu.positionAppartientArmee(a,new Position(x,y)));

        if(jeu.positionAppartientArmee(a,new Position(x,y))){
            bouger(x,y);
        }
    }
    
    private void bouger(int x, int y){
        jouable = jeu.positionsJouablesPourUnBateau(x, y);
        Vue.afficherListeDePosition(jouable);
    }


    /* LE TIRE  */
 /* L'utilisateur selectionne un bateau pour tirer */
    private void tirerSurLesPositions(Armee a) {
        Vue.tirer(a.getNom());
        String position = Vue.action();
        int y = transofrmerX(position);
        int x = transfomerY(position);
        if (jeu.positionAppartientArmee(a,new Position(x,y))) {
            jeu.sauvegarder(x, y);
            bateauTire(a, x, y);
        } else {
            Vue.positionInvalide();
            if(message(a)){
                bateauTire(a,x,y);
            }
        }
        
    }

    private void bateauTire(Armee a, int x, int y) {
        int t = jeu.tire(a, x, y);
       
        if (t == 1) {
            jeu.tirerSurLesBateaux(a, new PorterUn(getMer().getTaille(), x, y, 0).getList());
        } else if (t == 2) {
            jeu.tirerSurLesBateaux(a, new PorterDeux(getMer().getTaille(), x, y, 0).getList());
        }
        jeu.setChangedAndNotify();
        Vue.afficherLeTire(t);
        verifierLesBateaus();
    }

    private boolean message(Armee a) {
        int x;
        int y;
        do {
            Vue.tirer(a.getNom());
            String position = Vue.action();
            y = transofrmerX(position);
            x = transfomerY(position);
        } while (!jeu.positionAppartientArmee(a,new Position(x,y)));
        return true;
    }

    /* Verifie la liste des bateaux. Si vide le jeu s'arrête  */
    private void verifierLesBateaus() {
        if (a1().getList().isEmpty()) {
            vainqueur();
        }
        if (a2().getList().isEmpty()) {
            vainqueur();
        }
    }


    private void donnerListBateau() {
        Vue.batA1(jeu.a1());
        Vue.batA2(jeu.a2());
    }

    private void play(Armee a) {
        tirerSurLesPositions(a);
        selectionnerUnBateau(a);
        selectionnerUnePosition(a);
    }

    /* Les étapes pour initialiser le jeu  */
    private void initialiser() {
        ajouterArmee(a1, a2);
        donnerPosAleatoire(a1());
        donnerPosAleatoire(a2());
        verifierLesDeuxListes();
        donnerListBateau();
        minePosition();
        Vue.afficheSeeMap(getMer());
        Vue.afficher(a1(), a2());
        while (!a1().getList().isEmpty() && !a2().getList().isEmpty()) {
            play(a1());
            play(a2());
        }
        vainqueur();
    }

    private void vainqueur() {
        String nom = (a1().getList().isEmpty()) ? a2().getNom() : a1().getNom();
        gameover(nom);
    }

    private void gameover(String nom) {
        Vue.end(nom);
        System.exit(0);
    }
}
