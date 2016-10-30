/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;

/**
 *
 * @author lupin
 */
public class app {
    
    public static void main(String[] args){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigA.class);

        TextEditor te = ctx.getBean(TextEditor.class);
        te.spellCheck();    
      
        HelloWorld hw = ctx.getBean(HelloEurope.class);
        hw.printMessage();
        
        BeanCollections bc = ctx.getBean(BeanCollections.class);
        System.out.println(bc.getAddressList());
        
        ctx.registerShutdownHook();
        
        //prototype beans are not destroyed by spring, only singleton
    }
}
