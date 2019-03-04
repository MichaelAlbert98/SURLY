//DeleteHandler.java - Creates a temp relation from a given relation based on a
//set of conditions.
//Created by Michael Albert
//Created Febuary 27, 2019
//Revised March 3, 2019

import java.util.*;
import java.lang.*;

public class SelectHandler {

  private int i;
  private String relName;
  private String tempName;
  private LinkedList<Relation> database;

   public SelectHandler(){}

   public int select(ArrayList<String> splitText, LinkedList<Relation> database, int ix) {
     this.i = ix;
     this.database = database;
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
         for (int j = 0; j < database.size(); j++) {
           if (database.get(j).getName().equals(relName)) {
             tempRel.setAF(database.get(j).getAttributeFormat());
             for (int k=0; k<selectList.size(); k++) {
               if (selectList.get(k)) {
                 Tuple copy = database.get(j).getTuples().get(k).deepCopy();
                 tempRel.getTuples().add(copy);
               }
             }
             //can't overwrite non-temp relations
             if (tempName.equals(relName) && !database.get(j).getTemp()) {
               System.out.println(Constants.ERR_NAME_OVERWRITE);
               return Helper.findSemicolon(splitText, this.i);
             }
             //delete old temp relation
             else if (tempName.equals(relName) && database.get(j).getTemp()) {
               database.remove(j);
             }
             database.add(tempRel);
           }
         }
       }
       else { //format not correct, return
         System.out.println(Constants.ERR_BAD_FORMAT);
       }
       return Helper.findSemicolon(splitText, this.i);
     }

     //select all tuples in given relation
     else {
       for (int j = 0; j < database.size(); j++) {
         if (database.get(j).getName().equals(relName)) {
           tempRel.setAF(database.get(j).getAttributeFormat());
           for (int k = 0; k < database.get(j).getTuples().size(); k++) {
             Tuple copy = database.get(j).getTuples().get(k).deepCopy();
             tempRel.getTuples().add(copy);
           }
           //can't overwrite non-temp relations
           if (tempName.equals(relName) && !database.get(j).getTemp()) {
             System.out.println(Constants.ERR_NAME_OVERWRITE);
             return this.i;
           }
           //delete old temp relation
           else if (tempName.equals(relName) && database.get(j).getTemp()) {
             database.remove(j);
           }
           database.add(tempRel);
           return this.i;
         }
       }
       System.out.println(Constants.ERR_NOT_FND);
       return this.i;
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

   private Relation getRelation(String s) {
      ListIterator<Relation> l = database.listIterator();
      while(l.hasNext()) {
         Relation r = l.next();
        if(r.getName().equals(s)) {
            return r;
         }
      }
      return null;
   }
}
