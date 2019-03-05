//DeleteHandler.java - Iterates through the database for a given name. If a
//relation with the name is found, all tuples in that relation are removed.
//Created by Michael Albert
//Created January 27, 2019
//Revised January 28, 2019

import java.util.*;
import java.lang.*;

public class DeleteHandler {

   private int i;
   private String relName;

   public DeleteHandler(){}

   public int delete(ArrayList<String> splitText, LinkedList<Relation> database, int ix) {
     this.i = ix;
     Where where = new Where();

     if (!formatCheck(splitText)) {
       return this.i;
     }

     if (splitText.get(this.i).equals("where")) {
       if (where.whereFormat(splitText,this.i)) {
         ArrayList<Boolean> deleteList = where.whereFind(splitText,database,this.i);
         for (int j=1; j<database.size(); j++) {
           if (database.get(j).getName().equals(relName)) {
             //can't delete temp relations
             if (database.get(j).getTemp()) {
               System.out.println(Constants.ERR_TEMP_MODIFY);
               return Helper.findSemicolon(splitText, this.i);
             }
             for (int k=deleteList.size()-1; k>=0; k--) {
               if (deleteList.get(k)) {
                 database.get(j).getTuples().remove(k);
                 deleteList.remove(k);
               }
             }
           }
         }
       }
       else { //format not correct, return
         System.out.println(Constants.ERR_BAD_FORMAT);
       }
       return Helper.findSemicolon(splitText, this.i);
     }

     //delete all tuples in given relation
     else {
       for (int j = 1; j < database.size(); j++) {
         if (database.get(j).getName().equals(relName)) {

           //can't delete temp relations
           if (database.get(j).getTemp()) {
             System.out.println(Constants.ERR_TEMP_MODIFY);
             return this.i + 2;
           }

           database.get(j).getTuples().clear();
           return this.i;
         }
       }
       System.out.println(Constants.ERR_NOT_FND);
       return this.i;
     }
   }

   private boolean formatCheck(ArrayList<String> splitText) {
     i++;

     if (i >= splitText.size() || Helper.isKeyword(splitText.get(i)) || Helper.isBreakChar(splitText.get(i))) {
       i--;
       System.out.println(Constants.ERR_BAD_FORMAT);
       return false;
     }

     relName = splitText.get(i);
     i++;

     if (!splitText.get(i).equals(";") && !splitText.get(i).toLowerCase().equals("where")) {
       i--;
       System.out.println(Constants.ERR_BAD_FORMAT);
       return false;
     }

     return true;
   }
}
