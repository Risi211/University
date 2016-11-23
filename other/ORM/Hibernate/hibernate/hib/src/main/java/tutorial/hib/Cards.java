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
@Table(name = "cards")
public class Cards implements Serializable{
    @Id
    @GeneratedValue   
    private int idCard;
    
    @Column(name = "value")
    private String value;
        
    @ManyToOne
    @JoinColumn(name = "idEm")
    private Employee employee;    
    
    public Cards() {}
    
    /*
    public Cards(int em, String v){
        idEm = em;
        value = v;
    }
    */
    
    public void setIdCard(int id){
        idCard = id;
    }
    public int getIdCard(){
        return idCard;
    }    
/*
    public void setIdEm(int id){
        idEm = id;
    }
    public int getIdEm(){
        return idEm;
    }    
*/
    public void setValue(String v){
        value = v;
    }
    public String getValue(){
        return value;
    }
    
    public void setEmployee(Employee em){
        this.employee = em;
    }
    public Employee getEmployee(){
        return employee;
    }    
}
