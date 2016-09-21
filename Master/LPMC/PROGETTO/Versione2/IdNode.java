/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ppp;

public class IdNode extends Node {

  private String id;
  private STentry entry;
  private int nestingDiff;
  
  public IdNode (String i, STentry st, int nd) {
   id=i;
   entry=st;
   nestingDiff=nd;
  }
  
  public String toPrint(String s) {
   return "";  
  }
  
  public Node typeCheck () {
     return entry.getType();
  }
  
  public String codeGeneration () {
    
    String lookupAR="";
    for (int i=0; i<nestingDiff; i++) 
      lookupAR=lookupAR+"lw\n";
    
    return
      "lfp\n"+
      lookupAR+
      "push "+entry.getOffset()+"\n"+
      "add\n"+
      "lw\n";
    
/*    String lookupFun=
      lookupBase+
      "lfp\n"+
      lookupAR+
      "push "+offset+"\n"+
      "push 1\n"+
      "sub\n"+
      "add\n"+
      "lw\n";    
    
    if ( MiniFunTypes.isArrow(decl.typeCheck()) )
      return lookupFun;
    else
      return lookupBase;
  } */

  }
    
}  