public class StringFormatter {

   public static String formatString(String s, int size) {
      String leadingSpaces = "";
      String trailingSpaces = "";
   
         for(int i = 0; i < size - s.length(); i++) {
            if(i%2 == 0) {
               leadingSpaces += " ";
            }
            else {
               trailingSpaces += " ";
            }
         }
         return leadingSpaces + s + trailingSpaces;
   }

}