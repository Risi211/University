/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial.hib;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author lupin
 */
@Entity
@Table
public class Nicks implements Serializable{
    @Id
    @Column(name = "idEm")
    private int idEm;
    @Column(name = "n1")
    private String nick1;
    @Column(name = "n2")
    private String nick2;

    public Nicks() {}
    
    public Nicks(int idEm) {
        this.idEm = idEm;
    }
    
    public void setIdEm(int id){
        idEm = id;
    }
    public int getIdEm(){
        return idEm;
    }
    public void setNick1(String n1){
        nick1 = n1;
    }
    public String getNick1(){
        return nick1;
    }

    public void setNick2(String n2){
        nick2 = n2;
    }
    public String getNick2(){
        return nick2;
    }    
}
