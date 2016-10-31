/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial.hib;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author lupin
 */
@Entity
@Table
public class Employee implements Serializable{
    @Id
    @GeneratedValue    
    private int id;    
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;    
    @Column(name = "salary")
    private int salary;

    @OneToOne(cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn
    private Nicks nicknames;
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL) //fetch = FetchType.EAGER
    //@OrderBy("idCard asc")
    private Set<Cards> cards;
    
    @ManyToMany(mappedBy = "employees", cascade = CascadeType.ALL) 
    private Set<Office> offices;
    
    public Employee(){}
    
    public Employee(String name, String surname, int salary){
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }
    
    public int getId(){return id;}
    public void setId(int v){id = v;}

    public String getName(){return name;}
    public void setName(String v){name = v;}

    public String getSurname(){return surname;}
    public void setSurname(String v){surname = v;}

    public int getSalary(){return salary;}
    public void setSalary(int v){salary = v;}  
    
    public Nicks getNicks(){return nicknames;}
    public void setNicks(Nicks n){nicknames = n;}     
    
    public Set<Cards> getCards(){return cards;}
    public void setCards(Set<Cards> c){cards = c;}
    
    public Set<Office> getOffices(){return offices;}
    public void setOffices(Set<Office> of){offices = of;}    
    
}
