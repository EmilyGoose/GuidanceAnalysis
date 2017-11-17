/**
* This template can be used as reference or a starting point
* for your final summative project
* @author Mangat
**/

//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
  
//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class GameFrame extends JFrame { 

  //class variable (non-static)
   static double x, y;
   static BarPanel gamePanel;
  
  
  //Constructor - this runs first
  GameFrame() { 
    
    super("My Game");  
    // Set the frame to full screen 
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(800, 600);
    this.setResizable(false);

    
    //Set up the game panel (where we put our graphics)
    gamePanel = new BarPanel();
    this.add(new BarPanel());
    
    MyKeyListener keyListener = new MyKeyListener();
    this.addKeyListener(keyListener);

    MyMouseListener mouseListener = new MyMouseListener();
    this.addMouseListener(mouseListener);

    this.requestFocusInWindow(); //make sure the frame has focus   
    
    this.setVisible(true);
  
   
  } //End of Constructor

  
  /** --------- INNER CLASSES ------------- **/
  
  // Inner class for the the game area - This is where all the drawing of the screen occurs
  private class BarPanel extends JPanel {
    public void paintComponent(Graphics g) {
      
      ArrayList <Integer> percentages = new ArrayList<Integer>();
      ArrayList <String> categories = new ArrayList<String>();
      categories.add("one");
      categories.add("two");
      categories.add("three");
      categories.add("four");
      categories.add("five");
      
      for (int i =0; i < 5; i++) {
        Integer rand = new Integer((int)(Math.random() * 300) );
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
         
         g.fillRect(40 + (60 * i), 520 - percentages.get(i), 45, percentages.get(i) ); //draws a bar for the category
         g.drawString(categories.get(i), (40 + (60 * i)),530 );             //draws a label for the category  
         
       }   
      
    }
  }
  
  // -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
    public class MyKeyListener implements KeyListener {
  
      public void keyTyped(KeyEvent e) {  
      }

      public void keyPressed(KeyEvent e) {
        //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
       
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {  //If 'D' is pressed
          System.out.println("YIKES D KEY!");
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //If ESC is pressed
          System.out.println("YIKES ESCAPE KEY!"); //close frame & quit
        } 
      }   
      
      public void keyReleased(KeyEvent e) {
      }
    } //end of keyboard listener
  
  // -----------  Inner class for the keyboard listener - This detects mouse movement & clicks and runs the corresponding methods 
    public class MyMouseListener implements MouseListener {
   
      public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked");
        System.out.println("X:"+e.getX() + " y:"+e.getY());
      }

      public void mousePressed(MouseEvent e) {
      }

      public void mouseReleased(MouseEvent e) {
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }
    } //end of mouselistener
    
}