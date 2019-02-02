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
    // Iterate through each string passed as an argument
    for(int j = 0; j < relations.size(); j++) {
      // Iterate through each relation in the db
      Iterator<Relation> dbIterator = database.iterator();
      Relation r = null;
      while(dbIterator.hasNext()) {
         Relation tmp = dbIterator.next();
         if(tmp.getName().equals(relations.get(j))) {
            r = tmp; // When the string matches the relation name, save it
         }
      }
      if(r != null) {
         System.out.println(r); // If a relation was found, print it
      }
      else {
         if(relations.get(j).toLowerCase().equals("catalog")) {
            printCatalog(database.get(0));
         }
         else{
            System.out.println("Relation \"" + relations.get(j).toString() + "\" does not exist.");
         }
      }
    }
    return i;
  }
  
  private static void printCatalog(Relation r) {
    String printval = "Printing Catalog:\n";
    for(int i = 0; i < r.getTuples().size(); i++) {
      printval+= r.getTuples().get(i).catalogPrint() + " ";
    }
    System.out.print(printval + "\n");
  }
}