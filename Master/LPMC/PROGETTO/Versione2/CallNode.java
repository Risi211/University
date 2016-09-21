/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ppp;

import java.util.ArrayList;

public class CallNode extends Node {

  private String id;
  private STentry st; 
  private ArrayList<Node> par; 
  private int nestingDiff;
  
  public CallNode (String i, STentry e, ArrayList<Node> p, int nd) {
   id=i;
   st=e;
   par = p;
   nestingDiff=nd;
  }
  
  public String toPrint(String s) {
     return "";
  }
  
  public Node typeCheck () {
     Node t = st.getType();
     if ( !(t instanceof ArrowTypeNode) ) {
       System.out.println("Invocation of a non-function "+id);
       System.exit(0);
     }
     ArrayList<Node> p = ((ArrowTypeNode) t).getPar();
     if ( !(p.size() == par.size()) ) {
       System.out.println("Wrong number of parameters in the invocation of "+id);
       System.exit(0);
     } 
     for (int i=0; i<par.size(); i++) 
       if ( !(FOOLlib.isSubtype( (par.get(i)).typeCheck(), p.get(i)) ) ) {
         System.out.println("Wrong type for "+(i+1)+"-th parameter in the invocation of "+id);
         System.exit(0);
       } 
     return ((ArrowTypeNode) t).getRet();
  }
  
  public String codeGeneration () {

    String parCode="";
    for (int i=par.size()-1; i>=0; i--) 
      parCode=parCode+(par.get(i)).codeGeneration();
    
    String lookupAR="";
    for (int i=0; i<nestingDiff; i++) 
      lookupAR=lookupAR+"lw\n";
      
    return 
      "lfp\n"+
      parCode+  
      "lfp\n"+
      lookupAR+
      "lfp\n"+
      lookupAR+
      "push "+st.getOffset()+"\n"+ //scrive nello stack l'indirizzo di esecuzione della funzione
      "add\n"+
      "lw\n"+ //lo olegge e lo scrive in cima allo stack
      "js\n";  //modifica RA, il lra del funNode ha questo valore quindi
  }
    
}  