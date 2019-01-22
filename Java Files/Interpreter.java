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

}
