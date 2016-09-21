/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ppp;

public class ParNode extends Node {

  private String id;
  private Node type;
  
  public ParNode (String i, Node t) {
   id=i;
   type=t;
  }
  
  public String toPrint(String s) {
     return "";
  }
  
  public Node typeCheck () {
     return type;
  }
  
  public String codeGeneration () {
    return "";
  }
    
}  