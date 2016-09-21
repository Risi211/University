/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package p2;

public class STentry {
 
  private Node dec;
  private int nl;
  private Node type;
  private int offset;
    
  public STentry (Node d, int n, int o)
  {dec=d;
   nl=n;
   offset=o;} 
 
  public STentry (Node d, int n, Node t, int o)
  {dec=d;
   nl=n;
   type=t;
   offset=o;}
  
  public void addType (Node t)
  {type=t;}
  
  public Node getType ()
  {return type;}
  
  public int getOffset ()
  {return offset;}
}  