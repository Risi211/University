/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package p2;

public class FOOLlib {

  private static int counter=0;
  private static int funCounter=0;
  private static String functionCode="";
    
  public static boolean isSubtype (Node a, Node b) {
    boolean ret=false; 
    if ( (a.toPrint("")).equals(b.toPrint("")) ) ret=true;
    if ( (a.toPrint("")).equals("bool") && (b.toPrint("")).equals("int") ) ret=true;
    return ret;
  } 
  
  public static String getLabel() {
    return "label"+(counter++);
  }
  
  public static String getFunctionLabel() {
    return "function"+(funCounter++);
  }
    
  public static void addFunctionCode (String code) {
    functionCode=functionCode+"\n"+code;
  }
  
  public static String getFunctionCode() {
    return functionCode;
  }
    
}