//InsertHandler.java - Reads through token list when INSERT is found,
//adds tuple to database if formatting is correct.
//Created by Michael Albert and Jacob Coffland
//Created January 20, 2019
//Revised Febuary 24, 2019

import java.util.*;
import java.lang.*;

public class InsertHandler {

  private final static String ERR_END_REACHED = "End of document reached.";
  private final static String ERR_ATT_OVERFLOW = "Too many attributes for selected relation";
  private final static String ERR_NO_ATT = "No insert names given.";
  private final static String ERR_BAD_FORMAT = "Insert format does not match relation format";
  private final static String ERR_DUP_TUPLE = "Duplicate tuple found, not inserted";
  private final static String ERR_INST_CAT = "Cannot insert tuples to Catalog";
  private int i;

  public InsertHandler(){}

  public int insert(ArrayList<String> splitText, LinkedList<Relation> database, int j) {
    this.i = j;
    int count = 0; // Number of attrubutes in insertion
    String relationName = parseRelationName(splitText, this.i);
    if(relationName == null) {
      return this.i;
    }
    Relation r = getRelation(relationName, database);
    if(r == null) {
      return this.i + 1;
    }
    if(r.getName().toLowerCase().equals("catalog")) {
      System.out.println(ERR_INST_CAT);
      return this.i;
    }
    Tuple tuple = new Tuple();
    this.i += 2;
    while(this.i < splitText.size() && !splitText.get(this.i).equals(";")) {
      if(checkFormat(splitText,count,r)) {
        return this.i;
      }
      // Create an attribute object, storing the value in attribute.name, and filling out the rest from the relation
      Attribute a = new Attribute(splitText.get(this.i), r.getAttributeType(count), r.getAttributeLength(count));
      tuple.addAttribute(a);
      this.i++;
      count++;
    }
    if (count == 0) {
      System.out.println(ERR_NO_ATT);
      return this.i;
    }
    if (!verifyAttributeFormat(tuple, r.getAttributeFormat())) {
      System.out.println(ERR_BAD_FORMAT);
      return this.i;
    }
    if(r.contains(tuple)) {
      System.out.println(ERR_DUP_TUPLE);
      return this.i;
    }
    r.addTuple(tuple); // If no errors are found at this point, its safe to add to the tuple to the relation
    System.out.println("Inserting " + count + " attributes to " + relationName + ".");
    return this.i;
  }

  private Boolean checkFormat(ArrayList<String> splitText,int count, Relation r) {
    if(this.i >= splitText.size()) {
      System.out.println(ERR_END_REACHED);
      return true;
    }
    if(Helper.isKeyword(splitText.get(this.i)) || Helper.isBreakChar(splitText.get(this.i))) {
      this.i--;
      return true; // If keyword is found mid-insert, throw away current insertion and return to interpret()
    }
    if(count >= r.getAttributeFormat().size()) {
      System.out.println(ERR_ATT_OVERFLOW);
      return true;
    }
    return false;
  }

  // Gets a relation with a given name from a given database
  private  Relation getRelation(String name, LinkedList<Relation> db) {
    Iterator<Relation> dbIterator = db.iterator();
    Relation r;
    while(dbIterator.hasNext()) {
      if((r = dbIterator.next()).getName().toLowerCase().equals(name)) {
         return r;
      }
    }
    System.out.println("Error, relation \"" + name + "\" not found");
    return null;
  }

  // Checks to make sure that the attribute format of a tuple matches the format of a relation
  private boolean verifyAttributeFormat(Tuple t, LinkedList<Attribute> relationFormat) {
    LinkedList<Attribute> tupleAttributes = t.getAttr();
    for(int j = 0; j < relationFormat.size(); j++) {
      if( j >= tupleAttributes.size() ) {
         return false;
      }
      if( tupleAttributes.get(j).getName().length() > relationFormat.get(j).getLength() ) {
         return false;
      }
      if( tupleAttributes.get(j).getDataType().toLowerCase().equals("num") ) {
         if(!isNum(tupleAttributes.get(j).getName())) {
            return false;
         }
      }
    }
    return true;
  }

  // Checks to make sure a string does not contain a keyword or break char
  private String parseRelationName(ArrayList<String> splitText, int j) {
    j++;
    if (j < splitText.size() && !Helper.isKeyword(splitText.get(j)) && !Helper.isBreakChar(splitText.get(j))) {
      return splitText.get(j).toLowerCase();
    }
    return null;
  }

  private boolean isNum(String s) {
    try {
      double d = Double.parseDouble(s);
    }
    catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
}
