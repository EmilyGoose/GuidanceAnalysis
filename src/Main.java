import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
  public static void main(String args[]){
    System.exit(0);
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
    
    try{
      uniFile = new File("uniFile.csv");
      collegeFile = new File("collegeFile.csv");
      input1 = new Scanner(collegeFile);
      input2 = new Scanner(uniFile);
    }catch(FileNotFoundException e){
      System.out.println("File not found");
    }
    
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
      
      //tags = getTags(programCode, programName);
      
      allData.add(new DataEntry(status, school, programName, programCode, tags)); 
      if(status = true){
        acceptedData.add(new DataEntry(status, school, programName, programCode, tags));
      }
    }
    
    
    input2.nextLine(); //skip header row;
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
      
      //tags = getTags(programCode, programName); //add tags
      
      allData.add(new DataEntry(status, school, programName, programCode, tags)); 
      if(status = true){
        acceptedData.add(new DataEntry(status, school, programName, programCode, tags));
      }
    }
    
    /*for(int i = 0; i < allData.size(); i++){ //Testing
      System.out.println(allData.get(i).getStatus());     
    }
    ArrayList<String> temp independent = new ArrayList<String>();
    System.out.println(analysis(
                                
                                
    input1.close();
    input2.close();
    */
  }
  
  
  public static ArrayList<Integer> analysis(ArrayList<String> independent, ArrayList<String> dependent, String dependentType, ArrayList<DataEntry> data){
    ArrayList<Integer> percentages = new ArrayList<Integer>(); 
    int[] countArray = new int[independent.size()];
    ArrayList<DataEntry> matchData = new ArrayList<DataEntry>();

    
    if(dependentType == "Tags"){
      for(int i = 0; i < independent.size(); i ++){
        for(int j = 0; j < data.size(); j ++){
          if(data.get(j).getSchool() == independent.get(i)){
            matchData.add(data.get(j));
          }
        }
      }
      
      for(int i = 0; i < matchData.size(); i ++){
        for(int j = 0; j < independent.size(); j ++){
          for(int k = 0; k < matchData.get(i).getTags().size(); k ++){
            if(independent.get(j) == matchData.get(i).getTags().get(k)){
              countArray[j]++;
            }
          }
        }
      }

    }else if(dependentType == "School"){
      for(int i = 0; i < independent.size(); i ++){
        for(int j = 0; j < data.size(); j ++){
          for(int k = 0; k < matchData.get(i).getTags().size(); k ++){
            if(independent.get(j) == matchData.get(i).getTags().get(k)){
              matchData.add(data.get(j));
              break;
            }
          }
        }
      }
      
      for(int i = 0; i < matchData.size(); i ++){
        for(int j = 0; j < independent.size(); j ++){
          if(independent.get(j) == matchData.get(i).getSchool()){
            countArray[j]++;
          }
        }
      }
    }
  
    for(int a = 0; a < countArray.length; a ++){
      countArray[a] = (countArray[a]/matchData.size())*100;
    }
    
    for(int b = 0; b < countArray.length; b ++){
      percentages.add(countArray[b]);
    }
    
    return percentages;
  }
}
