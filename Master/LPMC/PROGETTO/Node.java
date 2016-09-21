/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package p2;

public abstract class Node {
    
  abstract public String toPrint(String indent);

  abstract public Node typeCheck();
  
  abstract public String codeGeneration();
  
}  