//DestroyHandler.java - Iterates through the database for a given name. If a
//relation with the name is found, the schema and relation are removed.
//Created by Michael Albert
//Created January 28, 2019
//Revised January 28, 2019

import java.util.*;
import java.lang.*;

public class DestroyHandler {

   public DestroyHandler(){}

   public int destroy(ArrayList<String> splitText, LinkedList<Relation> database, int i) {
     int j = i + 1;
     int k = i + 2;
     if (k < splitText.size() && !Helper.isKeyword(splitText.get(j)) && !Helper.isBreakChar(splitText.get(j))
        && splitText.get(k).trim().equals(";")) {
        String relationName = splitText.get(j).trim().toLowerCase();
        //remove relation from catalog
        for (int a = 0; a < database.get(0).getTuples().size(); a++) {
          if (database.get(0).getTuples().get(a).getName().equals(relationName)) {
            database.get(0).getTuples().remove(a);
          }
        }
        //remove relation from database
        for (int a = 1; a < database.size(); a++) {
          if (database.get(a).getName().equals(relationName)) {
            database.remove(a);
            return i + 2;
          }
        }
        System.out.println(Helper.ERR_NOT_FND);
        return i + 2;
     }
     System.out.println(Helper.ERR_BAD_FORMAT);
     return i;
   }
}
