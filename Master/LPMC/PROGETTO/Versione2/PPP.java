/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ppp;

import java.io.*;
import org.antlr.runtime.*;

/**
 *
 * @author luca.parisi2
 */
public class PPP {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, RecognitionException, Exception {
                       
        String fileName = "C:\\Users\\luca.parisi2\\Desktop\\prova.fool";
      
        ANTLRFileStream input = new ANTLRFileStream(fileName);
        FOOLLexer lexer = new FOOLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FOOLParser parser = new FOOLParser(tokens);
         
        Node ast = parser.prog();
        //System.out.println( (ast.typeCheck()).toPrint("") );
        
        String asm = ast.codeGeneration();
        
        System.out.println(asm+"\r\n");
        
        FileWriter fstream = new FileWriter(fileName+".asm");
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(asm);
        out.close();
        
        SVMLexer lex = new SVMLexer(new ANTLRFileStream(fileName+".asm"));
        CommonTokenStream tokensVM = new CommonTokenStream(lex);
        SVMParser parserVM = new SVMParser(tokensVM);

        ExecuteVM vm = new ExecuteVM(parserVM.createCode());
        vm.cpu(); 
        
    }    
    
}
