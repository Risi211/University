/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package p2;

public class BoolNode extends Node {

  private boolean val;
  
  public BoolNode (boolean n) {
   val=n;
  }
  
  public String toPrint(String s) {
   if (val) return s+"Bool:true\n";
       else return s+"Bool:false\n";  
  }
  
  public Node typeCheck() {
    return new BoolTypeNode();
  } 
  
  public String codeGeneration () {
    if (val)
      return "push 1\n";
    else 
      return "push 0\n";
  }
         
}  