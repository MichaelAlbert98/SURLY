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
}
