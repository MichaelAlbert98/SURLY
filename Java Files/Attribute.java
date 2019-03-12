//Attribute.java - Class for an attribute in the database. Contains the attribute
//name, the datatype, and the length.
//Created by Michael Albert
//Created January 27, 2019
//Revised January 28, 2019

import java.util.*;
import java.lang.*;

public class Attribute {

   private String name;
   private String dataType;
   private String relation;
   private int length;

   //constructor
   public Attribute(String name, String dataType, int length){
     this.name = name;
     this.dataType = dataType;
     this.length = length;
     this.relation = "";
   }

   // -------------------Getters ---------------------------
   public String getName() {
      return name;
   }

   public String getDataType() {
      return this.dataType;
   }

   public int getLength() {
      return this.length;
   }
   
   public String getRelation() {
      return this.relation;
   }

   // ---------------Other Methods -------------------------

   public void setRelation(String r) {
      this.relation = r;
   }
   
   public String toString() {
      return "" + name + " " + dataType + " " + length + "";
   }

   public String valueToString() {
      return Helper.formatString(name, length);
   }

   public boolean equals(Attribute a) {
      if(!a.getName().equals(name)) {
         return false;
      }
      return true;
   }

   //Creates a deep copy so temp relations can be created without references to original
   public Attribute deepCopy() {
     Attribute copy = new Attribute(this.name,this.dataType,this.length);
     copy.setRelation(this.getRelation());
     return copy;
   }
}
