//DeleteHandler.java - Iterates through the database for a given name. If a
//relation with the name is found, all tuples in that relation are removed.
//Created by Michael Albert
//Created January 27, 2019
//Revised January 28, 2019

import java.util.*;
import java.lang.*;

public class SelectHandler {

   public SelectHandler(){}

   public int select(ArrayList<String> splitText, LinkedList<Relation> database, int i) {

     String relationName = "";
     String tempName = splitText.get(i-2);
     Relation tempRel = new Relation(tempName);
     tempRel.setTemp(true);

     //make sure formatting is valid
     if (!splitText.get(i-1).equals("=") || (Helper.isBreakChar(tempName) || Helper.isKeyword(tempName))) {
       System.out.println(Constants.ERR_BAD_FORMAT);
       return i;
     }

     i++;

     if (i < splitText.size() && !Helper.isKeyword(splitText.get(i)) && !Helper.isBreakChar(splitText.get(i))) {
       relationName = splitText.get(i);
     }
     else {
       System.out.println(Constants.ERR_BAD_FORMAT);
       i--;
       return i;
     }

     i++;

     if (!splitText.get(i).equals(";") && !splitText.get(i).toLowerCase().equals("where")) {
       System.out.println(Constants.ERR_BAD_FORMAT);
       i--;
       return i;
     }

     //find tuples that match where conditions and make temp relation out of them.
     if (splitText.get(i).toLowerCase().equals("where")) {
       Where where = new Where();

       if (where.whereFormat(splitText,i)) {
         ArrayList<Boolean> selectList = where.whereFind(splitText,database,i);
         for (int j = 1; j < database.size(); j++) {
           if (database.get(j).getName().equals(relationName)) {
             tempRel.setAF(database.get(j).getAttributeFormat());
             for (int k=0; k<selectList.size(); k++) {
               if (selectList.get(k)) {
                 Tuple copy = database.get(j).getTuples().get(k).deepCopy();
                 tempRel.getTuples().add(copy);
               }
             }
           }
         }
         database.add(tempRel);
       }
       else { //format not correct, return
         System.out.println(Constants.ERR_BAD_FORMAT);
       }

       while (!splitText.get(i).equals(";")) {
         i++;
       }
       return i;
     }

     //select all tuples in given relation
     else {
       for (int j = 1; j < database.size(); j++) {
         if (database.get(j).getName().equals(relationName)) {
           tempRel.setAF(database.get(j).getAttributeFormat());
           for (int k = 0; k < database.get(j).getTuples().size(); k++) {
             Tuple copy = database.get(j).getTuples().get(k).deepCopy();
             tempRel.getTuples().add(copy);
           }
           database.add(tempRel);
           return i;
         }
       }
       System.out.println(Constants.ERR_NOT_FND);
       return i;
     }
   }
}
