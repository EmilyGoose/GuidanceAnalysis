/**
 * File Name: BarPanel.java
 * Author: Vincent Guo
 * Date: Nov. 15, 2017
 * Function: Custom JPanel that displays a bar chart based on provided percentages
 */


import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JPanel;

public class BarPanel extends JPanel {
    public void paintComponent(Graphics g) {
      
      ArrayList <Integer> percentages = new ArrayList<Integer>();
      ArrayList <String> categories = new ArrayList<String>();
      categories.add("one");
      categories.add("two");
      categories.add("three");
      categories.add("four");
      categories.add("five");
      
      for (int i =0; i < 5; i++) {
        Integer rand = new Integer((int)(Math.random() * 100) );
        percentages.add(rand);
        
      }
      
      
       super.paintComponent(g); //required
       setDoubleBuffered(true); 
       
       g.setColor(Color.BLACK);
       g.drawLine( 30, 20, 30, 520); //vertical axis
       g.drawLine ( 30, 520, 700, 520); //horizontal axis
       for (int i = 20; i < 520; i+= 20) {  //for loop used to draw y-axis labels
         g.drawLine( 25, i, 35, i);
         String num = ( (104 - (i / 5)) + "");
         g.drawString(num, 5, i);
       }
       
       for (int i = 0; i < percentages.size(); i ++) { //for loop used to draw bar graphs
         
         int red = (int)(Math.random() * 255);
         int green = (int)(Math.random() * 255);
         int blue = (int)(Math.random() * 255);         
         Color randColour = new Color(red,green,blue); //creates a random colour         
         g.setColor(randColour);
         
         g.fillRect(40 + (60 * i), (520 - (percentages.get(i) * 5)), 45, (percentages.get(i) * 5) ); //draws a bar for the category
         g.setColor(Color.BLACK);
         g.drawString(categories.get(i), (40 + (60 * i)),530 );             //draws a label for the category  
         System.out.println(percentages.get(i));
         
       }   
      
    }
  }