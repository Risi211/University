/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package p2;


public class MinusNode extends Node {

  private Node left;
  private Node right;
  
  public MinusNode (Node l, Node r) {
   left=l;
   right=r;
  }
  
  public String toPrint(String s) {
   return s+"Minus\n" + left.toPrint(s+"  ")  
                     + right.toPrint(s+"  ") ; 
  }
  
  public Node typeCheck() {
    if (! ( FOOLlib.isSubtype(left.typeCheck(),new IntTypeNode()) &&
            FOOLlib.isSubtype(right.typeCheck(),new IntTypeNode()) ) ) {
      System.out.println("Non integers in subtraction");
      System.exit(0);
    }
    return new IntTypeNode();
  } 
  
  public String codeGeneration() {
    return
      left.codeGeneration()+
      right.codeGeneration()+
      "sub\n";
  }
    
  
}