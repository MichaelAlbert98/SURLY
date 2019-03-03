//Tuple.java - Class for a tuple in the database. Contains a LinkedList of
//attributes.
//Created by Michael Albert
//Created January 27, 2019
//Revised January 28, 2019

import java.util.*;
import java.lang.*;

public class Tuple {

  private String name;
  private LinkedList<Attribute> attributes;

   // -------------- Constructors ---------------------
   public Tuple(){
     this.name = null;
     this.attributes = new LinkedList<Attribute>();
   }

   public Tuple(String name) {
     this.name = name;
     this.attributes = new LinkedList<Attribute>();
   }

   // --------------- Getters and Setters -------------------------

   public LinkedList<Attribute> getAttr() {
     return this.attributes;
   }

   public String getName() {
     return this.name;
   }

   public void setAttr(LinkedList<Attribute> attr) {
     this.attributes = attr;
     return;
   }

   public void setName(String name) {
     this.name = name;
     return;
   }

   // --------------- Other Methods --------------------

   public void addAttribute(Attribute a){
      attributes.add(a);
   }

   public String catalogPrint() {
      String ret = "Relation name: " + this.name + ", Attribute format: ";
      for(int i = 0; i < attributes.size(); i++) {
         ret += attributes.get(i).toString();
         if(i < attributes.size() - 1) {
            ret += " | ";
         }
      }
      ret += "\n";
      return ret;
   }

   public String valueToString() {
      String ret = "";
      for (int i = 0; i < attributes.size(); i++) {
         ret += attributes.get(i).valueToString() + " ";
      }
      return ret;
   }

   public boolean equals(Tuple t) {
      LinkedList<Attribute> otherAttributes = t.getAttr();
      for(int i = 0; i < attributes.size(); i++ ) {
         if(i >= otherAttributes.size()) {
            return false;
         }
         if(!otherAttributes.get(i).equals(attributes.get(i))) {
            return false;
         }
      }
      return true;
   }

   //Creates a deep copy so temp relations can be created without references to original
   public Tuple deepCopy() {
     Tuple copy = new Tuple();
     LinkedList<Attribute> copyAttrs = new LinkedList<Attribute>();
     for (int i = 0; i < this.attributes.size(); i++) {
       Attribute copyAttr = this.attributes.get(i).deepCopy();
       copyAttrs.add(copyAttr);
     }
     copy.setName(this.name);
     copy.setAttr(copyAttrs);
     return copy;
   }
}
