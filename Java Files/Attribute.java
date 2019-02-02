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
   private int length;

   //constructor
   public Attribute(String name, String dataType, int length){
     this.name = name;
     this.dataType = dataType;
     this.length = length;
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
   
   // ---------------Other Methods -------------------------
   
   public String toString() {
      return "" + name + " " + dataType + " " + length + "";
   }
   
   public String valueToString() {
      return StringFormatter.formatString(name, length);
   }
   
   public boolean equals(Attribute a) {
      if(!a.getName().equals(name)) {
         return false;
      }
      return true;
   }
   
}
