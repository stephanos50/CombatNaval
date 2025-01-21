/*
 * To change this license header, choose License Headers 
in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.Set;
import model.Armee;
import model.BatGrand;
import model.BatPetit;
import model.Bateau;
import model.Case;
import model.Eau;
import model.Jeu;
import model.Mer;
import model.MineAtomic;
import model.MineClassic;
import model.Position;
/**
 *
 * @author 2706starvanitis
 */
public class Vue implements Observer{
    private static Case[][] uneCase;
    private static boolean mode;
    private static  List<Bateau> batA1;
    private static  List<Bateau> batA2;
    private static final Vue VUE = new Vue();
    private static final Scanner INPUT = new Scanner(System.in);
    

    /* Constructeur en private cree une seul fois */
    /*  Pattern Singleton */
    private Vue(){}
    
    /* Renvoi l'instance à controler   */
    public static Vue getInstance(){
        return VUE;
    }
    /* Aff_A1A2 le jeu en console */
    public static void afficheSeeMap(Mer mer){
        Case[][] cases = mer.getMer();
        uneCase = cases;
        System.out.println("");
        rangerLettre(mer.getTaille());
        for(int i = 0; i < mer.getTaille() ; ++i){
            System.out.print (Couleur.WHITE +chercherCaractere(i) + " " + (i + 1) + "  " );
            for(int j = 0; j < mer.getTaille(); ++j){
                affiche(" |");
                if(batA1(j,i)){ }  
                else if(batA2(j,i)){ }
                else if(cases[j][i].getCont() instanceof MineClassic){
                    String car = (mode) ? " ." : "  " ;
                    affiche(car);
                 }
                else if(cases[j][i].getCont() instanceof MineAtomic){
                    String car = (mode) ? " #" : "  " ;
                    affiche(car);
                }
                else if(cases[j][i].getCont() instanceof Eau){
                   affiche("  ");
                }
                else {
                    affiche("  ");
                }
            }
            afficheString(" |");
        }
        afficheString("");
    }
     /* Ensemble des lettres utilisées pour l'affichage */
    private static void rangerLettre(int taille){
        StringBuilder sb = new StringBuilder(String.format("%-8s",""));
        for( Alphabet a : Alphabet.values() ){
            if(a.ordinal() < taille){
                sb.append(String.format("%-4s", a.name()));
            }
        }
        afficheStringBuilder(sb);
    }
    /* String print*/
    public static void affiche(String msg){
        System.out.print(msg);
    }
    /* String println */
    public static void afficheString(String msg){
        System.out.println(msg);
    }
    /* StringBuilder println */
    private static void afficheStringBuilder(StringBuilder sb){
        System.out.println(sb);
    }
    /* Aff_A1A2 les état des armées */
    public static void etatDesArmees(){
        StringBuilder sb  =  new StringBuilder(Couleur.RED +"Etat des armée \n");
        sb.append(Couleur.WHITE);
        sb.append(String.format("%-15s%-10s%-6s%-10s%-3s","Position","Arméee",
                "Type","Intégrité","%"));
        afficheStringBuilder(sb);
    }
    /* Renvoie le caractere de la lettre   */
    private static String  chercherCaractere(int x){
        for(Alphabet l : Alphabet.values()){
            if(l.ordinal() == x){
                return  l.name();
            }
        }
        return " ";
    }
   
    private static boolean batA1(int a, int b){
        int index = bateau(batA1, a, b);
        if(index != 100){
            if(batA1.get(index) instanceof BatGrand){
                affiche(Couleur.CYAN + " B" + Couleur.RESET);
                return true;
            }
            if (batA1.get(index) instanceof BatPetit) {
                affiche(Couleur.CYAN + " S" + Couleur.RESET);
                return true;
            }
        }
        return false;
    }
    private static boolean batA2(int a, int b){
        int index = bateau(batA2, a,b);
        if(index != 100){
            if(batA2.get(index) instanceof BatGrand){
                affiche(Couleur.YELLOW + " B" + Couleur.RESET);
                return true;
            }
            if (batA2.get(index) instanceof BatPetit){
                affiche(Couleur.YELLOW + " S" + Couleur.RESET);
                return true;
            }
        }
        return false;
    }
    private static int bateau(List<Bateau> l,int a, int b){
        int index = 0;
        for(int i = 0; i < l.size(); ++i){
            int x = l.get(i).getPosition().getX();
            int y = l.get(i).getPosition().getY();
            if(x == a && y == b){
                index = i;
                return index;
            }
        }
        index = 100;
        return index;
    }
    
    /* Affiche la liste des positions jouable pour une armée */
    public static void afficherListeDePosition(Set<Position> l){
        for(Position p : l){
            System.out.print( " " +chercherCaractere(p.getX()) + "" + (p.getY()+1));
        }
        System.out.println("");
    }
    
    public static void batA1(List<Bateau> a){
        batA1 = a;
    }
    public static void batA2(List<Bateau> a){
        batA2 = a;
    }
    
    /* On demande au joueur  de saisir un nom pour une armée */
    public static String SaisirNom(){
        affiche(Message.nom.toString() );
        String nom = INPUT.next();
        return nom;
    }
    
    /* On demande au joueur  de saisir le mode pour afficher ou non les mine */
    public static String ModeDebug(String a, String msg){
        String debug ;
        do{
            try {
                affiche(msg);
                debug = INPUT.next(a);
                if(vefifierContenue(debug)){
                    return debug;
                }
                
            } catch (Exception e) {
                debug = INPUT.next();
               affiche(Message.insertion.toString());
            }
        }while(true);
    }
    /* Si différent de 0 ou 1 renvoie false */
    private static  boolean vefifierContenue(String debug){
        if(debug.contains("0") || debug.contains("1")){
            if(debug.contains("0")){
                mode = true;
            }
            return true;
        }
        return false;
    }
    
    public static void armeeExiste(){
        afficheString(Message.erreurNom.toString());
    }
    public static void afficherLeTire(int t){
        afficheString(Message.shoot.toString() + " " + t);
    }
    public static void end(String a){
        afficheString(Message.gameover.toString());
        afficheString(a + Message.end.toString());
        
    }
    
    /* Recupère dans un StringBuilder l'etat d'une armée  */
    private static void afficherEtatDesArmee(Armee arm, Couleur couleur){
        String nom = arm.getNom();
        List<Bateau> bat = arm.getList();
        StringBuilder  sb =  new StringBuilder();
        for(Bateau b : bat){
            sb.append(couleur);
            sb.append( 
                String.format( "%-2s%-13s%-10s%-6s%-10s",
                chercherCaractere(b.getPosition().getX()),
                b.getPosition().getY() +1,
                nom ,
                b.getType(),
                b.getIntegriter()
            ));
            sb.append("\n");
        }
        afficheStringBuilder(sb);
    }
    /* affiche message pour tirer */
    public static void tirer(String a){
        affiche( a + Message.tirer.toString());
    }
    /* affiche message pour choisir un bateau  */
    public static void choisirUnBateau(String a){
        afficheString(a + Message.choisirUnBateau.toString());
    }
    public static void validerDeplacement(String a){
        afficheString(a + Message.validerdeplacement.toString());
    }
    /* affiche message pour bouger */
    public static void bouger(String a){
        afficheString(a + Message.bouger.toString());
    }
    /* affiche message pour une position invalide */
    public static void positionInvalide(){
        afficheString(Message.position.toString());
    }
    public static void rencontrerMine(int x, int y){
        if(uneCase[x][y].getCont() instanceof MineAtomic){
            afficheString(Message.dinamic.toString());
        }
        else {
            afficheString(Message.supprimer.toString());
        }
        
    }
    
    /* Utilisé pour tirer, selectionner, et bouger */
    /* Renvoie au Controleur un String */
    public static String action(){
        String nom ;
        do {
            try {
                nom = INPUT.next("[A-Ea-e][1-5]");
                return nom;
            } catch (Exception e) {
                INPUT.next();
                affiche(Message.insertion.toString());
            }
        }while(true);
    }
    
    /* Affiche l'état des deux armées  */
    public static void afficher(Armee a, Armee b){
        etatDesArmees();
        afficherEtatDesArmee(a,Couleur.CYAN);
        afficherEtatDesArmee(b,Couleur.YELLOW);
    }

    @Override
    public void update(Observable obs, Object arg) {
        if(obs instanceof Jeu){
             Jeu mer = (Jeu) obs;
             afficheSeeMap(mer.getMer());
             afficher(mer.getA1(), mer.getA2());
        }
    }
}

    
    
    




