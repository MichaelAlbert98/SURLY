//RelationHandler.java - Checks the formatting of relation commands and if it is
//correct, adds the new relation to the database.
//Created by Michael Albert and Jacob Coffland
//Created January 27, 2019
//Revised March 10, 2019

import java.util.*;
import java.lang.*;

public class RelationHandler {
  //attributes
  private int count;

   public RelationHandler(){}

   public int relation(ArrayList<String> splitText, LinkedList<Relation> database, int i) {
    count = 0;
    String relationName = "";
    LinkedList<Attribute> attributeFormat = new LinkedList<Attribute>();
    i++;
    if (i < splitText.size() && !Helper.isKeyword(splitText.get(i)) && !Helper.isBreakChar(splitText.get(i))) {
      relationName = splitText.get(i).toLowerCase(); //make sure relation name is valid
      if (alreadyExists(relationName,database)) {
        return i;
      }
    }
    else {
      i--;
      return i;
    }
    i++;
    if(!splitText.get(i).equals("(")) {
      System.out.println(Constants.ERR_BAD_FORMAT);
      i--;
      return i; // Fail on bad syntax
    }
    i++;
    ArrayList<Attribute> attrList = new ArrayList<Attribute>();
    while(i < splitText.size() && !splitText.get(i).equals(")")){
      i = formatCheck(splitText,i);
      Attribute attr = new Attribute(splitText.get(i-3).toLowerCase(),splitText.get(i-2).toLowerCase(),Integer.parseInt(splitText.get(i-1)));
      attrList.add(attr);
      attributeFormat.add(attr);
      if(splitText.get(i).equals(",")) {
         i++;
      }
      count++;
    }
    i++;
    if (i < splitText.size() && !splitText.get(i).equals(";")) {
      System.out.println(Constants.ERR_BAD_FORMAT);
      i--;
      return i;
    }
    for (int j=0;j<attrList.size();j++) {
      Tuple catTuple = new Tuple(relationName);
      catTuple.addAttribute(attrList.get(j));
      database.get(0).getTuples().add(catTuple); //add relation to catalog
    }
    Relation relation = new Relation(relationName,attributeFormat); //create relation and add it to database
    database.add(relation);
    System.out.println("Creating " + relationName + " with " + count + " attributes.");
    return i;
  }

  private Boolean alreadyExists(String name, LinkedList<Relation> database) {
    for (int i = 1; i < database.size();i++) {
      if (database.get(i).getName().equals(name) || name.equals(Constants.CATALOG)) {
        System.out.println(Constants.ERR_DUP_ITEM);
        return true;
      }
    }
    return false;
  }

  private int formatCheck(ArrayList<String> splitText, int i) {
    for(int j = 0; j < 3; j++) {
       if(Helper.isKeyword(splitText.get(i))) {
          i--;
          return i; // If keyword is found mid-relation, throw away current relation and return to interpret()
       }
       if (Helper.isBreakChar(splitText.get(i))) {
         return i; //make sure there are no break chars as attributes
       }
       if(j == 1 && (!splitText.get(i).toLowerCase().equals(Constants.NUM) && !splitText.get(i).toLowerCase().equals(Constants.CHAR))) {
          System.out.print(Constants.ERR_BAD_FORMAT);
          return i;
       }
       if(j == 2 && !Helper.isPositiveInt(splitText.get(i))) {
          System.out.print(Constants.ERR_BAD_FORMAT);
          return i;
       }
       i++;
    }
    return i;
  }
}
