/**
 * Tagger.java
 * Creates tags for each DataEntry class
 */

//Import statements
import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner; //TODO change to gui

public class Tagger {
	//Matching of course codes to tags
	HashMap<String, ArrayList<String>> tagDatabase;
	//List of possible tags for future implementation
	private ArrayList<String> possibleTags;
	private Scanner inputScanner;
	
	Tagger() {
        inputScanner = new Scanner(System.in);
	    try {
            BufferedReader input = new BufferedReader(new FileReader("res/tags.json"));
            StringBuilder JSONString = new StringBuilder();
            String nextLine = input.readLine();
            while (nextLine != null) {
                JSONString.append(nextLine);
                nextLine = input.readLine();
            }

            tagDatabase = JSON.decodeJSON(JSONString.toString());
            possibleTags = tagDatabase.remove("possibleTags");
        } catch (FileNotFoundException E) {
	        //This exception happens if the file doesn't exist
            tagDatabase = new HashMap<>();
            possibleTags = new ArrayList<>();
        } catch (IOException E) {
	        System.out.println("Exception while fetching tags: " + E);
        }

        System.out.println(tagDatabase);
	}

	private boolean saveTags() {
	    //Write all the data to JSON
	    StringBuilder JSONString = new StringBuilder("[\n");
	    JSONString.append(JSON.toJSONString(this.tagDatabase, 1));
	    JSONString.append(",\n");
	    JSONString.append(JSON.toJSONString(this.possibleTags, 1));
	    JSONString.append("\n]");
	    try {
            BufferedWriter output = new BufferedWriter(new FileWriter("res/tags.json"));
            output.write(JSONString.toString());
            output.close();
        } catch (IOException E) {
	        System.out.println("Exception while saving tags: " + E);
	        return false;
        }

        return true;
    }
	
	public ArrayList<String> getTags(String programCode, String programName) {
		if (tagDatabase.containsKey(programCode)) {
			//The course code already has tags associated with it! Just return those
			return tagDatabase.get(programCode);
		} else {
			//Prompt the user for tags
			//Scanner is temporary, only for testing

            System.out.println("No tags found, enter manual");
            AddTags tags = new AddTags(programCode, programName, "Post-secondary", new ArrayList<String>(), possibleTags);
            while (!tags.ready);
			ArrayList<String> newTags = tags.newTags;
			//End temporary testing code

            //Add new elements to possibleTags while keeping out duplicates
            for (String tag : newTags) {
                boolean unique = true;
                for (String oldTag : possibleTags) {
                    if (oldTag.equals(tag)) {
                        unique = false;
                    }
                }
                if (unique) {
                    possibleTags.add(tag);
                }
            }
			tagDatabase.put(programCode, newTags);
			this.saveTags();
			return newTags;
		}
	}

	public ArrayList<String> editTags(String programCode, String programName) {
		//TODO: Call the GUI
		return this.getTags(programCode, programName);
	}
}
