
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
  public static void main(String args[]){
    Scanner input1 = null;
    Scanner input2 = null;
    File uniFile = null;
    File collegeFile = null;
    String row = "";
    boolean status = false;
    String school = "";
    String programName = "";
    String programCode = "";
    ArrayList<String> tags = null;
    
    
    ArrayList<DataEntry> data = new ArrayList<DataEntry>();
    
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
      
      if(row.indexOf("\"") == 0){ //saving school
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
      
      if(row.indexOf("\"") == 0){//saving program name
    	  row = row.substring(1);
    	  programName = row.substring(0, row.indexOf("\"")); 
    	  row = row.substring(row.indexOf("\"") + 2);
      }else{
    	  programName = row.substring(0, row.indexOf(","));
    	  row = row.substring(row.indexOf(",") + 1);
      }
      
      // <><><><><><><><>< Tags add here<><><><><><><<>><><><><><><><><><><><><><><>
      
      data.add(new DataEntry(status, school, programName, programCode, tags)); 
    }
    
    
    input2.nextLine(); //skip header row;
    while(input2.hasNext() == true){
      row = input2.nextLine();
      
      if(row.indexOf("\"") == 0){ //saving school
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
      
      if(row.indexOf("\"") != -1){ //saving program name
    	  programName = row.substring(0, row.indexOf("\"")); 
    	  row = row.substring(row.indexOf("\"") + 1);
      }else{
    	  programName = row.substring(0, row.indexOf(",")); 
    	  row = row.substring(row.indexOf(",") + 1);
      }
      
      row = row.substring(row.indexOf(",") + 1); //disregard entry point
      
      if(row.indexOf(",") == 0){ //saving status
        status = false;
      }else{
        status = true;
      }
      // <><><><><><><><>< Tags add here<><><><><><><<>><><><><><><><><><><><><><><>
      
      data.add(new DataEntry(status, school, programName, programCode, tags)); 
    }
    
    /*for(int i = 0; i < data.size(); i++){ //Testing
     System.out.println(data.get(i).getStatus());     
     */
    
    input1.close();
    input2.close();
  }
  
  public static void analysis(ArrayList<String> filters, ArrayList<DataEntry> data){
	  int counter = 0;
	  ArrayList<DataEntry> match = new ArrayList<DataEntry>();
	  for(int x = 0; x < data.size(); x ++){
		  match.set(x, data.get(x));
	  }
	  for(int i = 0; i < filters.size(); i ++){ //taking DataEntries off the list if they do not fit the filters
		  for(int j = 0; j < data.size(); j ++){
			  if(data.get(j).getTags().indexOf(filters.get(i)) == -1){
				  match.set(j, null);
			  }
		  }
	  }
	  while(match.indexOf(null) != -1){
		  if(match.get(counter) == null){
			  match.remove(counter);
			  counter = 0;
		  }else{
			  counter++;
		  }
	  }
  }
}
