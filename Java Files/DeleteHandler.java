//DeleteHandler.java - Iterates through the database for a given name. If a
//relation with the name is found, all tuples in that relation are removed.
//Created by Michael Albert
//Created January 27, 2019
//Revised January 28, 2019

import java.util.*;
import java.lang.*;

public class DeleteHandler {

   public DeleteHandler(){}

   public int delete(ArrayList<String> splitText, LinkedList<Relation> database, int i) {
     i++;
     if (i < splitText.size() && !Helper.isKeyword(splitText.get(i)) && !Helper.isBreakChar(splitText.get(i))) {
       String relationName = splitText.get(i).toLowerCase(); //make sure relation name is valid
     }
     else {
       i--;
       return i;
     }
     i++;
     if (!splitText.get(i).equals(";") && !splitText.get(i).toLowerCase().equals("where")) {
       System.out.println(ERR_BAD_FORMAT);
       i--;
       return i
     }

     //find tuples that match where conditions and delete
     if (splitText.get(i).toLowerCase().equals("where")) {
       if (Helper.whereFormat(splitText,i)) {
         ArrayList<Relation> deleteList = Helper.whereFind(splitText,database,i);
         for (int j=1; j<database.size(); j++) {
           if (database.get(j).getName().equals(relationName)) {
             for (int k=0; k<deleteList.size(); k++) {
               database.get(j).getTuples().remove(deleteList.get(k));
             }
           }
         }
       }
       while (!splitText.get(i).equals(";")) {
         i++;
       }
       else { //format not correct, return
         System.out.println(ERR_BAD_FORMAT);
         return i;
       }
       return i;
     }

     //delete all tuples in given relation
     else {
       for (int j = 1; j < database.size(); j++) {
         if (database.get(j).getName().equals(relationName)) {
           database.get(j).getTuples().clear();
           return i ;
         }
       }
       System.out.println(Constants.ERR_NOT_FND);
       return i;
     }

     //old version of DeleteHandler
     /*
     int j = i + 1;
     int k = i + 2;
     if (k < splitText.size() && !Helper.isKeyword(splitText.get(j)) && !Helper.isBreakChar(splitText.get(j))
        && splitText.get(k).trim().equals(";")) {
        String relationName = splitText.get(j).trim().toLowerCase();
        for (int a = 1; a < database.size(); a++) {
          if (database.get(a).getName().equals(relationName)) {
            database.get(a).getTuples().clear();
            return i + 2;
          }
        }
        System.out.println(Constants.ERR_NOT_FND);
        return i + 2;
     }
     System.out.println(Constants.ERR_BAD_FORMAT);
     return i;
   } */
}
