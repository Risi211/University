/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package p2;

public class NotNode extends Node {

  private Node exp;
  
  public NotNode (Node l) {
   exp=l;  
  }
  
  public String toPrint(String s) {
   return s+"Not\n" + exp.toPrint(s+"  ");
  }
  
  public Node typeCheck() {
    Node l = exp.typeCheck();
    //controllo che il valore degli int sia solo 0 (false) o 1 (true)
    //così sono comparabili integer e boolean, altrimenti genero errore
    if(l.toPrint("").equals("bool"))
    {
        return new BoolTypeNode();
    }        
   else
    {
          System.out.println("Incompatible types in Not Operator: L'input deve essere booleano");
          System.exit(0);
    }       
    return null;
  }  
  
  public String codeGeneration () {
    String labelZero=FOOLlib.getLabel();
    String labelEnd=FOOLlib.getLabel();    
    return
      exp.codeGeneration()+
      "push 0\n"+ //se è 0, diventa 1, else è 1 e diventa 0            
      "beq "+labelZero+"\n"+
        //se sono qui significa che è 1 e diventa 0
      "push 0\n"+ //diventa il contrario
      "b "+labelEnd+"\n"+
      labelZero+":\n"+
      "push 1\n"+ //se sono qui significa che è 0 e diventa 1
      labelEnd+":\n"; 
  }
  
    
}