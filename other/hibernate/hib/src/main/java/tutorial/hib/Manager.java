/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial.hib;

import java.util.List;
import org.hibernate.*; 
import org.hibernate.cfg.Configuration;

/**
 *
 * @author lupin
 */
public class Manager {
    
    private static SessionFactory factory;
    
    static{
        initSession();
    }

    public static void initSession(){
      try{
         factory = new Configuration().configure().buildSessionFactory();
      }catch (Throwable ex) { 
         System.err.println("Failed to create sessionFactory object." + ex);
         throw new ExceptionInInitializerError(ex); 
      }        
    }    
    
    public static void closeSession(){
        factory.close();
    }
    
    private Manager() {}
    
    public static Integer addEmployee(Employee em){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;
        try{
           tx = session.beginTransaction();
           employeeID = (Integer) session.save(em); 
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }        
        return employeeID;        
    }
    
   public static List<Employee> listEmployees( ){
      Session session = factory.openSession();
      Transaction tx = null;
      List<Employee> employees = null;
      try{
         tx = session.beginTransaction();
         employees = session.createQuery("FROM Employee").list(); 
         tx.commit();         
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
      return employees;
   } 

   public static void updateSalary(Integer EmployeeID, int salary ){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         Employee employee = (Employee)session.get(Employee.class, EmployeeID); 
         employee.setSalary(salary);
         session.update(employee); 
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
   }

   public static void deleteEmployee(Integer EmployeeID){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         Employee employee = (Employee)session.get(Employee.class, EmployeeID); 
         session.delete(employee); 
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
   }   
}
