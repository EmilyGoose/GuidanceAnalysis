
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.lang.StringBuilder;

public class Main {
  public static void main(String args[]){
    ArrayList<DataEntry> allData = new ArrayList<DataEntry>();
    ArrayList<DataEntry> acceptedData = new ArrayList<DataEntry>();
    
    allData = readNewData();
    
    for(int i = 0; i < allData.size(); i ++){ //getting accepted data
      if(allData.get(i).getStatus() == true){
        acceptedData.add(allData.get(i));
      }
    }
    
   //___________________TEMPORARY CONSOLE ANALYSIS METHOD TESTING UNTIL GUI MADE______________________
    System.out.println("Please enter the type of the dependent variable (\"Tags\" or \"AcceptedStatus\")");
    dependentType = input.nextLine();
    
    if(dependentType.equals("Tags")){
      System.out.println("Please enter the Tags to be searched. Type \"exit\" when done.");
      while(userInput.equals("exit") == false){
        userInput = input.nextLine();
        if(userInput.equals("exit") == false){
          dependent.add(userInput);
        }
      }
    }
    
    userInput = "";
    
    if(dependentType.equals("Tags")){
      independentType = "School";
    }else if(dependentType.equals("AcceptedStatus")){
      independentType = "School";
    }
    
    System.out.println("Please enter the Schools you would like to filter by. Type \"exit\" when done.");
    while(userInput.equals("exit") == false){
      userInput = input.nextLine();
      if(userInput.equals("exit") == false){
        independent.add(userInput);
      }
    }
    
    System.out.println(analysis(independent, independentType, dependent, dependentType, allData, true));
  }
  
  public static ArrayList<DataEntry> readNewData(){
    Scanner input1 = null;
    Scanner input2 = null;
    File uniFile = null;
    File collegeFile = null;
    String row = "";
    boolean status = false;
    String school = "";
    String programName = "";
    String programCode = "";
    ArrayList<String> tags = new ArrayList<String>();    
    ArrayList<DataEntry> allData = new ArrayList<DataEntry>();
    ArrayList<DataEntry> acceptedData = new ArrayList<DataEntry>();
    FileChooser fileChoose = new FileChooser();
    Tagger tagger = new Tagger();
    
    //testing
    tags.add("Engineering");
    tags.add("Math");
    
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
    while(input1.hasNext() == true){
      row = input1.nextLine();
      if(row.indexOf(",") == 0){ //determines accepted status of colleges
        status = false;
        row = row.substring(row.indexOf(",") + 1);
      }else if (row.indexOf("A") ==0){
        status = true;
        row = row.substring(row.indexOf(",") + 1);
      }
      
      if(row.indexOf("\"") == 0){ //saving school, check if there are commas
        row = row.substring(1);
        school = row.substring(0, row.indexOf("\"")); 
        row = row.substring(row.indexOf("\"") + 2);
      }else{
        school = row.substring(0, row.indexOf(",")); 
        row = row.substring(row.indexOf(",") + 1); 
      }
      if(row.indexOf("\"") == 0){ //saving program code, check if there are commas
        row = row.substring(1);
        programCode = row.substring(0, row.indexOf("\"")); 
        row = row.substring(row.indexOf("\"") + 2);
      }else{
        programCode = row.substring(0, row.indexOf(","));
        row = row.substring(row.indexOf(",") + 1);
      }
      if(row.indexOf("\"") == 0){//saving program name, check if there are commas
        row = row.substring(1);
        programName = row.substring(0, row.indexOf("\"")); 
        row = row.substring(row.indexOf("\"") + 2);
      }else{
        programName = row.substring(0, row.indexOf(","));
        row = row.substring(row.indexOf(",") + 1);
      }
      //tags = tagger.getTags(programCode, programName);
      
      allData.add(new DataEntry(status, school, programName, programCode, new ArrayList<String>())); 
    }
    
    //Reading universities file
    input2.nextLine(); //skip header row
    while(input2.hasNext() == true){
      row = input2.nextLine();
      
      if(row.indexOf("\"") == 0){ //saving school, check if there are commas
        school = row.substring(1, row.indexOf("\"")); 
        row = row.substring(row.indexOf(",") + 1);
      }else{
        school = row.substring(0, row.indexOf(",")); 
        row = row.substring(row.indexOf(",") + 1); 
      }
      if(row.indexOf("\"") == 0){ //saving program code, check if there are commas
        programCode = row.substring(1, row.indexOf(" - ")); 
        row = row.substring(row.indexOf(" - ") + 3);
      }else{
        programCode = row.substring(0, row.indexOf(" - "));
        row = row.substring(row.indexOf(" - ") + 3);
      }
      if(row.indexOf("\"") != -1){ //saving program name, check if there are commas
        programName = row.substring(0, row.indexOf("\"")); 
        row = row.substring(row.indexOf("\"") + 1);
      }else{
        programName = row.substring(0, row.indexOf(",")); 
        row = row.substring(row.indexOf(",") + 1);
      }
      row = row.substring(row.indexOf(",") + 1); //disregard entry point
      if(row.indexOf(",") == 0){ //saving status, comma at zero means cell is blank
        status = false;
      }else{
        status = true;
      }
      
      //tags = tagger.getTags(programCode, programName); //add tags
      
      allData.add(new DataEntry(status, school, programName, programCode, tags)); 
    }  
    input1.close();
    input2.close();
    writeToFile(acceptedData); //write data to CSV file
    return allData;
  }
  
  public static ArrayList<Integer> analysis(ArrayList<String> independent, String independentType, ArrayList<String> dependent, String dependentType, ArrayList<DataEntry> data, boolean and){
    ArrayList<Integer> percentages = new ArrayList<Integer>(); 
    int[] countArray = new int[independent.size()];
    ArrayList<DataEntry> matchData = new ArrayList<DataEntry>();
    boolean exit = false;
    int matchCount  = 0;
    
    for(int i = 0; i < countArray.length; i ++){
      countArray[i] = 0;
    }
    
    if(dependentType.equals("Tags") && independentType.equals("School")){ 
      for(int i = 0; i < independent.size(); i ++){ //loop through all the independent variables and add data entries that fit into an ArrayList
        for(int j = 0; j < data.size(); j ++){
          if(data.get(j).getSchool().equals(independent.get(i))){
            matchData.add(data.get(j));
          }
        }
      }
      
      if(and == false){ //or option selected, returns data that has one or more of the tags
        for(int i = 0; i < matchData.size(); i ++){ //loop through the matchData and check for tags that match
          exit = false;
          for(int j = 0; j < dependent.size() && exit == false; j ++){
            for(int k = 0; k < matchData.get(i).getTags().size() && exit == false; k ++){
              if(dependent.get(j).equals(matchData.get(i).getTags().get(k))){
                for(int m = 0; m < independent.size() && exit == false; m ++){
                  if(independent.get(m).equals(matchData.get(i).getSchool())){
                    countArray[m] = countArray[m] + 1;
                    exit = true;
                    matchCount++;
                  }
                }
              }
            }
          }
        }
      }else if(and == true){ //and option selected
        for(int i = 0; i < matchData.size(); i ++){ //loop through the matchData and check for tags that match
          exit = false;
          for(int j = 0; j < dependent.size(); j ++){
            if(matchData.get(i).getTags().indexOf(dependent.get(j)) == -1){
              exit = true;
            }
          }
          if(exit == false){ //if all the tags in the dependent are found in one of the data entries add to the count
            for(int m = 0; m < independent.size(); m ++){
              if(independent.get(m).equals(matchData.get(i).getSchool())){
                countArray[m] = countArray[m] + 1;
                matchCount++;
              }
            }
          }
        }
      }
      
      }else if(dependentType.equals("AcceptedStatus") && independentType.equals("School")){
      for(int i = 0; i < independent.size(); i ++){ //loop through all the independent variables and add data entries that fit into an ArrayList
        for(int j = 0; j < data.size(); j ++){
          if(data.get(j).getSchool().equals(independent.get(i))){
            matchData.add(data.get(j));
          }
        }
      }  
      for(int i = 0; i < matchData.size(); i ++){
        if(matchData.get(i).getStatus() == true){ 
          for(int m = 0; m < independent.size(); m ++){//Remove after adding GUI, pass in acceptedData instead
            if(independent.get(m).equals(matchData.get(i).getSchool())){//add to count corresponding with school if it is accepted
              countArray[m] = countArray[m] + 1;
              matchCount++;
            }
          }
        }
      }
    }
    
    for(int a = 0; a < countArray.length; a ++){ //changing each count to a percentage
      percentages.add((int)(Math.round(countArray[a]*100.0/matchCount)));
    }
    return percentages; //returning percentages
  }
  
  
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
    output.println("Accepted Status,School,Program Name,Program Code,Tags"); //header
    
    for(int i = 0; i < data.size(); i ++){
      outputString.setLength(0);
      outputString.append(data.get(i).getStatus() + ","); //adding status to StringBuilder, add quotes if there are commas
      if(data.get(i).getSchool().indexOf(",") != -1){
        outputString.append("\"" + data.get(i).getSchool() + "\"" + ",");
      }else{
        outputString.append(data.get(i).getSchool() + ",");
      }
      
      if(data.get(i).getProgramName().indexOf(",") != -1){ //adding program name to StringBuilder, add quotes if there are commas
        outputString.append("\"" + data.get(i).getProgramName() + "\"" + ",");
      }else{
        outputString.append(data.get(i).getProgramName() + ",");
      }
      
      if(data.get(i).getProgramCode().indexOf(",") != -1){ //adding program code, add quotes if there are commas
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
      output.println(outputString);
    }
    output.close(); //close PrintWriter
  }
  
  public ArrayList<String> getAllSchools(ArrayList<DataEntry> data){ //returns ArrayList of unique schools
    ArrayList<String> schools = new ArrayList<String>();
    for(int i = 0; i < data.size(); i ++){
      if(schools.indexOf(data.get(i).getSchool()) != -1){
        schools.add(data.get(i).getSchool());
      }
    }
    return schools;
  }
  
  public ArrayList<String> getAllProgramNames(ArrayList<DataEntry> data){ //returns ArrayList of unique program names
    ArrayList<String> programNames = new ArrayList<String>();
    for(int i = 0; i < data.size(); i ++){
      if(programNames.indexOf(data.get(i).getProgramName()) != -1){
        programNames.add(data.get(i).getProgramName());
      }
    }
    return programNames;
  }
}

