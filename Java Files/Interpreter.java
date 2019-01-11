//Interpreter.java -
//Created by Michael Albert
//Created January 09, 2019
//Revised January 10, 2019

import java.util.*;

public class Interpreter {

  //attributes
  private static int i;

  //constructor
  private Interpreter() {
  }

  public static void interpret(ArrayList<String> tokens) {
    i = 0;
    for (; i < tokens.size(); i++) {
      if (tokens.get(i).toLowerCase().equals("relation")) {
        relation(tokens);
      }
      else if (tokens.get(i).toLowerCase().equals("insert")) {
        insert(tokens);
      }
      else if (tokens.get(i).toLowerCase().equals("print")) {
        print(tokens);
      }
      else {
        i = i + 0;
        //System.out.println("The command found was not 'relation', 'insert', or 'print'. Please check the text file.");
      }
    }
  }

  private static void relation(ArrayList<String> tokens) {
    System.out.println("Relation started this token.");
    i++;
    return;
  }

  private static void insert(ArrayList<String> tokens) {
    System.out.println("Insert started this token.");
    i++;
    return;
  }

  private static void print(ArrayList<String> tokens) {
    System.out.println("Print started this token.");
    i++;
    return;
  }
}
