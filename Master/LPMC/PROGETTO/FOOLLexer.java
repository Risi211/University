/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package p2;

// $ANTLR 3.5.1 /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g 2015-02-04 18:46:53

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class FOOLLexer extends Lexer {
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
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public FOOLLexer() {} 
	public FOOLLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public FOOLLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "/home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g"; }

	// $ANTLR start "SEMIC"
	public final void mSEMIC() throws RecognitionException {
		try {
			int _type = SEMIC;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:168:7: ( ';' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:168:9: ';'
			{
			match(';'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SEMIC"

	// $ANTLR start "COLON"
	public final void mCOLON() throws RecognitionException {
		try {
			int _type = COLON;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:169:7: ( ':' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:169:9: ':'
			{
			match(':'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COLON"

	// $ANTLR start "COMMA"
	public final void mCOMMA() throws RecognitionException {
		try {
			int _type = COMMA;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:170:7: ( ',' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:170:9: ','
			{
			match(','); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMA"

	// $ANTLR start "EQ"
	public final void mEQ() throws RecognitionException {
		try {
			int _type = EQ;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:171:4: ( '==' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:171:6: '=='
			{
			match("=="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "EQ"

	// $ANTLR start "OR"
	public final void mOR() throws RecognitionException {
		try {
			int _type = OR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:172:4: ( '||' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:172:6: '||'
			{
			match("||"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "OR"

	// $ANTLR start "AND"
	public final void mAND() throws RecognitionException {
		try {
			int _type = AND;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:173:5: ( '&&' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:173:7: '&&'
			{
			match("&&"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "AND"

	// $ANTLR start "NOT"
	public final void mNOT() throws RecognitionException {
		try {
			int _type = NOT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:174:5: ( 'not' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:174:7: 'not'
			{
			match("not"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "NOT"

	// $ANTLR start "GR"
	public final void mGR() throws RecognitionException {
		try {
			int _type = GR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:175:4: ( '>=' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:175:6: '>='
			{
			match(">="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "GR"

	// $ANTLR start "LE"
	public final void mLE() throws RecognitionException {
		try {
			int _type = LE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:176:4: ( '<=' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:176:6: '<='
			{
			match("<="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LE"

	// $ANTLR start "ASS"
	public final void mASS() throws RecognitionException {
		try {
			int _type = ASS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:177:5: ( '=' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:177:7: '='
			{
			match('='); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ASS"

	// $ANTLR start "PLUS"
	public final void mPLUS() throws RecognitionException {
		try {
			int _type = PLUS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:178:6: ( '+' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:178:8: '+'
			{
			match('+'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PLUS"

	// $ANTLR start "MINUS"
	public final void mMINUS() throws RecognitionException {
		try {
			int _type = MINUS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:179:9: ( '-' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:179:11: '-'
			{
			match('-'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "MINUS"

	// $ANTLR start "TIMES"
	public final void mTIMES() throws RecognitionException {
		try {
			int _type = TIMES;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:180:7: ( '*' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:180:9: '*'
			{
			match('*'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "TIMES"

	// $ANTLR start "DIV"
	public final void mDIV() throws RecognitionException {
		try {
			int _type = DIV;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:181:6: ( '/' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:181:8: '/'
			{
			match('/'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DIV"

	// $ANTLR start "NAT"
	public final void mNAT() throws RecognitionException {
		try {
			int _type = NAT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:182:5: ( ( ( '1' .. '9' ) ( '0' .. '9' )* ) | '0' )
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( ((LA2_0 >= '1' && LA2_0 <= '9')) ) {
				alt2=1;
			}
			else if ( (LA2_0=='0') ) {
				alt2=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}

			switch (alt2) {
				case 1 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:182:7: ( ( '1' .. '9' ) ( '0' .. '9' )* )
					{
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:182:7: ( ( '1' .. '9' ) ( '0' .. '9' )* )
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:182:8: ( '1' .. '9' ) ( '0' .. '9' )*
					{
					if ( (input.LA(1) >= '1' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:182:18: ( '0' .. '9' )*
					loop1:
					while (true) {
						int alt1=2;
						int LA1_0 = input.LA(1);
						if ( ((LA1_0 >= '0' && LA1_0 <= '9')) ) {
							alt1=1;
						}

						switch (alt1) {
						case 1 :
							// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:
							{
							if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop1;
						}
					}

					}

					}
					break;
				case 2 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:182:33: '0'
					{
					match('0'); 
					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "NAT"

	// $ANTLR start "TRUE"
	public final void mTRUE() throws RecognitionException {
		try {
			int _type = TRUE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:183:6: ( 'true' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:183:8: 'true'
			{
			match("true"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "TRUE"

	// $ANTLR start "FALSE"
	public final void mFALSE() throws RecognitionException {
		try {
			int _type = FALSE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:184:7: ( 'false' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:184:9: 'false'
			{
			match("false"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FALSE"

	// $ANTLR start "LPAR"
	public final void mLPAR() throws RecognitionException {
		try {
			int _type = LPAR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:185:7: ( '(' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:185:9: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LPAR"

	// $ANTLR start "RPAR"
	public final void mRPAR() throws RecognitionException {
		try {
			int _type = RPAR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:186:6: ( ')' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:186:8: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "RPAR"

	// $ANTLR start "CLPAR"
	public final void mCLPAR() throws RecognitionException {
		try {
			int _type = CLPAR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:187:8: ( '{' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:187:10: '{'
			{
			match('{'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CLPAR"

	// $ANTLR start "CRPAR"
	public final void mCRPAR() throws RecognitionException {
		try {
			int _type = CRPAR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:188:7: ( '}' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:188:9: '}'
			{
			match('}'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CRPAR"

	// $ANTLR start "IF"
	public final void mIF() throws RecognitionException {
		try {
			int _type = IF;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:189:5: ( 'if' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:189:7: 'if'
			{
			match("if"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "IF"

	// $ANTLR start "THEN"
	public final void mTHEN() throws RecognitionException {
		try {
			int _type = THEN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:190:7: ( 'then' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:190:9: 'then'
			{
			match("then"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "THEN"

	// $ANTLR start "ELSE"
	public final void mELSE() throws RecognitionException {
		try {
			int _type = ELSE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:191:7: ( 'else' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:191:9: 'else'
			{
			match("else"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ELSE"

	// $ANTLR start "PRINT"
	public final void mPRINT() throws RecognitionException {
		try {
			int _type = PRINT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:192:7: ( 'print' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:192:9: 'print'
			{
			match("print"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PRINT"

	// $ANTLR start "LET"
	public final void mLET() throws RecognitionException {
		try {
			int _type = LET;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:193:5: ( 'let' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:193:7: 'let'
			{
			match("let"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LET"

	// $ANTLR start "IN"
	public final void mIN() throws RecognitionException {
		try {
			int _type = IN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:194:4: ( 'in' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:194:6: 'in'
			{
			match("in"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "IN"

	// $ANTLR start "VAR"
	public final void mVAR() throws RecognitionException {
		try {
			int _type = VAR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:195:5: ( 'var' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:195:7: 'var'
			{
			match("var"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "VAR"

	// $ANTLR start "FUN"
	public final void mFUN() throws RecognitionException {
		try {
			int _type = FUN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:196:5: ( 'fun' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:196:7: 'fun'
			{
			match("fun"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FUN"

	// $ANTLR start "INT"
	public final void mINT() throws RecognitionException {
		try {
			int _type = INT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:197:5: ( 'int' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:197:7: 'int'
			{
			match("int"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INT"

	// $ANTLR start "BOOL"
	public final void mBOOL() throws RecognitionException {
		try {
			int _type = BOOL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:198:6: ( 'bool' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:198:8: 'bool'
			{
			match("bool"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "BOOL"

	// $ANTLR start "ID"
	public final void mID() throws RecognitionException {
		try {
			int _type = ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:200:5: ( ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )* )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:200:7: ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )*
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:201:5: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )*
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( ((LA3_0 >= '0' && LA3_0 <= '9')||(LA3_0 >= 'A' && LA3_0 <= 'Z')||(LA3_0 >= 'a' && LA3_0 <= 'z')) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop3;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ID"

	// $ANTLR start "WHITESP"
	public final void mWHITESP() throws RecognitionException {
		try {
			int _type = WHITESP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:203:10: ( ( '\\t' | ' ' | '\\r' | '\\n' )+ )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:203:12: ( '\\t' | ' ' | '\\r' | '\\n' )+
			{
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:203:12: ( '\\t' | ' ' | '\\r' | '\\n' )+
			int cnt4=0;
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( ((LA4_0 >= '\t' && LA4_0 <= '\n')||LA4_0=='\r'||LA4_0==' ') ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:
					{
					if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt4 >= 1 ) break loop4;
					EarlyExitException eee = new EarlyExitException(4, input);
					throw eee;
				}
				cnt4++;
			}

			 skip(); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WHITESP"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			int _type = COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:205:9: ( '/*' ( options {greedy=false; } : . )* '*/' )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:205:11: '/*' ( options {greedy=false; } : . )* '*/'
			{
			match("/*"); 

			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:205:16: ( options {greedy=false; } : . )*
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( (LA5_0=='*') ) {
					int LA5_1 = input.LA(2);
					if ( (LA5_1=='/') ) {
						alt5=2;
					}
					else if ( ((LA5_1 >= '\u0000' && LA5_1 <= '.')||(LA5_1 >= '0' && LA5_1 <= '\uFFFF')) ) {
						alt5=1;
					}

				}
				else if ( ((LA5_0 >= '\u0000' && LA5_0 <= ')')||(LA5_0 >= '+' && LA5_0 <= '\uFFFF')) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:205:43: .
					{
					matchAny(); 
					}
					break;

				default :
					break loop5;
				}
			}

			match("*/"); 

			 skip(); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMENT"

	// $ANTLR start "ERR"
	public final void mERR() throws RecognitionException {
		try {
			int _type = ERR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:207:9: ( . )
			// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:207:11: .
			{
			matchAny(); 
			 System.out.println("Invalid char: "+getText()); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ERR"

	@Override
	public void mTokens() throws RecognitionException {
		// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:8: ( SEMIC | COLON | COMMA | EQ | OR | AND | NOT | GR | LE | ASS | PLUS | MINUS | TIMES | DIV | NAT | TRUE | FALSE | LPAR | RPAR | CLPAR | CRPAR | IF | THEN | ELSE | PRINT | LET | IN | VAR | FUN | INT | BOOL | ID | WHITESP | COMMENT | ERR )
		int alt6=35;
		alt6 = dfa6.predict(input);
		switch (alt6) {
			case 1 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:10: SEMIC
				{
				mSEMIC(); 

				}
				break;
			case 2 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:16: COLON
				{
				mCOLON(); 

				}
				break;
			case 3 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:22: COMMA
				{
				mCOMMA(); 

				}
				break;
			case 4 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:28: EQ
				{
				mEQ(); 

				}
				break;
			case 5 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:31: OR
				{
				mOR(); 

				}
				break;
			case 6 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:34: AND
				{
				mAND(); 

				}
				break;
			case 7 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:38: NOT
				{
				mNOT(); 

				}
				break;
			case 8 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:42: GR
				{
				mGR(); 

				}
				break;
			case 9 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:45: LE
				{
				mLE(); 

				}
				break;
			case 10 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:48: ASS
				{
				mASS(); 

				}
				break;
			case 11 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:52: PLUS
				{
				mPLUS(); 

				}
				break;
			case 12 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:57: MINUS
				{
				mMINUS(); 

				}
				break;
			case 13 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:63: TIMES
				{
				mTIMES(); 

				}
				break;
			case 14 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:69: DIV
				{
				mDIV(); 

				}
				break;
			case 15 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:73: NAT
				{
				mNAT(); 

				}
				break;
			case 16 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:77: TRUE
				{
				mTRUE(); 

				}
				break;
			case 17 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:82: FALSE
				{
				mFALSE(); 

				}
				break;
			case 18 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:88: LPAR
				{
				mLPAR(); 

				}
				break;
			case 19 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:93: RPAR
				{
				mRPAR(); 

				}
				break;
			case 20 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:98: CLPAR
				{
				mCLPAR(); 

				}
				break;
			case 21 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:104: CRPAR
				{
				mCRPAR(); 

				}
				break;
			case 22 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:110: IF
				{
				mIF(); 

				}
				break;
			case 23 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:113: THEN
				{
				mTHEN(); 

				}
				break;
			case 24 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:118: ELSE
				{
				mELSE(); 

				}
				break;
			case 25 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:123: PRINT
				{
				mPRINT(); 

				}
				break;
			case 26 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:129: LET
				{
				mLET(); 

				}
				break;
			case 27 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:133: IN
				{
				mIN(); 

				}
				break;
			case 28 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:136: VAR
				{
				mVAR(); 

				}
				break;
			case 29 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:140: FUN
				{
				mFUN(); 

				}
				break;
			case 30 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:144: INT
				{
				mINT(); 

				}
				break;
			case 31 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:148: BOOL
				{
				mBOOL(); 

				}
				break;
			case 32 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:153: ID
				{
				mID(); 

				}
				break;
			case 33 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:156: WHITESP
				{
				mWHITESP(); 

				}
				break;
			case 34 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:164: COMMENT
				{
				mCOMMENT(); 

				}
				break;
			case 35 :
				// /home/luca/Scrivania/PROGETTO LPMC/tmp/5/FOOL.g:1:172: ERR
				{
				mERR(); 

				}
				break;

		}
	}


	protected DFA6 dfa6 = new DFA6(this);
	static final String DFA6_eotS =
		"\4\uffff\1\43\2\36\1\47\2\36\3\uffff\1\56\2\uffff\2\47\4\uffff\6\47\12"+
		"\uffff\1\47\11\uffff\4\47\4\uffff\1\105\1\107\5\47\1\uffff\1\115\3\47"+
		"\1\121\1\uffff\1\122\1\uffff\2\47\1\125\1\126\1\47\1\uffff\1\130\1\131"+
		"\1\47\2\uffff\1\133\1\47\2\uffff\1\135\2\uffff\1\136\1\uffff\1\137\3\uffff";
	static final String DFA6_eofS =
		"\140\uffff";
	static final String DFA6_minS =
		"\1\0\3\uffff\1\75\1\174\1\46\1\157\2\75\3\uffff\1\52\2\uffff\1\150\1\141"+
		"\4\uffff\1\146\1\154\1\162\1\145\1\141\1\157\12\uffff\1\164\11\uffff\1"+
		"\165\1\145\1\154\1\156\4\uffff\2\60\1\163\1\151\1\164\1\162\1\157\1\uffff"+
		"\1\60\1\145\1\156\1\163\1\60\1\uffff\1\60\1\uffff\1\145\1\156\2\60\1\154"+
		"\1\uffff\2\60\1\145\2\uffff\1\60\1\164\2\uffff\1\60\2\uffff\1\60\1\uffff"+
		"\1\60\3\uffff";
	static final String DFA6_maxS =
		"\1\uffff\3\uffff\1\75\1\174\1\46\1\157\2\75\3\uffff\1\52\2\uffff\1\162"+
		"\1\165\4\uffff\1\156\1\154\1\162\1\145\1\141\1\157\12\uffff\1\164\11\uffff"+
		"\1\165\1\145\1\154\1\156\4\uffff\2\172\1\163\1\151\1\164\1\162\1\157\1"+
		"\uffff\1\172\1\145\1\156\1\163\1\172\1\uffff\1\172\1\uffff\1\145\1\156"+
		"\2\172\1\154\1\uffff\2\172\1\145\2\uffff\1\172\1\164\2\uffff\1\172\2\uffff"+
		"\1\172\1\uffff\1\172\3\uffff";
	static final String DFA6_acceptS =
		"\1\uffff\1\1\1\2\1\3\6\uffff\1\13\1\14\1\15\1\uffff\2\17\2\uffff\1\22"+
		"\1\23\1\24\1\25\6\uffff\1\40\1\41\1\43\1\1\1\2\1\3\1\4\1\12\1\5\1\6\1"+
		"\uffff\1\40\1\10\1\11\1\13\1\14\1\15\1\42\1\16\1\17\4\uffff\1\22\1\23"+
		"\1\24\1\25\7\uffff\1\41\5\uffff\1\26\1\uffff\1\33\5\uffff\1\7\3\uffff"+
		"\1\35\1\36\2\uffff\1\32\1\34\1\uffff\1\20\1\27\1\uffff\1\30\1\uffff\1"+
		"\37\1\21\1\31";
	static final String DFA6_specialS =
		"\1\0\137\uffff}>";
	static final String[] DFA6_transitionS = {
			"\11\36\2\35\2\36\1\35\22\36\1\35\5\36\1\6\1\36\1\22\1\23\1\14\1\12\1"+
			"\3\1\13\1\36\1\15\1\17\11\16\1\2\1\1\1\11\1\4\1\10\2\36\32\34\6\36\1"+
			"\34\1\33\2\34\1\27\1\21\2\34\1\26\2\34\1\31\1\34\1\7\1\34\1\30\3\34\1"+
			"\20\1\34\1\32\4\34\1\24\1\5\1\25\uff82\36",
			"",
			"",
			"",
			"\1\42",
			"\1\44",
			"\1\45",
			"\1\46",
			"\1\50",
			"\1\51",
			"",
			"",
			"",
			"\1\55",
			"",
			"",
			"\1\61\11\uffff\1\60",
			"\1\62\23\uffff\1\63",
			"",
			"",
			"",
			"",
			"\1\70\7\uffff\1\71",
			"\1\72",
			"\1\73",
			"\1\74",
			"\1\75",
			"\1\76",
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
			"\1\100",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\101",
			"\1\102",
			"\1\103",
			"\1\104",
			"",
			"",
			"",
			"",
			"\12\47\7\uffff\32\47\6\uffff\32\47",
			"\12\47\7\uffff\32\47\6\uffff\23\47\1\106\6\47",
			"\1\110",
			"\1\111",
			"\1\112",
			"\1\113",
			"\1\114",
			"",
			"\12\47\7\uffff\32\47\6\uffff\32\47",
			"\1\116",
			"\1\117",
			"\1\120",
			"\12\47\7\uffff\32\47\6\uffff\32\47",
			"",
			"\12\47\7\uffff\32\47\6\uffff\32\47",
			"",
			"\1\123",
			"\1\124",
			"\12\47\7\uffff\32\47\6\uffff\32\47",
			"\12\47\7\uffff\32\47\6\uffff\32\47",
			"\1\127",
			"",
			"\12\47\7\uffff\32\47\6\uffff\32\47",
			"\12\47\7\uffff\32\47\6\uffff\32\47",
			"\1\132",
			"",
			"",
			"\12\47\7\uffff\32\47\6\uffff\32\47",
			"\1\134",
			"",
			"",
			"\12\47\7\uffff\32\47\6\uffff\32\47",
			"",
			"",
			"\12\47\7\uffff\32\47\6\uffff\32\47",
			"",
			"\12\47\7\uffff\32\47\6\uffff\32\47",
			"",
			"",
			""
	};

	static final short[] DFA6_eot = DFA.unpackEncodedString(DFA6_eotS);
	static final short[] DFA6_eof = DFA.unpackEncodedString(DFA6_eofS);
	static final char[] DFA6_min = DFA.unpackEncodedStringToUnsignedChars(DFA6_minS);
	static final char[] DFA6_max = DFA.unpackEncodedStringToUnsignedChars(DFA6_maxS);
	static final short[] DFA6_accept = DFA.unpackEncodedString(DFA6_acceptS);
	static final short[] DFA6_special = DFA.unpackEncodedString(DFA6_specialS);
	static final short[][] DFA6_transition;

	static {
		int numStates = DFA6_transitionS.length;
		DFA6_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA6_transition[i] = DFA.unpackEncodedString(DFA6_transitionS[i]);
		}
	}

	protected class DFA6 extends DFA {

		public DFA6(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 6;
			this.eot = DFA6_eot;
			this.eof = DFA6_eof;
			this.min = DFA6_min;
			this.max = DFA6_max;
			this.accept = DFA6_accept;
			this.special = DFA6_special;
			this.transition = DFA6_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( SEMIC | COLON | COMMA | EQ | OR | AND | NOT | GR | LE | ASS | PLUS | MINUS | TIMES | DIV | NAT | TRUE | FALSE | LPAR | RPAR | CLPAR | CRPAR | IF | THEN | ELSE | PRINT | LET | IN | VAR | FUN | INT | BOOL | ID | WHITESP | COMMENT | ERR );";
		}
		@Override
		public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
			IntStream input = _input;
			int _s = s;
			switch ( s ) {
					case 0 : 
						int LA6_0 = input.LA(1);
						s = -1;
						if ( (LA6_0==';') ) {s = 1;}
						else if ( (LA6_0==':') ) {s = 2;}
						else if ( (LA6_0==',') ) {s = 3;}
						else if ( (LA6_0=='=') ) {s = 4;}
						else if ( (LA6_0=='|') ) {s = 5;}
						else if ( (LA6_0=='&') ) {s = 6;}
						else if ( (LA6_0=='n') ) {s = 7;}
						else if ( (LA6_0=='>') ) {s = 8;}
						else if ( (LA6_0=='<') ) {s = 9;}
						else if ( (LA6_0=='+') ) {s = 10;}
						else if ( (LA6_0=='-') ) {s = 11;}
						else if ( (LA6_0=='*') ) {s = 12;}
						else if ( (LA6_0=='/') ) {s = 13;}
						else if ( ((LA6_0 >= '1' && LA6_0 <= '9')) ) {s = 14;}
						else if ( (LA6_0=='0') ) {s = 15;}
						else if ( (LA6_0=='t') ) {s = 16;}
						else if ( (LA6_0=='f') ) {s = 17;}
						else if ( (LA6_0=='(') ) {s = 18;}
						else if ( (LA6_0==')') ) {s = 19;}
						else if ( (LA6_0=='{') ) {s = 20;}
						else if ( (LA6_0=='}') ) {s = 21;}
						else if ( (LA6_0=='i') ) {s = 22;}
						else if ( (LA6_0=='e') ) {s = 23;}
						else if ( (LA6_0=='p') ) {s = 24;}
						else if ( (LA6_0=='l') ) {s = 25;}
						else if ( (LA6_0=='v') ) {s = 26;}
						else if ( (LA6_0=='b') ) {s = 27;}
						else if ( ((LA6_0 >= 'A' && LA6_0 <= 'Z')||LA6_0=='a'||(LA6_0 >= 'c' && LA6_0 <= 'd')||(LA6_0 >= 'g' && LA6_0 <= 'h')||(LA6_0 >= 'j' && LA6_0 <= 'k')||LA6_0=='m'||LA6_0=='o'||(LA6_0 >= 'q' && LA6_0 <= 's')||LA6_0=='u'||(LA6_0 >= 'w' && LA6_0 <= 'z')) ) {s = 28;}
						else if ( ((LA6_0 >= '\t' && LA6_0 <= '\n')||LA6_0=='\r'||LA6_0==' ') ) {s = 29;}
						else if ( ((LA6_0 >= '\u0000' && LA6_0 <= '\b')||(LA6_0 >= '\u000B' && LA6_0 <= '\f')||(LA6_0 >= '\u000E' && LA6_0 <= '\u001F')||(LA6_0 >= '!' && LA6_0 <= '%')||LA6_0=='\''||LA6_0=='.'||(LA6_0 >= '?' && LA6_0 <= '@')||(LA6_0 >= '[' && LA6_0 <= '`')||(LA6_0 >= '~' && LA6_0 <= '\uFFFF')) ) {s = 30;}
						if ( s>=0 ) return s;
						break;
			}
			NoViableAltException nvae =
				new NoViableAltException(getDescription(), 6, _s, input);
			error(nvae);
			throw nvae;
		}
	}

}
