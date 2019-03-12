import java.util.*;

public class JoinHandler {
   
   private LinkedList<Relation> db;
   private final int DEBUG_LEVEL = 0;
   
   public JoinHandler(){}
   public int join(ArrayList<String> tokens, LinkedList<Relation> database, int i) {
      
      db = database;
      // Check to make sure there are at least 2 tokens before i and 8 after
      int valid = validateIndex(tokens, i);
      if(valid != 0) { return valid; }
      
      // -------------------------Parsing and validation --------------------------------
      // Part 1 - Name
      String name = tokens.get(i-2);
      if(illegalName(name)) { return i + 1; }
      // Part 2 - '='
      if(!tokens.get(i - 1).equals("=")) {
         System.out.println(Constants.ERR_JOIN_SYNTAX);
         return i + 1;
      }
      // Part 3 - JOIN (note: this check shold never fail)
      if(!tokens.get(i).toLowerCase().equals("join")) {
         System.out.println(Constants.ERR_JOIN_SYNTAX);
         return i + 1;
      }
      // Part 4 - Relation A
      Relation relA = getRelation(tokens.get(i+1));
      if(relA == null) { return i + 1; }
      // Part 5 - ','
      if(!tokens.get(i + 2).equals(",")) {
         System.out.println(Constants.ERR_JOIN_SYNTAX);
         return i + 2;
      }
      // Part 6 - Relation B
      Relation relB = getRelation(tokens.get(i+3));
      if(relB == null) { return i + 3; }
      // Part 7 - ON
      if(!tokens.get(i + 4).toLowerCase().equals("on")) {
         System.out.println(Constants.ERR_JOIN_SYNTAX);
         return i + 4;
      }
      // Part 8 - Relation A Attribute
      Attribute attA = getAttribute(relA, tokens.get(i + 5), tokens.get(i + 7));
      if(attA == null) { return i + 5; }
      // Part 9 - '='
      if(!tokens.get(i + 6).equals("=")) {
         System.out.println(Constants.ERR_JOIN_SYNTAX);
         return i + 6;
      }
      // Part 10 - Relation Attribute 2
      Attribute attB = getAttribute(relB, tokens.get(i + 5), tokens.get(i + 7));
      if(attB == null) { return i + 7; }
      // Part 11 - ';'
      if(!tokens.get(i + 8).equals(";")) {
         System.out.println(Constants.ERR_JOIN_SYNTAX);
         return i + 8;
      }
      
      // ---------------------------- Computation -------------------------------//
      
      // Check if either of the attributes are ambiguous
      
      if(isAmbig(tokens.get(i + 5), relA, relB) || isAmbig(tokens.get(i + 7), relA, relB)) { return i + 8; }
      
      // Get the indecies of the matching attributes
      int attAPos = getAttributePosition(relA, attA);
      int attBPos = getAttributePosition(relB, attB);
      if(attAPos == -1 || attBPos == -1) {
         System.out.println(Constants.ERR_JOIN_LOGIC);
         return i + 8;
      }
      
      // Create the join relation      
      LinkedList<Attribute> joinAttributeFormat = getJoinAttributeFormat(relA, relB);
      Relation joinRelation = new Relation(name, joinAttributeFormat);
      joinRelation.setTemp(true);
      
      // Fill the join relation
      fillJoinRelation(joinRelation, relA, relB, attAPos, attBPos);
      
      if(DEBUG_LEVEL > 0) {
         System.out.println("-----------------------------------------------------\n" +
            "Trying to join! Syntax is good, the join name is gonna be " + name + 
            ", the relations are " + relA.getName() + " and " + relB.getName() +
            ",\nand the attributes to match on are " + relA.getName() + "." + attA.getName() + 
            " and " + relB.getName() + "." + attB.getName() + ". The attribute positions are " + attAPos + " and " + attBPos +
            ". The relation is:\n" + joinRelation);
      }
      // Search the db for a temporary relation of the same name, and delete it
      ListIterator<Relation> dbIterator = db.listIterator();
      int j = 0;
      while(dbIterator.hasNext()) {
         Relation r = dbIterator.next();
         if(r.getName().equals(name)) {
            db.remove(j);
         }
         j++;
      }
      LinkedList<Tuple> catalog = db.get(0).getTuples();
      int ix = 0;
      while(ix < catalog.size()) {
         Tuple t = catalog.get(ix);
         if(t.getName().equals(name)){ 
            catalog.remove(ix);
         }
         else {
            ix++;
         }
      }
      db.add(joinRelation);
      for(int k = 0; k < joinAttributeFormat.size(); k++) {
         Tuple catalogEntry = new Tuple(name);
         catalogEntry.addAttribute(joinAttributeFormat.get(k));
         database.get(0).getTuples().add(catalogEntry);
      }
      return i + 8;
   }
   
   private boolean isAmbig (String attr, Relation a, Relation b) {
      if(getDotIndex(attr) < 0) {
         Attribute attA = getAttribute(a, attr);
         Attribute attB = getAttribute(b, attr);
         boolean isAmbiguous = (attA != null && attB != null);
         if(isAmbiguous) {
            System.out.println(Constants.ERR_AMBIG);
            return true;
         }
      }
      return false;
   }
   
   private void fillJoinRelation(Relation joinRelation, Relation relA, Relation relB, int attAPos, int attBPos) {
      ListIterator<Tuple> relAIter = relA.getTuples().listIterator();
      while(relAIter.hasNext()) {
         LinkedList<Attribute> aTuple = relAIter.next().getAttr();
         ListIterator<Tuple> relBIter = relB.getTuples().listIterator();
         while(relBIter.hasNext()) {
            LinkedList<Attribute> bTuple = relBIter.next().getAttr();
            if(getAttributeAt(aTuple, attAPos).getName().equals(getAttributeAt(bTuple, attBPos).getName())) {
               // add the tuples together
               Tuple t = mergeTuples(aTuple, bTuple, relA.getName(), relB.getName());
               joinRelation.addTuple(t);
               if(DEBUG_LEVEL > 1) {
                  System.out.println("Found matching tuples at " + aTuple + " matches " + bTuple + ".\nNew tuple is " + t.getAttr());
               }
            }
         }   
      }
   }
   
   private Tuple mergeTuples(LinkedList<Attribute> aTuple, LinkedList<Attribute> bTuple, String relAName, String relBName) {
      LinkedList<Attribute> atts = new LinkedList<Attribute>();
      ListIterator<Attribute> aAttsIter = aTuple.listIterator();
      ListIterator<Attribute> bAttsIter = bTuple.listIterator();
      Attribute nextAttribute;
      while(aAttsIter.hasNext()) {
         nextAttribute = aAttsIter.next();
         nextAttribute.setRelation(relAName);
         atts.add(nextAttribute);
      }
      while(bAttsIter.hasNext()) {
         nextAttribute = bAttsIter.next();
         nextAttribute.setRelation(relBName);
         atts.add(nextAttribute);
      }
      Tuple t = new Tuple();
      t.setAttr(atts);
      return t;
   }
   
   private LinkedList<Attribute> getJoinAttributeFormat(Relation relA, Relation relB) {
      LinkedList<Attribute> relationAttributeFormat = new LinkedList<Attribute>();
      ListIterator<Attribute> aIter = relA.getAttributeFormat().listIterator();
      ListIterator<Attribute> bIter = relB.getAttributeFormat().listIterator();
      while(aIter.hasNext()) {
         Attribute a = aIter.next();
         a.setRelation(relA.getName());
         relationAttributeFormat.add(a);
      }
      int ix = 0;
      while(bIter.hasNext()) {
         Attribute a = bIter.next();
         a.setRelation(relB.getName());
         relationAttributeFormat.add(a);
      }
      return relationAttributeFormat;
   }
   
   private Attribute getAttributeAt(LinkedList<Attribute> list, int ix) {
      ListIterator<Attribute> li = list.listIterator();
      Attribute ret;
      do {
         ret = li.next();
         ix--;
      } while(ix >= 0);
      return ret;
   }
   
   private int getAttributePosition(Relation r, Attribute a) {
      int ix = 0;
      String aName = a.getName().toLowerCase();
      ListIterator<Attribute> li = r.getAttributeFormat().listIterator();
      while(li.hasNext()) {
         Attribute ra = li.next();
         if(ra.getName().toLowerCase().equals(aName)) {
            return ix;
         }
         ix++;
      }
      return -1;
   }
   
   private Attribute getAttribute(Relation r, String a, String b) {
      
      Attribute ret = null;
      Relation preDot;
      int dot = getDotIndex(a);
      if(dot == 0) {
         System.out.println(Constants.ERR_JOIN_SYNTAX); 
         return null;
      }
      else if(dot > 0) {
         // Get the relation given before the dot in a
         preDot = getRelation(a.substring(0,dot));
         if(preDot.getName().toLowerCase().equals(r.getName().toLowerCase())) {
            ret = getAttribute(r, a.substring(dot + 1,a.length()));
         }
      }
      else {
         ret = getAttribute(r, a);
      }
      // If the relation in string a was not found, try b
      if(ret == null) {
         dot = getDotIndex(b);
         if(dot == 0) {
            System.out.println(Constants.ERR_JOIN_SYNTAX); 
            return null;
         }
         else if(dot > 0) {
            preDot = getRelation(b.substring(0,dot));
            if(preDot == null || !preDot.getName().toLowerCase().equals(r.getName().toLowerCase())) {
               System.out.println(Constants.ERR_NOT_FND);
               return null;
            }
            ret = getAttribute(r, b.substring(dot + 1,b.length()));
         }
         else {
            ret = getAttribute(r, b);
         }
      }
      if(ret == null) {
         System.out.println(Constants.ERR_NOT_FND);
      }
      return ret;
   }
   
   private Attribute getAttribute(Relation r, String s) {
      ListIterator<Attribute> li = r.getAttributeFormat().listIterator();
      while(li.hasNext()) {
         Attribute a = li.next();
         if(a.getName().toLowerCase().equals(s.toLowerCase())) {
            return a;
         }
      }
      return null;
   }
   
   private int getDotIndex(String s) {
      int ix;
      for(ix = 0; ix < s.length(); ix++) {
         if(s.charAt(ix) == '.') {
            return ix;
         }
      }
      return -1;
   }
   
   private Relation getRelation(String s) {
      ListIterator<Relation> ri = db.listIterator();
      while(ri.hasNext()) {
         Relation r = ri.next();
         if(s.toLowerCase().equals(r.getName())) {
            return r;
         }
      }
      System.out.println(Constants.ERR_NOT_FND);
      return null;
   }
   
   // Check to make sure there are at least 2 tokens before i and 8 after
   private int validateIndex(ArrayList<String> tokens, int i) {
      if(i < 2 ) {
         System.out.println(Constants.ERR_JOIN_SYNTAX);
         return 2;
      }
      if(i + 8 >= tokens.size()) {
         System.out.println(Constants.ERR_END_REACHED);
         return i + 1;
      }
      return 0;
   }
   
   // Checks if the given name is in use by a non temporary relation or is a keyword/break char
   private boolean illegalName(String n) {
      if(Helper.isBreakChar(n) || Helper.isKeyword(n)) {
         return true; // Error messages handled by Helper
      }
      ListIterator<Relation> it = db.listIterator();
      while(it.hasNext()) {
         Relation r = it.next();
         // If the name is in use by a non temporary relation, its illegal
         if(!r.getTemp() && r.getName().equals(n.toLowerCase())) {
            System.out.println(Constants.ERR_NAME_OVERWRITE);
            return true;
         }
      }
      return false;
   }
}