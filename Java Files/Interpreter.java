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
    int count = 0;
    i++;
    String relationName = splitText.get(i);
    i++;
    if(!splitText.get(i).equals("(")) {
      return;
    }
    i++;
    while(!splitText.get(i).equals(")")){
      for(int j = 0; j < 3; j++) {
         i++;
         if(isKeyword(splitText.get(i))) {
            i--;
            System.out.println("bad syntax");
            return;
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
    int count = 0;
    i++;
    String insertionName = splitText.get(i);
    i++;
    while(!splitText.get(i).equals(";")) {
      //System.out.println("the " + count + "th item is " + splitText.get(i));
      i++;
      if(isKeyword(splitText.get(i))) {
            i--;
            System.out.println("bad syntax");
            return;
         }
      count++;
    }
    System.out.println("Inserting " + count + " attributes to " + insertionName + ".");
    return;
  }

  private static void print(ArrayList<String> splitText) {
    int count = 0;
    i++;
    while(!splitText.get(i).equals(";")) {
      i++;
      while(splitText.get(i).equals(",")){
         i++;
      }
      if(isKeyword(splitText.get(i))) {
            i--;
            System.out.println("bad syntax");
            return;
         }
      count++;
    }
    System.out.println("Printing " + count + " relations.");
    return;
  }

  private static boolean isKeyword(String s) {
    if(s.toLowerCase().equals("relation") || s.toLowerCase().equals("insert") || s.toLowerCase().equals("print")) {
      return true;
    }
    return false;
  }

  /*private static String analyzeToken(String s) {
    if(s.toLowerCase().equals("relation") || s.toLowerCase().equals("insert") || s.toLowerCase().equals("print")) {return "keyword";}
    if(s.toLowerCase().equals("(")){return "open paren";}
    if(s.toLowerCase().equals(")")){return "close paren";}
    if(s.toLowerCase().equals(",")){return "comma";}
    if(s.toLowerCase().equals(";")){return "sc";}
    return "none";
  }*/

}
