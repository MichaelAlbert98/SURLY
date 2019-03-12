//Helper.java - List of utility functions for Surly
//Created by Michael Albert
//Created Febuary 19, 2019
//Revised January 24, 2019

import java.util.*;
import java.lang.*;

public class Helper {

  private Helper() {}

  public static int findSemicolon(ArrayList<String> splitText, int i) {
    while (!splitText.get(i).equals(";")) {
      i++;
    }
    return i;
  }

  public static boolean compareCheck(int relAttrForm, Tuple tup, String operator, String cond) {
    Attribute attr = tup.getAttr().get(relAttrForm);
    String type = getType(cond);

    //check that condition is of correct type for given attribute
    if (attr.getDataType().equals("num") && type.equals("num")) {
      int condNum = Integer.parseInt(cond);
      boolean meetsCond = testNumCond(Integer.parseInt(attr.getName()),operator,Integer.parseInt(cond));
      return meetsCond;
    }

    //check that condition is of correct type for given attribute
    else if (attr.getDataType().equals("char") && type.equals("string")) {
      boolean meetsCond = testStringCond(attr.getName(),operator,cond);
      return meetsCond;
    }

    //return false if neither if statement procs
    return false;
  }

  //determines if String is an int or string.
  private static String getType(String cond) {
    if (cond.matches("-?\\d+")) {
      return "num";
    }
    return "string";
  }

  private static boolean testNumCond(int attr, String op, int cond) {
    switch (op) {
      case "=":
        if (attr == cond) {
          return true;
        }
        break;
      case "!=":
        if (attr != cond) {
          return true;
        }
        break;
      case "<":
        if (attr < cond) {
          return true;
        }
        break;
      case ">":
        if (attr > cond) {
          return true;
        }
        break;
      case "<=":
        if (attr <= cond) {
          return true;
        }
        break;
      case ">=":
        if (attr >= cond) {
          return true;
        }
        break;
    }
    return false;
  }

  private static boolean testStringCond(String attr, String op, String cond) {
    switch (op) {
      case "=":
        if (attr.equals(cond)) {
          return true;
        }
        break;
      case "!=":
        if (!attr.equals(cond)) {
          return true;
        }
        break;
    }
    return false;
  }

  // Returns true if the given string is a keyword (relation, insert, print: case insensitive)
  public static boolean isKeyword(String s) {
    if(s.toLowerCase().equals(Constants.RELATION) || s.toLowerCase().equals(Constants.INSERT) || s.toLowerCase().equals(Constants.PRINT)) {
      System.out.println("Keyword found. Either a break character is missing or you inserted a command word where it shouldn't be.");
      return true;
    }
    return false;
  }

  // Returns true if the given string is a break character
  public static boolean isBreakChar(String s) {
    if(s.equals("(") || s.equals(")") || s.equals("=") || s.equals("!=") || s.equals("<") || s.equals("<=") || s.equals(">")
        || s.equals(">=") || s.equals(";") || s.equals("*") || s.equals("\'") || s.equals(",")) {
      System.out.println("Break char found where it shouldn't be. Check the file for typos.");
      return true;
    }
    return false;
  }

   public static boolean isPositiveInt(String s) {
      try{
         if(Integer.parseInt(s) > 0) {
            return true;
         }
         return false;
         }
         catch(Exception e) {
            return false;
         }
   }

   public static String formatString(String s, int size) {
      String leadingSpaces = "";
      String trailingSpaces = "";
      String choppedString = "";
      int k = 0;
      while( k < s.length() && k < size ) {
         choppedString += s.charAt(k);
         k++;
      }
      for(int i = 0; i < size - choppedString.length(); i++) {
         if(i%2 == 0) {
            leadingSpaces += " ";
         }
         else {
            trailingSpaces += " ";
         }
      }
      return leadingSpaces + choppedString + trailingSpaces;
   }

   // Returns the index of a qualified attribute in a relation
   // Ex:
   // Relation join has the following attribute format: prereq.cnum, prereq.pnum, course.cnum, course.title, course.credits
   // getQualifiedAttributeIndex(join, "prereq.cnum") returns 0
   // getQualifiedAttributeIndex(join, "prereq.title") returns -1
   // getQualifiedAttributeIndex(join, "course.cnum") returns 2


   public static int getQualifiedAttributeIndex(Relation r, String s) {
      int dot = isQualifiedAttribute(s);
      String attributeName;
      String relationName = "";
      if(dot == -1) {
         attributeName = s;
      }
      else {
         relationName = s.substring(0,dot);
         attributeName = s.substring(dot + 1,s.length());
      }
      //System.out.println(relationName + ", " + attributeName);
      int ix = 0;
      ListIterator<Attribute> rIter = r.getAttributeFormat().listIterator();
      while(rIter.hasNext()) {
         Attribute a = rIter.next();
         //System.out.println("attribute: " + a.getRelation() + "." + a + ", attName: " + attributeName);
         if(a.getName().equals(attributeName)) {
            if(dot != -1) {
               if(a.getRelation() !="" && relationName.equals(a.getRelation())) {
                  return ix;
               }
            }
            else {
               return ix;
            }
         }
         ix++;
      }
      return -1;
   }

   // Returns true if the given string is an ambiguous attribute for the given relation
   // Ex:
   // for join = prereq.cnum, prereq.pnum, course.cnum, course.title, course.credits
   // isAmbiguous(join, cnum, database) returns true
   // isAmbiguous(join, title, database) returns false
   public static boolean isAmbiguous(Relation r, String attribute) {
      int dot = isQualifiedAttribute(attribute);
      if(dot > -1) {
         return false;
      }
      ListIterator<Attribute> relationAttributesIterator = r.getAttributeFormat().listIterator();
      int count = 0;
      while(relationAttributesIterator.hasNext()) {
         String name = relationAttributesIterator.next().getName();
         if(name.equals(attribute)) {
            count++;
         }
      }
      if(count > 1) {
         return true;
      }
      return false;


      /*String relationA = "";
      String relationB = "";
      ListIterator<Attribute> relationAttributesIterator = r.getAttributeFormat().listIterator();
      while(relationAttributesIterator.hasNext()) {
         Attribute a = relationAttributesIterator.next();
         if(!a.getRelation().equals("")) {
            if(relationA.equals("")) {
               relationA = a.getRelation();
            }
            else if (relationA.equals(a.getRelation())) {
               // do nothing
            }
            else {
               relationB = a.getRelation();
            }
         }
      }
      Relation a = getRelationByName(relationA, database);
      Relation b = getRelationByName(relationB, database);
      if(b == null) {
         return false;
      }
      boolean aContainsAttribute = false;
      boolean bContainsAttribute = false;
      LinkedList<Attribute> aAttributeFormat = a.getAttributeFormat();
      for(int i = 0; i < aAttributeFormat.size(); i++) {
         if(aAttributeFormat.get(i).getName().equals(attribute)) {
            aContainsAttribute = true;
         }
      }
      LinkedList<Attribute> bAttributeFormat = b.getAttributeFormat();
      for(int j = 0; j < bAttributeFormat.size(); j++) {
         if(bAttributeFormat.get(j).getName().equals(attribute)) {
            bContainsAttribute = true;
         }
      }
      System.out.println("Relation a: " + relationA + ", relation b: " + relationB);
      return aContainsAttribute && bContainsAttribute;
      */
   }

   // Gets an atribute from a relation, including qualified attributes
   // Ex:
   // getQualifiedAttribute(course, "course.cnum") returns the attribute cnum from the course relation
   // getQualifiedAttribute(course, "prereq.cnum") returns null
   public static Attribute getQualifiedAttribute(Relation r, String s) {
      int dot = isQualifiedAttribute(s);
      String attributeName;
      String relationName = "";
      if(dot == -1) {
         attributeName = s;
      }
      else {
         relationName = s.substring(0,dot);
         attributeName = s.substring(dot + 1,s.length());
      }
      // System.out.println(relationName + ", " + attributeName);
      ListIterator<Attribute> rIter = r.getAttributeFormat().listIterator();
      while(rIter.hasNext()) {
         Attribute a = rIter.next();
         // System.out.println("attribute: " + a.getRelation() + "." + a + ", attName: " + attributeName);
         if(a.getName().equals(attributeName)) {
            if(dot != -1) {
               if(a.getRelation() !="" && relationName.equals(a.getRelation())) {
                  return a;
               }
            }
            else {
               return a;
            }
         }
      }
      return null;
   }

   // Returns location of the '.' in a qualified attribute. If attribute is not qualified, returns -1.
   // String s- the string representation of the attribue, including qualifier
   // Ex:
   // isQualifiedAttribute("COURSE.CNUM") returns 6,
   // isQualifiedAttribute(".COURSECNUM") returns 0 (which should generate an error in the calling function)
   // isQualifiedAttribute("CNUM") returns -1
   private static int isQualifiedAttribute(String s) {
      int ix;
      for(ix = 0; ix < s.length(); ix++) {
         if(s.charAt(ix) == '.') {
            return ix;
         }
      }
      return -1;
   }

   public static Relation getRelationByName(String name, LinkedList<Relation> database) {
      ListIterator<Relation> iter = database.listIterator();
      while(iter.hasNext()) {
         Relation r = iter.next();
         if(r.getName().equals(name)) {
            return r;
         }
      }
      return null;
   }

}
