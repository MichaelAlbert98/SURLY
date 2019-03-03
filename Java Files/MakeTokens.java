//MakeTokens.java - Breaks a text file into a list of tokens
//Created by Michael Albert and Jacob Coffland
//Created January 09, 2019
//Revised January 18, 2019

import java.util.*;

public class MakeTokens {

  private int i = 0;

  //constructor
  public MakeTokens() {
  }

  //takes a string of text and converts it to valid tokens.
  public ArrayList<String> tokenizer(String text) {
    ArrayList<String> tokenList = new ArrayList<String>();
    while (i < text.length()) {
      switch (text.charAt(i)) {
        case '(':
          tokenList = caseChar("(",tokenList);
          break;
        case ')':
          tokenList = caseChar(")",tokenList);
          break;
        case '=':
          tokenList = caseChar("=",tokenList);
          break;
        case '!':
          tokenList = caseChar2("!",tokenList,text);
          break;
        case '<':
          tokenList = caseChar2("<",tokenList,text);
          break;
        case '>':
          tokenList = caseChar2(">",tokenList,text);
          break;
        case ';':
          tokenList = caseChar(";",tokenList);
          break;
        case '*':
          tokenList = caseChar("*",tokenList);
          break;
        case '\'':
          String result = "";
          while ((i < text.length() - 1)) {
            i++;
            if (text.charAt(i) == '\'') {
              i++;
              tokenList.add(result.toLowerCase());
              break;
            }
            result = result + text.charAt(i);
          }
          //extra break in case of missing '
          break;
        case ',':
          tokenList = caseChar(",",tokenList);
          break;
        case ' ':
          i++;
          break;
        default:
          tokenList = caseCharDefault(tokenList,text);
          break;
      }
    }
    return tokenList;
  }

  private ArrayList<String> caseChar(String value, ArrayList<String> tokenList) {
    tokenList.add(value);
    i++;
    return tokenList;
  }

  private ArrayList<String> caseChar2(String value, ArrayList<String> tokenList, String text) {
    if (text.charAt(i+1) == ('=')) {
      tokenList.add(value + "=");
      i = i + 2;
    }
    else {
      tokenList = caseChar(value,tokenList);
    }
    return tokenList;
  }

  private ArrayList<String> caseCharDefault(ArrayList<String> tokenList, String text) {
    String result = "";
    while ((i < text.length()) && (text.charAt(i) != '(') && (text.charAt(i) != ')') && (text.charAt(i) != '=') && (text.charAt(i) != '(')
          && (text.charAt(i) != '<') && (text.charAt(i) != '>') && (text.charAt(i) != ';') && (text.charAt(i) != '*')
          && (text.charAt(i) != '\'') && (text.charAt(i) != ',') && (text.charAt(i) != ' ')) {
      result = result + text.charAt(i);
      i++;
    }
    tokenList.add(result.toLowerCase());
    return tokenList;
  }
}
