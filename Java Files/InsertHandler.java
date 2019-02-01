import java.util.*;
import java.lang.*;

public class InsertHandler {

  private InsertHandler(){}

  public static int insert(ArrayList<String> splitText, LinkedList<Relation> database, int i) {
    int count = 0; // Number of attrubutes in insertion
    i++;
    String relationName = splitText.get(i);
    if (i < splitText.size() && !Interpreter.isKeyword(splitText.get(i)) && !Interpreter.isBreakChar(splitText.get(i))) {
      relationName = splitText.get(i); //make sure insert name is valid
    }
    else {
      i--;
      return i;
    }
    Relation r = getRelation(relationName, database);
    if(r == null) {
      System.out.println("Error, relation \"" + relationName + "\" not found");
      return i;
    }
    Tuple tuple = new Tuple();
    i++;
    while(i < splitText.size() && !splitText.get(i).equals(";")) {
      if(i >= splitText.size()) {
        System.out.println("End of document reached.");
        return i;
      }
      if(Interpreter.isKeyword(splitText.get(i)) || Interpreter.isBreakChar(splitText.get(i))) {
        i--;
        return i; // If keyword is found mid-insert, throw away current insertion and return to interpret()
      }
      if(count >= r.getAttributeFormat().size()) {
        System.out.println("Too many attributes for selected relation");
        return i;
      }
      // Create an attribute object, storing the value in attribute.name, and filling out the rest from the relation
      Attribute a = new Attribute(splitText.get(i), r.getAttributeType(count), r.getAttributeLength(count));
      tuple.addAttribute(a);
      i++;
      count++;
    }
    if (count == 0) {
      System.out.println("No insert names given.");
      return i;
    }
    if (!verifyAttributeFormat(tuple, r.getAttributeFormat())) {
      System.out.println("Insert format does not match relation format");
      return i;
    }
    r.addTuple(tuple); // If no errors are found at this point, its safe to add to the tuple to the relation
    System.out.println("Inserting " + count + " attributes to " + relationName + ".");
    return i;
  }  
  
  private static Relation getRelation(String name, LinkedList<Relation> db) {
    Iterator<Relation> dbIterator = db.iterator();
    Relation r;
    while(dbIterator.hasNext()) {
      if((r = dbIterator.next()).getName().equals(name)) {
         return r;
      }
    }
    return null;
  }
  
  private static boolean verifyAttributeFormat(Tuple t, LinkedList<Attribute> relationFormat) {
    //LinkedList<Attribute> relationFormat = attributeFormat.getAttr();
    LinkedList<Attribute> tupleAttributes = t.getAttr();
    for(int i = 0; i < relationFormat.size(); i++) {
      if( i >= tupleAttributes.size() ) {
         return false;
      }
      if( tupleAttributes.get(i).getName().length() > relationFormat.get(i).getLength() ) {
         return false;
      }
    }
    return true;
  }
}