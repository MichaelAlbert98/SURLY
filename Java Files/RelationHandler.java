import java.util.*;
import java.lang.*;

public class RelationHandler {

   private RelationHandler(){}
   
   public static int relation(ArrayList<String> splitText, int i) {
    int count = 0; // Number of attributes in relation
    String relationName = "";
    i++;
    if (i < splitText.size() && !Interpreter.isKeyword(splitText.get(i)) && !Interpreter.isBreakChar(splitText.get(i))) {
      relationName = splitText.get(i); //make sure relation name is valid
    }
    else {
      i--;
      return i;
    }
    i++;
    if(!splitText.get(i).equals("(")) {
      System.out.println("bad syntax; no open paren");
      return i; // Fail on bad syntax
    }
    i++;
    while(i < splitText.size() && !splitText.get(i).equals(")")){
      for(int j = 0; j < 3; j++) {
         if(Interpreter.isKeyword(splitText.get(i))) {
            i--;
            return i; // If keyword is found mid-relation, throw away current relation and return to interpret()
         }
         if (Interpreter.isBreakChar(splitText.get(i))) {
           return i; //make sure there are no break chars as attributes
         }
         if(j == 1 && (!splitText.get(i).toLowerCase().equals("num") && !splitText.get(i).toLowerCase().equals("char"))) {
            System.out.print("Type of attribute must be CHAR or NUM; found " + splitText.get(i) + "\n");
            return i;
         }
         if(j == 2 && !Interpreter.isPositiveInt(splitText.get(i))) {
            System.out.print("Attribute length must be a positive integer; found " + splitText.get(i) + "\n");
            return i;
         }
         i++;
      }
      if(splitText.get(i).equals(",")) {
         i++;
      }
      count++;
    }
    //check for end of command semicolon
    i++;
    if (i < splitText.size() && !splitText.get(i).equals(";")) {
      System.out.println("bad syntax; missing semicolon");
      i--;
      return i;
    }

    System.out.println("Creating " + relationName + " with " + count + " attributes.");
    return i;
  }

}