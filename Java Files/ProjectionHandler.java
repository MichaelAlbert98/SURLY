import java.util.*;

public class ProjectionHandler {
   
   public ProjectionHandler(){}
   private LinkedList<Relation> db;
   
   public int project (ArrayList<String> tokens, LinkedList<Relation> database, int ix) {
      db = database;
      String name = parseName(tokens, ix);
      Relation relation = parseRelation(tokens, ix);
      LinkedList<Attribute> attributes = parseAttributes(tokens, relation, ix);
      LinkedList<Integer> attributePositions = getAttributePositions(attributes, relation);
      Relation projection = new Relation(name, attributes);
      projection.setTemp(true);
      fillProjection(relation, projection, attributes, attributePositions);
      database.add(projection);
      //System.out.println("projection is \n" + projection + "positions are: " + attributePositions + "\n");
      
      return getNewIx(tokens, ix);
   }
   
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
         //System.out.println("Tuple created! " + t.getAttr());
      }
   }
   
   private Attribute getAttributeAt(Integer i, Tuple t) {
      //System.out.print("int is " + i + " atts are: " + t.getAttr()+"\n");
      ListIterator<Attribute> li = t.getAttr().listIterator();
      Attribute a = li.next();
      int ix = 0;
      while(ix < i) {
         a = li.next();
         ix++;;
      }
      return a;
   }
   
   private LinkedList<Integer> getAttributePositions(LinkedList<Attribute> atts, Relation r) {
      LinkedList<Integer> positions = new LinkedList<Integer>();
      LinkedList<Attribute> relationAtts = r.getAttributeFormat();
      //System.out.print(atts + "\n" + relationAtts);
      ListIterator<Attribute> ai = atts.listIterator();
      while(ai.hasNext()) {
         Attribute ai_next = ai.next();
         ListIterator<Attribute> ri = relationAtts.listIterator();
         int ix = 0;
         while(ri.hasNext()) {
            if(ai_next.getName().equals(ri.next().getName())) {
               positions.push(new Integer(ix));
            }
            ix++;
         }
      }
      LinkedList<Integer> positionsOrdered = reverse(positions);
      return positionsOrdered;
   }
   
   private LinkedList<Integer> reverse(LinkedList<Integer> l) {
      LinkedList<Integer> rl = new LinkedList<Integer>();
      ListIterator<Integer> li = l.listIterator();
      while(li.hasNext()){
         rl.push(li.next());
      }
      return rl;
   }
   
   private LinkedList<Attribute> reverseAtts(LinkedList<Attribute> l) {
      LinkedList<Attribute> rl = new LinkedList<Attribute>();
      ListIterator<Attribute> li = l.listIterator();
      while(li.hasNext()){
         rl.push(li.next());
      }
      return rl;
   }
   
   private String parseName(ArrayList<String> tokens, int ix) {
      if(ix > 1) {
         // Make sure prev token is assignment operator
         if(!tokens.get(ix - 1).equals("=")) {
            return null;
         }
         String name = tokens.get(ix - 2);
         if(!Helper.isKeyword(name) && !Helper.isBreakChar(name) && (getRelation(name) == null)){
            return name;
         }
      }
      return null;
   }
   
   private LinkedList<Attribute> parseAttributes(ArrayList<String> tokens, Relation r, int ix) {
      Stack<Attribute> attributeStack = new Stack<Attribute>();
      LinkedList<Attribute> attributes = new LinkedList<Attribute>();
      LinkedList<Attribute> relationAttributes = r.getAttributeFormat();
      do{
         ix++;
         String att = tokens.get(ix);
         Attribute attribute = getAttribute(relationAttributes, att);
         if(attribute != null) {
            attributeStack.push(attribute);
         }
         ix++;
      }
      while (tokens.get(ix).equals(","));
      while(!attributeStack.empty()) {
         attributes.push(attributeStack.pop());
      }
      return attributes;
   }
   
   private Attribute getAttribute(LinkedList<Attribute> atts, String name) {
      ListIterator<Attribute> li = atts.listIterator();
      while(li.hasNext()) {
         Attribute a = li.next();
         if(a.getName().equals(name)) {
            return a;
         }
      }
      return null;
   }
   
   private Relation parseRelation(ArrayList<String> tokens, int ix) {
      ix++;
      Relation r;
      String s;
      do {
         s = tokens.get(ix);
         r = getRelation(s);
         if(r != null) {
            return r;
         }
         ix++;
      }
      while(r == null);
      return null;
   }
   
   private int getNewIx(ArrayList<String> tokens, int ix) {
      ix++;
      Relation r;
      String s;
      do {
         s = tokens.get(ix);
         r = getRelation(s);
         if(r != null) {
            return ix;
         }
         ix++;
      }
      while(r == null);
      return ix;
      }
   
   private boolean projectionUsesAttribute(LinkedList<Attribute> atts, Attribute a) {   
      ListIterator<Attribute> li = atts.listIterator();
      while(li.hasNext()) {
         if(li.next().getName().equals(a.getName())) {
            return true;
         }   
      }
      System.out.println("couldnt find name " + a.getName());
      return false;
   }
   
   public Relation getRelation(String s) {
      ListIterator<Relation> l = db.listIterator();
      while(l.hasNext()) {
         Relation r = l.next();
        if(r.getName().equals(s)) {
            return r;
         }
      }
      return null;
   }  
}