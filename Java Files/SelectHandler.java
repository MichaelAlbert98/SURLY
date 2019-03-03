//DeleteHandler.java - Iterates through the database for a given name. If a
//relation with the name is found, all tuples in that relation are removed.
//Created by Michael Albert
//Created January 27, 2019
//Revised January 28, 2019

import java.util.*;
import java.lang.*;

public class SelectHandler {

  private int i;
  private String relName;
  private String tempName;

   public SelectHandler(){}

   public int select(ArrayList<String> splitText, LinkedList<Relation> database, int ix) {
     this.i = ix;
     Where where = new Where();
     tempName = splitText.get(i-2);

     if (!formatCheck(splitText)) {
       return this.i;
     }

     Relation tempRel = new Relation(tempName);
     tempRel.setTemp(true);

     //find tuples that match where conditions and make temp relation out of them.
     if (splitText.get(i).toLowerCase().equals("where")) {
       if (where.whereFormat(splitText,i)) {
         ArrayList<Boolean> selectList = where.whereFind(splitText,database,i);
         for (int j = 1; j < database.size(); j++) {
           if (database.get(j).getName().equals(relName)) {
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
         if (database.get(j).getName().equals(relName)) {
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

   private boolean formatCheck(ArrayList<String> splitText) {
     if (!splitText.get(i-1).equals("=") || (Helper.isBreakChar(tempName) || Helper.isKeyword(tempName))) {
       System.out.println(Constants.ERR_BAD_FORMAT);
       return false;
     }

     i++;

     if (i < splitText.size() && !Helper.isKeyword(splitText.get(i)) && !Helper.isBreakChar(splitText.get(i))) {
       relName = splitText.get(i);
     }
     else {
       System.out.println(Constants.ERR_BAD_FORMAT);
       i--;
       return false;
     }

     i++;

     if (!splitText.get(i).equals(";") && !splitText.get(i).toLowerCase().equals("where")) {
       System.out.println(Constants.ERR_BAD_FORMAT);
       i--;
       return false;
     }
     return true;
   }
}  
