//Where.java - Checks formatting and correctness of where conditions for various commands
//Created by Michael Albert
//Created Febuary 25, 2019
//Revised March 10, 2019

import java.util.*;
import java.lang.*;

public class Where {

  public Where() {}

  //returns boolean that lets user know if the format is correct
  public Boolean whereFormat(ArrayList<String> splitText, int i) {
    //cannot use where on catalog
    if (splitText.get(i-1).toLowerCase().equals(Constants.CATALOG)) {
      System.out.println(Constants.ERR_BAD_FORMAT);
      return false;
    }

    while ((i+4) < splitText.size() && !splitText.get(i).equals(";")) {
      // return if first part of conditions isn't non-keyword string
      if (Helper.isKeyword(splitText.get(i+1)) || Helper.isBreakChar(splitText.get(i+1))) {
        return false;
      } 
      // return if second part of conditions isn't operator
      if (!Constants.OPERATORS.contains(splitText.get(i+2))) {
        return false;
      }
      // return if third part of conditions isn't non-keyword string
      if (Helper.isKeyword(splitText.get(i+3)) || Helper.isBreakChar(splitText.get(i+3))) {
        return false;
      }
      // check if 'and' or 'or' or ';'
      if ((!splitText.get(i+4).toLowerCase().equals("and")) && (!splitText.get(i+4).toLowerCase().equals("or"))
         && (!splitText.get(i+4).toLowerCase().equals(";")))  {
        return false;
      }
      i = i +4;
    }
    return true;
  }

  // returns a arraylist of booleans where true means that tuple matched and false means it did not
  public ArrayList<Boolean> whereFind(ArrayList<String> splitText, LinkedList<Relation> database, int i) {
    // get specified relation
    Boolean found = false;
    int j = 0;
    while (!found) {
      if (j == database.size()){
        System.out.println(Constants.ERR_NOT_FND);
        return null;
      }
      else if (database.get(j).getName().equals(splitText.get(i-1).toLowerCase())) {
        found = true;
      }
      j++;
    }
    Relation relation = database.get(j-1);
    ArrayList<Boolean> trueTuples = whereIterate(relation,splitText,i);
    return trueTuples;
  }

  public ArrayList<Boolean> whereIterate(Relation relation,ArrayList<String> splitText, int i) {
    ArrayList<ArrayList<Boolean>> andConds = new ArrayList<ArrayList<Boolean>>();
    LinkedList<Tuple> allTuples = relation.getTuples();
    ArrayList<Boolean> meetsConds;
    int relAttrForm;
    int temp = i; //make temp variable to maintain i

    while (( temp+4 < splitText.size() && (splitText.get(temp+4).equals("and") || splitText.get(temp+4).equals("or") || splitText.get(temp+4).equals(";")) && !splitText.get(temp).equals(";"))) {

      meetsConds = new ArrayList<Boolean>();
      for (int j=0; j<allTuples.size(); j++) {
        meetsConds.add(true);
      }
      //do first condition no matter what
      if(Helper.isAmbiguous(relation, splitText.get(temp+1))) {
         System.out.println(Constants.ERR_AMBIG);
         return null;
      }
      relAttrForm = Helper.getQualifiedAttributeIndex(relation, splitText.get(temp+1));
      if (relAttrForm == -1) {
        System.out.println(Constants.ERR_BAD_FORMAT);
        return new ArrayList<Boolean>();
      }
      for (int j=0; j<allTuples.size(); j++) {
        if (!Helper.compareCheck(relAttrForm, allTuples.get(j), splitText.get(temp+2), splitText.get(temp+3))) {
          meetsConds.set(j,false);
        }
      }
      //continue narrowing down if 'and' statements follow
      while (splitText.get(temp+4).toLowerCase().equals("and")) {

        temp = temp + 4;
        //narrow down List to tuples who fulfill condition
        relAttrForm = Helper.getQualifiedAttributeIndex(relation, splitText.get(temp+1));
        if (relAttrForm == -1) {
          System.out.println(Constants.ERR_BAD_FORMAT);
          return new ArrayList<Boolean>();
        }
        for (int j=0; j<allTuples.size(); j++) {
          if (meetsConds.get(j)) {
            if (!Helper.compareCheck(relAttrForm, allTuples.get(j), splitText.get(temp+2), splitText.get(temp+3))) {
              meetsConds.set(j,false);
            }
          }
        }

      }
      andConds.add(meetsConds);

      temp = temp + 4;
    }

    //now 'or' together all of the grouped 'and' Tuples into a single linkedlist
    int count = 1;
    while (count < andConds.size()) {

      //check each grouped 'and'. If it has matched tuples not in the first grouped 'and', add them.
      for (int j=0; j<andConds.get(count).size(); j++) {
        if (!andConds.get(0).get(j) && andConds.get(count).get(j)) {
          andConds.get(0).set(j,true);
        }
      }

      count++;
    }

    return andConds.get(0);
  }
}
