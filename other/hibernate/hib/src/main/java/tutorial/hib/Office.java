/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial.hib;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author lupin
 */
@Entity
@Table(name = "office")
public class Office implements Serializable{
    @Id
    @GeneratedValue   
    private int id;

    @Column(name = "address")
    private String address;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_office", joinColumns = {
        @JoinColumn(name = "idOf") },
        inverseJoinColumns = { @JoinColumn(name = "idEm") })    
    private Set<Employee> employees;
    
    public Office() {}
    
    public void setId(int id){this.id = id;}
    public int getId(){return id;}
    
    public void setAddress(String ads){this.address = ads;}
    public String getAddress(){return address;}    
    
    public void setEmployees(Set<Employee> em){this.employees = em;}
    public Set<Employee> getEmployees(){return employees;}    
    
}
