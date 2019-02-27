import java.util.*;
import java.lang.*;

public class PrintHandler {

  private int i;

  public PrintHandler(){}

  public int print(ArrayList<String> splitText, LinkedList<Relation> database, int j) {
    this.i = j;
    int count = 0; // Number of relations being printed
    ArrayList<String> relations = new ArrayList<String>();
    this.i++;
    while(this.i < splitText.size() && !splitText.get(this.i).equals(";")) {
      if (formatCheck(splitText)) {
        return this.i;
      }
      relations.add(splitText.get(this.i).toLowerCase());
      this.i++;
      count++;
    }
    System.out.println();
    // Iterate through each string passed as an argument
    for(int k = 0; k < relations.size(); k++) {
      // Iterate through each relation in the db
      Iterator<Relation> dbIterator = database.iterator();
      Relation r = null;
      while(dbIterator.hasNext()) {
         Relation tmp = dbIterator.next();
         if(tmp.getName().equals(relations.get(k))) {
            r = tmp; // When the string matches the relation name, save it
         }
      }
      if(r != null) {
        if(relations.get(k).toLowerCase().equals("catalog")) {
           printCatalog(database.get(0));
        }
        else {
          System.out.println(r); // If a relation was found, print it
        }
      }
      else {
        System.out.println(Helper.ERR_NOT_FND);
      }
    }
    return this.i;
  }

  private Boolean formatCheck(ArrayList<String> splitText) {
    if(this.i >= splitText.size()) {
       System.out.println(Helper.ERR_END_REACHED);
       return true;
    }
    if(splitText.get(this.i).equals(",")){
       this.i++;
    }
    if(Helper.isKeyword(splitText.get(this.i))) {
       this.i--;
       return true; // If keyword is found mid-print, throw away current print job and return to interpret();
    }
    if (Helper.isBreakChar(splitText.get(this.i))) {
      return true; //make sure there are no break chars as attributes
    }
    return false;
  }

  private void printCatalog(Relation r) {
    String printval = "Printing Catalog:\n";
    for(int i = 0; i < r.getTuples().size(); i++) {
      printval+= r.getTuples().get(i).catalogPrint();
    }
    System.out.print(printval + "\n");
  }
}
