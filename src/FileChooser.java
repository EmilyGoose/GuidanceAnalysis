/**
 * Adi Gelb
 * nov 15
 * FileChooser
 * opens from starting frame JFileChooser, allows user to select CSV file for uploading data
 */

import java.io.*;
import javax.swing.JFileChooser;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class FileChooser extends JPanel{
	
    JFileChooser fc;
    
    public FileChooser(){
    	super(new BorderLayout());
    	
    	fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    	fc.setDialogTitle("Choose a university file:");
    	
    	fc.setAcceptAllFileFilterUsed(false);
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
    	fc.addChoosableFileFilter(filter);
    	//fc.setFileSelectionMode(JFileChooser.FILES_ONLY); //already the default mode  	    	
    	
    	int returnValue = fc.showSaveDialog(null);
    	if (returnValue == JFileChooser.APPROVE_OPTION){
    		File file = fc.getSelectedFile();
    		System.out.println("You selected the directory: " + file);
    		
    	}
    	
    }
    

}

