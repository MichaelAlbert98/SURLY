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
    Interpreter.interpret(tokens);

    // //temp to see if tokenizer is working
    for (int i = 0; i < tokens.size();i++) {
      System.out.println(tokens.get(i));
    }
    // //end temp
    return;
  }
}
