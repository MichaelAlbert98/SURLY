//Helper.java - List of utility functions for Surly
//Created by Michael Albert
//Created Febuary 19, 2019
//Revised January 24, 2019

public class Helper {

  public final static String ERR_END_REACHED = "End of document reached.";
  public final static String ERR_BAD_FORMAT = "Format is not correct.";
  public final static String ERR_NOT_FND = "Could not find the specified name.";
  public final static String ERR_DUP_ITEM = "This name already exists.";
  public final static String ERR_ATT_OVERFLOW = "Too many attributes for selected relation";
  public final static String ERR_NO_ATT = "No insert names given.";
  public final static String ERR_INST_CAT = "Cannot insert tuples to Catalog";

  private Helper() {}

  // Returns true if the given string is a keyword (relation, insert, print: case insensitive)
  public static boolean isKeyword(String s) {
    if(s.toLowerCase().equals("relation") || s.toLowerCase().equals("insert") || s.toLowerCase().equals("print")) {
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
