/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package p2;

public class IfNode extends Node {

  private Node cond;
  private Node th;
  private Node el;
  
  public IfNode (Node c, Node t, Node e) {
   cond=c;
   th=t;
   el=e;
  }
  
  public String toPrint(String s) {
   return s+"If\n" + cond.toPrint(s+"  ") 
                 + th.toPrint(s+"  ")   
                 + el.toPrint(s+"  ") ; 
  }
  
  public Node typeCheck () {
    if (!(FOOLlib.isSubtype(cond.typeCheck(),new BoolTypeNode()))) {
      System.out.println("non boolean condition in if");
      System.exit(0);
    }
    Node t = th.typeCheck();
    Node e = el.typeCheck();
    if (FOOLlib.isSubtype(t,e)) 
      return e;
    if (FOOLlib.isSubtype(e,t))
      return t;
    System.out.println("Incompatible types in then else branches");
    System.exit(0);
    return null;
  }
  
  public String codeGeneration () {
    String labelThen=FOOLlib.getLabel();
    String labelEnd=FOOLlib.getLabel();  
    return
      cond.codeGeneration()+
      "push 1\n"+
      "beq "+labelThen+"\n"+
      el.codeGeneration()+
      "b "+labelEnd+"\n"+
      labelThen+":\n"+
      th.codeGeneration()+
      labelEnd+":\n";   
  }
    
    
}  