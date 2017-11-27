import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class AddTags extends JFrame implements ListSelectionListener {
 
 String programCode;
 String programTitle;
 String uniColl;
 String[] programTags;
 ArrayList<String> allTags = new ArrayList<>();
 ArrayList<String> newTags = new ArrayList<>();
 
 JFrame thisFrame;
 JTextField textField;
 JList list;
 DefaultListModel listModel;
 String newLine = "\n";
 JButton addButton;
 JButton removeButton;
 JButton finishButton;
 JButton exitButton;
 JTextField tagField;
 
 AddTags(String programCode, String programTitle, String uniColl, String[] programTags, ArrayList<String> allTags){
  super("Edit Tags");
  
  //creating frame and adding features
  this.thisFrame = this;
  this.setSize(600,350);
  this.setLocationRelativeTo(null); //start the frame in the center of the screen;  
  this.setResizable (false);
  
  //initializing all the variables
  this.programCode = programCode;
  this.programTitle = programTitle;
  this.programTags = programTags;
  this.allTags = allTags;
  this.uniColl = uniColl;
  
  //creating main panel
  JPanel main = new JPanel();

  //adding previous tags to list content
  listModel = new DefaultListModel();
  for (int i = 0; i < programTags.length; i++) {
   listModel.addElement(programTags[i]);
  }
  
  //creating list and adding features
  list = new JList (listModel);
  list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  list.setSelectedIndex(0);
  list.addListSelectionListener((ListSelectionListener) this);
  JScrollPane listScrollPane = new JScrollPane(list);
  Dimension d = list.getPreferredSize();
  d.width = 300;
        d.height = 100;
        listScrollPane.setPreferredSize(d);
        
        //add button creation and adding to actionListener
        addButton = new JButton("Add Tag: ");
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand("Add Tag:");
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
        
        //remove button creation
        removeButton = new JButton ("Remove Selected Tag");
        removeButton.setActionCommand("Remove Selected Tag");
        removeButton.addActionListener(new RemoveListener());
        
        //text field where new tagField is entered
        tagField = new JTextField(20);
        tagField.addActionListener(addListener);
        tagField.getDocument().addDocumentListener(addListener);
        String thisTag = listModel.getElementAt(list.getSelectedIndex()).toString();
        
        //finish button
        finishButton = new JButton ("Save and Finish");
        finishButton.addActionListener(new FinishListener());
        
        //exit button
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ExitListener());
        
        //info panel: has program code and name and university
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        JLabel info1 = new JLabel("Program Title: "+ programTitle);
        JLabel info2 = new JLabel("Program Code: "+ programCode);
        JLabel info3 = new JLabel("University/College: "+ uniColl);
     
        infoPanel.add(info1);
        infoPanel.add(info2);
        infoPanel.add(info3);
        
        //north panel
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout (northPanel, BoxLayout.Y_AXIS));
        northPanel.add(Box.createVerticalStrut(10));
        northPanel.add(infoPanel);
        northPanel.add(Box.createVerticalStrut(15));
        
        northPanel.add(listScrollPane);
        northPanel.add(Box.createVerticalStrut(15));
        
        //panel for buttons below list: stuff to make it pretty
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));  
        buttonPanel.add(removeButton);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(tagField);
        buttonPanel.add(addButton);
       
        JPanel button2Panel = new JPanel();
        button2Panel.add(finishButton);
        button2Panel.add(exitButton);
        
        main.add(northPanel);
        main.add(buttonPanel);
        main.add(button2Panel);
        
        
        this.add(main);
        this.setVisible(true);
 }
 
 class RemoveListener implements ActionListener{
  public void actionPerformed(ActionEvent e) {
   int index = list.getSelectedIndex();


   int size = listModel.getSize();
   System.out.println(size);
   if (size == 0) {
    removeButton.setEnabled(false);
   }else {
    if (index == listModel.getSize()) {
     index--;
    }
    listModel.remove(index);
    list.setSelectedIndex(index);
    list.ensureIndexIsVisible(index);
   }
  }
 }
 
 class AddListener implements ActionListener, DocumentListener{
  boolean enable = false;
  JButton button;
  
  public AddListener(JButton button) {
   this.button = button;
  }
  
  public void actionPerformed(ActionEvent e) {
   String newTag = tagField.getText();
   
    if (tagField.equals("") || listModel.contains(tagField) == true) {
     tagField.requestFocusInWindow();
     tagField.selectAll();
     return;
    }
    
    int index = list.getSelectedIndex();
    
    listModel.addElement(tagField.getText());  //adds text on to the end
    
    //adds text to this programs arrayList
     newTags.add(newTag);
     //adds new tag to array list with all existing tags (to be used for future suggestions)
     boolean check = false;
     for (int i = 0; i < allTags.size(); i++){     
       if (newTag.equals(allTags.get(i))){
         check = true;
       }
     }
     if (check == false){
       allTags.add(newTag);
     }
    
    //resetting textfield:
    tagField.requestFocusInWindow();
    tagField.setText("");
    
    list.setSelectedIndex(index);
    list.ensureIndexIsVisible(index);
  }

// @Override
 public void changedUpdate(DocumentEvent e) {
  if(!handleEmptyTextField(e)) {
   enableButton();
  }
 }

 @Override
 public void insertUpdate(DocumentEvent e) {
  enableButton();
  
 }

 @Override
 public void removeUpdate(DocumentEvent e) {
  handleEmptyTextField(e);  
 }

 public void enableButton() {
  if (!enable) {
   button.setEnabled(true);
  }
 }
 
 public boolean handleEmptyTextField(DocumentEvent e) {
  if (e.getDocument().getLength() <=0) {
   button.setEnabled(false);
   enable = false;
   return true;
  }
  return false;
 }
 }
 

 class FinishListener implements ActionListener{
    public void actionPerformed(ActionEvent e) {
     
      new StartingFrame();
      System.out.println(newTags);
      thisFrame.dispose();
    }
  }
 
 class ExitListener implements ActionListener{
  public void actionPerformed(ActionEvent e){
   thisFrame.dispose();
   new StartingFrame();
  }
 }
 
 public void valueChanged(ListSelectionEvent e) {
   //empty method: DON'T PUT ANYTHING HERE
   //causes errors
 }
 
   public static void main(String[] args) { 
    String one = "abc";
    String two = "def";
    String yes = "1234";
    String[] three= new String[]{"ghi", "jkl", "mnop"};
    ArrayList<String> four = new ArrayList<String>();
    four.add("qrs");
    four.add("tuv");
    new AddTags(one, two, yes, three, four);
   }
 
}

 
