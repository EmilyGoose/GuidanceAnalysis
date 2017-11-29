/**
 * SelectDataFrame.java
 * Author: Felix Tai
 * Function: Allows users to select data they want to graph
 */

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;

import java.util.ArrayList;

class TypeSelectFrame extends JFrame{
  JFrame thisFrame;
  String type = "";
  String[] graphOptions = new String[]{"Please select an option", "Subject vs. School", "Year over Year Comparison", "Single Variable"};
  ArrayList<DataEntry> data;
    
  TypeSelectFrame(ArrayList<DataEntry> data) { //put in current year data only
    super("Filter Data");
    this.thisFrame = this; 
    
    this.setSize(1000,1000);
    this.setLocationRelativeTo(null); //start the frame in the center of the screen;  
    this.setResizable (false);
    this.data = data;
    JCheckBox check;//define variables     
    Font bigFont = new Font("", Font.PLAIN, 20);
    
    //create a main panel for other panels
    JPanel main = new JPanel();
    main.setLayout(new BorderLayout());
    JPanel panel1 = new JPanel();
    
    //Create new JLabel for header
    JLabel header = new JLabel("What type of data should be compared?", JLabel.CENTER);
    header.setFont(bigFont);
    
    //Create JComboBox to select graph type
    JComboBox graphList = new JComboBox(graphOptions);
    graphList.setMaximumSize(new Dimension(150, 25));
    graphList.addActionListener(new graphListener());
    
    //Create button to confirm graph selection
    JButton confirmButton = new JButton("Select Data");
    confirmButton.setPreferredSize(new Dimension(100, 50));
    confirmButton.addActionListener(new confirmButtonListener());
    
    panel1.add(graphList);
    panel1.add(confirmButton);
    main.add(header, BorderLayout.NORTH);
    main.add(panel1, BorderLayout.CENTER);
    
    this.add(main);
    this.setVisible(true);
  }
  
  //ActionListener for confirmButton
  class confirmButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      if(type.equals("Subject vs. School")){
        thisFrame.dispose(); 
        new SelectDataFrame(data);
      }else if(type.equals("Year over Year Comparison")){
        thisFrame.dispose();
        new YearDataFrame();
      }else if(type.equals("Single Variable")){
        thisFrame.dispose();
        new SingleDataFrame(data);
      }else{ //no type selected, nothing happens
      }
    }      
  }
  
  //ActionListener for the comboBox
  class graphListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      JComboBox cb = (JComboBox)e.getSource();
      type = (String)cb.getSelectedItem();
    }
  }
}


////////////////////////////////////////////////////////////////////////
class SelectDataFrame extends JFrame { //frame to pick data to compare schools and a tag
  JFrame thisFrame;
  int year;
  String tag;
  Analysis analysisClass = new Analysis();
  ArrayList<String> dependent = new ArrayList<String>();
  ArrayList<String> independent = new ArrayList<String>();
  ArrayList<Integer> yearOption;
  ArrayList<String> tagOption;
  ArrayList<String> schools;
  
  //Constructor - this runs first
  SelectDataFrame(ArrayList<DataEntry> data) { 
    super("Filter Data");
    this.thisFrame = this; 
    this.setSize(1000,1000);
    this.setLocationRelativeTo(null); //start the frame in the center of the screen;  
    this.setResizable (false);
    
   yearOption = analysisClass.getAllYears(data);
   tagOption = analysisClass.getAllTags(data);
   schools = analysisClass.getAllSchools(data);
    
    yearOption.add(0, 0000); //blank options to ensure data is chosen
    tagOption.add(0, "Select...");
                   
    JCheckBox check;//define variables     
    Font bigFont = new Font("", Font.PLAIN, 20);
    
    //create a main panel for other panels
    JPanel main = new JPanel();
    main.setLayout(new BorderLayout());
    
    JPanel panel1 = new JPanel(); //creating nested panels
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    panel2.setLayout(new GridLayout(0,3));
    
    //Create new JLabel for header
    JLabel header = new JLabel("Select Data for Graphing", JLabel.CENTER);
    header.setFont(bigFont);
    
    //Create JComboBox to select year
    JComboBox yearList = new JComboBox(yearOption.toArray());
    yearList.addActionListener(new yearListener());
    yearList.setMaximumSize(new Dimension(150, 25));
    panel1.add(new JLabel("Select Year"));
    panel1.add(yearList);
    
    //Create JComboBox to select tag
    JComboBox tagList = new JComboBox(tagOption.toArray());
    tagList.addActionListener(new tagListener());
    tagList.setMaximumSize(new Dimension(150, 25));
    panel1.add(new JLabel("Select Tag"));
    panel1.add(tagList);
    
    //Create CheckBox for each school
    panel2.add(new JLabel("Select School(s)"));
    for(int i = 0; i < schools.size(); i ++){
      check = new JCheckBox(schools.get(i));
      check.addActionListener(new checkBoxListener());
      panel2.add(check);
    }
    
    //Create new button to confirm selections and start the graph
    JButton confirmButton = new JButton("Graph Data");
    confirmButton.setPreferredSize(new Dimension(100, 50));
    confirmButton.addActionListener(new confirmButtonListener());
    panel3.add(confirmButton);
    
    //add the main panel to the frame
    main.add(header, BorderLayout.NORTH);
    main.add(panel1, BorderLayout.WEST);
    main.add(panel2, BorderLayout.CENTER);
    main.add(panel3, BorderLayout.EAST);
    this.add(main);
    
    //Start the app
    this.setVisible(true);
  }
  
  //ActionListener for the year comboBox
  class yearListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      JComboBox cb = (JComboBox)e.getSource();
      year = (int)cb.getSelectedItem();
    }
  }
  
  //ActionListener for the tag comboBox
  class tagListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      JComboBox cb = (JComboBox)e.getSource();
      tag = (String)cb.getSelectedItem();
    }
  }
  
  //ActionListener for the checkBox
  class checkBoxListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      JCheckBox checkBox = (JCheckBox)e.getSource();
      if(checkBox.isSelected()){ //Add to independent ArrayList if checked, otherwise remove
        independent.add(checkBox.getText());
      }else if(!checkBox.isSelected()){
        independent.remove(checkBox.getText());
      }
    }
  }
  
  //ActionListener for the button to launch graphs
  class confirmButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      if(!tag.equals("Select...") && year != 0000){ //Make sure an option is selected for both tags and years
        thisFrame.dispose();
        dependent.add(tag);
        dependent.add(Integer.toString(year));
        //Create graph
      //getPercentages(analysis(independent, dependent, data)) <----- feed this into graph methods
      }
    }
  }   
}

///////////////////////////////////////////////////////////////////////////

class YearDataFrame extends JFrame { //frame to select data vs year
  JFrame thisFrame;
  Analysis analysisClass = new Analysis();
  String tag;
  String school;
  ArrayList<String> dependent = new ArrayList<String>();
  ArrayList<String> independent = new ArrayList<String>();
  ArrayList<DataEntry> data = analysisClass.readExistingData(); //gets all data, not just current year
  ArrayList<String> tagOption = analysisClass.getAllTags(data);
  ArrayList<String> schoolOption = analysisClass.getAllSchools(data);  
  
  //Constructor - this runs first
  YearDataFrame() { 
    super("Filter Data");
    this.thisFrame = this; 
    
    this.setSize(1000,1000);
    this.setLocationRelativeTo(null); //start the frame in the center of the screen;  
    this.setResizable (false);
    
    JCheckBox check;//define variables     
    Font bigFont = new Font("", Font.PLAIN, 20);
    
    tagOption.add(0, "none"); //add none option
    schoolOption.add(0, "none");
    
    //create a main panel for other panels
    JPanel main = new JPanel();
    main.setLayout(new BorderLayout());
    
    JPanel panel1 = new JPanel(); //creating nested panels
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
    
    //Create new JLabel for header
    JLabel header = new JLabel("Select Data for Graphing", JLabel.CENTER);
    header.setFont(bigFont);
    
    //Create JComboBox to select tag
    JComboBox tagList = new JComboBox(tagOption.toArray());
    tagList.addActionListener(new tagListener());
    tagList.setMaximumSize(new Dimension(150, 25));
    panel1.add(new JLabel("Select Tag"));
    panel1.add(tagList);
    
    //Create JComboBox to select school
    JComboBox schoolList = new JComboBox(schoolOption.toArray());
    schoolList.addActionListener(new schoolListener());
    schoolList.setMaximumSize(new Dimension(150, 25));
    panel1.add(new JLabel("Select School"));
    panel1.add(schoolList);
    
    //Create CheckBox for each year
    ArrayList<Integer> years = analysisClass.getAllYears(data);
    panel2.add(new JLabel("Select Year(s)"));
    for(int i = 0; i < years.size(); i ++){
      check = new JCheckBox(Integer.toString(years.get(i)));
      check.addActionListener(new checkBoxListener());
      panel2.add(check);
    }
    
    //Create new button to confirm selections and start the graph
    JButton confirmButton = new JButton("Graph Data");
    confirmButton.setPreferredSize(new Dimension(100, 50));
    confirmButton.addActionListener(new confirmButtonListener());
    panel3.add(confirmButton);
    
    //add the main panel to the frame
    main.add(header, BorderLayout.NORTH);
    main.add(panel1, BorderLayout.WEST);
    main.add(panel2, BorderLayout.CENTER);
    main.add(panel3, BorderLayout.EAST);
    this.add(main);
    
    //Start the app
    this.setVisible(true);
  }
  
  //ActionListener for the comboBox (tag)
  class tagListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      JComboBox cb = (JComboBox)e.getSource();
      tag = (String)cb.getSelectedItem();
    }
  }
  
  //ActionListener for the comboBox (school)
  class schoolListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      JComboBox cb = (JComboBox)e.getSource();
      school = (String)cb.getSelectedItem();
    }
  }
  
  //ActionListener for the checkBox
  class checkBoxListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      JCheckBox checkBox = (JCheckBox)e.getSource();
      if(checkBox.isSelected()){ //Add to independent ArrayList if checked, otherwise remove
        independent.add(checkBox.getText());
      }else if(!checkBox.isSelected()){
        independent.remove(checkBox.getText());
      }
    }
  }
  
  //ActionListener for the button to launch graphs
  class confirmButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      thisFrame.dispose();
      if(!tag.equals("none")){ //adding the selected tag and school to dependent
        dependent.add(tag);
      }
      if(!school.equals(null)){
        dependent.add(school);
      }
      //Create graph
      //getPercentages(analysis(independent, dependent, data)) <----- feed this into graph methods
    }
  }   
}


/////////////////////////////////////////////////////////////////////////////////////

class SingleDataFrame extends JFrame { //frame to select data vs year
  JFrame thisFrame;
  Analysis analysisClass = new Analysis();
  ArrayList<String> dependent = new ArrayList<String>();
  ArrayList<String> independent = new ArrayList<String>();
  
  //Constructor - this runs first
  SingleDataFrame(ArrayList<DataEntry> data) { 
    super("Filter Data");
    this.thisFrame = this; 
    
    this.setSize(1000,1000);
    this.setLocationRelativeTo(null); //start the frame in the center of the screen;  
    this.setResizable (false);
    
    JCheckBox check;//define variables     
    Font bigFont = new Font("", Font.PLAIN, 20);
    
    //create a main panel for other panels
    JPanel main = new JPanel();
    main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
    
    JPanel panel1 = new JPanel(); //creating nested panels
    panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
    
    //Create new JLabel for header
    JLabel header = new JLabel("Select Data for Graphing", JLabel.CENTER);
    header.setFont(bigFont);
    panel1.add(header);
    
    //Create JComboBox to select single variable
    JComboBox tagList = new JComboBox(analysisClass.getAllSchools(data).toArray());
    tagList.addActionListener(new comboListener());
    tagList.setMaximumSize(new Dimension(150, 25));
    panel1.add(new JLabel("Select Option"));
    panel1.add(tagList);
    
    //Create new button to confirm selections and start the graph
    JButton confirmButton = new JButton("Graph Data");
    confirmButton.addActionListener(new confirmButtonListener());
    panel1.add(confirmButton);
    
    //add the main panel to the frame
    main.add(header);
    main.add(panel1);
    this.add(main);
    
    //Start the app
    this.setVisible(true);
  }
  
  //ActionListener for the comboBox for selecting variable
  class comboListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      JComboBox cb = (JComboBox)e.getSource();
      String item = (String)cb.getSelectedItem();
      independent.clear(); //replace independent with current selection
      independent.add(item);
    }
  }
  
  //ActionListener for the button to launch graphs
  class confirmButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      thisFrame.dispose();
      dependent.add(""); //no dependent, indexOf of blank string always zero so analysis method still works
      //Create graph
      //getPercentages(analysis(independent, dependent, data)) <----- feed this into graph methods
    }
  }   
}



