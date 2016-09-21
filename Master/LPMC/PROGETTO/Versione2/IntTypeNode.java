/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ppp;

public class IntTypeNode extends Node {
  
  public IntTypeNode () {
  }
  
  public String toPrint(String s) {
        return "int";
  }
  
  public Node typeCheck() {
    return new IntTypeNode();
  }
  
  public String codeGeneration () {
    return "";
  }
    
}  