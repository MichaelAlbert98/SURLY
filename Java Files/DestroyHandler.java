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
        String relationName = splitText.get(j).trim();

        Relation rel = getRelation(relationName, database);

        if (rel == null) {
          System.out.println(Constants.ERR_NOT_FND);
          return i + 2;
        }

        else if (rel.getTemp()) {
          System.out.println(Constants.ERR_TEMP_MODIFY);
          return i + 2;
        }

        database.remove(rel);

        //remove relation from catalog
        for (int a = database.get(0).getTuples().size()-1; a >= 0; a--) {
          if (database.get(0).getTuples().get(a).getName().equals(relationName)) {
            database.get(0).getTuples().remove(a);
          }
        }
        return i + 2;
     }
     System.out.println(Constants.ERR_BAD_FORMAT);
     return i;
   }

   private Relation getRelation(String s, LinkedList<Relation> db) {
      ListIterator<Relation> l = db.listIterator();
      Relation r = l.next(); //Skip catalog
      while(l.hasNext()) {
        r = l.next();
        if(r.getName().equals(s)) {
            return r;
         }
      }
      return null;
   }
}
