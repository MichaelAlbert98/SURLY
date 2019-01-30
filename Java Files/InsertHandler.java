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
    Iterator<Relation> dbIterator = database.iterator();
    Relation r = null;
    Relation tmp;
    while(dbIterator.hasNext()) {
      if((tmp = dbIterator.next()).getName().equals(relationName)) {
         r = tmp;
         //System.out.print(r.getName());
      }
    }
    if(r == null) {
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
      Attribute a = new Attribute(splitText.get(i), r.getAttributeType(count), r.getAttributeLength(count));
      tuple.addAttribute(a);
      //System.out.print(splitText.get(i) + " " + r.getAttributeType(count) + " " + r.getAttributeLength(count));
      i++;
      count++;
    }
    if (count == 0) {
      System.out.println("No insert names given.");
      return i;
    }
    System.out.println(tuple);
    System.out.println("Inserting " + count + " attributes to " + relationName + ".");
    return i;
  }

  
}