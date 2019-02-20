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
  public Interpreter() {
  }

  // Iterate through the list of tokens, looking for keywords and calling the appropriate functions
  public void interpret(ArrayList<String> splitText) {
    RelationHandler relationer = new RelationHandler();
    InsertHandler inserter = new InsertHandler();
    PrintHandler printer = new PrintHandler();
    DestroyHandler destroyer = new DestroyHandler();
    DeleteHandler deleter = new DeleteHandler();
    LinkedList<Relation> database = new LinkedList<Relation>();
    Relation catalog = new Relation("catalog");
    database.add(catalog);
    i = 0;
    for (; i < splitText.size(); i++) {
      String token = splitText.get(i).trim();
      if (token.toLowerCase().equals("relation")) {
         i = relationer.relation(splitText, database, i);
      }
      else if (token.toLowerCase().equals("insert")) {
         i = inserter.insert(splitText, database, i);
      }
      else if (token.toLowerCase().equals("print")) {
         i = printer.print(splitText, database, i);
      }
      else if (token.toLowerCase().equals("destroy")) {
         i = destroyer.destroy(splitText, database, i);
      }
      else if (token.toLowerCase().equals("delete")) {
         i = deleter.delete(splitText, database, i);
      }
    }
  }
}
