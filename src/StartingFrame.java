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
import javax.swing.JOptionPane;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.awt.Font;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.xml.crypto.Data;
import javax.swing.JTextField;
import java.awt.Dimension;

class StartingFrame extends JFrame {
  
  JFrame thisFrame;
  Analysis analysis = new Analysis();
  ArrayList<DataEntry> data = analysis.readExistingData(); //read from existing data if it exists
  File currentCollegeFile;
  File currentUniFile;
  int year;
  Font bigFont = new Font("", Font.PLAIN, 20);
  
  //Constructor - this runs first
  StartingFrame() { 
    super("Start Screen");
    this.thisFrame = this; 
    
    this.setSize(700,700);
    this.setLocationRelativeTo(null); //start the frame in the center of the screen;  
    this.setResizable (false);
    
    //create a panel for buttons 1-2
    JPanel panel2 = new JPanel();
    panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
    
    //create a main panel for other panels
    JPanel main = new JPanel();
    main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
    
    //Create a JButton for the to add data
    JButton startButton = new JButton("Add Files");
    startButton.addActionListener(new addFileListener());
    startButton.setMaximumSize(new Dimension(200, 50));
    startButton.setAlignmentX(CENTER_ALIGNMENT);
    
    //create a JButton for exit panel
    JButton exitButton = new JButton("Exit");
    exitButton.addActionListener(new ExitButtonListener());
    exitButton.setMaximumSize(new Dimension(200, 50));
    exitButton.setAlignmentX(CENTER_ALIGNMENT);
    
    //create jButton for graph data selection panel
    JButton graphButton = new JButton("Graph Data");
    graphButton.addActionListener(new graphButtonListener());  
    graphButton.setMaximumSize(new Dimension(200, 50));
    graphButton.setAlignmentX(CENTER_ALIGNMENT);
    
    //Create a JLabel for the title
    JLabel startLabel = new JLabel("UniCollege Inventory");
    startLabel.setFont(bigFont);
    startLabel.setAlignmentX(CENTER_ALIGNMENT);
    
    //Create a JLabel for spacing
    JLabel spaceLabel = new JLabel(" ");
    spaceLabel.setFont(bigFont);
    spaceLabel.setAlignmentX(CENTER_ALIGNMENT);
    
    //Add all panels to the panel1 according to border layout
    panel2.add(startButton);
    panel2.add(graphButton);
    panel2.add(exitButton);
    main.add(spaceLabel);
    main.add(startLabel);
    main.add(panel2);
    
    //add the main panel to the frame
    this.add(main);
    //Start the app
    this.setVisible(true);
  }
  
  //This is an inner class that is used to detect a button press
  class addFileListener implements ActionListener {
    public void actionPerformed(ActionEvent event)  {
      DateFrame selectYear = new DateFrame();
    }     
  }
  
  class ExitButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent event){
      thisFrame.dispose();
    }
  }
  
  class graphButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent event){
      thisFrame.dispose();
      new TypeSelectFrame(data);
    }
  }
  
  class DateFrame extends JFrame{ //inner class for year selection frame that appears after button is pressed
    private int year;
    JFrame thisFrame;
    DateFrame(){
      super("Enter the Year of the data being selected");
      this.thisFrame = this; 
      this.setSize(700,400);
      JPanel datePanel = new JPanel();
      JLabel dateLabel = new JLabel("Year of admissions: ", JLabel.RIGHT);
      JTextField dateField = new JTextField(8);
      dateField.addActionListener(new textFieldListener());
      datePanel.add(dateLabel);
      datePanel.add(dateField);
      this.add(datePanel); //add panel to the frame
      this.setVisible(true);
    }
    
    public int getYear(){
      return this.year;
    }
    
    class textFieldListener implements ActionListener{
      boolean validYear = true;
      public void actionPerformed(ActionEvent event) {
        JTextField yearField = (JTextField)event.getSource();
        String text = yearField.getText();
        //try catch to make sure an integer is entered by the user
        try{
          if(text.length() < 4){ //check if the date is four digits long, mark as invalid if it isn't
            validYear = false;
          }
          year = Integer.parseInt(text);
        }catch(NumberFormatException e){ //input is not an integer, try again
          JOptionPane.showMessageDialog(null, "Invalid year. Please try again."); //displaying error message
          thisFrame.dispose();
          new DateFrame();
          validYear = false; //mark as an invalid year
        }
        if(validYear){ //year is valid
          thisFrame.dispose();
          System.out.println(year);
          analysis.readNewData(year); //read new data using analysis
          data = analysis.readExistingData(); //update data with new data (written to file);
        }
      }
    }
  }
}





