
package model;

import java.util.Set;

/**
 *
 * @author 2706starvanitis
 */
public  abstract class Bateau  implements Comparable<Bateau>   {
    protected Position position;
    protected int resistance;
    protected int integriter;
    protected String type;
   
   
    public Bateau(Position p){
        this.position = p;
    }
    public Position getPosition() {
        return position;
    }
    
    public  Bateau getBateau(){
        return this;
    }

    public int getIntegriter() {
        return integriter;
    }

    public void setIntegriter(int integriter) {
        this.integriter = integriter;
    }
    

    public void bateauEstDeplacer(Position p) {
            this.position = p;
             position.move(position);
    }
    public int getResistance() {
        return resistance;
    }

    /* Renvoie le tire 0 1 2 d'un bateau */
    public int tireEstDonner(){
        int tire = 0;
        if(this instanceof BatPetit){
            tire = this.tire();
        }
        else if(this instanceof BatGrand){
            tire = this.tire();
        }
        return tire;
        
    }
    /* Renvoie sous forme de liste l'ensemble des positions que le bateau peut jouer */
    public Set<Position> positionsDeDeplacements(int taille, Position p,boolean controleur){
        if(this instanceof BatPetit){
          return new DeplacementDeuxCases(taille,p.x,p.y,controleur).getList();
        }
        else {
           return new DeplacementUneCase(taille,p.x,p.y,controleur).getList();
        }
    }

    public abstract int tire();
    public abstract String getType();
    
    @Override
    public int compareTo(Bateau o) {
        return - this.position.compareTo(o.position);
    }


    
}


