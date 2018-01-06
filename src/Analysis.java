
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.lang.StringBuilder;
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

public class Analysis {
  
  ArrayList<DataEntry> allData = new ArrayList<DataEntry>();
  ArrayList<DataEntry> acceptedData = new ArrayList<DataEntry>();
  Scanner input1 = null;
  Scanner input2 = null;
  Tagger tagger = new Tagger();
  int year;
  
  /**
   * readNewData
   * Takes in year (selected by GUI) and reads from files selected by user
   * @param year - int containing year
   * @return ArrayList of DataEntries (only accepted data)
   */
  public void readNewData(int newYear){
    File uniFile;
    File collegeFile;   
    FileChooser fileChoose = new FileChooser();
    year = newYear;
    try{ //load files and create scanners, return error message if there is an error
      uniFile = fileChoose.getFile("University");
      collegeFile = fileChoose.getFile("College");
      input1 = new Scanner(collegeFile);
      input1.nextLine(); //skip header row
      input2 = new Scanner(uniFile);
      input2.nextLine(); //skip header row
    }catch(FileNotFoundException e){
      System.out.println("File not found");
    }
    readCollegeData(); //read the college file's data
  }
  
  //reading college csv data and organizing into sections
  public void readCollegeData(){
    String row;
    boolean status = false;
    String school;
    String programName;
    String programCode;
    String programCell;
    ArrayList<String> tags = new ArrayList<String>(); 
    row = input1.nextLine();
    ArrayList<String> rowList = new ArrayList<>();
    
    //Iterates through the row and makes sure we're only splitting on commas not part of quotes
    boolean inElement = false;
    int startIndex = 0;
    for (int i = 0; i < row.length(); i++) {
      char c = row.charAt(i);
      //Split on this comma
      if (!inElement && c == ',') {
        rowList.add(row.substring(startIndex, i));
        startIndex = i + 1;
        //Quotes start here, ignore everything until the next quote
      } else if (!inElement && c == '"') {
        inElement = true;
        //Quotes end here, we can start reading commas again
      } else if (inElement && c == '"') {
        inElement = false;
      }
    }
    
    status = rowList.get(0).equals("A");
    school = rowList.get(1);
    programCode = rowList.get(2);
    programName = rowList.get(3);
    if (tagger.tagDatabase.containsKey(programCode)) {
      //The course code already has tags associated with it! Just return those
      tags = tagger.tagDatabase.get(programCode);
      String combinedTraits = status + ", " + school + ", " + programCode + ", " + programName + ", " + year;
      allData.add(new DataEntry(status, school, programName, programCode, tags, combinedTraits, year)); //add to the list of entries
      if(input1.hasNext()){ //keep reading if there is still data
        readCollegeData();
      }else{
        readUniData(); // no more entries in college file, start to read uni data
      }
    } else {
      new AddTags(year, status, programCode, programName, school, new ArrayList<String>(), tagger.possibleTags, tagger);
    }
  }
  
  
  //Reading universities file
  public void readUniData(){
    String row;
    boolean status = false;
    String school;
    String programName;
    String programCode;
    String programCell;
    ArrayList<String> tags = new ArrayList<String>(); 
    row = input2.nextLine();
    ArrayList<String> rowList = new ArrayList<>();
    
    //Iterates through the row and makes sure we're only splitting on commas not part of quotes
    boolean inElement = false;
    int startIndex = 0;
    for (int i = 0; i < row.length(); i++) {
      char c = row.charAt(i);
      //Split on this comma
      if (!inElement && c == ',') {
        rowList.add(row.substring(startIndex, i));
        startIndex = i + 1;
        //Quotes start here, ignore everything until the next quote
      } else if (!inElement && c == '"') {
        inElement = true;
        //Quotes end here, we can start reading commas again
      } else if (inElement && c == '"') {
        inElement = false;
      }
    }
    
    school = rowList.get(0);
    programCell = rowList.get(1);
    status = rowList.get(3).length() > 0;
    programCode = programCell.substring(0, programCell.indexOf(" -")); //programCell contains both the code and name, seperated by a dash
    programCell.substring(programCell.indexOf(" - ")); //seperate and store in respective variables
    programName = programCell;
    if (tagger.tagDatabase.containsKey(programCode)) {
      //The course code already has tags associated with it! Just return those
      tags = tagger.tagDatabase.get(programCode);
      String combinedTraits = status + ", " + school + ", " + programCode + ", " + programName + ", " + year;
      allData.add(new DataEntry(status, school, programName, programCode, tags, combinedTraits, year)); //add to the list of entries
      if(input2.hasNext()){ //keep reading if there is still data
        readUniData();
      }else{
        input1.close(); //close scanners
        input2.close();
        for(DataEntry entry : allData){ //sort out only the accepted data
          if(entry.getStatus()){
            acceptedData.add(entry);
          }
        }
        writeToFile(acceptedData); //write data to CSV file
      }
    } else {
      new AddTags(year, status, programCode, programName, school, new ArrayList<String>(), tagger.possibleTags, tagger);
    }
  }
  
  
  public ArrayList<DataEntry> getData(){
    return acceptedData;
  }
  
  /**
   * writeToFile
   * Takes in data to be written to file and writes it as CSV
   * @param data - ArrayList of DataEntries to be written
   * @return none
   */
  public static void writeToFile(ArrayList<DataEntry> data){
    File storage = null; 
    PrintWriter output = null;
    try{ //load files and create PrintWriter
      storage = new File("storage.csv");
      output = new PrintWriter(storage);
    }catch(FileNotFoundException e){
      System.out.println("File not found");
    }
    StringBuilder outputString = new StringBuilder();
    output.println("Accepted Status,School,Program Name,Program Code,Tags,Year"); //write header
    
    for(int i = 0; i < data.size(); i ++){
      outputString.setLength(0);
      outputString.append(data.get(i).getStatus() + ","); //adding status to StringBuilder, add quotes if there are commas
      if(data.get(i).getSchool().contains(",")){
        outputString.append("\"" + data.get(i).getSchool() + "\"" + ",");
      }else{
        outputString.append(data.get(i).getSchool() + ",");
      }
      
      if(data.get(i).getProgramName().contains(",")){ //adding program name to StringBuilder, add quotes if there are commas
        outputString.append("\"" + data.get(i).getProgramName() + "\"" + ",");
      }else{
        outputString.append(data.get(i).getProgramName() + ",");
      }
      
      if(data.get(i).getProgramCode().contains(",")){ //adding program code, add quotes if there are commas
        outputString.append("\"" + data.get(i).getProgramCode() + "\"" + ",");
      }else{
        outputString.append(data.get(i).getProgramCode() + ",");
      }
      
      if(data.get(i).getTags().size() > 1){ //adding tags to StringBuilder if there are more than one
        outputString.append("\"");
        for(int j = 0; j < data.get(i).getTags().size(); j ++){
          if(j != data.get(i).getTags().size() - 1){ //adds commas after every tag except the last
            outputString.append(data.get(i).getTags().get(j) + ",");
          }else{
            outputString.append(data.get(i).getTags().get(j));
          }
        }
        outputString.append("\"");
      }else if(data.get(i).getTags().size() == 1){ //add tag to StringBuilder if there is a single one
        outputString.append(data.get(i).getTags().get(0));
      }
      outputString.append("," + data.get(i).getYear()); //adding the year
      output.println(outputString);
    }
    output.close(); //close PrintWriter
  }
  
  /**
   * readExistingData
   * reads from storage file and puts data into DataEntries
   * @return ArrayList of DataEntries read from the file
   */
  public static ArrayList<DataEntry> readExistingData(){
    Scanner input = null;
    boolean status = false;
    String school;
    String programName;
    String programCode;
    String combinedTraits; //String holding all the traits (all the programs, tags, school etc)
    String row;
    int year;
    ArrayList<String> tags = new ArrayList<String>();    
    ArrayList<DataEntry> data = new ArrayList<DataEntry>();
    String tagString = "";
    try{
      input = new Scanner(new File("storage.csv"));
    }catch(FileNotFoundException e){
      System.out.println("storage file not found");
    }
    
    input.nextLine(); //skip header row;
    while(input.hasNext()){
      row = input.nextLine();
      ArrayList<String> rowList = new ArrayList<>();
      
      //Iterates through the row and makes sure we're only splitting on commas not part of quotes
      boolean inElement = false;
      boolean exit = false;
      int startIndex = 0;
      for (int i = 0; i < row.length() && !exit; i++) {
        char c = row.charAt(i);
        //Split on this comma
        if (!inElement && c == ',') {
          rowList.add(row.substring(startIndex, i)); //add read element into arraylist
          startIndex = i + 1;
          //Quotes start here, ignore everything until the next quote
        } else if (!inElement && c == '"') {
          inElement = true;
          //Quotes end here, we can start reading commas again
        } else if (inElement && c == '"') {
          inElement = false;
        } else if (row.substring(i).indexOf(",") == -1){ //no more commas, last column
          rowList.add(row.substring(startIndex)); //add year
          exit = true;
        }
      }
      
      status = rowList.get(0).equals("true");
      school = rowList.get(1);
      programName = rowList.get(2);
      programCode = rowList.get(3);
      tagString = rowList.get(4);
      if(tagString.indexOf("\"") != -1){ //there are quotation marks, take them out
        tagString = tagString.substring(1, tagString.length() - 1);
      }
      year = Integer.parseInt(rowList.get(5));
      combinedTraits = status + ", " + school + ", " + programCode + ", " + programName + ", " + tagString + year;
      
      
      while(tagString.indexOf(",") != -1){ //adding all tags to combinedTraits
        tags.add(tagString.substring(0,tagString.indexOf(",")));
        tagString = tagString.substring(tagString.indexOf(",") + 1);                 
      }
      tags.add(tagString); //add the last tag (doesn't have a comma after)
      
      data.add(new DataEntry(status, school, programName, programCode, tags, combinedTraits, year)); 
    }
    return data;
  }
  
  /**
   * analysis
   * Takes in filters (independent and dependent) along with DataEntries to be analysed and returns the counts of occurences for each independent
   * @param independent ArrayList<String> of the independent variables
   * @param dependent ArrayList<String> of the dependent variables
   * @param data ArrayList<DataEntry> of the data being filtered
   * @return ArrayList<Integer> of the number of occurences for each independent variable
   */
  public static ArrayList<Integer> analysis(ArrayList<String> independent, ArrayList<String> dependent, ArrayList<DataEntry> data){
    ArrayList<Integer> count = new ArrayList<Integer>(); 
    ArrayList<DataEntry> matchData = new ArrayList<DataEntry>();
    
    for(int p = 0; p < independent.size(); p ++){ 
      count.add(0);
    }
    
    for(int i = 0; i < data.size(); i ++){
      for(int j = 0; j < independent.size(); j ++){ //add data that fits the independent variable to new arraylist
        if(data.get(i).getCombinedTraits().indexOf(independent.get(j)) != -1){
          matchData.add(data.get(i));
          System.out.println(data.get(i).getCombinedTraits());
        }
      }
    }
    //compare data that matches independent variable to see if it matches the dependent variable
    for(int a = 0; a < matchData.size(); a ++){
      for(int b = 0; b < dependent.size(); b ++){ //go through each dependent, DataEntry to null if it doesn't match a dependent
        if(matchData.get(a).getCombinedTraits().indexOf(dependent.get(b)) == -1){ 
          matchData.set(a, null);
        }
      }
      if(matchData.get(a) != null){ //meets all dependent variables, add to count
        for(int c = 0; c < independent.size(); c ++){
          if(matchData.get(a).getCombinedTraits().indexOf(independent.get(c)) != -1){ //add to count in corresponding arraylist index
            count.set(c, count.get(c) + 1);
          }
        }
      }
    }
    return count;
  }
  
  /**
   * getPercentages
   * used in conjunction with the analysis method, returns percent versions that can then be graphed
   * @param count ArrayList<Integer> of the occurences from Analysis
   * @return ArrayList<Integer> of the percentage versions of the parameter ArrayList
   */
  public static ArrayList<Integer> getPercentages(ArrayList<Integer> count){
    int totalEntries = 0;
    ArrayList<Integer> percentages = new ArrayList<Integer>();
    
    for(int i = 0; i < count.size(); i ++){
      totalEntries += count.get(i);
    }
    
    for(int j = 0; j < count.size(); j ++){
      percentages.add((int)(count.get(j)*100.0/(double)totalEntries));
    }
    return percentages;
  }
  
  /**
   * getAllSchools
   * Finds all unique schools
   * @param data ArrayList<DataEntry> to searh for all unique schools
   * @return ArrayList<String> with each unique school name
   */
  public static ArrayList<String> getAllSchools(ArrayList<DataEntry> data){ //returns ArrayList of unique schools
    ArrayList<String> schools = new ArrayList<String>();
    for(int i = 0; i < data.size(); i ++){
      if(schools.indexOf(data.get(i).getSchool()) == -1){
        schools.add(data.get(i).getSchool()); //add if school isn't in the schools ArrayList
      }
    }
    return schools;
  }
  
  /**
   * getAllYears
   * Finds all unique years
   * @param data ArrayList<DataEntry> to searh for all unique years
   * @return ArrayList<Integer> with each unique year
   */
  public static ArrayList<Integer> getAllYears(ArrayList<DataEntry> data){ //returns ArrayList of unique years
    ArrayList<Integer> years = new ArrayList<Integer>();
    for(int i = 0; i < data.size(); i ++){
      if(years.indexOf(data.get(i).getYear()) == -1){
        years.add(data.get(i).getYear()); //add if school isn't in the schools ArrayList
      }
    }
    return years;
  }
  
  /**
   * getAllTags
   * Finds all unique tags
   * @param data ArrayList<DataEntry> to searh for all unique tags
   * @return ArrayList<String> with each unique tag
   */
  public static ArrayList<String> getAllTags(ArrayList<DataEntry> data){ //returns ArrayList of unique tags
    ArrayList<String> tags = new ArrayList<String>();
    for(int i = 0; i < data.size(); i ++){ //loop through data and each DataEntry's tags
      for(int j = 0; j < data.get(i).getTags().size(); j ++){
        if(tags.indexOf(data.get(i).getTags().get(j)) == -1){
          tags.add(data.get(i).getTags().get(j)); //add if specific tag isn't in the tags ArrayList
        }
      }
    }
    return tags;
  }
  
  public class AddTags extends JFrame implements ListSelectionListener {
    
    private ArrayList<String> programTags;
    public ArrayList<String> allTags = new ArrayList<String>();
    public ArrayList<String> newTags = new ArrayList<String>();
    public boolean ready = false;
    private Tagger tagger;
    private Analysis analysis;
    
    private JFrame thisFrame;
    private JList list;
    private DefaultListModel listModel;
    private JButton removeButton;
    private JTextField tagField;
    private String combinedTraits;
    private int year;
    private boolean status;
    private String programCode;
    private String programName;
    private String school;
    
    
    AddTags(int year, boolean status, String programCode, String programName, String school, ArrayList<String> programTags, ArrayList<String> allTags, Tagger tagger){
      super("Edit Tags");
      
      //creating frame and adding features
      this.thisFrame = this;
      this.setSize(600,350);
      this.setLocationRelativeTo(null); //start the frame in the center of the screen;  
      this.setResizable (false);
      
      //initializing all the variables
      this.year = year;
      this.status = status;
      this.programCode = programCode;
      this.programName = programName;
      this.programTags = programTags;
      this.allTags = allTags;
      this.school = school;
      this.tagger = tagger;
      
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
      
      //finish button
      JButton finishButton = new JButton("Save and Finish");
      finishButton.addActionListener(new FinishListener());
      
      //info panel: has program code and name and university
      JPanel infoPanel = new JPanel();
      infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
      JLabel info1 = new JLabel("Program Title: "+ programName);
      JLabel info2 = new JLabel("Program Code: "+ programCode);
      JLabel info3 = new JLabel("University/College: "+ school);
      
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
        
        for (String tag : newTags) { //add any unique new tags to the database
          boolean unique = true;
          for (String oldTag : tagger.possibleTags) {
            if (oldTag.equals(tag)) {
              unique = false;
            }
          }
          if (unique) {
            tagger.possibleTags.add(tag);
          }
        }
        tagger.tagDatabase.put(programCode, newTags);
        tagger.saveTags(); //save these tags
        combinedTraits = status + ", " + school + ", " + programCode + ", " + programName + ", " + year;
        for(int i = 0; i < newTags.size(); i ++){ //adding all tags to combinedTraits
          combinedTraits += ", " + newTags.get(i);
        }
        allData.add(new DataEntry(status, school, programName, programCode, newTags, combinedTraits, year)); //add to the list of entries
        
        if(input1.hasNext()){
          readCollegeData(); //keep reading college data if there is any left
        }else if(input2.hasNext()){
          readUniData(); //keep reading uni data if there is any left
        }else{ //done reading all data
          input1.close(); //close scanners
          input2.close();
          for(DataEntry entry : allData){ //sort out only the accepted data
            if(entry.getStatus()){
              acceptedData.add(entry);
            }
          }
          writeToFile(acceptedData); //write data to CSV file
        } 
        
        
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
}
