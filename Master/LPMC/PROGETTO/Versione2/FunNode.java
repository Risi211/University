/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ppp;

import java.util.ArrayList;

public class FunNode extends Node {

  private String id;
  private Node type; 
  private ArrayList<Node> par; 
  private ArrayList<Node> def; 
  private Node body;
  
  public FunNode (String i, Node t) {
   id=i;
   type=t;
   par = new ArrayList<Node>();
  }
  
  public void addDecBody (ArrayList<Node> d, Node b) {
   def=d;
   body=b;
  }  
  
  public void addPar (Node p) {
   par.add(p);
  }  
  
  public String toPrint(String s) {
     return "";
  }
  
  public Node typeCheck () {
    ArrayList<Node> pt = new ArrayList<Node>();
    for (int i=0; i<par.size(); i++) 
      pt.add( (par.get(i)).typeCheck() );
    for (int j=0; j<def.size(); j++) 
      (def.get(j)).typeCheck();
    if ( !(FOOLlib.isSubtype(body.typeCheck(),type)) ){
      System.out.println("Wrong return type for function "+id);
      System.exit(0);
    }  
    return new ArrowTypeNode (pt, type);
  }
  
  public String codeGeneration () {

    String labelFun=FOOLlib.getFunctionLabel();
    
    String popParSequence="";
    for (int i=0; i<par.size(); i++) 
      popParSequence=popParSequence+"pop\n";

    String popDecSequence="";
    for (int i=0; i<def.size(); i++) 
      popDecSequence=popDecSequence+"pop\n";
      
    String declCode="";
    for (int i=0; i<def.size(); i++) 
      declCode=declCode+(def.get(i)).codeGeneration();
    
    FOOLlib.addFunctionCode(
      labelFun+":\n"+
      "cfp\n"+
      "lra\n"+ //salva RA xkè potrebbe cambiare se la funzione chiama altre funzioni
      declCode +
      body.codeGeneration()+
      "srv\n"+
      popDecSequence+                          
      "sra\n"+
      //"sfp\n"+ //il prof ha sbagliato, da rinfacciarglielo                     
       "pop\n"+       //butta via l'access link
      popParSequence+
       "sfp\n"+ //restaura il control link      
      "lrv\n"+                          
      "lra\n"+
      "js\n"                          
      );
    
    return 
//      "lfp\n"+
      "push "+labelFun+"\n";
  }
    
}  

/*
AR:
control link
parametri
access link
<FunNode codice>

*/