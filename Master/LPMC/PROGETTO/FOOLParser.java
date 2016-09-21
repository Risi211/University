package p2;

// $ANTLR 3.5.1 /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g 2015-02-04 18:46:52

import java.util.ArrayList;
import java.util.HashMap;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class FOOLParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "AND", "ASS", "BOOL", "CLPAR", 
		"COLON", "COMMA", "COMMENT", "CRPAR", "DIV", "ELSE", "EQ", "ERR", "FALSE", 
		"FUN", "GR", "ID", "IF", "IN", "INT", "LE", "LET", "LPAR", "MINUS", "NAT", 
		"NOT", "OR", "PLUS", "PRINT", "RPAR", "SEMIC", "THEN", "TIMES", "TRUE", 
		"VAR", "WHITESP"
	};
	public static final int EOF=-1;
	public static final int AND=4;
	public static final int ASS=5;
	public static final int BOOL=6;
	public static final int CLPAR=7;
	public static final int COLON=8;
	public static final int COMMA=9;
	public static final int COMMENT=10;
	public static final int CRPAR=11;
	public static final int DIV=12;
	public static final int ELSE=13;
	public static final int EQ=14;
	public static final int ERR=15;
	public static final int FALSE=16;
	public static final int FUN=17;
	public static final int GR=18;
	public static final int ID=19;
	public static final int IF=20;
	public static final int IN=21;
	public static final int INT=22;
	public static final int LE=23;
	public static final int LET=24;
	public static final int LPAR=25;
	public static final int MINUS=26;
	public static final int NAT=27;
	public static final int NOT=28;
	public static final int OR=29;
	public static final int PLUS=30;
	public static final int PRINT=31;
	public static final int RPAR=32;
	public static final int SEMIC=33;
	public static final int THEN=34;
	public static final int TIMES=35;
	public static final int TRUE=36;
	public static final int VAR=37;
	public static final int WHITESP=38;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public FOOLParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public FOOLParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return FOOLParser.tokenNames; }
	@Override public String getGrammarFileName() { return "/home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g"; }


	private ArrayList<HashMap<String,STentry>>  symTable = new ArrayList<HashMap<String,STentry>>();
	private int nestingLevel = -1;



	// $ANTLR start "prog"
	// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:17:1: prog returns [Node ast] : (e= exp SEMIC | LET d= dec IN e= exp SEMIC );
	public final Node prog() throws RecognitionException {
		Node ast = null;


		Node e =null;
		ArrayList<Node> d =null;

		try {
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:18:2: (e= exp SEMIC | LET d= dec IN e= exp SEMIC )
			int alt1=2;
			int LA1_0 = input.LA(1);
			if ( (LA1_0==FALSE||(LA1_0 >= ID && LA1_0 <= IF)||LA1_0==LPAR||(LA1_0 >= NAT && LA1_0 <= NOT)||LA1_0==PRINT||LA1_0==TRUE) ) {
				alt1=1;
			}
			else if ( (LA1_0==LET) ) {
				alt1=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);
				throw nvae;
			}

			switch (alt1) {
				case 1 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:18:4: e= exp SEMIC
					{
					pushFollow(FOLLOW_exp_in_prog30);
					e=exp();
					state._fsp--;

					match(input,SEMIC,FOLLOW_SEMIC_in_prog32); 
					ast = new ProgNode(e);
					}
					break;
				case 2 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:20:11: LET d= dec IN e= exp SEMIC
					{
					match(input,LET,FOLLOW_LET_in_prog57); 
					nestingLevel++;
					             HashMap<String,STentry> hm = new HashMap<String,STentry> ();
					             symTable.add(hm);
					            
					pushFollow(FOLLOW_dec_in_prog86);
					d=dec();
					state._fsp--;

					match(input,IN,FOLLOW_IN_in_prog88); 
					pushFollow(FOLLOW_exp_in_prog92);
					e=exp();
					state._fsp--;

					match(input,SEMIC,FOLLOW_SEMIC_in_prog94); 
					symTable.remove(nestingLevel--);
					             ast = new LetInNode(d,e) ;
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ast;
	}
	// $ANTLR end "prog"



	// $ANTLR start "dec"
	// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:30:1: dec returns [ArrayList<Node> astlist] : ( VAR i= ID COLON t= type ASS e= exp SEMIC | FUN i= ID COLON t= type LPAR (fid= ID COLON fty= type ( COMMA id= ID COLON ty= type )* )? RPAR ( LET d= dec IN )? e= exp SEMIC )* ;
	public final ArrayList<Node> dec() throws RecognitionException {
		ArrayList<Node> astlist = null;


		Token i=null;
		Token fid=null;
		Token id=null;
		Node t =null;
		Node e =null;
		Node fty =null;
		Node ty =null;
		ArrayList<Node> d =null;

		try {
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:31:2: ( ( VAR i= ID COLON t= type ASS e= exp SEMIC | FUN i= ID COLON t= type LPAR (fid= ID COLON fty= type ( COMMA id= ID COLON ty= type )* )? RPAR ( LET d= dec IN )? e= exp SEMIC )* )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:31:4: ( VAR i= ID COLON t= type ASS e= exp SEMIC | FUN i= ID COLON t= type LPAR (fid= ID COLON fty= type ( COMMA id= ID COLON ty= type )* )? RPAR ( LET d= dec IN )? e= exp SEMIC )*
			{
			astlist = new ArrayList<Node>();
				   int varOffset=-2;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:33:4: ( VAR i= ID COLON t= type ASS e= exp SEMIC | FUN i= ID COLON t= type LPAR (fid= ID COLON fty= type ( COMMA id= ID COLON ty= type )* )? RPAR ( LET d= dec IN )? e= exp SEMIC )*
			loop5:
			while (true) {
				int alt5=3;
				int LA5_0 = input.LA(1);
				if ( (LA5_0==VAR) ) {
					alt5=1;
				}
				else if ( (LA5_0==FUN) ) {
					alt5=2;
				}

				switch (alt5) {
				case 1 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:34:13: VAR i= ID COLON t= type ASS e= exp SEMIC
					{
					match(input,VAR,FOLLOW_VAR_in_dec144); 
					i=(Token)match(input,ID,FOLLOW_ID_in_dec148); 
					match(input,COLON,FOLLOW_COLON_in_dec150); 
					pushFollow(FOLLOW_type_in_dec154);
					t=type();
					state._fsp--;

					match(input,ASS,FOLLOW_ASS_in_dec156); 
					pushFollow(FOLLOW_exp_in_dec160);
					e=exp();
					state._fsp--;

					match(input,SEMIC,FOLLOW_SEMIC_in_dec162); 
					VarNode v = new VarNode((i!=null?i.getText():null),t,e);
					             astlist.add(v);
					             HashMap<String,STentry> hm = symTable.get(nestingLevel);
					             if ( hm.put((i!=null?i.getText():null),new STentry(v,nestingLevel,t,varOffset--)) != null  )
					             {System.out.println("Var id "+(i!=null?i.getText():null)+" at line "+(i!=null?i.getLine():0)+" already declared");
					              System.exit(0);}  
					            
					}
					break;
				case 2 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:43:13: FUN i= ID COLON t= type LPAR (fid= ID COLON fty= type ( COMMA id= ID COLON ty= type )* )? RPAR ( LET d= dec IN )? e= exp SEMIC
					{
					match(input,FUN,FOLLOW_FUN_in_dec205); 
					i=(Token)match(input,ID,FOLLOW_ID_in_dec209); 
					match(input,COLON,FOLLOW_COLON_in_dec211); 
					pushFollow(FOLLOW_type_in_dec215);
					t=type();
					state._fsp--;

					//inserimento di ID nella symtable
					               FunNode f = new FunNode((i!=null?i.getText():null),t);
					               astlist.add(f);
					               HashMap<String,STentry> hm = symTable.get(nestingLevel);
					               STentry entry = new STentry(f,nestingLevel,varOffset--);
					               if ( hm.put((i!=null?i.getText():null),entry) != null  )
					               {System.out.println("Fun id "+(i!=null?i.getText():null)+" at line "+(i!=null?i.getLine():0)+" already declared");
					                System.exit(0);}
					                //creare una nuova hashmap per la symTable
					                nestingLevel++;
					                HashMap<String,STentry> hmn = new HashMap<String,STentry> ();
					                symTable.add(hmn);
					                
					match(input,LPAR,FOLLOW_LPAR_in_dec247); 
					ArrayList<Node> parTypes = new ArrayList<Node>();
					                    int parOffset=1;
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:59:17: (fid= ID COLON fty= type ( COMMA id= ID COLON ty= type )* )?
					int alt3=2;
					int LA3_0 = input.LA(1);
					if ( (LA3_0==ID) ) {
						alt3=1;
					}
					switch (alt3) {
						case 1 :
							// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:59:18: fid= ID COLON fty= type ( COMMA id= ID COLON ty= type )*
							{
							fid=(Token)match(input,ID,FOLLOW_ID_in_dec270); 
							match(input,COLON,FOLLOW_COLON_in_dec272); 
							pushFollow(FOLLOW_type_in_dec276);
							fty=type();
							state._fsp--;


							                  parTypes.add(fty);
							                  ParNode fpar = new ParNode((fid!=null?fid.getText():null),fty);
							                  f.addPar(fpar);
							                  if ( hmn.put((fid!=null?fid.getText():null),new STentry(fpar,nestingLevel,fty,parOffset++)) != null  )
							                  {System.out.println("Parameter id "+(fid!=null?fid.getText():null)+" at line "+(fid!=null?fid.getLine():0)+" already declared");
							                   System.exit(0);}
							                  
							// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:68:19: ( COMMA id= ID COLON ty= type )*
							loop2:
							while (true) {
								int alt2=2;
								int LA2_0 = input.LA(1);
								if ( (LA2_0==COMMA) ) {
									alt2=1;
								}

								switch (alt2) {
								case 1 :
									// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:68:20: COMMA id= ID COLON ty= type
									{
									match(input,COMMA,FOLLOW_COMMA_in_dec317); 
									id=(Token)match(input,ID,FOLLOW_ID_in_dec321); 
									match(input,COLON,FOLLOW_COLON_in_dec323); 
									pushFollow(FOLLOW_type_in_dec327);
									ty=type();
									state._fsp--;


									                    parTypes.add(ty);
									                    ParNode par = new ParNode((id!=null?id.getText():null),ty);
									                    f.addPar(par);
									                    if ( hmn.put((id!=null?id.getText():null),new STentry(par,nestingLevel,ty,parOffset++)) != null  )
									                    {System.out.println("Parameter id "+(id!=null?id.getText():null)+" at line "+(id!=null?id.getLine():0)+" already declared");
									                     System.exit(0);}
									                    
									}
									break;

								default :
									break loop2;
								}
							}

							}
							break;

					}

					match(input,RPAR,FOLLOW_RPAR_in_dec406); 
					entry.addType( new ArrowTypeNode(parTypes , t) );
					ArrayList<Node> decList = new ArrayList<Node>();
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:81:15: ( LET d= dec IN )?
					int alt4=2;
					int LA4_0 = input.LA(1);
					if ( (LA4_0==LET) ) {
						alt4=1;
					}
					switch (alt4) {
						case 1 :
							// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:81:16: LET d= dec IN
							{
							match(input,LET,FOLLOW_LET_in_dec441); 
							pushFollow(FOLLOW_dec_in_dec445);
							d=dec();
							state._fsp--;

							match(input,IN,FOLLOW_IN_in_dec447); 
							decList=d;
							}
							break;

					}

					pushFollow(FOLLOW_exp_in_dec470);
					e=exp();
					state._fsp--;

					match(input,SEMIC,FOLLOW_SEMIC_in_dec472); 
					//chiudere scope
					               symTable.remove(nestingLevel--);
					               f.addDecBody(decList,e);
					              
					}
					break;

				default :
					break loop5;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return astlist;
	}
	// $ANTLR end "dec"



	// $ANTLR start "type"
	// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:90:1: type returns [Node ast] : ( INT | BOOL );
	public final Node type() throws RecognitionException {
		Node ast = null;


		try {
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:91:9: ( INT | BOOL )
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0==INT) ) {
				alt6=1;
			}
			else if ( (LA6_0==BOOL) ) {
				alt6=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}

			switch (alt6) {
				case 1 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:91:11: INT
					{
					match(input,INT,FOLLOW_INT_in_type534); 
					ast =new IntTypeNode();
					}
					break;
				case 2 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:92:11: BOOL
					{
					match(input,BOOL,FOLLOW_BOOL_in_type549); 
					ast =new BoolTypeNode();
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ast;
	}
	// $ANTLR end "type"



	// $ANTLR start "exp"
	// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:95:1: exp returns [Node ast] : f= term ( PLUS l= term | MINUS l2= term | OR l3= term )* ;
	public final Node exp() throws RecognitionException {
		Node ast = null;


		Node f =null;
		Node l =null;
		Node l2 =null;
		Node l3 =null;

		try {
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:96:3: (f= term ( PLUS l= term | MINUS l2= term | OR l3= term )* )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:96:5: f= term ( PLUS l= term | MINUS l2= term | OR l3= term )*
			{
			pushFollow(FOLLOW_term_in_exp574);
			f=term();
			state._fsp--;

			ast = f;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:97:7: ( PLUS l= term | MINUS l2= term | OR l3= term )*
			loop7:
			while (true) {
				int alt7=4;
				switch ( input.LA(1) ) {
				case PLUS:
					{
					alt7=1;
					}
					break;
				case MINUS:
					{
					alt7=2;
					}
					break;
				case OR:
					{
					alt7=3;
					}
					break;
				}
				switch (alt7) {
				case 1 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:98:3: PLUS l= term
					{
					match(input,PLUS,FOLLOW_PLUS_in_exp588); 
					pushFollow(FOLLOW_term_in_exp592);
					l=term();
					state._fsp--;

					ast = new PlusNode (ast,l);
					}
					break;
				case 2 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:100:5: MINUS l2= term
					{
					match(input,MINUS,FOLLOW_MINUS_in_exp607); 
					pushFollow(FOLLOW_term_in_exp611);
					l2=term();
					state._fsp--;

					ast = new MinusNode (ast,l2);
					}
					break;
				case 3 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:102:5: OR l3= term
					{
					match(input,OR,FOLLOW_OR_in_exp627); 
					pushFollow(FOLLOW_term_in_exp631);
					l3=term();
					state._fsp--;

					ast = new OrNode (ast,l3);
					}
					break;

				default :
					break loop7;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ast;
	}
	// $ANTLR end "exp"



	// $ANTLR start "term"
	// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:107:1: term returns [Node ast] : f= value ( TIMES l= value | DIV l2= value | AND l3= value )* ;
	public final Node term() throws RecognitionException {
		Node ast = null;


		Node f =null;
		Node l =null;
		Node l2 =null;
		Node l3 =null;

		try {
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:108:2: (f= value ( TIMES l= value | DIV l2= value | AND l3= value )* )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:108:4: f= value ( TIMES l= value | DIV l2= value | AND l3= value )*
			{
			pushFollow(FOLLOW_value_in_term667);
			f=value();
			state._fsp--;

			ast = f;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:109:6: ( TIMES l= value | DIV l2= value | AND l3= value )*
			loop8:
			while (true) {
				int alt8=4;
				switch ( input.LA(1) ) {
				case TIMES:
					{
					alt8=1;
					}
					break;
				case DIV:
					{
					alt8=2;
					}
					break;
				case AND:
					{
					alt8=3;
					}
					break;
				}
				switch (alt8) {
				case 1 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:109:7: TIMES l= value
					{
					match(input,TIMES,FOLLOW_TIMES_in_term677); 
					pushFollow(FOLLOW_value_in_term681);
					l=value();
					state._fsp--;

					ast = new MultNode (ast,l);
					}
					break;
				case 2 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:111:5: DIV l2= value
					{
					match(input,DIV,FOLLOW_DIV_in_term695); 
					pushFollow(FOLLOW_value_in_term700);
					l2=value();
					state._fsp--;

					ast = new DivNode (ast,l2);
					}
					break;
				case 3 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:113:5: AND l3= value
					{
					match(input,AND,FOLLOW_AND_in_term714); 
					pushFollow(FOLLOW_value_in_term719);
					l3=value();
					state._fsp--;

					ast = new AndNode (ast,l3);
					}
					break;

				default :
					break loop8;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ast;
	}
	// $ANTLR end "term"



	// $ANTLR start "value"
	// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:118:1: value returns [Node ast] : f= fatt ( EQ l= fatt | GR l2= fatt | LE l3= fatt )* ;
	public final Node value() throws RecognitionException {
		Node ast = null;


		Node f =null;
		Node l =null;
		Node l2 =null;
		Node l3 =null;

		try {
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:119:2: (f= fatt ( EQ l= fatt | GR l2= fatt | LE l3= fatt )* )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:119:4: f= fatt ( EQ l= fatt | GR l2= fatt | LE l3= fatt )*
			{
			pushFollow(FOLLOW_fatt_in_value754);
			f=fatt();
			state._fsp--;

			ast = f;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:120:6: ( EQ l= fatt | GR l2= fatt | LE l3= fatt )*
			loop9:
			while (true) {
				int alt9=4;
				switch ( input.LA(1) ) {
				case EQ:
					{
					alt9=1;
					}
					break;
				case GR:
					{
					alt9=2;
					}
					break;
				case LE:
					{
					alt9=3;
					}
					break;
				}
				switch (alt9) {
				case 1 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:121:3: EQ l= fatt
					{
					match(input,EQ,FOLLOW_EQ_in_value767); 
					pushFollow(FOLLOW_fatt_in_value771);
					l=fatt();
					state._fsp--;

					ast = new EqualNode (ast,l);
					}
					break;
				case 2 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:123:8: GR l2= fatt
					{
					match(input,GR,FOLLOW_GR_in_value789); 
					pushFollow(FOLLOW_fatt_in_value793);
					l2=fatt();
					state._fsp--;

					ast = new GreaterNode (ast,l2);
					}
					break;
				case 3 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:125:8: LE l3= fatt
					{
					match(input,LE,FOLLOW_LE_in_value811); 
					pushFollow(FOLLOW_fatt_in_value815);
					l3=fatt();
					state._fsp--;

					ast = new LowerNode (ast,l3);
					}
					break;

				default :
					break loop9;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ast;
	}
	// $ANTLR end "value"



	// $ANTLR start "fatt"
	// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:130:1: fatt returns [Node ast] : (n= NAT | TRUE | FALSE | NOT LPAR en= exp RPAR | LPAR e= exp RPAR | IF x= exp THEN CLPAR y= exp CRPAR ELSE CLPAR z= exp CRPAR | PRINT LPAR e= exp RPAR |i= ID ( LPAR (fa= exp ( COMMA a= exp )* )? RPAR )? );
	public final Node fatt() throws RecognitionException {
		Node ast = null;


		Token n=null;
		Token i=null;
		Node en =null;
		Node e =null;
		Node x =null;
		Node y =null;
		Node z =null;
		Node fa =null;
		Node a =null;

		try {
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:131:2: (n= NAT | TRUE | FALSE | NOT LPAR en= exp RPAR | LPAR e= exp RPAR | IF x= exp THEN CLPAR y= exp CRPAR ELSE CLPAR z= exp CRPAR | PRINT LPAR e= exp RPAR |i= ID ( LPAR (fa= exp ( COMMA a= exp )* )? RPAR )? )
			int alt13=8;
			switch ( input.LA(1) ) {
			case NAT:
				{
				alt13=1;
				}
				break;
			case TRUE:
				{
				alt13=2;
				}
				break;
			case FALSE:
				{
				alt13=3;
				}
				break;
			case NOT:
				{
				alt13=4;
				}
				break;
			case LPAR:
				{
				alt13=5;
				}
				break;
			case IF:
				{
				alt13=6;
				}
				break;
			case PRINT:
				{
				alt13=7;
				}
				break;
			case ID:
				{
				alt13=8;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 13, 0, input);
				throw nvae;
			}
			switch (alt13) {
				case 1 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:131:4: n= NAT
					{
					n=(Token)match(input,NAT,FOLLOW_NAT_in_fatt855); 
					ast = new NatNode(Integer.parseInt((n!=null?n.getText():null)));
					}
					break;
				case 2 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:133:4: TRUE
					{
					match(input,TRUE,FOLLOW_TRUE_in_fatt870); 
					ast = new BoolNode(true);
					}
					break;
				case 3 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:135:4: FALSE
					{
					match(input,FALSE,FOLLOW_FALSE_in_fatt883); 
					ast = new BoolNode(false);
					}
					break;
				case 4 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:137:4: NOT LPAR en= exp RPAR
					{
					match(input,NOT,FOLLOW_NOT_in_fatt895); 
					match(input,LPAR,FOLLOW_LPAR_in_fatt897); 
					pushFollow(FOLLOW_exp_in_fatt901);
					en=exp();
					state._fsp--;

					match(input,RPAR,FOLLOW_RPAR_in_fatt903); 
					ast = new NotNode(en);
					}
					break;
				case 5 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:139:4: LPAR e= exp RPAR
					{
					match(input,LPAR,FOLLOW_LPAR_in_fatt916); 
					pushFollow(FOLLOW_exp_in_fatt920);
					e=exp();
					state._fsp--;

					match(input,RPAR,FOLLOW_RPAR_in_fatt922); 
					ast = e;
					}
					break;
				case 6 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:141:4: IF x= exp THEN CLPAR y= exp CRPAR ELSE CLPAR z= exp CRPAR
					{
					match(input,IF,FOLLOW_IF_in_fatt934); 
					pushFollow(FOLLOW_exp_in_fatt938);
					x=exp();
					state._fsp--;

					match(input,THEN,FOLLOW_THEN_in_fatt940); 
					match(input,CLPAR,FOLLOW_CLPAR_in_fatt942); 
					pushFollow(FOLLOW_exp_in_fatt946);
					y=exp();
					state._fsp--;

					match(input,CRPAR,FOLLOW_CRPAR_in_fatt948); 
					match(input,ELSE,FOLLOW_ELSE_in_fatt956); 
					match(input,CLPAR,FOLLOW_CLPAR_in_fatt958); 
					pushFollow(FOLLOW_exp_in_fatt962);
					z=exp();
					state._fsp--;

					match(input,CRPAR,FOLLOW_CRPAR_in_fatt964); 
					ast = new IfNode(x,y,z);
					}
					break;
				case 7 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:144:4: PRINT LPAR e= exp RPAR
					{
					match(input,PRINT,FOLLOW_PRINT_in_fatt977); 
					match(input,LPAR,FOLLOW_LPAR_in_fatt979); 
					pushFollow(FOLLOW_exp_in_fatt983);
					e=exp();
					state._fsp--;

					match(input,RPAR,FOLLOW_RPAR_in_fatt985); 
					ast = new PrintNode(e);
					}
					break;
				case 8 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:146:4: i= ID ( LPAR (fa= exp ( COMMA a= exp )* )? RPAR )?
					{
					i=(Token)match(input,ID,FOLLOW_ID_in_fatt998); 
					//cercare la dichiarazione
					           int j=nestingLevel;
					           STentry entry=null; 
					           while (j>=0 && entry==null)
					             entry=(symTable.get(j--)).get((i!=null?i.getText():null));
					           if (entry==null)
					           {System.out.println("Id "+(i!=null?i.getText():null)+" at line "+(i!=null?i.getLine():0)+" not declared");
					            System.exit(0);}               
						   ast = new IdNode((i!=null?i.getText():null),entry,nestingLevel-j-1);
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:156:5: ( LPAR (fa= exp ( COMMA a= exp )* )? RPAR )?
					int alt12=2;
					int LA12_0 = input.LA(1);
					if ( (LA12_0==LPAR) ) {
						alt12=1;
					}
					switch (alt12) {
						case 1 :
							// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:156:6: LPAR (fa= exp ( COMMA a= exp )* )? RPAR
							{
							match(input,LPAR,FOLLOW_LPAR_in_fatt1013); 
							ArrayList<Node> argList = new ArrayList<Node>();
							// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:158:8: (fa= exp ( COMMA a= exp )* )?
							int alt11=2;
							int LA11_0 = input.LA(1);
							if ( (LA11_0==FALSE||(LA11_0 >= ID && LA11_0 <= IF)||LA11_0==LPAR||(LA11_0 >= NAT && LA11_0 <= NOT)||LA11_0==PRINT||LA11_0==TRUE) ) {
								alt11=1;
							}
							switch (alt11) {
								case 1 :
									// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:158:9: fa= exp ( COMMA a= exp )*
									{
									pushFollow(FOLLOW_exp_in_fatt1033);
									fa=exp();
									state._fsp--;

									argList.add(fa);
									// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:159:9: ( COMMA a= exp )*
									loop10:
									while (true) {
										int alt10=2;
										int LA10_0 = input.LA(1);
										if ( (LA10_0==COMMA) ) {
											alt10=1;
										}

										switch (alt10) {
										case 1 :
											// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:159:10: COMMA a= exp
											{
											match(input,COMMA,FOLLOW_COMMA_in_fatt1046); 
											pushFollow(FOLLOW_exp_in_fatt1050);
											a=exp();
											state._fsp--;

											argList.add(a);
											}
											break;

										default :
											break loop10;
										}
									}

									}
									break;

							}

							ast =new CallNode((i!=null?i.getText():null),entry,argList,nestingLevel-j-1);
							match(input,RPAR,FOLLOW_RPAR_in_fatt1072); 
							}
							break;

					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ast;
	}
	// $ANTLR end "fatt"

	// Delegated rules



	public static final BitSet FOLLOW_exp_in_prog30 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_SEMIC_in_prog32 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LET_in_prog57 = new BitSet(new long[]{0x0000002000220000L});
	public static final BitSet FOLLOW_dec_in_prog86 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_IN_in_prog88 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_exp_in_prog92 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_SEMIC_in_prog94 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_VAR_in_dec144 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_ID_in_dec148 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_COLON_in_dec150 = new BitSet(new long[]{0x0000000000400040L});
	public static final BitSet FOLLOW_type_in_dec154 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ASS_in_dec156 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_exp_in_dec160 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_SEMIC_in_dec162 = new BitSet(new long[]{0x0000002000020002L});
	public static final BitSet FOLLOW_FUN_in_dec205 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_ID_in_dec209 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_COLON_in_dec211 = new BitSet(new long[]{0x0000000000400040L});
	public static final BitSet FOLLOW_type_in_dec215 = new BitSet(new long[]{0x0000000002000000L});
	public static final BitSet FOLLOW_LPAR_in_dec247 = new BitSet(new long[]{0x0000000100080000L});
	public static final BitSet FOLLOW_ID_in_dec270 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_COLON_in_dec272 = new BitSet(new long[]{0x0000000000400040L});
	public static final BitSet FOLLOW_type_in_dec276 = new BitSet(new long[]{0x0000000100000200L});
	public static final BitSet FOLLOW_COMMA_in_dec317 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_ID_in_dec321 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_COLON_in_dec323 = new BitSet(new long[]{0x0000000000400040L});
	public static final BitSet FOLLOW_type_in_dec327 = new BitSet(new long[]{0x0000000100000200L});
	public static final BitSet FOLLOW_RPAR_in_dec406 = new BitSet(new long[]{0x000000109B190000L});
	public static final BitSet FOLLOW_LET_in_dec441 = new BitSet(new long[]{0x0000002000220000L});
	public static final BitSet FOLLOW_dec_in_dec445 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_IN_in_dec447 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_exp_in_dec470 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_SEMIC_in_dec472 = new BitSet(new long[]{0x0000002000020002L});
	public static final BitSet FOLLOW_INT_in_type534 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_BOOL_in_type549 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_term_in_exp574 = new BitSet(new long[]{0x0000000064000002L});
	public static final BitSet FOLLOW_PLUS_in_exp588 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_term_in_exp592 = new BitSet(new long[]{0x0000000064000002L});
	public static final BitSet FOLLOW_MINUS_in_exp607 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_term_in_exp611 = new BitSet(new long[]{0x0000000064000002L});
	public static final BitSet FOLLOW_OR_in_exp627 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_term_in_exp631 = new BitSet(new long[]{0x0000000064000002L});
	public static final BitSet FOLLOW_value_in_term667 = new BitSet(new long[]{0x0000000800001012L});
	public static final BitSet FOLLOW_TIMES_in_term677 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_value_in_term681 = new BitSet(new long[]{0x0000000800001012L});
	public static final BitSet FOLLOW_DIV_in_term695 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_value_in_term700 = new BitSet(new long[]{0x0000000800001012L});
	public static final BitSet FOLLOW_AND_in_term714 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_value_in_term719 = new BitSet(new long[]{0x0000000800001012L});
	public static final BitSet FOLLOW_fatt_in_value754 = new BitSet(new long[]{0x0000000000844002L});
	public static final BitSet FOLLOW_EQ_in_value767 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_fatt_in_value771 = new BitSet(new long[]{0x0000000000844002L});
	public static final BitSet FOLLOW_GR_in_value789 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_fatt_in_value793 = new BitSet(new long[]{0x0000000000844002L});
	public static final BitSet FOLLOW_LE_in_value811 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_fatt_in_value815 = new BitSet(new long[]{0x0000000000844002L});
	public static final BitSet FOLLOW_NAT_in_fatt855 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_TRUE_in_fatt870 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FALSE_in_fatt883 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NOT_in_fatt895 = new BitSet(new long[]{0x0000000002000000L});
	public static final BitSet FOLLOW_LPAR_in_fatt897 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_exp_in_fatt901 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_RPAR_in_fatt903 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LPAR_in_fatt916 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_exp_in_fatt920 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_RPAR_in_fatt922 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IF_in_fatt934 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_exp_in_fatt938 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_THEN_in_fatt940 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_CLPAR_in_fatt942 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_exp_in_fatt946 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_CRPAR_in_fatt948 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_ELSE_in_fatt956 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_CLPAR_in_fatt958 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_exp_in_fatt962 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_CRPAR_in_fatt964 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PRINT_in_fatt977 = new BitSet(new long[]{0x0000000002000000L});
	public static final BitSet FOLLOW_LPAR_in_fatt979 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_exp_in_fatt983 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_RPAR_in_fatt985 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_fatt998 = new BitSet(new long[]{0x0000000002000002L});
	public static final BitSet FOLLOW_LPAR_in_fatt1013 = new BitSet(new long[]{0x000000119A190000L});
	public static final BitSet FOLLOW_exp_in_fatt1033 = new BitSet(new long[]{0x0000000100000200L});
	public static final BitSet FOLLOW_COMMA_in_fatt1046 = new BitSet(new long[]{0x000000109A190000L});
	public static final BitSet FOLLOW_exp_in_fatt1050 = new BitSet(new long[]{0x0000000100000200L});
	public static final BitSet FOLLOW_RPAR_in_fatt1072 = new BitSet(new long[]{0x0000000000000002L});
}
