//Interpreter.java -
//Created by Michael Albert
//Created January 09, 2019
//Revised January 10, 2019

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
    i++;
    String relationName = splitText.get(i);
    i++;
    if(!splitText.get(i).equals("(")) {
      System.out.println("bad syntax; no open paren");
      return; // Fail on bad syntax
    }
    i++;
    while(!splitText.get(i).equals(")")){
      for(int j = 0; j < 3; j++) {
         i++;
         if(isKeyword(splitText.get(i))) {
            i--;
            System.out.println("bad syntax");
            return; // If keyword is found mid-relation, throw away current relation and return to interpret()
         }   
      }
      if(splitText.get(i).equals(",")) {
         i++;
      }
      count++;
    }
    System.out.println("Creating " + relationName + " with " + count + " attributes.");
    return;
  }

  private static void insert(ArrayList<String> splitText) {
    int count = 0; // Number of attrubutes in insertion
    i++;
    String insertionName = splitText.get(i);
    i++;
    while(!splitText.get(i).equals(";")) {
      i++;
      if(isKeyword(splitText.get(i))) {
            i--;
            System.out.println("bad syntax");
            return; // If keyword is found mid-insert, throw away current insertion and return to interpret()
         }
      count++;
    }
    System.out.println("Inserting " + count + " attributes to " + insertionName + ".");
    return;
  }

  private static void print(ArrayList<String> splitText) {
    int count = 0; // Number of relations being printed
    i++;
    while(!splitText.get(i).equals(";")) {
      i++;
      while(splitText.get(i).equals(",")){
         i++;
      }
      if(isKeyword(splitText.get(i))) {
            i--;
            System.out.println("bad syntax");
            return; // If keyword is found mid-print, throw away current print job and return to interpret();
         }
      count++;
    }
    System.out.println("Printing " + count + " relations.");
    return;
  }

  // Returns true if the given string is a keyword (relation, insert, print: case insensitive)
  private static boolean isKeyword(String s) {
    if(s.toLowerCase().equals("relation") || s.toLowerCase().equals("insert") || s.toLowerCase().equals("print")) {
      return true;
    }
    return false;
  }

}
