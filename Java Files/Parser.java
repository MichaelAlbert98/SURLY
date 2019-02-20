//Parser.java - Takes a filename and parses it such that relation,
//insert, and print commands are recognized.
//Created by Michael Albert
//Created January 09, 2019
//Revised January 18, 2019

import java.util.*;

public class Parser {

  public Parser() {
  }

  public void parse(String[] args) {
    try{
      ReadIn reader = new ReadIn();
      MakeTokens tokenMaker = new MakeTokens();
      Interpreter inter = new Interpreter();
      String text = reader.read(args[0]);
      ArrayList<String> tokens = tokenMaker.tokenizer(text);
      inter.interpret(tokens);
      return;
    }
    catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Please specify a file.");
      return;
    }
  }
}
