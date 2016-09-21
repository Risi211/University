/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ppp;

import java.util.ArrayList;

public class LetInNode extends Node {

  private ArrayList<Node> dec;
  private Node exp;
  
  public LetInNode (ArrayList<Node> d, Node e) {
   dec=d;
   exp=e;
  }
  
  public String toPrint(String s) {
     return "";   
  }
  
  public Node typeCheck () {
    for (int i=0; i<dec.size(); i++)
      (dec.get(i)).typeCheck();
    return exp.typeCheck();
  }
  
  
  public String codeGeneration () {
    
    String declCode="";  
    for (int i=0; i<dec.size(); i++) 
      declCode=declCode+(dec.get(i)).codeGeneration();
            
    return 
      "prog:\n"+
      "push -1\n"+
      declCode+
      exp.codeGeneration()+
      "halt\n"+
      FOOLlib.getFunctionCode()
      ;

  }
    
}  