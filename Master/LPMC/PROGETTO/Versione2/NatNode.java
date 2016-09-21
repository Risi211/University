/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ppp;

public class NatNode extends Node {

  private Integer val;
  
  public NatNode (Integer n) {
   val=n;
  }
  
  public String toPrint(String s) {
   return s+"Nat:" + Integer.toString(val) +"\n";  
  }
  
  public Node typeCheck() {
    return new IntTypeNode();
  } 
  
  public String codeGeneration () {
    return "push "+val+"\n";
  }
  
}  