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
        relation(splitText);
      }
      else if (token.toLowerCase().equals("insert")) {
        insert(splitText);
      }
      else if (token.toLowerCase().equals("print")) {
        print(splitText);
      }
    }
  }

  private static void relation(ArrayList<String> splitText) {
    int count = 0; // Number of attributes in relation
    String relationName = "";
    i++;
    if (!isKeyword(splitText.get(i)) && !isBreakChar(splitText.get(i))) {
      relationName = splitText.get(i); //make sure relation name is valid
    }
    else {
      i--;
      return;
    }
    i++;
    if(!splitText.get(i).equals("(")) {
      System.out.println("bad syntax; no open paren");
      return; // Fail on bad syntax
    }
    i++;
    while(!splitText.get(i).equals(")")){
      for(int j = 0; j < 3; j++) {
         if(isKeyword(splitText.get(i))) {
            i--;
            return; // If keyword is found mid-relation, throw away current relation and return to interpret()
         }
         if (isBreakChar(splitText.get(i))) {
           return; //make sure there are no break chars as attributes
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
    if (!splitText.get(i).equals(";")) {
      System.out.println("bad syntax; missing semicolon");
      i--;
      return;
    }

    System.out.println("Creating " + relationName + " with " + count + " attributes.");
    return;
  }

  private static void insert(ArrayList<String> splitText) {
    int count = 0; // Number of attrubutes in insertion
    i++;
    String insertionName = splitText.get(i);
    if (!isKeyword(splitText.get(i)) && !isBreakChar(splitText.get(i))) {
      insertionName = splitText.get(i); //make sure insert name is valid
    }
    else {
      i--;
      return;
    }
    i++;
    while(!splitText.get(i).equals(";")) {
      if(i >= splitText.size()) {
        System.out.println("End of document reached.");
        return;
      }
      if(isKeyword(splitText.get(i))) {
        i--;
        return; // If keyword is found mid-insert, throw away current insertion and return to interpret()
         }
      if (isBreakChar(splitText.get(i))) {
        return; //make sure there are no break chars as tuples
      }
      i++;
      count++;
    }
    System.out.println("Inserting " + count + " attributes to " + insertionName + ".");
    return;
  }

  private static void print(ArrayList<String> splitText) {
    int count = 0; // Number of relations being printed
    ArrayList<String> relations = new ArrayList<String>();
    i++;
    while(!splitText.get(i).equals(";")) {
      if(i >= splitText.size()) {
         System.out.println("End of document reached.");
         return;
      }
      if(splitText.get(i).equals(",")){
         i++;
      }
      if(isKeyword(splitText.get(i))) {
         i--;
         return; // If keyword is found mid-print, throw away current print job and return to interpret();
      }
      if (isBreakChar(splitText.get(i))) {
        return; //make sure there are no break chars as attributes
      }
      relations.add(splitText.get(i));
      i++;
      count++;
    }
    System.out.println("Printing " + count + " relations: " + relations);
    return;
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

}
