
import java.util.ArrayList;

public class DataEntry{
	private boolean acceptedStatus;
	private String school;
	private String programName;
	private String programCode;
	private ArrayList<String> tags;
	
	public DataEntry(boolean acceptedStatus, String school, String programName, String programCode,ArrayList<String> tags){
		this.acceptedStatus = acceptedStatus;	
		this.school = school;
		this.programName = programName;	
		this.programCode = programCode;
		this.tags = tags;	
	}
	
	public boolean getStatus(){
		return this.acceptedStatus;
	}
	
	public String getSchool(){
		return this.school;
	}
	
	public String getProgramName(){
		return this.programName;
	}
	
	public String getProgramCode(){
		return this.programCode;
	}
	
	public ArrayList<String> getTags(){
		return this.tags;
	}
	
	public void setStatus(boolean status){
		this.acceptedStatus = status;		
	}
	
	public void setSchool(String school){
		this.school = school;		
	}
	
	public void setProgramName(String programName){
		this.programName = programName;		
	}
	
	public void setProgramCode(String programCode){
		this.programCode = programCode;
	}
	
	public void setTags(ArrayList<String> tags){
		this.tags = tags;	
	}
}
