//InsertHandler.java - Reads through token list when INSERT is found,
//adds tuple to database if formatting is correct.
//Created by Michael Albert and Jacob Coffland
//Created January 20, 2019
//Revised Febuary 24, 2019

import java.util.*;
import java.lang.*;

public class InsertHandler {

  private int i;

  public InsertHandler(){}

  public int insert(ArrayList<String> splitText, LinkedList<Relation> database, int j) {
    this.i = j;
    int count = 0; // Number of attrubutes in insertion
    String relationName = parseRelationName(splitText, this.i);

    if (relationName == null) {
      return this.i;
    }
    Relation r = getRelation(relationName, database);

    if (r == null) {
      return this.i;
    }

    else if (r.getTemp()) {
      System.out.println(Constants.ERR_TEMP_MODIFY);
      return this.i;
    }

    else if (r.getName().equals(Constants.CATALOG)) {
      System.out.println(Constants.ERR_INST_CAT);
      return this.i;
    }

    else if (checkQualifiers(r)) {
      System.out.println(Constants.ERR_NOT_QUALIFIED);
      return this.i;
    }

    Tuple tuple = new Tuple();
    this.i += 2;

    while (this.i < splitText.size() && !splitText.get(this.i).equals(";")) {
      if (checkFormat(splitText,count,r)) {
        return this.i;
      }
      // Create an attribute object, storing the value in attribute.name, and filling out the rest from the relation
      Attribute a = new Attribute(splitText.get(this.i), r.getAttributeType(count), r.getAttributeLength(count));
      a.setRelation(r.getName());
      tuple.addAttribute(a);
      this.i++;
      count++;
    }

    if (checkTuple(count,tuple,r)) {
      return this.i;
    }

    r.addTuple(tuple); // If no errors are found at this point, its safe to add to the tuple to the relation
    System.out.println("Inserting " + count + " attributes to " + relationName + ".");
    return this.i;
  }

  private Boolean checkTuple(int count, Tuple tuple, Relation r) {
    if (count == 0) {
      System.out.println(Constants.ERR_NO_ATT);
      return true;
    }
    if (!verifyAttributeFormat(tuple, r.getAttributeFormat())) {
      System.out.println(Constants.ERR_BAD_FORMAT);
      return true;
    }
    if(r.contains(tuple)) {
      System.out.println(Constants.ERR_DUP_ITEM);
      return true;
    }
    return false;
  }

  private Boolean checkFormat(ArrayList<String> splitText,int count, Relation r) {
    if(this.i >= splitText.size()) {
      System.out.println(Constants.ERR_END_REACHED);
      return true;
    }
    if(Helper.isKeyword(splitText.get(this.i)) || Helper.isBreakChar(splitText.get(this.i))) {
      this.i--;
      return true; // If keyword is found mid-insert, throw away current insertion and return to interpret()
    }
    if(count >= r.getAttributeFormat().size()) {
      System.out.println(Constants.ERR_ATT_OVERFLOW);
      return true;
    }
    return false;
  }

  // Gets a relation with a given name from a given database
  private  Relation getRelation(String name, LinkedList<Relation> db) {
    Iterator<Relation> dbIterator = db.iterator();
    Relation r;
    while(dbIterator.hasNext()) {
      if((r = dbIterator.next()).getName().equals(name)) {
         return r;
      }
    }
    System.out.println(Constants.ERR_NOT_FND);
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
      if( tupleAttributes.get(j).getDataType().equals(Constants.NUM) ) {
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

  private boolean checkQualifiers(Relation r) {
    ArrayList<ArrayList<String>> constraints = r.getConstraints();

    for (int j = 0; j < constraints.size(); j++) {
      ArrayList<String> constraint = constraints.get(j);

    }
    return false;
  }
}
