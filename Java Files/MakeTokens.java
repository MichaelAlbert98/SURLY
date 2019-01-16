//MakeTokens.java - Breaks a text file into a list of tokens
//Created by Michael Albert
//Created January 09, 2019
//Revised January 10, 2019

import java.util.*;

public class MakeTokens {

  //constructor
  private MakeTokens() {
  }

  //takes a string of text and converts it to valid tokens.
  public static ArrayList<String> tokenizer(String text) {
    ArrayList<String> tokenList = new ArrayList<String>();
    int i = 0;
    while (i < text.length()) {
      switch (text.charAt(i)) {
        case '(':
          tokenList.add("(");
          i++;
          break;
        case ')':
          tokenList.add(")");
          i++;
          break;
        case '=':
          tokenList.add("=");
          i++;
          break;
        case '!':
          if (text.charAt(i+1) == ('=')) {
            tokenList.add("!=");
            i = i + 2;
          }
          else {
            tokenList.add("!");
            i++;
          }
          break;
        case '<':
          if (text.charAt(i+1) == ('=')) {
            tokenList.add("<=");
            i = i + 2;
          }
          else {
            tokenList.add("<");
            i++;
          }
          break;
        case '>':
          if (text.charAt(i+1) == ('=')) {
            tokenList.add(">=");
            i = i + 2;
          }
          else {
            tokenList.add(">");
            i++;
          }
          break;
        case ';':
          tokenList.add(";");
          i++;
          break;
        case '*':
          tokenList.add("*");
          i++;
          break;
        case '\'':
          String result = "";
          while ((i < text.length())) {
            i++;
            if (text.charAt(i) == '\'') {
              i++;
              break;
            }
            result = result + text.charAt(i);
          }
          //extra break in case of missing '
          break;
        case ',':
          tokenList.add(",");
          i++;
          break;
        case ' ':
          //not needed?
          //tokenList.add(" ");
          i++;
          break;
        default:
          result = "";
          while ((i < text.length()) && (text.charAt(i) != '(') && (text.charAt(i) != ')') && (text.charAt(i) != '=') && (text.charAt(i) != '(')
                && (text.charAt(i) != '<') && (text.charAt(i) != '>') && (text.charAt(i) != ';') && (text.charAt(i) != '*')
                && (text.charAt(i) != '\'') && (text.charAt(i) != ',') && (text.charAt(i) != ' ')) {
            result = result + text.charAt(i);
            i++;
          }
            tokenList.add(result);
          break;
      }
    }
    return tokenList;
  }
}
