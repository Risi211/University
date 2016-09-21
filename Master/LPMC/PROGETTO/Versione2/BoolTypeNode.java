/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ppp;

public class BoolTypeNode extends Node {
  
  public BoolTypeNode () {
  }
  
  public String toPrint(String s) {
    return "bool";
  }
    
  public Node typeCheck() {
     return new BoolTypeNode();
  }
  
  public String codeGeneration () {
    return "";
  }
    
}  