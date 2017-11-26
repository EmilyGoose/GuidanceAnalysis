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

import javax.swing.SwingUtilities;

import java.util.ArrayList;

class SelectDataFrame extends JFrame { 
  
  JFrame thisFrame;
  Main test = new Main();
  ArrayList<String> dependent = new ArrayList<String>();
  ArrayList<String> independent = new ArrayList<String>();
  ArrayList<DataEntry> data;
  
  //Constructor - this runs first
  SelectDataFrame() { 
    super("Filter Data");
    this.thisFrame = this; 
    
     this.setSize(1000,1000);
     this.setLocationRelativeTo(null); //start the frame in the center of the screen;  
     this.setResizable (false);
     
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
     
     //Create JComboBox to select tag
     JComboBox tagList = new JComboBox(test.getAllTags(data).toArray());
     tagList.addActionListener(new tagListener());
     tagList.setMaximumSize(new Dimension(150, 25));
     panel1.add(new JLabel("Select Tag"));
     panel1.add(tagList);
     
     //Create CheckBox for each school
     ArrayList<String> schools = test.getAllSchools(data);
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
  
  //ActionListener for the comboBox
  class tagListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      JComboBox cb = (JComboBox)e.getSource();
      String tag = (String)cb.getSelectedItem();
      dependent.clear(); //replace independent with current selection
      dependent.add(tag);
    }
  }
  
  //ActionListener for the checkBox
  class checkBoxListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      JCheckBox checkBox = (JCheckBox)e.getSource();
      if(checkBox.isSelected()){ //Add to dependent ArrayList if checked, otherwise remove
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
       PieChart pieGraph = new PieChart(); 
       //DISPLAY PIE CHART HERE WITH THE ANALYSIS METHOD (CURRENTLY RETURNS THE RAW NUMBERS, NOT PERCENTAGES)
     }
  }
   
   //Main method starts this application
   public static void main(String[] args) { 
     new SelectDataFrame();

   }
   
 }
