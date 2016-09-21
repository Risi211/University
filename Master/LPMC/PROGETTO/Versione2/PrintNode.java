/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ppp;

public class PrintNode extends Node {

  private Node val;
  
  public PrintNode (Node v) {
   val=v;
  }
  
  public String toPrint(String s) {
   return s+"Print\n" + val.toPrint(s+"  ") ;
  }
  
  public Node typeCheck() {
    return val.typeCheck();
  } 
  
  public String codeGeneration() {
      return
        val.codeGeneration()+
        "print\n";
      
    
  }

}  