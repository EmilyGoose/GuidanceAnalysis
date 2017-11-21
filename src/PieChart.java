/**
 * PieChart.java
 * Rewrite of Vincent's pie chart panel to be compatible with the program
 * Misha Larionov
 */

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

public class PieChart extends JPanel {

    //String, Integer mapping of labels to values
    //Values are absolute, percentages are calculated at runtime
    private HashMap<String, Integer> data;

    PieChart() {
        super();
        data = new HashMap<>();
    }


    //Allows the user to put in data on the fly
    public void addToData(String key, int value) {
        data.put(key, value);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setDoubleBuffered(true);

        //Set up a system that guarantees unique color across the whole spectrum
        int colorStep = (Integer.parseInt("FFFFFF", 16) - 255) / data.size();
        //This is the minimum color so that we don't have black as a possible value
        int color = Integer.parseInt("0000A0", 16);

        //Get the minimum of width and height to guarantee squareness
        int panelSize = Math.min(this.getWidth(), this.getHeight());

        int currentStartAngle = 0;

        int totalValue = this.calculateTotal(data);
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            //Calculate the percentage of the specified entry
            double percentage = ((double)entry.getValue() / totalValue);

            //Set the color of the panel
            g.setColor(new Color(color));

            //Calculate the angle required
            int angle = (int)Math.round(percentage * 360);

            //Draw the arc
            g.fillArc(0, 0, panelSize, panelSize, currentStartAngle, angle);

            //Increment the start angle and the color
            currentStartAngle += angle;
            color += colorStep;
        }
    }

    private int calculateTotal(HashMap<String, Integer> map) {
        int total = 0;
        for (int i : map.values()) {
            total += i;
        }
        return total;
    }


}
