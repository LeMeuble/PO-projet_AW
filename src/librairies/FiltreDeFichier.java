package librairies;

import java.io.File;
import javax.swing.filechooser.*;

/** Permet le filtre des fichiers du selecteur de carte */
public class FiltreDeFichier extends FileFilter {
 
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    //Accepte tout dossiers et fichiers .awdcmap
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
 
        String extension = getExtension(f);
        if (extension != null) {
            if (extension.equals("awdcmap")) {
                    return true;
            } else {
                return false;
            }
        }
        return false;
    }
 
    /**
     * Description du filtre
     */
    public String getDescription() {
        return "Peut seulement selectionner des cartes Advance Wars (.awdcmap).";
    }
}