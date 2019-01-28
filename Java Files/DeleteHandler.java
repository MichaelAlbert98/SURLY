import java.util.*;
import java.lang.*;

public class DeleteHandler {

   private DeleteHandler(){}

   public static int delete(ArrayList<String> splitText, LinkedList database, int i) {
     j = i + 1;
     k = i + 2;
     if (k < splitText.size() && !Interpreter.isKeyword(splitText.get(j)) && !Interpreter.isBreakChar(splitText.get(j))
        && splitText.get(k).trim().equals(";")) {
        String relationName = splitText.get(j).trim();
        for (int a = 1; a < database.size(); a++) {
          if (database.get(a).getName().equals(relationName)) {
            database.get(a).getTuples().clear();
            return i + 3;
          }
        }
        System.out.println("Relation name not found.");
     }
     System.out.println("Delete command not correctly formatted.");
     return i;
   }
}
