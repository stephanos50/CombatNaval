
package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/**
 *
 * @author 2706starvanitis
 */
public class Armee { 
    private List<Bateau> list = new ArrayList<>();
    private final int nbr = 3;
    private final String nom;
    
    public Armee(String nom) {
        this.nom = nom;
    }
    
    /* Donne le bateau qui est à la position x et y */
    public Bateau getBateau(Position p){
        Bateau bateau = null;
        for(int i = 0; i < list.size(); ++i){
            if(list.get(i).getPosition().equals(p)){
                bateau = list.get(i).getBateau();
            }
        }
        return bateau;
    }
    public Armee getArmee(){
        return this;
    }
    public int tire(int x, int y){
        for(int i = 0; i < list.size(); ++i){
            if(list.get(i).getPosition().equals(new Position(x,y))){
                 return list.get(i).tireEstDonner();
            }
        }
        return 0;
    }
    
    /* Le Bateau est supprimé ou bien on modiffie sont integrité */
    public boolean bateauEstSupprimer(Position p){
        boolean supprimer = false;
        if(this.getBateau(p) instanceof BatGrand){
            if( this.getBateau(p).getIntegriter() == 100){
                this.getBateau(p).setIntegriter(50);
            }
            else {
                supprimer = removeBateau(p);
            }
        }
        else {
            supprimer = removeBateau(p);
        }
        return supprimer;
    }
    
    private boolean removeBateau(Position p){
        list.remove(this.getBateau(p));
        return true;
    }
    
    /* Ajouter les bateau  */
    public boolean ajouterBateau(Position p, int index ){
        if (index == 0){
           return ajouterGrandBateau(p);
        }
        else if (index > 0) {
            return ajouterPetitBateau(p);
        }
        return false;
    }
    /* Vider la liste de l'armee A2 */
    public boolean viderList(List<Bateau> b){
        return list.removeAll(b);
    }
    /* Ajouter une grand bateau */
    private boolean ajouterGrandBateau(Position p){
        list.add(new BatGrand(p));
        return true;
    }
    /* Ajoute un petit bateau verifier que la position n'existe pas avant de l'ajouter*/
    private boolean ajouterPetitBateau(Position p){
        boolean valeur = true;
        for(int i = 0; i < list.size(); ++i){
            if(list.get(i).getPosition().equals(p)){
                valeur =  false;
            }
        }
        if(list.size() < nbr && list.size() >= 0 && valeur) {
            list.add(new BatPetit(p));
            valeur = true;
        }
        return valeur;
    }
    
    /* Renvoie l' ensemble des positions jouables */
    public Set<Position> positionJouable(int taille , Position p,boolean controleur){
       return getBateau(p).positionsDeDeplacements(taille,p,controleur);
    }

    public boolean supprimerBateau(Position p){
        removeBateau(p);
        return true;
    }
    
    /* Recherche la position initiale et change avec la nouvelle position */
    public boolean deplacerBateau(Position depart,Position arriver){
        boolean deplacer = false;
        if(this.getBateau(depart) instanceof Bateau){
            this.getBateau(depart).bateauEstDeplacer(arriver);
            deplacer = true;
        }
        return deplacer;
    }
    /* donne true si la position existe si non false */
    public boolean obtenir(Position p){
        boolean existe = false;
        for(int i = 0; i < list.size(); ++i){
            if(list.get(i).getPosition().equals(p)){
                existe = true;
            }
        }
        return existe;
    }
    /* On veut deux listes avec des positions différentes */
    @Override
    public boolean equals(Object o){
        if(o instanceof Armee){
            Armee that = (Armee) o;
            if(that.list.size() != this.list.size()){
                return false;
            }
            for(Bateau a : this.getList()){
                for(Bateau b : that.getList()){
                    if(a.getPosition().equals(b.getPosition())){
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }
           
    public List<Bateau> getList() {
        return list;
    }
     
    public String getNom() {
        return nom;
    }
    
    public int getNbr(){
        return nbr;
    }
}
    
   
    
   
    
    
   
    
   

    
   
    
   

    
    
   
    
   
    
  

   

   
    
   
       
    
       
        
    
   




   
    
    



    

