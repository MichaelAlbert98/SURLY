import java.util.*;
import java.lang.*;

public class InsertHandler {

  private InsertHandler(){}

  public static int insert(ArrayList<String> splitText, int i) {
    int count = 0; // Number of attrubutes in insertion
    i++;
    String insertionName = splitText.get(i);
    if (i < splitText.size() && !isKeyword(splitText.get(i)) && !isBreakChar(splitText.get(i))) {
      insertionName = splitText.get(i); //make sure insert name is valid
    }
    else {
      i--;
      return i;
    }
    i++;
    while(i < splitText.size() && !splitText.get(i).equals(";")) {
      if(i >= splitText.size()) {
        System.out.println("End of document reached.");
        return i;
      }
      if(isKeyword(splitText.get(i))) {
        i--;
        return i; // If keyword is found mid-insert, throw away current insertion and return to interpret()
         }
      if (isBreakChar(splitText.get(i))) {
        return i; //make sure there are no break chars as tuples
      }
      i++;
      count++;
    }
    if (count == 0) {
      System.out.println("No insert names given.");
      return i;
    }
    System.out.println("Inserting " + count + " attributes to " + insertionName + ".");
    return i;
  }
  
  // Returns true if the given string is a keyword (relation, insert, print: case insensitive)
  private static boolean isKeyword(String s) {
    if(s.toLowerCase().equals("relation") || s.toLowerCase().equals("insert") || s.toLowerCase().equals("print")) {
      System.out.println("Keyword found. Either a break character is missing or you inserted a command word where it shouldn't be.");
      return true;
    }
    return false;
  }

  // Returns true if the given string is a break character
  private static boolean isBreakChar(String s) {
    if(s.equals("(") || s.equals(")") || s.equals("=") || s.equals("!=") || s.equals("<") || s.equals("<=") || s.equals(">")
        || s.equals(">=") || s.equals(";") || s.equals("*") || s.equals("\'") || s.equals(",")) {
      System.out.println("Break char found where it shouldn't be. Check the file for typos.");
      return true;
    }
    return false;
  }

   private static boolean isPositiveInt(String s) {
      try{
         if(Integer.parseInt(s) > 0) {
            return true;
         }
         return false;
         }
         catch(Exception e) {
            return false;
         }
   }
  
}