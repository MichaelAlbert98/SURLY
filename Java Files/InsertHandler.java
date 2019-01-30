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
    // Iterate through db to find matching relation
    Iterator<Relation> dbIterator = database.iterator();
    Relation r = null;
    Relation tmp;
    while(dbIterator.hasNext()) {
      if((tmp = dbIterator.next()).getName().equals(relationName)) {
         r = tmp;
      }
    }
    // If relation does not exist, print error and return
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
      if(Interpreter.isKeyword(splitText.get(i))) {
        i--;
        return i; // If keyword is found mid-insert, throw away current insertion and return to interpret()
         }
      if (Interpreter.isBreakChar(splitText.get(i))) {
        return i; //make sure there are no break chars as tuples
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
    r.addTuple(tuple); // If no errors are found at this point, its safe to add to the tuple to the relation
    System.out.println("Inserting " + count + " attributes to " + relationName + ".");
    return i;
  }  
}