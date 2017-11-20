/**
 * JSON.java
 * Writes a HashMap of String keys and ArrayList<String> values to a JSON string
 * Misha Larionov
 */

//Import statements
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

//Start the class
public class JSON {
    //It's all static because I don't benefit much from instantiating it
    public static String toJSONString(HashMap<String, ArrayList<String>> map, int indentationLevel) {
        StringBuilder outputJSON = new StringBuilder();
        outputJSON.append(String.join("", Collections.nCopies(indentationLevel, "   "))); //Add some tabs
        outputJSON.append("{\n");

        for (String key : map.keySet()) {
            outputJSON.append(String.join("", Collections.nCopies(indentationLevel + 1, "   ")));
            outputJSON.append("\"" + key + "\" :\n");
            outputJSON.append(toJSONString(map.get(key), indentationLevel + 1));
            outputJSON.append(",\n");
        }
        //Delete trailing comma (Second-to-last element since we have a newline)
        outputJSON.deleteCharAt(outputJSON.length() - 2);

        outputJSON.append(String.join("", Collections.nCopies(indentationLevel, "   ")));
        outputJSON.append("}");
        return outputJSON.toString();
    }

    //When the function is called without indentationLevel, default to 0
    public static String toJSONString(HashMap<String, ArrayList<String>> map) {
        return toJSONString(map, 0);
    }

    //When an ArrayList is passed, do something different
    public static String toJSONString(ArrayList<String> list, int indentationLevel) {
        StringBuilder outputJSON = new StringBuilder();
        outputJSON.append(String.join("", Collections.nCopies(indentationLevel, "   ")));
        outputJSON.append("[\n");

        for (String value : list) {
            outputJSON.append(String.join("", Collections.nCopies(indentationLevel + 1, "   ")));
            outputJSON.append("\"" + value + "\",\n");
        }

        //Delete trailing comma (Second-to-last element since we have a newline)
        outputJSON.deleteCharAt(outputJSON.length() - 2);

        //Output the final bracket and return
        outputJSON.append(String.join("", Collections.nCopies(indentationLevel, "   ")));
        outputJSON.append("]");
        return outputJSON.toString();
    }

    //When an ArrayList is passed without indentationLevel
    public static String toJSONString(ArrayList<String> list) {
        return toJSONString(list, 0);
    }

    //Parses JSON from a String to an ArrayList
    public static ArrayList<String> decodeJSONList(String JSONString) {
        //Removes the first and last character (It's assumed they're both square brackets)
        JSONString = JSONString.substring(1, JSONString.length() - 1);

        //ArrayList for output
        ArrayList<String> outputList = new ArrayList<>();

        //Variables to check whether we're looking at an element
        boolean inElement = false;
        int startIndex = 0;
        for (int i = 0; i < JSONString.length(); i++) {
            if (JSONString.charAt(i) == '"') {
                if (inElement) {
                    inElement = false;
                    //Take the element and append it to the ArrayList
                    outputList.add(JSONString.substring(startIndex, i));
                } else {
                    inElement = true;
                    //The string starts at i+1, since i is the quote itself.
                    startIndex = i + 1;
                }
            }
        }
        return outputList;
    }

    //Parses JSON from a String to a HashMap
    public static HashMap<String, ArrayList<String>> decodeJSON(String JSONString) {
        //Declare the output HashMap
        HashMap<String, ArrayList<String>> outputMap = new HashMap<>();

        //Removes the first and last character (It's assumed they're both square brackets)
        JSONString = JSONString.substring(1, JSONString.length() - 1);

        //Declare variables we're going to use for parsing
        int depth = 0;
        boolean inElement = false;
        //currentElementDelimiter is required so that quotes within a square bracketed list don't trip inElement
        char currentElementDelimiter = '\0';
        int startIndex = 0;
        String currentKey = null;

        for (int i = 0; i < JSONString.length(); i++) {
            //Make sure we're not catching brackets located inside of a string
            if (!inElement) {
                if (JSONString.charAt(i) == '{') {
                    depth += 1;
                } else if (JSONString.charAt(i) == '}') {
                    depth -=1;
                } else if (JSONString.charAt(i) == '[') {
                    startIndex = i;
                    inElement = true;
                    currentElementDelimiter = JSONString.charAt(i);
                    startIndex = i;
                } else if (JSONString.charAt(i) == '"') {
                    inElement = true;
                    currentElementDelimiter = JSONString.charAt(i);
                    startIndex = i + 1;
                }
            } else if (JSONString.charAt(i) == ']' && currentElementDelimiter == '[') {
                inElement = false;
                ArrayList<String> newList = decodeJSONList(JSONString.substring(startIndex, i + 1));
                //The only list we should encounter at depth 0 is the possibleTags list
                if (depth == 0) {
                    outputMap.put("possibleTags", newList);
                } else {
                    outputMap.put(currentKey, newList);
                    currentKey = null;
                }
            } else if (JSONString.charAt(i) == '"' && currentElementDelimiter == '"') {
                inElement = false;
                //There's no reason this should be false but might as well check
                if (currentKey == null) {
                    currentKey = JSONString.substring(startIndex, i);
                }
            }
        }

        return outputMap;
    }
}
