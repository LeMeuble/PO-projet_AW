/**
 * package principal
 */
package main;

import librairies.AssociationTouches;
import librairies.StdDraw;
import main.terrain.Case;
import main.terrain.Grid;
import ressources.Affichage;
import ressources.Chemins;
import ressources.Config;
import ressources.ParseurCartes;

public class Jeu {

    private Player currentPlayer; //l'indice du joueur actif:  1 = rouge, 2 = bleu
    private Grid grid;

    public static int borderX;
    public static int borderY;

    private Cursor cursor;
    private Movement movement;

    // l'indice 0 est reserve au neutre, qui ne joue pas mais peut posseder des proprietes
    public Jeu(String fileName) throws Exception {

        //appel au parseur, qui renvoie un tableau de String
        String[][] parsed = ParseurCartes.parseCarte(fileName);

        borderX = parsed[0].length;
        borderY = parsed.length;

        this.grid = new Grid(parsed);
        this.currentPlayer = Player.RED; // rouge commence
        this.cursor = new Cursor();

        Config.setDimension(borderX, borderY);

        movement = new Movement(this.grid.getCase(0, 0));
        movement.update(this.grid.getCase(0, 1));
        movement.update(this.grid.getCase(0, 2));
        movement.update(this.grid.getCase(1, 2));
        movement.update(this.grid.getCase(1, 1));

    }

    public boolean isOver() {
        return false;
    }

    public void afficheStatutJeu() {
        Affichage.videZoneTexte();
        Affichage.afficheTexteDescriptif("Status du jeu");
    }


    public void display() {

        StdDraw.clear();
        afficheStatutJeu();

        for (int y = 0; y < borderY; y++) {

            for (int x = 0; x < borderX; x++) {

                Case c = this.grid.getCase(y, x);

                Affichage.dessineImageDansCase(x, y, c.getTerrain().getFile());
                if (c.hasUnit()) Affichage.dessineImageDansCase(x, y, c.getUnit().getFile());

            }
        }

        if (this.movement != null) {

            this.movement.render();

        }

        Affichage.dessineCurseur(this.cursor.getCurrentX(), this.cursor.getCurrentY());
        Affichage.dessineImageDansCase(1, 0, Chemins.getCheminFleche(Chemins.DIRECTION_FIN, Chemins.DIRECTION_DROITE));

        StdDraw.show(); //montre a l'ecran les changements demandes
    }

    public void initialDisplay() {
        StdDraw.enableDoubleBuffering(); // rend l'affichage plus fluide: tout draw est mis en buffer et ne s'affiche qu'au prochain StdDraw.show();
        display();
    }

    public void update() {

        boolean updateDisplay = false;
        AssociationTouches toucheSuivante = AssociationTouches.trouveProchaineEntree(); //cette fonction boucle jusqu'a la prochaine entree de l'utilisateur

        if (toucheSuivante.isHaut()) {
            cursor.up();
            updateDisplay = true;
        }

        if (toucheSuivante.isBas()) {
            cursor.down();
            updateDisplay = true;
        }

        if (toucheSuivante.isGauche()) {
            cursor.left();
            updateDisplay = true;
        }

        if (toucheSuivante.isDroite()) {
            cursor.right();
            updateDisplay = true;
        }

        //  ATTENTION ! si vous voulez detecter d'autres touches que 't',
        //  vous devez les ajouter au tableau Config.TOUCHES_PERTINENTES_CARACTERES
        if (toucheSuivante.isCaractere('t')) {
            String[] options = {"Oui", "Non"};
            if (Affichage.popup("Finir le tour de XXX?", options, true, 1) == 0) {
                //le choix 0, "Oui", a été selectionné
                //TODO: passer au joueur suivant
                System.out.println("FIN DE TOUR");
            }

            updateDisplay = true;
        }

        if (updateDisplay) display();
    }
}

