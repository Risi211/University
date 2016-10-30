/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial.spring;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.*;

/**
 *
 * @author lupin
 */
@Configuration
public class ConfigA {
    
    //abstract class do not need to be defined as beans
    
    @Bean(initMethod = "init", destroyMethod = "destroy" ) 
    @Scope("singleton")
    public HelloEurope helloEurope(){
       HelloEurope he = new HelloEurope();
       return he;
    }

    @Bean
    @Scope("singleton")
    public InitHelloWorld initHelloWorld(){
        return new InitHelloWorld();
    }
    
    @Bean
    @Scope("singleton")
    public BeanCollections beanCollections(){
        BeanCollections bc = new BeanCollections();
        List<String> ls = new ArrayList<>();
        ls.add("s1");
        ls.add("s2");
        bc.setAddressList(ls);
        return bc;
    }    
    
    @Bean 
    @Scope("prototype")
    public TextEditor textEditor(){
       return new TextEditor( spellChecker() );
    }

    @Bean 
    @Scope("prototype")
    public SpellChecker spellChecker(){
       return new SpellChecker( );
    }    
}
