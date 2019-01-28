import java.util.*;
import java.lang.*;

public class Relation {

  private String name;
  private ArrayList attributeFormat;
  private LinkedList tuples;

   public Relation(String name, ArrayList af){
     this.name = name;
     this.attributeFormat = af;
     this.tuples = new LinkedList();
   }

   public Relation(String name) {
     this.name = name;
     this.attributeFormat = null;
     this.tuples = new LinkedList();
   }

   public String getName() {
     return this.name;
   }

   public LinkedList getTuples() {
     return this.tuples;
   }
}
