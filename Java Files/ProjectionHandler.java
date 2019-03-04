import java.util.*;

public class ProjectionHandler {
   
   public ProjectionHandler(){}
   private LinkedList<Relation> db;
   
   public int project (ArrayList<String> tokens, LinkedList<Relation> database, int ix) {
      db = database;
      String name = parseName(tokens, ix);
      Relation relation = parseRelation(tokens, ix);
      LinkedList<Attribute> attributes = parseAttributes(tokens, relation, ix);
      System.out.println("name: " + name + "\nRelation: " + relation + "\nAttributes" + attributes);
      return ix + 1;
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