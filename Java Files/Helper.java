//Helper.java - List of utility functions for Surly
//Created by Michael Albert
//Created Febuary 19, 2019
//Revised January 24, 2019

public class Helper {

  private Helper() {}

  //makes sure the formatting of the conditions are correct
  public static boolean whereFormat(ArrayList<String> splitText, int i) {
    i++;
    while (!splitText.get(i).equals(";")) {
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
      //return if fourth part of conditions isn't 'and' 'or', or ';'
      if ((!splitText.get(i+4).toLowerCase.equals("and")) && (!splitText.get(i+4).toLowerCase.equals("or"))
         && (!splitText.get(i+4).toLowerCase.equals(";"))) {
        return false;
      }
      i = i +4;
    }
    return true;
  }

  //returns an arraylist of relations which match the given conditions
  public static ArrayList<Relation> whereFind(ArrayList<String> splitText, LinkedList<Relation> database, int i) {

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
