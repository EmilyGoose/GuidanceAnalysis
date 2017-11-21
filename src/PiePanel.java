/**
 * File Name: PiePanel.java
 * Author: Vincent Guo
 * Date: Nov. 15, 2017
 * Function: custom JPanel that is used to draw a Pie Chart using percentages
 */

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JPanel;

public class PiePanel extends JPanel {
    public void paintComponent(Graphics g) {

      ArrayList <Integer> percentages = new ArrayList<Integer>();
      ArrayList <String> categories = new ArrayList<String>();
      categories.add("one");
      categories.add("two");
      categories.add("three");
      categories.add("four");
      categories.add("five");
      percentages.add(20);
      percentages.add(10);
      percentages.add(40);
      percentages.add(15);
      percentages.add(15);
      
      for (int i = 0; i < percentages.size(); i++) {        //for loop converts the raw percentages into angle degrees for circle math
        percentages.set(i, (int)( Math.round (( percentages.get(i) *360 ) / 100.0 ) ) );
      }

       super.paintComponent(g); //required
       setDoubleBuffered(true);

       g.setFont( new Font(Font.MONOSPACED, Font.BOLD, 20));

       g.setColor(Color.BLACK);
       g.drawRect( 650, 30, 120, 25); //vertical axis
       g.drawString( "Legend", 673, 48);

       int startAngle = 0; //starting angle used for circle math when drawing

       for (int i = 0; i < percentages.size(); i ++) { //for loop used to draw pie charts
          System.out.println(startAngle);
         int red = (int)(Math.random() * 255);
         int green = (int)(Math.random() * 255);
         int blue = (int)(Math.random() * 255);
         Color randColour = new Color(red,green,blue); //creates a random colour

         g.setColor(Color.BLACK);
         g.drawRect(650, (55 + (25 * i)), 120, 25);                                 //these two lines draw another box for the legend
         g.drawLine (680, (55 + (25 * i)), 680, (55 + (25 * (i + 1))) );

         g.setFont( new Font(Font.SANS_SERIF, Font.BOLD, 15)); //used to write the category label
         g.drawString ( categories.get(i), 710, (70 + (25 * i)) );

         g.setColor(randColour);
         g.fillArc(150, 100, 400, 400, startAngle,percentages.get(i) ); //draws the filled arc of the circle in a random colour; adds a black marker to the colour
         g.fillRect(653, (58 + (25 * i)), 25, 20);
//         g.setColor(Color.BLACK);
//         g.fillArc(100, 50, 500, 500, startAngle + (percentages.get(i) / 2) , 1);
//         g.setColor(randColour);
//         g.fillArc(225, 175, 250, 250, startAngle,percentages.get(i) );
         startAngle += percentages.get(i);
        
       }
      
    }

//    public double circleSlope(double startAngle, double arcAngle) {  //calculates the slope of a line along a circle (based on a given angle and arc
//      double angle = (arcAngle / 2.0);
//      double y = Math.sin(angle) * 200.0;
//      double x = Math.cos(angle) * 200.0;
//
//      double m = (300.0 - y) / ( 350.0 - x);
//      return m;
//    }
//
//    public int circleIntercept(double startAngle, double arcAngle) {
//      double intercept;
//      double slope = circleSlope(startAngle, arcAngle);
//      intercept = 300.0 - (350.0 * slope);
//      return (int) intercept;
//    }
  }