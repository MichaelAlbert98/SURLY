import java.util.*;
import java.lang.*;

public class InsertHandler {

  private final static String ERR_END_REACHED = "End of document reached.";
  private final static String ERR_ATT_OVERFLOW = "Too many attributes for selected relation";
  private final static String ERR_NO_ATT = "No insert names given.";
  private final static String ERR_BAD_FORMAT = "Insert format does not match relation format";
  private final static String ERR_DUP_TUPLE = "Duplicate tuple found, not inserted";
  private final static String ERR_INST_CAT = "Cannot insert tuples to Catalog";

  public InsertHandler(){}

  public int insert(ArrayList<String> splitText, LinkedList<Relation> database, int i) {

    int count = 0; // Number of attrubutes in insertion

    String relationName = parseRelationName(splitText, i);
    if(relationName == null) { return i; }

    Relation r = getRelation(relationName, database);
    if(r == null) { return i + 1; }
    if(r.getName().toLowerCase().equals("catalog")){ System.out.println(ERR_INST_CAT); return i; }

    Tuple tuple = new Tuple();
    i += 2;

    while(i < splitText.size() && !splitText.get(i).equals(";")) {
      if(i >= splitText.size()) {
        System.out.println(ERR_END_REACHED);
        return i;
      }
      if(Helper.isKeyword(splitText.get(i)) || Helper.isBreakChar(splitText.get(i))) {
        i--;
        return i; // If keyword is found mid-insert, throw away current insertion and return to interpret()
      }
      if(count >= r.getAttributeFormat().size()) {
        System.out.println(ERR_ATT_OVERFLOW);
        return i;
      }
      // Create an attribute object, storing the value in attribute.name, and filling out the rest from the relation
      Attribute a = new Attribute(splitText.get(i), r.getAttributeType(count), r.getAttributeLength(count));
      tuple.addAttribute(a);
      i++;
      count++;
    }

    if (count == 0) {
      System.out.println(ERR_NO_ATT);
      return i;
    }

    if (!verifyAttributeFormat(tuple, r.getAttributeFormat())) {
      System.out.println(ERR_BAD_FORMAT);
      return i;
    }
    if(r.contains(tuple)) {
      System.out.println(ERR_DUP_TUPLE);
      return i;
    }

    r.addTuple(tuple); // If no errors are found at this point, its safe to add to the tuple to the relation
    System.out.println("Inserting " + count + " attributes to " + relationName + ".");
    return i;
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
    for(int i = 0; i < relationFormat.size(); i++) {
      if( i >= tupleAttributes.size() ) {
         return false;
      }
      if( tupleAttributes.get(i).getName().length() > relationFormat.get(i).getLength() ) {
         return false;
      }
      if( tupleAttributes.get(i).getDataType().toLowerCase().equals("num") ) {
         if(!isNum(tupleAttributes.get(i).getName())) {
            return false;
         }
      }
    }
    return true;
  }

  // Checks to make sure a string does not contain a keyword or break char
  private String parseRelationName(ArrayList<String> splitText, int i) {
    i++;
    if (i < splitText.size() && !Helper.isKeyword(splitText.get(i)) && !Helper.isBreakChar(splitText.get(i))) {
      return splitText.get(i).toLowerCase();
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
