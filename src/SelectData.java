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
import javax.swing.JOptionPane;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.FlowLayout;

//TypeSelectFrame launches after the graph data button is pressed on Starting frame
class TypeSelectFrame extends JFrame{
  JFrame thisFrame;
  String type = "";
  String[] graphOptions = new String[]{"Please select an option", "Subject vs. School", "Year over Year Comparison"};
  ArrayList<DataEntry> data;
  ArrayList<Integer> percentages = new ArrayList<Integer>();
  ArrayList<Integer> counts = new ArrayList<Integer>();
  Analysis analysisClass = new Analysis();
  
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
    
    //Create button to go back
    JButton backButton = new JButton("Back");
    backButton.setPreferredSize(new Dimension(100, 50));
    backButton.addActionListener(new backButtonListener());
    
    panel1.add(graphList);
    panel1.add(confirmButton);
    panel1.add(backButton);
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
      }
    }      
  }
  
  //ActionListener for button to go back to main menu
  class backButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      thisFrame.dispose();
      new StartingFrame();
    }
  }
  
  //ActionListener for the comboBox
  class graphListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      JComboBox cb = (JComboBox)e.getSource();
      type = (String)cb.getSelectedItem();
    }
  }
  
////////////////////////////////////////////////////////////////////////
  class SelectDataFrame extends JFrame { //frame to pick data to compare schools and a tag
    JFrame thisFrame;
    int year = 0;
    String tag = "Select...";
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
      panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
      
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
      
      //Create button to go back
      JButton backButton = new JButton("Back");
      backButton.setPreferredSize(new Dimension(100, 50));
      backButton.addActionListener(new backButtonListener());
      panel3.add(backButton);
      
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
    
    //ActionListener for button to go back to TypeSelectFrame
    class backButtonListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
        thisFrame.dispose();
        new TypeSelectFrame(data);
      }
    }
    
    //ActionListener for the button to launch graphs
    class confirmButtonListener implements ActionListener{
      Analysis analyzeInstance = new Analysis();
      public void actionPerformed(ActionEvent e){
        if(!tag.equals("Select...") && year != 0000 && independent.size() > 0){ //Make sure an option is selected for tag, year, and school(s)
          thisFrame.dispose();
          dependent.add(tag);
          dependent.add(Integer.toString(year));
          
          //Create graph
          thisFrame.dispose();
          
          //new frame for graph and table
          JFrame main = new JFrame("Data");
          thisFrame = main;
          main.setLayout(new FlowLayout());
          
          //create pie panel
          PieChart pie = new PieChart();
          pie.setPreferredSize(new Dimension(1000,1000));
          
          //Create button to go back
          JButton backButton = new JButton("Back");
          backButton.setPreferredSize(new Dimension(100, 50));
          backButton.addActionListener(new backButtonListener());
          
          //get the statistics and graph them
          counts = analysisClass.analysis(independent, dependent, data);
          percentages = analysisClass.getPercentages(counts); //convert to percentages
          for (int i = 0; i < independent.size(); i++) { //add data to pie panel
            pie.addToData(independent.get(i), percentages.get(i));
          }
          
          //add independent data and counts to a 2D array to make the jtable
          String[][] tableData = new String[independent.size()][2];
          for(int i = 0; i < independent.size(); i ++){
            tableData[i][0] = independent.get(i);
            tableData[i][1] = Integer.toString(counts.get(i));
          }
          //Create Jtable
          JTable jt=new JTable(tableData, new String[]{"Independent", "Count"});
          jt.getColumnModel().getColumn(0).setPreferredWidth(250);
          
          //Add panels to the frame
          main.add(pie);
          JPanel tablePanel = new JPanel();
          tablePanel.add(jt);
          tablePanel.add(backButton);
          main.add(tablePanel);
          main.setSize(1920,1080);
          pie.setVisible(true);
          main.setVisible(true);
          
          System.out.println(independent);
          System.out.println(dependent);
          System.out.println(counts);
        }else{ //tag, year, or school has not been selected, display error message
          JOptionPane.showMessageDialog(null,"Please ensure a year, program tag, and school have been selected.");
        }
      }
    }   
  }
  
///////////////////////////////////////////////////////////////////////////
  
  class YearDataFrame extends JFrame { //frame to select data vs year
    JFrame thisFrame;
    String tag = "none";
    String school = "none";
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
      panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
      
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
      
      //Create button to go back
      JButton backButton = new JButton("Back");
      backButton.setPreferredSize(new Dimension(100, 50));
      backButton.addActionListener(new backButtonListener());
      panel3.add(backButton);
      
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
    
    //ActionListener for button to go back to TypeSelectFrame
    class backButtonListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
        thisFrame.dispose();
        new TypeSelectFrame(data);
      }
    }
    
    //ActionListener for the button to launch graphs
    class confirmButtonListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
        boolean valid = true;
        
        //adding the selected tag and school to dependent
        if(!tag.equals("none") && !school.equals("none")){ //both options selected
          dependent.add(tag);
          dependent.add(school);
        }else if(!tag.equals("none") && school.equals("none")){ //only tag has been selected
          dependent.add(tag);
        }else if(!school.equals("none") && tag.equals("none")){ //only school has been selected
          dependent.add(school);
        }else{
          JOptionPane.showMessageDialog(null,"Only one selection can be \"none\"."); //both are none, show error message
          valid = false;
        }
        
        if(independent.size() > 0 && valid){ //check if year has been selected, graph is it has
          thisFrame.dispose();
          
          //new frame for graph and table
          JFrame main = new JFrame("Data");
          thisFrame = main;
          main.setLayout(new FlowLayout());
          
          //create pie panel
          PieChart pie = new PieChart();
          pie.setPreferredSize(new Dimension(1000,1000));
          
          //Create button to go back
          JButton backButton = new JButton("Back");
          backButton.setPreferredSize(new Dimension(100, 50));
          backButton.addActionListener(new backButtonListener());
          
          //get the statistics and graph them
          counts = analysisClass.analysis(independent, dependent, data);
          percentages = analysisClass.getPercentages(counts); //convert to percentages
          for (int i = 0; i < independent.size(); i++) { //add data to pie panel
            pie.addToData(independent.get(i), percentages.get(i));
          }
          
          //add independent data and counts to a 2D array to make the jtable
          String[][] tableData = new String[independent.size()][2];
          for(int i = 0; i < independent.size(); i ++){
            tableData[i][0] = independent.get(i);
            tableData[i][1] = Integer.toString(counts.get(i));
          }
          //Create Jtable
          JTable jt=new JTable(tableData, new String[]{"Independent", "Count"});
          jt.getColumnModel().getColumn(0).setPreferredWidth(250);
          
          //Add panels to the frame
          main.add(pie);
          JPanel tablePanel = new JPanel();
          tablePanel.add(jt);
          tablePanel.add(backButton);
          main.add(tablePanel);
          main.setSize(1920,1080);
          pie.setVisible(true);
          main.setVisible(true);
          
          System.out.println(independent);
          System.out.println(dependent);
          System.out.println(counts);
        }else if(valid){ //year has not been selected
          JOptionPane.showMessageDialog(null,"Please ensure a year has been selected.");
        }
      }
    }   
  }
}



