/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package p2;

import java.util.ArrayList;

public class ArrowTypeNode extends Node {

  private ArrayList<Node> par; 
  private Node ret;
  
  public ArrowTypeNode (ArrayList<Node> p, Node r) {
   par=p;
   ret=r;
  }
    
  public String toPrint(String s) {
     return s + "ArrowTypeNode";
  }
  
  public Node getRet () {
    return ret;
  }
  
  public ArrayList<Node> getPar () {
    return par;
  }
    
  public Node typeCheck () {
     return new ArrowTypeNode (par, ret);
  }
    
  public String codeGeneration () {
    return "";
  }

}  