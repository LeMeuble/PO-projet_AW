package librairies;

import java.io.*;
import java.awt.*;
import javax.swing.*;

/**Cette classe gere le selecteur de fichier qui se lance en debut de jeu. */
public class SelecteurDeFichier extends JPanel{
    private static final long serialVersionUID = 1L;
    private JFileChooser fc;

    public SelecteurDeFichier(String starting_path) {
        super(new BorderLayout());
        fc = new JFileChooser(starting_path);
        JPanel buttonPanel = new JPanel(); 
        add(buttonPanel, BorderLayout.PAGE_START);
    }

    /**
     * Fonction permettant de choisir un plateau de jeu a ouvrir
     * @return Le chemin d'acces vers un fichier .adwcmap a ouvrir
     * @throws InterruptedIOException
     */
    public String selectFile() throws InterruptedIOException { 
    	//empeche de selectionner un fichier d'une autre extension
	    FiltreDeFichier filter = new FiltreDeFichier(); 
	    this.fc.setFileFilter(filter);
		int returnVal = this.fc.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("Selection de : " +
	    		   this.fc.getSelectedFile().getPath());
	       return this.fc.getSelectedFile().getPath();
	    }
	    else //survient si la selection est interrompue
	    {
	    	throw new InterruptedIOException();
	    }
    }

}
