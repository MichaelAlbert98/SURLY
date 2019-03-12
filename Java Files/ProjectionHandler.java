import java.util.*;

public class ProjectionHandler {
   
   public ProjectionHandler(){}
   private LinkedList<Relation> db;
   
   public int project (ArrayList<String> tokens, LinkedList<Relation> database, int ix) {
      db = database;
      int newIx = getNewIx(tokens, ix);
      if(badSyntax(tokens, ix)) {
         System.out.println(Constants.ERR_BAD_FORMAT);
      }
      String name = parseName(tokens, ix);
      if(name == null) {
         return newIx;
      }
      if(illegalRelationName(name)) {
         System.out.println(Constants.ERR_NAME_OVERWRITE);
         return newIx;
      }
      Relation relation = parseRelation(tokens, ix);
      if(relation == null) {
         return newIx;
      }
      LinkedList<Attribute> attributes = parseAttributes(tokens, relation, ix);
      if(attributes.isEmpty()){
         return newIx;
      }
      LinkedList<Integer> attributePositions = getAttributePositions(attributes, relation);
      Relation projection = new Relation(name, attributes);
      projection.setTemp(true);
      //System.out.println("Name: " + name + " Attributes: " + attributes + " relation: " + relation + " projection " + "Apos: " + attributePositions + "\nEND\n\n");
      fillProjection(relation, projection, attributes, attributePositions);
      database.add(projection);
      //System.out.println("projection is \n" + projection + "positions are: " + attributePositions + "\n");
      
      return newIx;
   }
   
   private boolean badSyntax(ArrayList<String> tokens, int ix) {
      if(ix < 2) { return true; }
      if(ix + 3 > tokens.size()) { return true;}
      if(!tokens.get(ix - 1).equals("=")) {return true;}
      if(tokens.get(ix + 1).equals(";")) {return true;}
      if(tokens.get(ix + 2).equals(";")) {return true;}
      if(tokens.get(ix + 3).equals(";")) {return true;}
      
      return false;
   }
   
   // Checks if the relation name is in use by a non-temporary relation
   private boolean illegalRelationName(String n) {
      ListIterator<Relation> li = db.listIterator();
      while(li.hasNext()) {
         Relation r = li.next();
         if(r.getTemp() == false) {
            String name = r.getName();
            if(name.toLowerCase().equals(n.toLowerCase())) {
               return true;
            }
         }
      }
      return false;
   }
   
   // Fills the projection relation with the appropriate attributes
   private void fillProjection(Relation r, Relation p, LinkedList<Attribute> atts, LinkedList<Integer> aPositions){ 
      LinkedList<Tuple> relationTuples = r.getTuples();
      ListIterator<Tuple> ri = relationTuples.listIterator();
      while(ri.hasNext()) {
         LinkedList<Attribute> projectionTuple = new LinkedList<Attribute>();
         ListIterator<Attribute> ai = atts.listIterator();
         ListIterator<Integer> ii = aPositions.listIterator();
         Tuple currentTuple = ri.next();
         while(ii.hasNext()) {
            projectionTuple.push(getAttributeAt(ii.next(), currentTuple));
         }
         Tuple t = new Tuple();
         LinkedList<Attribute> projectionTupleOrdered = reverseAtts(projectionTuple);
         t.setAttr(projectionTupleOrdered);
         p.addTuple(t);
      }
   }
   
   // Gets the attribute at the specified index
   private Attribute getAttributeAt(Integer i, Tuple t) {
      ListIterator<Attribute> li = t.getAttr().listIterator();
      Attribute a = li.next();
      int ix = 0;
      while(ix < i) {
         a = li.next();
         ix++;
      }
      return a;
   }
   
   // Gets the positions of the attributes in the projection, relative to the original relation
   private LinkedList<Integer> getAttributePositions(LinkedList<Attribute> atts, Relation r) {
      LinkedList<Integer> positions = new LinkedList<Integer>();
      LinkedList<Attribute> relationAtts = r.getAttributeFormat();
      //System.out.print(atts + "\n" + relationAtts);
      ListIterator<Attribute> ai = atts.listIterator();
      while(ai.hasNext()) {
         Attribute ai_next = ai.next();
         String attributeAsString = ai_next.getRelation() + ai_next.getName();
         positions.push(new Integer(Helper.getQualifiedAttributeIndex(r, attributeAsString)));
         /* ListIterator<Attribute> ri = relationAtts.listIterator();
         int ix = 0;
         while(ri.hasNext()) {
            if(ai_next.getName().toLowerCase().equals(ri.next().getName().toLowerCase())) {
               positions.push(new Integer(ix));
            }
            ix++;
         } */
      }
      LinkedList<Integer> positionsOrdered = reverse(positions);
      return positionsOrdered;
   }
   
   // Reverses a linked list of integers
   private LinkedList<Integer> reverse(LinkedList<Integer> l) {
      LinkedList<Integer> rl = new LinkedList<Integer>();
      ListIterator<Integer> li = l.listIterator();
      while(li.hasNext()){
         rl.push(li.next());
      }
      return rl;
   }
   
   // Reverses a linked list of attributes
   private LinkedList<Attribute> reverseAtts(LinkedList<Attribute> l) {
      LinkedList<Attribute> rl = new LinkedList<Attribute>();
      ListIterator<Attribute> li = l.listIterator();
      while(li.hasNext()){
         rl.push(li.next());
      }
      return rl;
   }
   
   // Parses the projection name from the command
   private String parseName(ArrayList<String> tokens, int ix) {
      if(ix > 1) {
         // Make sure prev token is assignment operator
         if(!tokens.get(ix - 1).equals("=")) {
            return null;
         }
         String name = tokens.get(ix - 2);
         if(!Helper.isKeyword(name) && !Helper.isBreakChar(name)){
            return name;
         }
      }
      return null;
   }
   
   // Parses the attributes from the command
   private LinkedList<Attribute> parseAttributes(ArrayList<String> tokens, Relation r, int ix) {
      Stack<Attribute> attributeStack = new Stack<Attribute>();
      LinkedList<Attribute> attributes = new LinkedList<Attribute>();
      LinkedList<Attribute> relationAttributes = r.getAttributeFormat();
      do{
         ix++;
         String att = tokens.get(ix);
         if(Helper.isAmbiguous(r, att)) {
            System.out.println(Constants.ERR_AMBIG);
            return new LinkedList<Attribute>();
         }
         Attribute attribute = Helper.getQualifiedAttribute(r, att);
         if(attribute != null) {
            attributeStack.push(attribute);
         }
         ix++;
      }
      while (tokens.get(ix).equals(","));
      while(!attributeStack.empty()) {
         attributes.push(attributeStack.pop());
      }
      if(attributes.isEmpty()){ System.out.println(Constants.ERR_NO_ATTS); }
      return attributes;
   }
   
   // Gets the attribute with the given name
   private Attribute getAttribute(LinkedList<Attribute> atts, String name) {
      ListIterator<Attribute> li = atts.listIterator();
      while(li.hasNext()) {
         Attribute a = li.next();
         if(a.getName().toLowerCase().equals(name.toLowerCase())) {
            return a;
         }
      }
      return null;
   }
   
   // Parses the relation from the command
   private Relation parseRelation(ArrayList<String> tokens, int ix) {
      ix++;
      Relation r;
      String s;
      do {
         if(ix >= tokens.size()) {
            return null;
         }
         s = tokens.get(ix);
         if(Helper.isKeyword(s)) {
            return null;
         }
         r = getRelation(s);
         if(r != null) {
            return r;
         }
         ix++;
      }
      while(r == null && !tokens.get(ix).equals(";"));
      return null;
   }
   
   // Gets the appropriate index for i after the command is done executing
   private int getNewIx(ArrayList<String> tokens, int ix) {
      ix++;
      Relation r;
      String s;
      do {
         if(ix >= tokens.size()) {
            System.out.printf(Constants.ERR_END_REACHED);
            return ix - 1;
         }
         s = tokens.get(ix);
         if(Helper.isKeyword(s)) {
            return ix;
         }
         r = getRelation(s);
         if(r != null) {
            return ix;
         }
         ix++;
      }
      while(r == null);
      return ix;
   }   
   
   // Gets the relaion by the given name
   public Relation getRelation(String s) {
      ListIterator<Relation> l = db.listIterator();
      while(l.hasNext()) {
         Relation r = l.next();
        if(r.getName().toLowerCase().equals(s.toLowerCase())) {
            return r;
         }
      }
      return null;
   }  
}