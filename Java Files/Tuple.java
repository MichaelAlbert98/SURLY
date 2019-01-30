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

   public Tuple(){
     this.name = null;
     this.attributes = new LinkedList<Attribute>();
   }

   public Tuple(String name) {
     this.name = name;
     this.attributes = new LinkedList<Attribute>();
   }
   
   public void addAttribute(Attribute a){
      attributes.add(a);
   }

   public LinkedList<Attribute> getAttr() {
     return this.attributes;
   }

   public String getName() {
     return this.name;
   }
   
   public String toString() {
      String ret = "Tuple name: " + this.name;
      for(int i = 0; i < attributes.size(); i++) {
         ret += attributes.get(i).toString();
      }
      return ret;
   }
}
