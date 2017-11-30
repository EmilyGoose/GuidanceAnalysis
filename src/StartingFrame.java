/**
 * Adi Gelb
 * november 15
 * StartingFrame.java
 * opening frame with several beginning options for user
 */


	import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

	import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
	import java.io.File;
	import java.lang.reflect.Array;
	import java.util.ArrayList;

	import javax.swing.BoxLayout;
	import javax.xml.crypto.Data;

class StartingFrame extends JFrame {

	  JFrame thisFrame;
	  ArrayList<DataEntry> data;
	  File currentCollegeFile;
	  File currentUniFile;
	  
	  //Constructor - this runs first
	  StartingFrame() { 
	    super("Start Screen");
	    this.thisFrame = this; 
	    
	    this.setSize(700,700);
	    this.setLocationRelativeTo(null); //start the frame in the center of the screen;  
	    this.setResizable (false);
	    
	    //Create a Panel for title
	    JPanel panel1 = new JPanel();
	    panel1.setLayout(new FlowLayout());
	    
	    //create a panel for buttons 1-2
	    JPanel panel2 = new JPanel();
	    panel2.setLayout(new FlowLayout());
	    
	    //create a main panel for other panels
	    JPanel main = new JPanel();
	    main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
	    
	    //Create a JButton for the centerPanel
	    JButton startButton = new JButton("Add Files");
	    startButton.addActionListener(new addFileListener());
	    
	    //create a JButton for exit panel
	    JButton exitButton = new JButton("Exit");
	    exitButton.addActionListener(new ExitButtonListener());
	    
	    //create jbutton for remove files panel
	    JButton removeButton = new JButton("Remove Files");
	    removeButton.addActionListener(new RemoveButtonListener());
	    
	    //create jbutton for browse data
	    
	    //create jbutton for make graph data    
	    
	    
	     //Create a JButton for the centerPanel
	    JLabel startLabel = new JLabel("UniCollege Inventory");
	    
	    //Add all panels to the panel1 according to border layout
	    panel1.add(startLabel);
	    panel2.add(startButton);
	    panel2.add(exitButton);
	    panel2.add(removeButton);
	    main.add(panel1);
	    main.add(panel2);
	    
	    //add the main panel to the frame
	    this.add(main);
	    //Start the app
	    this.setVisible(true);
	  }
	  
	  //This is an inner class that is used to detect a button press
	 class addFileListener implements ActionListener {
	    public void actionPerformed(ActionEvent event)  {
//
//	    	JFrame main = new JFrame();

	    	FileChooser fChooser = new FileChooser();
//
//	    	main.add(fChooser);
//	    	main.pack();
//	    	main.setVisible(true);

	    	data = Analysis.readNewData(2017);
	    }

	  }
	 
	 class ExitButtonListener implements ActionListener{
	   public void actionPerformed(ActionEvent event){
	     thisFrame.dispose();
	   }
	 }
	 
	 class RemoveButtonListener implements ActionListener{
		 public void actionPerformed(ActionEvent event){
			 
		 }
	 }
	  
	  
	  //Analysis method starts this application
	  public static void main(String[] args) { 
	    new StartingFrame();

	  }
	  
	}


