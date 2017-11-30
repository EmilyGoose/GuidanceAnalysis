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

public class AddTags extends JFrame implements ListSelectionListener {
  
  private String programCode;
  private String programTitle;
  private String uniColl;
  private ArrayList<String> programTags;
  private ArrayList<String> allTags = new ArrayList<>();
  public ArrayList<String> newTags = new ArrayList<>();
  public boolean ready = false;
  
  private JFrame thisFrame;
  private JList list;
  private DefaultListModel listModel;
  private JButton removeButton;
  private JTextField tagField;
  
  AddTags(String programCode, String programTitle, String uniColl, ArrayList<String> programTags, ArrayList<String> allTags){
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
    for (int i = 0; i < programTags.size(); i++) {
      listModel.addElement(programTags.get(i));
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
    JButton addButton = new JButton("Add Tag: ");
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
    String thisTag = list.getSelectedIndex() > -1 ? listModel.getElementAt(list.getSelectedIndex()).toString() : null ;
    
    //finish button
    JButton finishButton = new JButton("Save and Finish");
    finishButton.addActionListener(new FinishListener());
        
    //info panel: has program code and name and university
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    JLabel info1 = new JLabel("Program Title: "+ programTitle);
    JLabel info2 = new JLabel("Program Code: "+ programCode);
    JLabel info3 = new JLabel("University/College: "+ uniColl);
    
    //adding separate information to panel
    infoPanel.add(info1);
    infoPanel.add(info2);
    infoPanel.add(info3);
    
    //north panel: contains info and list
    JPanel northPanel = new JPanel();
    northPanel.setLayout(new BoxLayout (northPanel, BoxLayout.Y_AXIS));
    northPanel.add(Box.createVerticalStrut(10));
    northPanel.add(infoPanel);
    northPanel.add(Box.createVerticalStrut(15));
    northPanel.add(listScrollPane);
    northPanel.add(Box.createVerticalStrut(15));
    
    //panel for buttons below list and appearance modifier
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));  
    buttonPanel.add(removeButton);
    buttonPanel.add(Box.createHorizontalStrut(5));
    buttonPanel.add(new JSeparator(SwingConstants.VERTICAL));
    buttonPanel.add(Box.createHorizontalStrut(5));
    buttonPanel.add(tagField);
    buttonPanel.add(addButton);
    
    //holds 2nd row of buttons (finish & save)
    JPanel button2Panel = new JPanel();
    button2Panel.add(finishButton);
    
    //adding all panels to main
    main.add(northPanel);
    main.add(buttonPanel);
    main.add(button2Panel);
    
    //adding main to frame and making frame visible
    this.add(main);
    this.setVisible(true);
  }
  
  /* RemoveListener
   * removes tags from list and from array
   */
  
  class RemoveListener implements ActionListener{
    public void actionPerformed(ActionEvent e) {
      int index = list.getSelectedIndex();    //index of selected item in list
      int size = listModel.getSize();     //total size of list
      if (size == 0) {
        removeButton.setEnabled(false);     //closes remove button when theres nothing in the list
      }else {
        if (index == size) {
          index--;
        }
        listModel.remove(index);     //removes item from list
        list.setSelectedIndex(index);     //moves selected index
        list.ensureIndexIsVisible(index);    //makes selection visible
        
      }
    }
  }
  
  /* AddListener class
   * adds from JTextField to JList
   */
  
  class AddListener implements ActionListener, DocumentListener{
    boolean enable = false;    //used for controlling butons
    boolean check = false;     //used to check if item is already in JList
    boolean checkAll = false;
    JButton button;            //just a button
    
    public AddListener(JButton button) {
      this.button = button;
    }
    
    public void actionPerformed(ActionEvent e) {
      String newTag = tagField.getText();               //gets tag user entered into textfield
      
      //checks if entered tag is the same as a preexisting one
      for (int i =0; i <listModel.getSize(); i ++){
        if (newTag.equalsIgnoreCase((String)listModel.getElementAt(i))){
          check = true;
        }
      }
      //creating tagfield requirements
      if (tagField.equals("") || listModel.contains(tagField) == true) {
        tagField.requestFocusInWindow();
        tagField.selectAll();
        return;
      }
      
      int index = list.getSelectedIndex();    //get index for selection placement
      
      if (check == false){
        listModel.addElement(tagField.getText());  //adds text on to the end
      }
      
      //adds new tag to array list with all existing tags by checking if it is already there 
      for (int i = 0; i < allTags.size(); i++){     
        if (newTag.equals(allTags.get(i))){
          checkAll = true;
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
    
    //the following methods must be included in this class in order for it to function
    
    @Override
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
  
  /* FinishListener
   * adds contents of JList to ArrayList, closes current frame and opens StartingFrame
   */
  class FinishListener implements ActionListener{
    public void actionPerformed(ActionEvent e) {
      
      //adds JList tags to this programs ArrayList tags
      for (int i = 0; i < listModel.getSize(); i++){
        newTags.add((String)listModel.getElementAt(i));
      }

      ready = true;

      //launches new frame and disposes of old frame
//      new StartingFrame();
      thisFrame.dispose();
    }
  }
  
  //method is necessary for DocumentListener
  public void valueChanged(ListSelectionEvent e) {
    //empty method: DON'T PUT ANYTHING HERE
    //causes errors
  }

  
}
