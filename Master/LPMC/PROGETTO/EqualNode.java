/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package p2;

public class EqualNode extends Node {

  private Node left;
  private Node right;
  
  public EqualNode (Node l, Node r) {
   left=l;
   right=r;
  }
  
  public String toPrint(String s) {
   return s+"Equal\n" + left.toPrint(s+"  ")   
                      + right.toPrint(s+"  ") ; 
  }
  
  public Node typeCheck() {
    Node l = left.typeCheck();
    Node r = right.typeCheck();
    if (! ( FOOLlib.isSubtype(l,r) || FOOLlib.isSubtype(r,l) ) ) {
      System.out.println("Incompatible types in equal");
      System.exit(0);
    }
    return new BoolTypeNode();
  }  
  
  public String codeGeneration () {
    String labelTrue=FOOLlib.getLabel();
    String labelEnd=FOOLlib.getLabel();    
    return
      left.codeGeneration()+
      right.codeGeneration()+
      "beq "+labelTrue+"\n"+
      "push 0\n"+
      "b "+labelEnd+"\n"+
      labelTrue+":\n"+
      "push 1\n"+
      labelEnd+":\n"; 
  }
  
    
}  