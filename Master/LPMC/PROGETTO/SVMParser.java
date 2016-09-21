/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package p2;

// $ANTLR 3.2 Sep 23, 2009 12:02:23 /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g 2014-12-02 15:11:37

import java.util.HashMap;
import java.util.ArrayList;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class SVMParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "PUSH", "NUMBER", "LABEL", "POP", "ADD", "SUB", "MULT", "DIV", "STOREW", "LOADW", "COL", "BRANCH", "BRANCHEQ", "BRANCHLESS", "JS", "LOADRA", "STORERA", "LOADRV", "STORERV", "LOADFP", "STOREFP", "COPYFP", "LOADHP", "STOREHP", "PRINT", "HALT", "WHITESP", "ERR"
    };
    public static final int HALT=29;
    public static final int BRANCHEQ=16;
    public static final int NUMBER=5;
    public static final int JS=18;
    public static final int SUB=9;
    public static final int LOADW=13;
    public static final int STORERV=22;
    public static final int MULT=10;
    public static final int WHITESP=30;
    public static final int EOF=-1;
    public static final int ERR=31;
    public static final int PRINT=28;
    public static final int LOADRA=19;
    public static final int PUSH=4;
    public static final int STOREW=12;
    public static final int LOADFP=23;
    public static final int LOADRV=21;
    public static final int STORERA=20;
    public static final int STOREFP=24;
    public static final int LABEL=6;
    public static final int STOREHP=27;
    public static final int BRANCHLESS=17;
    public static final int POP=7;
    public static final int COL=14;
    public static final int BRANCH=15;
    public static final int DIV=11;
    public static final int LOADHP=26;
    public static final int COPYFP=25;
    public static final int ADD=8;

    // delegates
    // delegators


        public SVMParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public SVMParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return SVMParser.tokenNames; }
    public String getGrammarFileName() { return "/Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g"; }


          
        private int[] code = new int[ExecuteVM.CODESIZE];    
        private int i = 0;
        private HashMap<String,Integer> labelAddress = new HashMap<String,Integer>();
        private ArrayList<String> labels = new ArrayList<String>();
        private ArrayList<Integer> addresses  = new ArrayList<Integer>();
        
        public int[] createCode() throws Exception {
           assembly();
           return code;
        }
        



    // $ANTLR start "assembly"
    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:27:1: assembly : ( PUSH e= NUMBER | PUSH e= LABEL | POP | ADD | SUB | MULT | DIV | STOREW | LOADW | e= LABEL COL | BRANCH e= LABEL | BRANCHEQ e= LABEL | BRANCHLESS e= LABEL | JS | LOADRA | STORERA | LOADRV | STORERV | LOADFP | STOREFP | COPYFP | LOADHP | STOREHP | PRINT | HALT )* ;
    public final void assembly() throws RecognitionException {
        Token e=null;

        try {
            // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:27:9: ( ( PUSH e= NUMBER | PUSH e= LABEL | POP | ADD | SUB | MULT | DIV | STOREW | LOADW | e= LABEL COL | BRANCH e= LABEL | BRANCHEQ e= LABEL | BRANCHLESS e= LABEL | JS | LOADRA | STORERA | LOADRV | STORERV | LOADFP | STOREFP | COPYFP | LOADHP | STOREHP | PRINT | HALT )* )
            // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:27:11: ( PUSH e= NUMBER | PUSH e= LABEL | POP | ADD | SUB | MULT | DIV | STOREW | LOADW | e= LABEL COL | BRANCH e= LABEL | BRANCHEQ e= LABEL | BRANCHLESS e= LABEL | JS | LOADRA | STORERA | LOADRV | STORERV | LOADFP | STOREFP | COPYFP | LOADHP | STOREHP | PRINT | HALT )*
            {
            // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:27:11: ( PUSH e= NUMBER | PUSH e= LABEL | POP | ADD | SUB | MULT | DIV | STOREW | LOADW | e= LABEL COL | BRANCH e= LABEL | BRANCHEQ e= LABEL | BRANCHLESS e= LABEL | JS | LOADRA | STORERA | LOADRV | STORERV | LOADFP | STOREFP | COPYFP | LOADHP | STOREHP | PRINT | HALT )*
            loop1:
            do {
                int alt1=26;
                alt1 = dfa1.predict(input);
                switch (alt1) {
            	case 1 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:27:13: PUSH e= NUMBER
            	    {
            	    match(input,PUSH,FOLLOW_PUSH_in_assembly26); 
            	    e=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_assembly30); 
            	    code[i++] = PUSH; 
            	    			     code[i++] = Integer.parseInt((e!=null?e.getText():null));

            	    }
            	    break;
            	case 2 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:29:6: PUSH e= LABEL
            	    {
            	    match(input,PUSH,FOLLOW_PUSH_in_assembly41); 
            	    e=(Token)match(input,LABEL,FOLLOW_LABEL_in_assembly45); 
            	    code[i++] = PUSH;
            	    	    		     labels.add((e!=null?e.getText():null));
            	    	  		     addresses.add(new Integer(i)); 
            	    	  		     code[i++] = 0;

            	    }
            	    break;
            	case 3 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:33:6: POP
            	    {
            	    match(input,POP,FOLLOW_POP_in_assembly65); 
            	    code[i++] = POP;

            	    }
            	    break;
            	case 4 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:34:6: ADD
            	    {
            	    match(input,ADD,FOLLOW_ADD_in_assembly80); 
            	    code[i++] = ADD;

            	    }
            	    break;
            	case 5 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:35:6: SUB
            	    {
            	    match(input,SUB,FOLLOW_SUB_in_assembly94); 
            	    code[i++] = SUB;

            	    }
            	    break;
            	case 6 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:36:6: MULT
            	    {
            	    match(input,MULT,FOLLOW_MULT_in_assembly108); 
            	    code[i++] = MULT;

            	    }
            	    break;
            	case 7 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:37:6: DIV
            	    {
            	    match(input,DIV,FOLLOW_DIV_in_assembly121); 
            	    code[i++] = DIV;

            	    }
            	    break;
            	case 8 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:38:6: STOREW
            	    {
            	    match(input,STOREW,FOLLOW_STOREW_in_assembly135); 
            	    code[i++] = STOREW;

            	    }
            	    break;
            	case 9 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:39:6: LOADW
            	    {
            	    match(input,LOADW,FOLLOW_LOADW_in_assembly148); 
            	    code[i++] = LOADW;

            	    }
            	    break;
            	case 10 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:40:6: e= LABEL COL
            	    {
            	    e=(Token)match(input,LABEL,FOLLOW_LABEL_in_assembly169); 
            	    match(input,COL,FOLLOW_COL_in_assembly171); 
            	    labelAddress.put((e!=null?e.getText():null),new Integer(i));

            	    }
            	    break;
            	case 11 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:41:6: BRANCH e= LABEL
            	    {
            	    match(input,BRANCH,FOLLOW_BRANCH_in_assembly184); 
            	    e=(Token)match(input,LABEL,FOLLOW_LABEL_in_assembly188); 
            	    code[i++] = BRANCH;
            	    	  		     labels.add((e!=null?e.getText():null));
            	    	  		     addresses.add(new Integer(i)); 
            	    	  		     code[i++] = 0;

            	    }
            	    break;
            	case 12 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:45:6: BRANCHEQ e= LABEL
            	    {
            	    match(input,BRANCHEQ,FOLLOW_BRANCHEQ_in_assembly198); 
            	    e=(Token)match(input,LABEL,FOLLOW_LABEL_in_assembly202); 
            	    code[i++] = BRANCHEQ;
            	    	  	  	     labels.add((e!=null?e.getText():null));
            	    	  		     addresses.add(new Integer(i)); 
            	    	  		     code[i++] = 0;

            	    }
            	    break;
            	case 13 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:49:6: BRANCHLESS e= LABEL
            	    {
            	    match(input,BRANCHLESS,FOLLOW_BRANCHLESS_in_assembly211); 
            	    e=(Token)match(input,LABEL,FOLLOW_LABEL_in_assembly215); 
            	    code[i++] = BRANCHLESS;
            	    	  	  	     labels.add((e!=null?e.getText():null));
            	    	  		     addresses.add(new Integer(i)); 
            	    	  		     code[i++] = 0;

            	    }
            	    break;
            	case 14 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:53:6: JS
            	    {
            	    match(input,JS,FOLLOW_JS_in_assembly224); 
            	    code[i++] = JS;

            	    }
            	    break;
            	case 15 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:54:6: LOADRA
            	    {
            	    match(input,LOADRA,FOLLOW_LOADRA_in_assembly253); 
            	    code[i++] = LOADRA;

            	    }
            	    break;
            	case 16 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:55:6: STORERA
            	    {
            	    match(input,STORERA,FOLLOW_STORERA_in_assembly271); 
            	    code[i++] = STORERA;

            	    }
            	    break;
            	case 17 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:56:6: LOADRV
            	    {
            	    match(input,LOADRV,FOLLOW_LOADRV_in_assembly289); 
            	    code[i++] = LOADRV;

            	    }
            	    break;
            	case 18 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:57:6: STORERV
            	    {
            	    match(input,STORERV,FOLLOW_STORERV_in_assembly307); 
            	    code[i++] = STORERV;

            	    }
            	    break;
            	case 19 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:58:6: LOADFP
            	    {
            	    match(input,LOADFP,FOLLOW_LOADFP_in_assembly325); 
            	    code[i++] = LOADFP;

            	    }
            	    break;
            	case 20 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:59:6: STOREFP
            	    {
            	    match(input,STOREFP,FOLLOW_STOREFP_in_assembly343); 
            	    code[i++] = STOREFP;

            	    }
            	    break;
            	case 21 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:60:6: COPYFP
            	    {
            	    match(input,COPYFP,FOLLOW_COPYFP_in_assembly360); 
            	    code[i++] = COPYFP;

            	    }
            	    break;
            	case 22 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:61:6: LOADHP
            	    {
            	    match(input,LOADHP,FOLLOW_LOADHP_in_assembly378); 
            	    code[i++] = LOADHP;

            	    }
            	    break;
            	case 23 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:62:6: STOREHP
            	    {
            	    match(input,STOREHP,FOLLOW_STOREHP_in_assembly396); 
            	    code[i++] = STOREHP;

            	    }
            	    break;
            	case 24 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:63:6: PRINT
            	    {
            	    match(input,PRINT,FOLLOW_PRINT_in_assembly413); 
            	    code[i++] = PRINT;

            	    }
            	    break;
            	case 25 :
            	    // /Users/zavattar/Desktop/LPeMC2014/Esercitazione_12_12/FOOL/SVM.g:64:6: HALT
            	    {
            	    match(input,HALT,FOLLOW_HALT_in_assembly432); 
            	    code[i++] = HALT;

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

              for (int ind=0; ind<labels.size(); ind++) {
                    	  Integer t = labelAddress.get(labels.get(ind));
                    	  Integer a = addresses.get(ind);
            		  code[a.intValue()]=t.intValue(); 
                            }
                          

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "assembly"

    // Delegated rules


    protected DFA1 dfa1 = new DFA1(this);
    static final String DFA1_eotS =
        "\34\uffff";
    static final String DFA1_eofS =
        "\1\1\33\uffff";
    static final String DFA1_minS =
        "\1\4\1\uffff\1\5\31\uffff";
    static final String DFA1_maxS =
        "\1\35\1\uffff\1\6\31\uffff";
    static final String DFA1_acceptS =
        "\1\uffff\1\32\1\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1"+
        "\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30"+
        "\1\31\1\1\1\2";
    static final String DFA1_specialS =
        "\34\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\2\1\uffff\1\12\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\uffff\1\13"+
            "\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27"+
            "\1\30\1\31",
            "",
            "\1\32\1\33",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA1_eot = DFA.unpackEncodedString(DFA1_eotS);
    static final short[] DFA1_eof = DFA.unpackEncodedString(DFA1_eofS);
    static final char[] DFA1_min = DFA.unpackEncodedStringToUnsignedChars(DFA1_minS);
    static final char[] DFA1_max = DFA.unpackEncodedStringToUnsignedChars(DFA1_maxS);
    static final short[] DFA1_accept = DFA.unpackEncodedString(DFA1_acceptS);
    static final short[] DFA1_special = DFA.unpackEncodedString(DFA1_specialS);
    static final short[][] DFA1_transition;

    static {
        int numStates = DFA1_transitionS.length;
        DFA1_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA1_transition[i] = DFA.unpackEncodedString(DFA1_transitionS[i]);
        }
    }

    class DFA1 extends DFA {

        public DFA1(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 1;
            this.eot = DFA1_eot;
            this.eof = DFA1_eof;
            this.min = DFA1_min;
            this.max = DFA1_max;
            this.accept = DFA1_accept;
            this.special = DFA1_special;
            this.transition = DFA1_transition;
        }
        public String getDescription() {
            return "()* loopback of 27:11: ( PUSH e= NUMBER | PUSH e= LABEL | POP | ADD | SUB | MULT | DIV | STOREW | LOADW | e= LABEL COL | BRANCH e= LABEL | BRANCHEQ e= LABEL | BRANCHLESS e= LABEL | JS | LOADRA | STORERA | LOADRV | STORERV | LOADFP | STOREFP | COPYFP | LOADHP | STOREHP | PRINT | HALT )*";
        }
    }
 

    public static final BitSet FOLLOW_PUSH_in_assembly26 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NUMBER_in_assembly30 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_PUSH_in_assembly41 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_LABEL_in_assembly45 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_POP_in_assembly65 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_ADD_in_assembly80 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_SUB_in_assembly94 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_MULT_in_assembly108 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_DIV_in_assembly121 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_STOREW_in_assembly135 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_LOADW_in_assembly148 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_LABEL_in_assembly169 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_COL_in_assembly171 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_BRANCH_in_assembly184 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_LABEL_in_assembly188 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_BRANCHEQ_in_assembly198 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_LABEL_in_assembly202 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_BRANCHLESS_in_assembly211 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_LABEL_in_assembly215 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_JS_in_assembly224 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_LOADRA_in_assembly253 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_STORERA_in_assembly271 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_LOADRV_in_assembly289 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_STORERV_in_assembly307 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_LOADFP_in_assembly325 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_STOREFP_in_assembly343 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_COPYFP_in_assembly360 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_LOADHP_in_assembly378 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_STOREHP_in_assembly396 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_PRINT_in_assembly413 = new BitSet(new long[]{0x000000003FFFBFD2L});
    public static final BitSet FOLLOW_HALT_in_assembly432 = new BitSet(new long[]{0x000000003FFFBFD2L});

}