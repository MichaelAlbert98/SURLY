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

  public static void interpret(String text) {
    i = 0;
    String[] splitText = text.split("\\;");
    //-1 is a temp fix until I look up how to remove empty elements of an array.
    for (; i < splitText.length - 1; i++) {
      splitText[i] = splitText[i].trim();
      String[] command = splitText[i].split(" ", 2);
      if (command[0].toLowerCase().equals("relation")) {
        relation(command[1]);
      }
      else if (command[0].toLowerCase().equals("insert")) {
        insert(command[1]);
      }
      else if (command[0].toLowerCase().equals("print")) {
        print(command[1]);
      }
      else {
        System.out.println("The command found was not 'relation', 'insert', or 'print'. Please check the text file.");
      }
    }
  }

  private static void relation(String info) {
    System.out.println("Relation started this token.");
    return;
  }

  private static void insert(String info) {
    System.out.println("Insert started this token.");
    return;
  }

  private static void print(String info) {
    System.out.println("Print started this token.");
    return;
  }
}
