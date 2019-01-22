import java.util.*;
import java.lang.*;

public class RelationHandler {

   private RelationHandler(){}
   
   public static int relation(ArrayList<String> splitText, int i) {
    int count = 0; // Number of attributes in relation
    String relationName = "";
    i++;
    if (i < splitText.size() && !isKeyword(splitText.get(i)) && !isBreakChar(splitText.get(i))) {
      relationName = splitText.get(i); //make sure relation name is valid
    }
    else {
      i--;
      return i;
    }
    i++;
    if(!splitText.get(i).equals("(")) {
      System.out.println("bad syntax; no open paren");
      return i; // Fail on bad syntax
    }
    i++;
    while(i < splitText.size() && !splitText.get(i).equals(")")){
      for(int j = 0; j < 3; j++) {
         if(isKeyword(splitText.get(i))) {
            i--;
            return i; // If keyword is found mid-relation, throw away current relation and return to interpret()
         }
         if (isBreakChar(splitText.get(i))) {
           return i; //make sure there are no break chars as attributes
         }
         if(j == 1 && (!splitText.get(i).toLowerCase().equals("num") && !splitText.get(i).toLowerCase().equals("char"))) {
            System.out.print("Type of attribute must be CHAR or NUM; found " + splitText.get(i) + "\n");
            return i;
         }
         if(j == 2 && !isPositiveInt(splitText.get(i))) {
            System.out.print("Attribute length must be a positive integer; found " + splitText.get(i) + "\n");
            return i;
         }
         i++;
      }
      if(splitText.get(i).equals(",")) {
         i++;
      }
      count++;
    }
    //check for end of command semicolon
    i++;
    if (i < splitText.size() && !splitText.get(i).equals(";")) {
      System.out.println("bad syntax; missing semicolon");
      i--;
      return i;
    }

    System.out.println("Creating " + relationName + " with " + count + " attributes.");
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