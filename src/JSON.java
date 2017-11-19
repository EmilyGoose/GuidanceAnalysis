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

    public static HashMap<String, ArrayList<String>> decodeJSON(String JSONString) {

        return null;
    }
}
