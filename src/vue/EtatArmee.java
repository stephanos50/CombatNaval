/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;
import javafx.scene.paint.Color;
import model.Position;
/**
 *
 * @author 0106olomelcenco
 */
public  class EtatArmee  {
    private Position  position;
    private String  type;
    private int integrite ;

    public EtatArmee(Position position, String type, int integrite) {
        this.position = position;
        this.type = type;
        this.integrite = integrite;
    }
    
    public Position getPosition() {
        return position;
    }

    public String getType() {
        return type;
    }

    public int getIntegrite() {
        return integrite;
    }

    public void setIntegrite(int integrite) {
        this.integrite = integrite;
    }
    
    public String getX(){
        return new Lettre().caractere(position.getX()-1);
    }

    @Override
    public String toString() {
        return   position + " " +  type + " " + integrite ;
    }
}
