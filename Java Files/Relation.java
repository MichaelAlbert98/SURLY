//Relation.java - Class for a relation in the database. Contains the relation
//name, the required tuple format, and tuples that have been inserted.
//Created by Michael Albert
//Created January 27, 2019
//Revised January 28, 2019

import java.util.*;
import java.lang.*;

public class Relation {

  private String name;
  private LinkedList<Attribute> attributeFormat;
  private LinkedList<Tuple> tuples;

   public Relation(String name, LinkedList<Attribute> af){
     this.name = name;
     this.attributeFormat = af;
     this.tuples = new LinkedList<Tuple>();
   }

   public Relation(String name) {
     this.name = name;
     this.attributeFormat = null;
     this.tuples = new LinkedList<Tuple>();
   }
   
   public String getAttributeType(int index) {
      return this.attributeFormat.get(index).getDataType();
   }
   
   public int getAttributeLength(int index) {
      return this.attributeFormat.get(index).getLength();
   }

   public String getName() {
     return this.name;
   }

   public LinkedList<Tuple> getTuples() {
     return this.tuples;
   }

}
