/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ppp;

import java.util.ArrayList;

public class ProgNode extends Node {

  private Node exp;
  
  public ProgNode (Node e) {
   exp=e;
  }
  
  public String toPrint(String s) {
    
   return "Prog\n" + exp.toPrint("  ") ;
  }
  
  public Node typeCheck() {
    return exp.typeCheck();
  } 

  public String codeGeneration() {
    return exp.codeGeneration();
  }

    
}  