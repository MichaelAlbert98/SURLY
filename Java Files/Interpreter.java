//Interpreter.java - takes an ArrayList of tokens, iterates through them, and
//prints to the terminal based on the commands found.
//Created by Michael Albert and Jacob Coffland
//Created January 09, 2019
//Revised March 10, 2019

import java.util.*;
import java.lang.*;

public class Interpreter {

  //attributes
  private int i;

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
    SelectHandler selecter = new SelectHandler();
    ProjectionHandler projecter = new ProjectionHandler();
    JoinHandler joiner = new JoinHandler();
    Integrity_Constraint constrainer = new Integrity_Constraint();
    LinkedList<Relation> database = new LinkedList<Relation>();
    Relation catalog = new Relation(Constants.CATALOG);
    database.add(catalog);
    i = 0;
    for (; i < splitText.size(); i++) {
      String token = splitText.get(i).trim();
      if (token.toLowerCase().equals(Constants.RELATION)) {
         i = relationer.relation(splitText, database, i);
      }
      else if (token.toLowerCase().equals(Constants.INSERT)) {
         i = inserter.insert(splitText, database, i);
      }
      else if (token.toLowerCase().equals(Constants.PRINT)) {
         i = printer.print(splitText, database, i);
      }
      else if (token.toLowerCase().equals(Constants.DESTROY)) {
         i = destroyer.destroy(splitText, database, i);
      }
      else if (token.toLowerCase().equals(Constants.DELETE)) {
         i = deleter.delete(splitText, database, i);
      }
      else if (token.toLowerCase().equals(Constants.SELECT)) {
         i = selecter.select(splitText, database, i);
      }
      else if (token.toLowerCase().equals(Constants.PROJECT)) {
         i = projecter.project(splitText, database, i);
      }
      else if (token.toLowerCase().equals(Constants.JOIN)) {
         i = joiner.join(splitText, database, i);
      }
      else if (token.toLowerCase().equals(Constants.INTEGRITY_CONSTRAINT)) {
         i = constrainer.constrain(splitText, database, i);
      }
    }
  }
}
