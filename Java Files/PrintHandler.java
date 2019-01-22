import java.util.*;
import java.lang.*;

public class PrintHandler {

  private PrintHandler(){}
  
  public static int print(ArrayList<String> splitText, int i) {
    int count = 0; // Number of relations being printed
    ArrayList<String> relations = new ArrayList<String>();
    i++;
    while(i < splitText.size() && !splitText.get(i).equals(";")) {
      if(i >= splitText.size()) {
         System.out.println("End of document reached.");
         return i;
      }
      if(splitText.get(i).equals(",")){
         i++;
      }
      if(isKeyword(splitText.get(i))) {
         i--;
         return i; // If keyword is found mid-print, throw away current print job and return to interpret();
      }
      if (isBreakChar(splitText.get(i))) {
        return i; //make sure there are no break chars as attributes
      }
      relations.add(splitText.get(i));
      i++;
      count++;
    }
    System.out.println("Printing " + count + " relations: " + relations);
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
