//Integrity_Constraint.java - Creates a list of conditions for tuples in a given relation.
//Created by Michael Albert
//Created March 10, 2019
//Revised March 11, 2019

import java.util.*;
import java.lang.*;

public class Integrity_Constraint {

  private int i;
  private String relName;
  private LinkedList<Relation> database;

   public Integrity_Constraint(){}

   public int constrain(ArrayList<String> splitText, LinkedList<Relation> database, int ix) {
     this.i = ix;
     this.database = database;
     Where where = new Where();

     if (!formatCheck(splitText)) {
       return this.i;
     }

     //add qualifier to relation qualifier.
     else if (where.whereFormat(splitText,this.i)) {
       Relation rel = getRelation(this.relName);
       ArrayList<String> qualifier = new ArrayList<String>();
       while (!splitText.get(i).equals(";")) {
         qualifier.add(splitText.get(i));
         i++;
       }
       rel.getConstraints().add(qualifier);
     }

     return this.i;
   }

   private boolean formatCheck(ArrayList<String> splitText) {
     i++;

     if (i >= splitText.size() || Helper.isKeyword(splitText.get(i)) || Helper.isBreakChar(splitText.get(i))) {
       i--;
       System.out.println(Constants.ERR_BAD_FORMAT);
       return false;
     }

     this.relName = splitText.get(i);
     i++;

     if (!splitText.get(i).toLowerCase().equals("where")) {
       i--;
       System.out.println(Constants.ERR_BAD_FORMAT);
       return false;
     }

     return true;
   }

   // Gets the relaion by the given name
   public Relation getRelation(String s) {
      ListIterator<Relation> l = database.listIterator();
      while(l.hasNext()) {
         Relation r = l.next();
        if(r.getName().toLowerCase().equals(s.toLowerCase())) {
            return r;
         }
      }
      return null;
   }
}
