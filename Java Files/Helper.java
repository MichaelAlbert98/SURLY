//Helper.java - List of utility functions for Surly
//Created by Michael Albert
//Created Febuary 19, 2019
//Revised January 24, 2019

import java.util.*;

public class Helper {

  private Helper() {}

  //returns boolean that lets user know if the format is correct
  public static boolean whereFormat(ArrayList<String> splitText, int i) {
    i++;
    while ((i+4) < splitText.size() && !splitText.get(i).equals(";")) {
      //return if first part of conditions isn't non-keyword string
      if (Helper.isKeyword(splitText.get(i+1)) || Helper.isBreakChar(splitText.get(i+1))) {
        return false;
      }
      //return if second part of conditions isn't operator
      if (!Constants.OPERATORS.contains(splitText.get(i+2))) {
        return false;
      }
      //return if third part of conditions isn't non-keyword string
      if (Helper.isKeyword(splitText.get(i+3)) || Helper.isBreakChar(splitText.get(i+3))) {
        return false;
      }
      //check if 'and' or 'or' or ';'
      if ((!splitText.get(i+4).toLowerCase().equals("and")) && (!splitText.get(i+4).toLowerCase().equals("or"))
         && (!splitText.get(i+4).toLowerCase().equals(";")))  {
        return false;
      }
      i = i +4;
    }
    return true;
  }

  //returns a linkedlist of tuples which match the given conditions
  public static LinkedList<Tuple> whereFind(ArrayList<String> splitText, LinkedList<Relation> database, int i) {
    //get specified relation
    Relation relation;
    boolean found = false;
    int j = 0;
    while (!found) {
      if (database.get(j).getName().equals(splitText.get(i-1).toLowerCase())) {
        found = true;
        relation = database.get(j);
      }
      j++;
    }
    LinkedList<Tuple> allTuples = relation.getTuples();

    //create list of a list of tuples which fit each group of 'and' qualifiers
    LinkedList<LinkedList<Tuple>> andTuples = new LinkedList<LinkedList<Tuple>>();

    int relAttrForm;
    int temp = i; //make temp variable to maintain i
    while (splitText.get(temp+4).equals(";") || splitText.get(temp+4).toLowerCase().equals("and") || splitText.get(temp+4).toLowerCase().equals("or")) {
      //start off with all tuples in list
      LinkedList<Tuple> andGroupTuple = allTuples;

      //do first condition no matter what
      relAttrForm = relation.getAttrFormSpecif(splitText.get(temp+1));
      for (j=0; j<andGroupTuple.size(); j++) {
        if (!compareCheck(relAttrForm, andGroupTuple.get(j), splitText.get(temp+2), splitText.get(temp+3))) {
          andGroupTuple.remove(j);
        }
      }
      //continue narrowing down if 'and' statements follow
      while (splitText.get(temp+4).toLowerCase().equals("and")) {

        temp = temp + 4;
        //narrow down List to tuples who fulfill condition
        relAttrForm = relation.getAttrFormSpecif(splitText.get(temp+1));
        for (j=0; j<andGroupTuple.size(); j++) {
          if (!compareCheck(relAttrForm, andGroupTuple.get(j), splitText.get(temp+2), splitText.get(temp+3))) {
            andGroupTuple.remove(j);
          }
        }

      }
      andTuples.add(andGroupTuple);
      temp = temp + 4;
    }

    //now 'or' together all of the grouped 'and' Tuples into a single linkedlist
    temp = i; //remake temp
    int count = 1;
    while (count < andTuples.size()) {

      //check each grouped 'and'. If it has tuples not in the first grouped 'and', add them.
      for (j=0; j<andTuples.get(count).size(); j++) {
        if (!andTuples.get(0).contains(andTuples.get(count).get(j))) {
          andTuples.get(0).add(andTuples.get(count).get(j));
        }
      }

      count++;
    }

    return andTuples.get(0);
  }

  private static boolean compareCheck(int relAttrForm, Tuple tup, String operator, String cond) {
    boolean meetsCond = false;
    switch (operator) {
      case "=":
        if (tup.getAttr().get(relAttrForm) == cond) {
          meetsCond = true;
          return meetsCond;
        }
        break;
      case "!=":
        if (tup.getAttr().get(relAttrForm) != cond) {
          meetsCond = true;
          return meetsCond;
        }
        break;
      case "<":
        if (tup.getAttr().get(relAttrForm) < cond) {
          meetsCond = true;
          return meetsCond;
        }
        break;
      case ">":
        if (tup.getAttr().get(relAttrForm) > cond) {
          meetsCond = true;
          return meetsCond;
        }
        break;
      case "<=":
        if (tup.getAttr().get(relAttrForm) <= cond) {
          meetsCond = true;
          return meetsCond;
        }
        break;
      case ">=":
        if (tup.getAttr().get(relAttrForm) >= cond) {
          meetsCond = true;
          return meetsCond;
        }
        break;
      }
      return meetsCond;
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
