
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.lang.StringBuilder;

public class Analysis {
  
  /**
   * readNewData
   * Takes in year (selected by GUI) and reads from files selected by user
   * @param integer containing year
   * @return ArrayList of DataEntries (only accepted data)
   */
  public static ArrayList<DataEntry> readNewData(int year){
    Scanner input1 = null;
    Scanner input2 = null;
    File uniFile;
    File collegeFile;
    String row;
    boolean status = false;
    String school;
    String programName;
    String programCode;
    String combinedTraits; //String holding all the traits (all the programs, tags, school etc)
    ArrayList<String> tags = new ArrayList<String>();    
    ArrayList<DataEntry> allData = new ArrayList<DataEntry>();
    ArrayList<DataEntry> acceptedData = new ArrayList<DataEntry>();
    FileChooser fileChoose = new FileChooser();
    Tagger tagger = new Tagger();
    
    try{ //load files and create scanners, return error message if there is an error
      uniFile = fileChoose.getFile("University");
      collegeFile = fileChoose.getFile("College");
      input1 = new Scanner(collegeFile);
      input2 = new Scanner(uniFile);
    }catch(FileNotFoundException e){
      System.out.println("File not found");
    }
    
    //reading college file
    input1.nextLine(); //skip header row;
    while(input1.hasNext()){
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
      //tags = tagger.getTags(programCode, programName);
      combinedTraits = status + ", " + school + ", " + programCode + ", " + programName + ", " + year; 
      
      for(int i = 0; i < tags.size(); i ++){ //adding all tags to combinedTraits
        combinedTraits += ", " + tags.get(i);
      }
      allData.add(new DataEntry(status, school, programName, programCode, tags, combinedTraits, year));
    }
    
    //Reading universities file
    input2.nextLine(); //skip header row
    while(input2.hasNext()){
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
      programName = rowList.get(1);
      status = rowList.get(3).length() > 0;
      programCode = "";
      //tags = tagger.getTags(programCode, programName); //add tags
      combinedTraits = status + ", " + school + ", " + programCode + ", " + programName + ", " + year;
      
      for(int i = 0; i < tags.size(); i ++){ //adding all tags to combinedTraits
        combinedTraits += ", " + tags.get(i);
      }     
      allData.add(new DataEntry(status, school, programName, programCode, tags, combinedTraits, year)); 
    }  
    input1.close();
    input2.close();
    
    for(DataEntry entry : allData){
      if(entry.getStatus()){
        acceptedData.add(entry);
      }
    }
    writeToFile(acceptedData); //write data to CSV file
    return acceptedData;
  }  
  
  /**
   * writeToFile
   * Takes in data to be written to file and writes it as CSV
   * @param ArrayList of DataEntries to be written
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
   * @param none
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
      
      status = rowList.get(0).equals("true");
      school = rowList.get(1);
      programName = rowList.get(2);
      programCode = rowList.get(3);
      tagString = rowList.get(4);
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
   * @param ArrayList<String> of the independent variables, ArrayList<String> of the dependent variables, ArrayList<DataEntry> of the data being filtered
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
        }
      }
    }
    //compare data that matches independent variable to see if it matches the dependent variable
    for(int a = 0; a < matchData.size(); a ++){
      for(int b = 0; b < dependent.size(); b ++){ 
        if(matchData.get(a).getCombinedTraits().indexOf(dependent.get(b)) != -1){
          for(int c = 0; c < independent.size(); c ++){
            if(matchData.get(a).getCombinedTraits().indexOf(independent.get(c)) != -1){ //add to count in corresponding arraylist index
              count.set(c, count.get(c) + 1);
            }
          }
        }
      }
    }
    return count;
  }
  
  /**
   * getPercentages
   * used in conjunction with the analysis method, returns percent versions that can then be graphed
   * @param ArrayList<Integer> of the occurences from Analysis
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
   * @param ArrayList<DataEntry> to searh for all unique schools
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
   * @param ArrayList<DataEntry> to searh for all unique years
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
   * @param ArrayList<DataEntry> to searh for all unique tags
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
}

