/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;
import model.Bateau;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 *
 * @author stefanos
 */
public class VueTableView  {
    private final TableView<EtatArmee> table ;
    private  TableColumn<EtatArmee, String> position ;
    private  TableColumn<EtatArmee, String> type ; 
    private  TableColumn<EtatArmee, String> integrite ;
    
    public VueTableView(List<Bateau> bateau){
        this.table = new TableView<>(); 
        this.position = new TableColumn<>("Position");
        this.type = new TableColumn<>("Type");
        this.integrite = new TableColumn<>("Integrité");
        construireTable(bateau);
    }
    
    private void  construireTable(List<Bateau> bateau){
        position.setCellValueFactory(param -> { 
            EtatArmee etat = param.getValue(); 
            return new SimpleStringProperty(etat.getX() + " " + etat.getPosition().getY() ); 
        });
        
        type.setCellValueFactory(param -> { 
            EtatArmee etat = param.getValue(); 
            return new SimpleStringProperty(etat.getType()); 
        }); 
        integrite.setCellValueFactory(param -> { 
            EtatArmee etat = param.getValue(); 
            return new SimpleStringProperty(etat.getIntegrite() + " " );
        });
        
        table.getColumns().setAll(position,type,integrite);
        
        for(Bateau b : bateau){
            table.getItems().add(new EtatArmee(b.getPosition(), b.getType(), b.getIntegriter())); 
        }
    }
    
    
   
     
        
    

    public TableView<EtatArmee> getTable() {
        return table;
    }
    
   
}
