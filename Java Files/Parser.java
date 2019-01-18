//Parser.java - Takes a filename and parses it such that relation,
//insert, and print commands are recognized.
//Created by Michael Albert
//Created January 09, 2019
//Revised January 18, 2019

import java.util.*;

public class Parser {

  private Parser() {
  }

  public static void parse(String[] args) {
    try{
      String text = ReadIn.reader(args[0]);
      ArrayList<String> tokens = MakeTokens.tokenizer(text);
      Interpreter.interpret(tokens);
      return;
    }
    catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Please specify a file.");
      return;
    }
  }
}
