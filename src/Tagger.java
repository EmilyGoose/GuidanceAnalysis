/**
 * Tagger.java
 * Creates tags for each DataEntry class
 */

//Import statements
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner; //TODO change to gui

public class Tagger {
	//Matching of course codes to tags
	HashMap<String, ArrayList<String>> tagDatabase;
	//List of possible tags for future implementation
	ArrayList<String> possibleTags;
	
	Tagger() {
		//todo: Read from file for persistence
		tagDatabase = new HashMap<>();
		possibleTags = new ArrayList<>();
	}
	
	public ArrayList<String> GetTags(String programCode, String programName) {
		if (tagDatabase.containsKey(programCode)) {
			//The course code already has tags associated with it! Just return those
			return tagDatabase.get(programCode);
		} else {
			//Prompt the user for tags
			//TODO: Make this a GUI
			//Scanner is temporary, only for testing
			Scanner input = new Scanner(System.in);
			String newTagString = input.nextLine();
			input.close();
			ArrayList<String> newTags = new ArrayList<String>(Arrays.asList(newTagString.split(",")));
			//End temporary testing code
			possibleTags.addAll(newTags);
			tagDatabase.put(programCode, newTags);
			return newTags;
		}
	}
}
