//Parser.java - Takes a filename and parses it such that relation,
//insert, and print commands are recognized.
//Created by Michael Albert
//Created January 09, 2019
//Revised January 10, 2019

import java.util.*;

public class Parser {

  private Parser() {
  }

  public static void parse(String filename) {
    String text = ReadIn.reader(filename);
    ArrayList<String> tokens = MakeTokens.tokenizer(text);
    //temp
    for (int i = 0; i < tokens.size();i++) {
      System.out.println(tokens.get(i));
    }
    return;
  }
}
