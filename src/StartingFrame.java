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
import javax.swing.JTextField;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
	import java.util.ArrayList;

	import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;

	class StartingFrame extends JFrame { 

	  JFrame thisFrame;
	  
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
	    startButton.addActionListener(new BrowseButtonListener());
	    
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
	 class BrowseButtonListener implements ActionListener {  
	    public void actionPerformed(ActionEvent event)  {

	    	ArrayList<DataEntry> data = Analysis.readNewData();
	    	ArrayList<Integer> percentages = Analysis.analysis(Analysis.getAllSchools(data), Analysis.getAllTags(data), data);
	    	JFrame main = new JFrame("Data");
	    	PieChart pie = new PieChart();
	    	for (int i = 0; i < Analysis.getAllSchools(data).size(); i++) {
	    		pie.addToData(Analysis.getAllSchools(data).get(i), percentages.get(i));
			}
			main.add(pie);
	    	main.setSize(1920,1080);
	    	pie.setVisible(true);
	    	main.setVisible(true);
//	      JFrame browseFrame= new JFrame("Browse");
//	      browseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//	      JPanel main = new JPanel();

//	      JPanel datePanel = new JPanel();
//	      JLabel dateLabel = new JLabel("Year of admissions: ", JLabel.RIGHT);
//	      JTextField dateField = new JTextField(8);
//	      datePanel.add(dateLabel);
//	      datePanel.add(dateField);

//		  FileChooser fc = new FileChooser();
//	      main.add(fc);
//	      fc.getFile("college"); //todo: do something with the file
	      //main.add(datePanel);
//	      browseFrame.add(main);
//	      browseFrame.pack();
//	      browseFrame.setVisible(true);
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


