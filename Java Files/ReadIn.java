//ReadIn.java - Turns a given text file into a string, ignoring comments
//Created by Michael Albert
//Created January 08, 2019
//Revised January 16, 2019

import java.util.*;
import java.io.FileReader;

public class ReadIn {
  //constructor
  public ReadIn() {
  }

  //reads the text file, ignoring comments, and returns a string of the text
  public String read(String fileName) {
    String result = new String("");
    try {
      //create scanner that ignores comments and newlines
      Scanner in = new Scanner(new FileReader(fileName)).useDelimiter("\\/\\*[^*]*\\*\\/|\\n|\\r");
      while(in.hasNext()) {
        result = result + in.next() + " ";
      }
      in.close();
    }

    //throw exception if no file with given name exists in directory
    catch(Exception e) {
      System.out.println("The file name you specified could not be found.");
    }
    //System.out.print(result);
    return result;
  }
}
