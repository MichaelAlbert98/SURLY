//Constants.java - List of constants used by Surly
//Created by Michael Albert and Jacob Coffland
//Created Febuary 27, 2019
//Revised March 10, 2019

import java.util.*;

public class Constants {

  //error messages
  public final static String ERR_END_REACHED = "End of document reached.";
  public final static String ERR_BAD_FORMAT = "Format is not correct.";
  public final static String ERR_NOT_FND = "Could not find the specified name.";
  public final static String ERR_DUP_ITEM = "This name already exists.";
  public final static String ERR_NAME_OVERWRITE = "Cannot overwrite a non-temporary relation.";
  public final static String ERR_TEMP_MODIFY = "Cannot modify temporary relations";
  public final static String ERR_ATT_OVERFLOW = "Too many attributes for selected relation";
  public final static String ERR_NO_ATT = "No insert names given.";
  public final static String ERR_INST_CAT = "Cannot insert tuples to Catalog";
  public final static String ERR_NO_ATTS = "No attributes specified";
  public final static String ERR_NOT_QUALIFIED = "Tuple to be inserted did not meet relation qualifiers.";
  public final static String ERR_JOIN_SYNTAX = "Bad syntax for join";
  public final static String ERR_JOIN_LOGIC = "Join Handler logic error";
  public final static String ERR_AMBIG = "ambiguous attribute";

  //string constants
  public final static String CATALOG = "catalog";
  public final static String RELATION = "relation";
  public final static String INSERT = "insert";
  public final static String PRINT = "print";
  public final static String DESTROY = "destroy";
  public final static String DELETE = "delete";
  public final static String SELECT = "select";
  public final static String PROJECT = "project";
  public final static String INTEGRITY_CONSTRAINT = "integrity_constraint";
  public final static String JOIN = "join";
  public final static String CHAR = "char";
  public final static String NUM = "num";

  //object constants
  public final static ArrayList<String> OPERATORS = new ArrayList<>(Arrays.asList("=","!=","<",">","<=",">="));

}
