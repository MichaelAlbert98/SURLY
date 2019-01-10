//Main.java - Starts the Lexical Anaylzer.
//Created by Michael Albert
//Created January 08, 2019
//Revised January 08, 2019

public class Main {

  public static void main(String[] args) {
    try {
      Parser.parse(args[0]);
      return;
    }
    catch (Exception e) {
      System.out.println("Please specify a command file.");
    }
  }
}
