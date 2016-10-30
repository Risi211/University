/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial.hib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author lupin
 */
public class Htest {   
    
    public static void main(String[] args){
           
        List<Integer> ids = new ArrayList<>();
        
        //create 100 employees
        for(int i = 0; i < 100; i++){
            Employee em1 = new Employee("e1", "se1", 100);
            ids.add(Manager.addEmployee(em1));
        }                
        
        //update first 100 employees
        for(int i = 0; i < 100; i++){
            Manager.updateSalary(ids.get(i), 120);
        }
        
        //print list of employees
        List<Employee> employees = Manager.listEmployees();
        System.out.println("number of employees = " + employees.size());
        for(Iterator<Employee> it = employees.iterator(); it.hasNext();){
            Employee em = it.next();
            System.out.println("id=" + em.getId() + ", name=" + em.getName() + ","
                    + " surname=" + em.getSurname() + ", salary=" + em.getSalary());
        }
                
        //delete last 10 employees
        for(int i = 0; i < 10; i++){
            Manager.deleteEmployee(ids.get(i));
        }            
        
        //set nicknames of all existing employees
        employees = Manager.listEmployees();
        for(Iterator<Employee> it = employees.iterator(); it.hasNext();){
            Employee em = it.next();
            Nicks n = new Nicks(em.getId());
            n.setNick1("en1");
            n.setNick2("en2");
            em.setNicks(n);
            Manager.updateEmployee(em);
        }
        
        
        //end hibernate session
        Manager.closeSession();
    }
}
