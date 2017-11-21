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
 String[] programTags;
 String[] allTags;
 
 public ArrayList<String> newTags = new ArrayList<>();
 
 JFrame thisFrame;
 JTextField textField;
 JList list;
 DefaultListModel listModel;
 String newLine = "\n";
 JButton addButton;
 JButton removeButton;
 JButton finishButton;
 JTextField tagField;
 
 AddTags(String programCode, String programTitle, String[] programTags, String[] allTags){
  super("Edit Tags");
  
  //creating frame and adding features
  this.thisFrame = this;
  this.setSize(600,300);
  this.setLocationRelativeTo(null); //start the frame in the center of the screen;  
  this.setResizable (true);
  
  //initializing all the variables
  this.programCode = programCode;
  this.programTitle = programTitle;
  this.programTags = programTags;
  this.allTags = allTags;
  
  //creating main panel
  JPanel main = new JPanel();
  main.setLayout(new FlowLayout());
  
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
       // list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        Dimension d = list.getPreferredSize();
        d.width = 300;
        d.height = 100;
        listScrollPane.setPreferredSize(d);
        
        //add button creation and adding to actionListener
        JButton addButton = new JButton("Add Tag: ");
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand("Add Tag:");
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
        
        //remove button creation
        JButton removeButton = new JButton ("Remove Selected Tag");
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
               
        main.add(listScrollPane);
        main.add(buttonPanel);
        main.add(button2Panel);
        
        this.add(main);
        this.setVisible(true);
 }
 
 class RemoveListener implements ActionListener{
  public void actionPerformed(ActionEvent e) {
   int index = list.getSelectedIndex();
   listModel.remove(index);
   
   int size = listModel.getSize();
   
   if (size == 0) {
    removeButton.setEnabled(false);
   }else {
    if (index == listModel.getSize()) {
     index--;
    }
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
 
 
 class EditListener implements ActionListener{
	 public void actionPerformed(ActionEvent e){
		 
	 }
 } 
 class FinishListener implements ActionListener{
	   public void actionPerformed(ActionEvent e) {
	     for (int i = 0; i < listModel.getSize(); i++){
	       newTags.add((String)listModel.getElementAt(i));
	     }
	     new StartingFrame();
	     System.out.println(newTags);
	     thisFrame.dispose();
	   }
	 }
 
 public void valueChanged(ListSelectionEvent e) {
   //empty method: DON'T PUT ANYTHING HERE
   //causes errors
 }
 
   public static void main(String[] args) { 
    String one = "abc";
    String two = "def";
    String[] three= new String[]{"ghi", "jkl", "mnop"};
    String[] four = new String[]{"qrs", "tuv"};
    new AddTags(one, two, three, four);
   }
 
}

 
