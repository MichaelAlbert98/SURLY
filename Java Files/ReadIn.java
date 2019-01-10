//ReadIn.java - Turns a given text file into a string, ignoring comments
//Created by Michael Albert
//Created January 08, 2019
//Revised January 09, 2019

import java.util.*;
import java.io.FileReader;

public class ReadIn {
  //constructor
  private ReadIn() {
  }

  //reads the text file, ignoring comments, and returns a string of the text
  public static String reader(String fileName) {
    String result = new String("");
    try {
      //create scanner that ignores text between /* */
      Scanner in = new Scanner(new FileReader(fileName + ".txt")).useDelimiter(("\\/\\*[^*]*\\*\\/"));
      while(in.hasNext()) {
        result = result + in.next();
      }
      in.close();
    }

    //throw exception if no file with given name exists in directory
    catch(Exception e) {
      System.out.println("The file name you specified could not be found.");
    }
    return result;
  }
}
