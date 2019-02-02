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

   // ----------------- Constructors -----------------------
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
   
   // -------------------Getters ---------------------------

   public String getName() {
     return this.name;
   }
   
   public LinkedList<Tuple> getTuples() {
     return this.tuples;
   }
   
   public LinkedList<Attribute> getAttributeFormat() {
     return this.attributeFormat;
   }
   
   public String getAttributeType(int index) {
      return this.attributeFormat.get(index).getDataType();
   }
   
   public int getAttributeLength(int index) {
      return this.attributeFormat.get(index).getLength();
   }

   // ---------------- Other methods ----------------------
   
   public void addTuple(Tuple t) {
      tuples.add(t);
   }
   
   //Checks to see if the relation already contains a given tuple
   public boolean contains(Tuple t) {
      for(int i = 0; i < tuples.size(); i++) {
         if(tuples.get(i).equals(t)) {
            return true;
         }
      }
      return false;
   }
   
   public String toString() {
      String ret = "";
      ret += "Relation name: " + name + ", ";
      ret += "Attribute format: ";
      for(int j = 0; j < attributeFormat.size(); j++) {
         ret += attributeFormat.get(j);
         if(j < attributeFormat.size() -1) {
            ret += " | ";
         }
      }
      ret += "\n-------------------------------------------------\n";
      for(int j = 0; j < attributeFormat.size(); j++) {
      
         ret += StringFormatter.formatString(attributeFormat.get(j).getName(), attributeFormat.get(j).getLength()) + " ";
      }
      ret += "\n";
      for(int j = 0; j < tuples.size(); j++) {
         ret += tuples.get(j).valueToString() + "\n";
      }
      return ret;
   }
}
