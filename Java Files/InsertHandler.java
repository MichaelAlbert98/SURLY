import java.util.*;
import java.lang.*;

public class InsertHandler {

  private InsertHandler(){}

  public static int insert(ArrayList<String> splitText, int i) {
    int count = 0; // Number of attrubutes in insertion
    i++;
    String insertionName = splitText.get(i);
    if (i < splitText.size() && !Interpreter.isKeyword(splitText.get(i)) && !Interpreter.isBreakChar(splitText.get(i))) {
      insertionName = splitText.get(i); //make sure insert name is valid
    }
    else {
      i--;
      return i;
    }
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
      i++;
      count++;
    }
    if (count == 0) {
      System.out.println("No insert names given.");
      return i;
    }
    System.out.println("Inserting " + count + " attributes to " + insertionName + ".");
    return i;
  }

  
}