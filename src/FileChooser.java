/**
 * Adi Gelb
 * nov 15
 * FileChooser
 * opens from starting frame JFileChooser, allows user to select CSV file for uploading data
 */

import java.io.*;
import javax.swing.JFileChooser;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser extends JPanel{
	
    JFileChooser fc;
    
    FileChooser(){
    	super(new BorderLayout());
    }

    public File getFile(String schoolType) {
        fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        fc.setDialogTitle("Choose a " + schoolType.toLowerCase() + " file:");

        fc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
        fc.addChoosableFileFilter(filter);
        //fc.setFileSelectionMode(JFileChooser.FILES_ONLY); //already the default mode

        int returnValue = fc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION){
            return fc.getSelectedFile();
        }

        return null;

    }
    

}

