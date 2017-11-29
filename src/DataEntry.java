
import java.util.ArrayList;

public class DataEntry{
  private boolean acceptedStatus;
  private String school;
  private String programName;
  private String programCode;
  private String combinedTraits;
  private ArrayList<String> tags;
  private int year;
  
  //constructor for DataEntry
  public DataEntry(boolean acceptedStatus, String school, String programName, String programCode, ArrayList<String> tags, String combinedTraits, int year){
    this.acceptedStatus = acceptedStatus; 
    this.school = school;
    this.programName = programName; 
    this.programCode = programCode;
    this.tags = tags; 
    this.combinedTraits = combinedTraits;
    this.year = year;
  }
  
  /**
   * getStatus
   * getter for status
   * @param none
   * @return boolean acceptedStatus of this entry (true if accepted, false if not)
   */
  public boolean getStatus(){
    return this.acceptedStatus;
  }
  
  /**
   * getSchool
   * getter for school
   * @param none
   * @return string of the school name of this entry
   */
  public String getSchool(){
    return this.school;
  }
  
  /**
   * getSCombinedTraits
   * getter for combinedTraits
   * @param none
   * @return string with all the traits (school, year etc)
   */
  public String getCombinedTraits(){
    return this.combinedTraits; 
  }
  
  /**
   * getProgramName
   * getter for programName
   * @param none
   * @return string of the program name of this entry
   */
  public String getProgramName(){
    return this.programName;
  }
  
  /**
   * getProgramCode
   * getter for programCode
   * @param none
   * @return string of the program of this entry
   */
  public String getProgramCode(){
    return this.programCode;
  }
  
  /**
   * getTags
   * getter for tags
   * @param none
   * @return ArrayList<String> of the tags of this entry
   */
  public ArrayList<String> getTags(){
    return this.tags;
  }
  
  /**
   * getYear
   * getter for year
   * @param none
   * @return int of the year of this entry
   */
  public int getYear(){
    return this.year;
  }
  
  /**
   * setStatus
   * setter for status
   * @param boolean representing acceptedStatus (true for accepted, false if not)
   * @return none
   */
  public void setStatus(boolean status){
    this.acceptedStatus = status;  
  }
  
  /**
   * setSchool
   * setter for status
   * @param string of the school
   * @return none
   */
  public void setSchool(String school){
    this.school = school;  
  }
  
  /**
   * setCombinedTraits
   * setter for combinedTraits
   * @param string of the combinedTraits
   * @return none
   */
  public void setCombinedTraits(String combinedTraits){
    this.combinedTraits = combinedTraits;
  }
  
  /**
   * setProgramName
   * setter for programName
   * @param string of the programName
   * @return none
   */
  public void setProgramName(String programName){
    this.programName = programName;  
  }
  
  /**
   * setProgramCode
   * setter for programCode
   * @param string of the programCode
   * @return none
   */
  public void setProgramCode(String programCode){
    this.programCode = programCode;
  }
  
  /**
   * setTags
   * setter for tags
   * @param ArrayList<String> of all the tags
   * @return none
   */
  public void setTags(ArrayList<String> tags){
    this.tags = tags; 
  }
  
  /**
   * setYear
   * setter for year
   * @param int of the year
   * @return none
   */
  public void setYear(int year){
   this.year = year; 
  }
}
