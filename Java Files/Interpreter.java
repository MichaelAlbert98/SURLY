//Interpreter.java - takes an ArrayList of tokens, iterates through them, and
//prints to the terminal based on the commands found.
//Created by Michael Albert and Jacob Coffland
//Created January 09, 2019
//Revised January 18, 2019

import java.util.*;
import java.lang.*;

public class Interpreter {

  //attributes
  private static int i;

  //constructor
  private Interpreter() {
  }

  // Iterate through the list of tokens, looking for keywords and calling the appropriate functions
  public static void interpret(ArrayList<String> splitText) {
    i = 0;
    for (; i < splitText.size(); i++) {
      String token = splitText.get(i).trim();
      if (token.toLowerCase().equals("relation")) {
         i = RelationHandler.relation(splitText, i);
      }
      else if (token.toLowerCase().equals("insert")) {
         i = InsertHandler.insert(splitText, i);
      }
      else if (token.toLowerCase().equals("print")) {
         i = PrintHandler.print(splitText, i);
      }
    }
  }
  
  // Returns true if the given string is a keyword (relation, insert, print: case insensitive)
  public static boolean isKeyword(String s) {
    if(s.toLowerCase().equals("relation") || s.toLowerCase().equals("insert") || s.toLowerCase().equals("print")) {
      System.out.println("Keyword found. Either a break character is missing or you inserted a command word where it shouldn't be.");
      return true;
    }
    return false;
  }

  // Returns true if the given string is a break character
  public static boolean isBreakChar(String s) {
    if(s.equals("(") || s.equals(")") || s.equals("=") || s.equals("!=") || s.equals("<") || s.equals("<=") || s.equals(">")
        || s.equals(">=") || s.equals(";") || s.equals("*") || s.equals("\'") || s.equals(",")) {
      System.out.println("Break char found where it shouldn't be. Check the file for typos.");
      return true;
    }
    return false;
  }

   public static boolean isPositiveInt(String s) {
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
