import java.util.*;
import java.lang.*;

public class PrintHandler {

  private PrintHandler(){}
  
  public static int print(ArrayList<String> splitText, LinkedList<Relation> database, int i) {
    int count = 0; // Number of relations being printed
    ArrayList<String> relations = new ArrayList<String>();
    i++;
    while(i < splitText.size() && !splitText.get(i).equals(";")) {
      if(i >= splitText.size()) {
         System.out.println("End of document reached.");
         return i;
      }
      if(splitText.get(i).equals(",")){
         i++;
      }
      if(Interpreter.isKeyword(splitText.get(i))) {
         i--;
         return i; // If keyword is found mid-print, throw away current print job and return to interpret();
      }
      if (Interpreter.isBreakChar(splitText.get(i))) {
        return i; //make sure there are no break chars as attributes
      }
      relations.add(splitText.get(i));
      i++;
      count++;
    }
    System.out.println();
    for(int j = 0; j < relations.size(); j++) {
      Iterator<Relation> dbIterator = database.iterator();
      Relation r = null;
      while(dbIterator.hasNext()) {
         Relation tmp = dbIterator.next();
         if(tmp.getName().equals(relations.get(j))) {
            r = tmp;
         }
      }
      if(r != null) {
         System.out.println(r);
      }
      else {
         System.out.println("Relation \"" + relations.get(j).toString() + "\" does not exist.");
      }
    }
    return i;
  }
}