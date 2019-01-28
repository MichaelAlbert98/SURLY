//DestroyHandler.java - Iterates through the database for a given name. If a
//relation with the name is found, the schema and relation are removed.
//Created by Michael Albert
//Created January 28, 2019
//Revised January 28, 2019

import java.util.*;
import java.lang.*;

public class DestroyHandler {

   private DestroyHandler(){}

   public static int destroy(ArrayList<String> splitText, LinkedList database, int i) {
     j = i + 1;
     k = i + 2;
     if (k < splitText.size() && !Interpreter.isKeyword(splitText.get(j)) && !Interpreter.isBreakChar(splitText.get(j))
        && splitText.get(k).trim().equals(";")) {
        String relationName = splitText.get(j).trim();
        //remove relation from catalog
        for (int a = 0; a < database.get(0).getTuples().size(); a++) {
          if (database.get(0).get(a).get(0).equals(relationName)) {
            database.get(0).remove(a);
          }
        }
        //remove relation from database
        for (int a = 1; a < database.size(); a++) {
          if (database.get(a).getName().equals(relationName)) {
            database.get(a) = null;
            return i + 3;
          }
        }
        System.out.println("Relation name not found.");
        return i + 3;
     }
     System.out.println("Destroy command not correctly formatted.");
     return i;
   }
}
