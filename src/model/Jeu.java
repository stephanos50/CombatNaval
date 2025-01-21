/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author stefanos
 */
public class Jeu extends Observable {
    private boolean controleur;
    private Armee uneArmee;
    private Armee a1;
    private Armee a2;
    private Mer mer;
    private int taille;

    public Jeu() {
        this.mer = new Mer();
        controleur = true;
    }
    
    public Jeu(int taille, String a, String b) {
        this.taille = taille;
        this.a1 = new Armee(a);
        this.a2 = new Armee(b);
        this.mer = new Mer(this.taille);
        controleur = false;
    }

    public Armee getA1() {
        return a1;
    }

    public Armee getA2() {
        return a2;
    }
    public Mer getMer() {
        return mer;
    }
    public int getTaille(){
        return mer.getTaille();
    }
    public String nomArmeeA1(){
        return a1.getNom();
    }
    public String nomArmeeA2(){
        return a2.getNom();
    }
    public List<Bateau> a1() {
        return a1.getList();
    }
    public List<Bateau> a2() {
        return a2.getList();
    }
     private int iX(){
        return Position.getIx();
    }
    private int iY(){
        return Position.getIy();
    }


    /* Au démarrage du jeu,  et en dernier on place les Mines dans la Mer */
    /* On donne le type de mines également Classic ou Dynamic */
    public void ajouterMine(List<Position> position) {
            mineAjouter(position);
    }
    
    /* On ajoute les mines */
    private void mineAjouter(List<Position> position) {
        for (int i = 1; i < mer.getTaille(); ++i) {
            for (int j = 1; j < mer.getTaille(); ++j) {
                if (position.contains(mer.getPosition(i, j))) {
                    placdrLesTypesDeMines(i,j);
                }
            }
        }
    }
    /* On place un type de mine au hasard */
    private void placdrLesTypesDeMines(int i, int j){
        Random random = new Random();
        int x = random.nextInt(2);
        if( x == 0 ){
            mer.ajouterContenue(i,j,new MineClassic());
        }
        if(x == 1){
            mer.ajouterContenue(i,j,new MineAtomic());
        }
    }
    /* Le controleur demande de vérifier la position saisie pour le deplacemnt d'un bâteau */
    public boolean verifierLaSaisie(Position p){
        return (p.equals(new Position(iX(),iY())));
    }
    
    /* Une armée tire et renvoie la valeur du tir d'un bateau 0, 1 ou bien 2 */
    /* Le Contrôleur récupère le tir pour calculer les positions du tir */
    public int tire(Armee a, int x, int y){
        return a.tire(x, y);
    }
    /* Donne l'ensemble des positions utilisées pour chaque bateau */
    public List<Position> positionsDeToutLesBateaux() {
        List<Position> list = new  ArrayList<>();
        for (Bateau p : a1.getList()) {
            list.add(p.getPosition());
        }
        for (Bateau p : a2.getList()) {
            list.add(p.getPosition());
        }
        return list;
    }
    /* Reçoit une position à jouer du Contrôleur */
    /* Et demande une comparaison avec les positions jouable pour un bateau */
    public boolean verifierPosition(Position p) {
        Armee a = jeSuisUneArmee(new Position(iX(),iY()));
        return verifierPositionJouable(a,p);
    }

    /* Reçois la position ou le bateau doit se dépalcer et vérifie qu'elle appartient bien  */
    /* à la liste des positions jouables  */
    private boolean verifierPositionJouable(Armee a,Position position) {
        boolean jouable = false;
        Set<Position> pos = a.positionJouable(mer.getTaille(),new Position(iX(),iY()),controleur);
        for (Position p : pos) {
            if (p.equals(position)) {
                    jouable = true;
            }
        }
        return jouable;
    }
    
    public int getX(){
        return Position.getIx();
    }
    public int getY(){
        return Position.getIy();
    }
    
    /* On change la position en x et y  du bateau */
    /* On change la position en x et y  du bateau */
    private void armeeSupprimerBateau(Position arriver) {
        Position depart = new Position(iX(),iY());
        if(!a1.bateauEstSupprimer(depart)){
            a1.deplacerBateau(depart,arriver);
        }
        else if(!a2.bateauEstSupprimer(depart)){
            a2.deplacerBateau(depart,arriver);
        }
    }
    
    /* La mer supprime son contenue si c'est une mine de type classic */
    /* si c'est une mine de type atomic le bateau est supprimer la case devient non jouable */
    private  boolean merSupprimerContenue(Position p){
        boolean supprimer = false;
        if(mer.contienrTypeMine(p)){ // Je suis une mine Classic
            supprimer = mer.mineEstSupprimer(p);
        }
        else { // Je suis une mine Atomic 
            Armee a = jeSuisUneArmee(new Position(iX(),iY()));
            if(a.supprimerBateau(new Position(iX(),iY()))){
               mer.getMer()[p.x][p.y].setJouable(false);
            }
        }
        return supprimer;
    }
    
    /* Si La mer contient une mine je retourne true */
    private boolean merContientMine(Position p){
        if(mer.contienrMine(p)){
            return true;
        }
        return false;
    }
    /* Donne la position pour  deplacer le bateau et verifie si la case suivante contient une mine */
    /* Si, lors du déplacement la position suivante est une Mine,  */
    /* On supprime, en premier, la mine et ensuite on supprime ou modifie le bateau */
    /* Si lors du déplacment la position suivante est normel on déplace le bateau */
    public boolean deplacerBateau(Position p) {
        boolean supprimer = false;
        if(merContientMine(p)){
            merSupprimerContenue(p);
            armeeSupprimerBateau(p);
            supprimer = true;
        }
        else { 
            armeeDeplacerBateau(p);
        }
        return supprimer;
    }
    /*  L'armée demande au Bateau de se déplacer  */
    private boolean armeeDeplacerBateau(Position arriver){
        Position depart = new Position(iX(),iY());
        boolean valider = false;
        if(caseRadioactive(arriver)){
            if (a1.deplacerBateau(depart,arriver)) {
                valider = true;
            }
            if (a2.deplacerBateau(depart,arriver)) {
                 valider = true;
            }
        }
        return valider;
    } 
    
    /* Verifie que la case est jouable */
    private boolean caseRadioactive(Position p){
        return (mer.getMer()[p.x][p.y].getJouable());
    }
    /* On sauvegarde les positons en x et y dans une variable static de Position */
    public void sauvegarder(int x, int y) {
        Position.sauvegarder(x, y);
    }
    /* Retourne la liste des positions que le bateau peut utiliser */
    public Set<Position> positionsJouablesPourUnBateau(int x, int y) {
        sauvegarder(x, y);
        Position p = new Position(x,y);
        Set<Position> list01 = veriferPosition(positionsJouablesDeArmee(p), a1);
        Set<Position> list02 = veriferPosition(list01, a2);
        return list02;
    }
    /* Retourne l'ensemble des positions qui peuvent être jouée par un bateau */
    /* D'apres son type Grand ou Petit */
    private Set<Position> positionsJouablesDeArmee(Position p) {
        Armee arm = jeSuisUneArmee(p);
        Set<Position> position = arm.positionJouable(mer.getTaille(),p,controleur);
        return position;
    }
    /* je vérifie que la position appartient à une armée*/
    /* Si la position appartient à Armme 01 renvoie armee a1 */
    /* Si non renvoie armee 02 */
    private Armee jeSuisUneArmee(Position p) {
        Armee arm = null;
        if(a1.obtenir(p)){
            arm = a1;
        }
        else if(a2.obtenir(p)){
            arm = a2;
        }
        return arm;
    }   
    /* TIRER */
    /* Reçois de Contrôleur la liste des positions pour le tir */
    public void tirerSurLesBateaux(Armee a,Set<Position> position) {
        Set<Position> pos = veriferPosition(position,a);
        tirerSurLesPositions(pos);
          
    }
    /* Reçois la liste des positions */
    /* Vérifie  que les positions reçu n'appartient pas à une armée */ 
    /* Et retoure la list modifiée  de positions */
    /* On vérifie chaque case d'un tir */
    private Set<Position> veriferPosition(Set<Position> list, Armee a) {
        List<Bateau> b = a.getList(); // La liste des Bateaux d'une armée
        for (int j = 0; j < b.size(); ++j) {
            if(list.contains(b.get(j).getBateau().getPosition())){
                list.remove(b.get(j).getPosition());
            }
        }
        return list;
    }
   
    /* Reçoit la liste des positions pour tirer */
    public void tirerSurLesPositions(Set<Position> position) {
        List<Position> tirer = new ArrayList<>();
        for(Position p : position){
            tirer = tireSupprimeBateau(p,tirer);
        }
        if(!tirer.isEmpty()){
            supprimerBateauAuHasard(uneArmee,tirer);
        }
        tirer.clear();
       
    }
    /* Vérifie que le tire est effectué par un petit bateau */
    /* Un petit bateau ne peut tirer que sur un bateau de n'imorte quel type*/
    private boolean demandeLeTypePourTirer(Armee a){
        boolean unefois = false;
        if(a.getBateau(new Position(iX(), iY())) instanceof BatPetit){
            unefois = true;
        }
        return unefois;
    }
  
    /* Choisir un bateau au hasard à supprimer */
    private boolean  supprimerBateauAuHasard(Armee a, List<Position> list){
        Random random = new Random();
        int i = random.nextInt(list.size());
        int x = list.get(i).getX();
        int y = list.get(i).getY();
        return supprimerBateau(a,new Position(x,y));
    }
    
    /* Vérifie à qui appartient la position du tire et supprmie les bateau */
    private List<Position>  tireSupprimeBateau(Position p, List<Position> tirer){
        if (positionAppartientArmee(a1,p)) {
            uneArmee = a1;
            if(demandeLeTypePourTirer(a2)){
                tirer.add(p);
            }
            else {
                supprimerBateau(a1,p);
            }
        }
        else if (positionAppartientArmee(a2, p)) {
            uneArmee = a2;
            if(demandeLeTypePourTirer(a1)){
               tirer.add(p);
            }
            else {
                supprimerBateau(a2,p);
            }
        }
        return tirer;
    }
    /* Vérifie que le bateau avec sa position appartient bien à l'Armée */
    public boolean positionAppartientArmee(Armee a, Position p) {
        return a.obtenir(p);
    }
    /* On supprime un bateau appartenant à une amrée */
    private boolean  supprimerBateau(Armee a, Position p){
        return a.bateauEstSupprimer(p);
    }
    
    /*  Les armees sont crées*/
    public void ajouterArmee(String a1, String a2) {
        this.a1 = new Armee(a1);
        this.a2 = new Armee(a2);
    }
    /* On vérifie  que les Deux armmées ont bien des positions différentes */
    /* Si les listes de positions sont différentes true */
    public boolean checkLists(Armee a, Armee b) {
        return !a.equals(b);
    }
    /* Si une des positions d'un bateau  dans la liste de l'armée 2 */
    /* est identique à une positions d'un bateau de arméee 1 */
    /* On vide la liste  */
    public boolean viderList(Armee a) {
        return a.viderList(a.getList());
    }
    /* Reçoit de Controleur une position x et y aléatoire */
    public boolean positionAleatoire(Armee a, Position p,int index) {
        if(ajouterBateau(a, p,index)){
            return true;
        }
        return false;
    }
    /* Ajoute la position x et y  */
    private boolean ajouterBateau(Armee a, Position p, int index) {
         if(a.ajouterBateau(p,index)){
             return true;
         }
         return false;
    }
    public void notifie() {
        notifyObservers(mer);
    }
    /* A chaque changement d'état on notify l'Observer */
    public void setChangedAndNotify() {
        setChanged();
        notifyObservers(mer);
    }

    
}
