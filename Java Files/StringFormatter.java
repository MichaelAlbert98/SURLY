public class StringFormatter {

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