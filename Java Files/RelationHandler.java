import java.util.*;
import java.lang.*;

public class RelationHandler {
  //attributes
  private int count;

   public RelationHandler(){}

   public int relation(ArrayList<String> splitText, LinkedList<Relation> database, int i) {
    count = 0;
    String relationName = "";
    Tuple catTuple;
    LinkedList<Attribute> attributeFormat = new LinkedList<Attribute>();
    i++;
    if (i < splitText.size() && !Helper.isKeyword(splitText.get(i)) && !Helper.isBreakChar(splitText.get(i))) {
      relationName = splitText.get(i).toLowerCase(); //make sure relation name is valid
      catTuple = new Tuple(relationName);
      if (alreadyExists(relationName,database)) {
        return i;
      }
    }
    else {
      i--;
      return i;
    }
    i++;
    if(!splitText.get(i).equals("(")) {
      System.out.println(Helper.ERR_BAD_FORMAT);
      return i; // Fail on bad syntax
    }
    i++;
    while(i < splitText.size() && !splitText.get(i).equals(")")){
      i = formatCheck(splitText,i);
      Attribute attr = new Attribute(splitText.get(i-3).toLowerCase(),splitText.get(i-2).toLowerCase(),Integer.parseInt(splitText.get(i-1)));
      catTuple.getAttr().add(attr); //add attributes to catalog tuple and attributeFormat
      attributeFormat.add(attr);
      if(splitText.get(i).equals(",")) {
         i++;
      }
      count++;
    }
    i++;
    if (i < splitText.size() && !splitText.get(i).equals(";")) {
      System.out.println(Helper.ERR_BAD_FORMAT);
      i--;
      return i;
    }
    database.get(0).getTuples().add(catTuple); //add relation to catalog
    Relation relation = new Relation(relationName,attributeFormat); //create relation and add it to database
    database.add(relation);
    System.out.println("Creating " + relationName + " with " + count + " attributes.");
    return i;
  }

  private Boolean alreadyExists(String name, LinkedList<Relation> database) {
    for (int i = 1; i < database.size();i++) {
      if (database.get(i).getName().equals(name) || name.equals("catalog")) {
        System.out.println(Helper.ERR_DUP_ITEM);
        return true;
      }
    }
    return false;
  }

  private int formatCheck(ArrayList<String> splitText, int i) {
    for(int j = 0; j < 3; j++) {
       if(Helper.isKeyword(splitText.get(i))) {
          i--;
          return i; // If keyword is found mid-relation, throw away current relation and return to interpret()
       }
       if (Helper.isBreakChar(splitText.get(i))) {
         return i; //make sure there are no break chars as attributes
       }
       if(j == 1 && (!splitText.get(i).toLowerCase().equals("num") && !splitText.get(i).toLowerCase().equals("char"))) {
          System.out.print(Helper.ERR_BAD_FORMAT);
          return i;
       }
       if(j == 2 && !Helper.isPositiveInt(splitText.get(i))) {
          System.out.print(Helper.ERR_BAD_FORMAT);
          return i;
       }
       i++;
    }
    return i;
  }
}
