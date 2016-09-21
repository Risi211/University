/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package p2;

public class OrNode extends Node {

  private Node left;
  private Node right;
  
  public OrNode (Node l, Node r) {
   left=l;
   right=r;
  }
  
  public String toPrint(String s) {
   return s+"Or\n" + left.toPrint(s+"  ")   
                      + right.toPrint(s+"  ") ; 
  }
  
  public Node typeCheck() {
    Node l = left.typeCheck();
    Node r = right.typeCheck();
    //controllo che il valore degli int sia solo 0 (false) o 1 (true)
    //così sono comparabili integer e boolean, altrimenti genero errore
    if(l.toPrint("").equals("bool") && r.toPrint("").equals("bool"))
    {
        return new BoolTypeNode();
    }        
   else
    {
          System.out.println("Incompatible types in Or Operator: devono essere entrambi booleani");
          System.exit(0);
    }       
    return null;
  }  
  
  public String codeGeneration () {
      String labelEqual=FOOLlib.getLabel();
    String labelEnd=FOOLlib.getLabel();    
    return
      left.codeGeneration()+
      right.codeGeneration()+
      "beq "+labelEqual+"\n"+
      "push 1\n"+ //sono diversi, il risultato è true
      "b "+labelEnd+"\n"+
      labelEqual+":\n"+
      //"push 1\n"+
      //così finisce il valore di left direttamente nello stack, e dato che sia left che right
      //possono essere solo boolean, o ci va 0 (false) o 1 (true) in cima allo stack
      left.codeGeneration()+      
      labelEnd+":\n"; 
  }
  
    
}