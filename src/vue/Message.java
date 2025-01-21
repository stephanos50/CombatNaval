/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;
/**
 *
 * @author stefanos
 */
public enum Message {
    tirer(" Selectionner un bateau pour tirer "),
    shoot(" Le bâteau tire "),
    choisirUnBateau(" : Séléctionnez un  bateau à déplacer ([A-Ea-e][1-5]) \n "),
    bouger(" Séléctionne la case de déstination   "),
    position(" La position n'existe pas "),
    nom(" Nom de l'armée " ),
    erreurNom(" L\'Armée existe déja "),
    insertion(" Valeur invalide : \n "),
    valide(" Selectionné une position  "),
    cases(" choisir une case pour votre bateau "),
    bateau(" placez un de vos bâteau "),
    deplacerBateau(" Selectionné un bateau à déplacer "),
    finDePartie("Game Over"),
    passerTour(" Passez votre tour "),
    degug(" Voulez vous afficher les mines 0(oui) ou 1(nom) "),
    retour(" ne fonctionne pas "),
    validerdeplacement(" Voulez vous déplacer un bateau 0(oui) ou 1(nom) \n "),
    gameover(" Game Over "),
    end( " vous avez gagné"),
    dinamic(" Le bateau à rencontré une mine radioactive "),
    supprimer(" Le bateau à rencontré une mine ");
    
    
    

    private final String message;

    Message(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
    
}
