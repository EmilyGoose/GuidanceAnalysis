/**
 * Tagger.java
 * Creates tags for each DataEntry class
 */

//Import statements
import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner; 
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class Tagger {
  //Matching of course codes to tags
  HashMap<String, ArrayList<String>> tagDatabase;
  //List of possible tags for future implementation
  ArrayList<String> possibleTags = new ArrayList<String>();
  private Scanner inputScanner;
  ArrayList<String> newTags;
  
  Tagger() {
    inputScanner = new Scanner(System.in);
    try {
      BufferedReader input = new BufferedReader(new FileReader("tags.json"));
      StringBuilder JSONString = new StringBuilder();
      String nextLine = input.readLine();
      while (nextLine != null) {
        JSONString.append(nextLine);
        nextLine = input.readLine();
      }
      
      tagDatabase = JSON.decodeJSON(JSONString.toString());
      if(tagDatabase.get("possibleTags") != null){
        possibleTags = tagDatabase.remove("possibleTags");
      }
    } catch (FileNotFoundException E) {
      //This exception happens if the file doesn't exist
      tagDatabase = new HashMap<>();
      possibleTags = new ArrayList<>();
    } catch (IOException E) {
      System.out.println("Exception while fetching tags: " + E);
    }
  }
  
  public boolean saveTags() {
    //Write all the data to JSON
    StringBuilder JSONString = new StringBuilder("[\n");
    JSONString.append(JSON.toJSONString(this.tagDatabase, 1));
    JSONString.append(",\n");
    JSONString.append(JSON.toJSONString(this.possibleTags, 1));
    JSONString.append("\n]");
    try {
      BufferedWriter output = new BufferedWriter(new FileWriter("tags.json"));
      output.write(JSONString.toString());
      output.close();
    } catch (IOException E) {
      System.out.println("Exception while saving tags: " + E);
      return false;
    }
    
    return true;
  }
  
  
  //public ArrayList<String> editTags(String programCode, String programName, String school) {
    //TODO: Call the GUI
    //return this.getTags(programCode, programName, school);
  //}
  
  //method is necessary for DocumentListener
  public void valueChanged(ListSelectionEvent e) {
    //empty method: DON'T PUT ANYTHING HERE
    //causes errors
  } 
}

