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
    System.out.println("Relation started this token.");
    return;
  }

  private static void insert(ArrayList<String> splitText) {
    System.out.println("Insert started this token.");
    return;
  }

  private static void print(ArrayList<String> splitText) {
    System.out.println("Print started this token.");
    return;
  }
}
