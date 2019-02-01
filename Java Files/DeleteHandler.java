//DeleteHandler.java - Iterates through the database for a given name. If a
//relation with the name is found, all tuples in that relation are removed.
//Created by Michael Albert
//Created January 27, 2019
//Revised January 28, 2019

import java.util.*;
import java.lang.*;

public class DeleteHandler {

   private DeleteHandler(){}

   public static int delete(ArrayList<String> splitText, LinkedList<Relation> database, int i) {
     int j = i + 1;
     int k = i + 2;
     if (k < splitText.size() && !Interpreter.isKeyword(splitText.get(j)) && !Interpreter.isBreakChar(splitText.get(j))
        && splitText.get(k).trim().equals(";")) {
        String relationName = splitText.get(j).trim().toLowerCase();
        for (int a = 1; a < database.size(); a++) {
          if (database.get(a).getName().equals(relationName)) {
            database.get(a).getTuples().clear();
            return i + 3;
          }
        }
        System.out.println("Relation name not found.");
        return i + 3;
     }
     System.out.println("Delete command not correctly formatted.");
     return i;
   }
}
