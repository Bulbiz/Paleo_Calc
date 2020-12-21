/**
 * Lexer implementation for the paleo-lib.
 * (Generated from ./paleo-lib/src/main/jflex/jflexer.jflex)
 */

package paleo.lib.parser;

import java.util.ArrayList;

import paleo.lib.token.*;
import paleo.lib.historic.HistoricToken;
import paleo.lib.historic.exception.InvalidHistoricTokenException;
import paleo.lib.token.OperandToken;
import java.util.List;
import paleo.lib.token.SetOperandToken;

%%

%public
%final
%class JFLexer
%unicode

%{
	private boolean histFlag = false;
	private HistoricToken currentToken = null;

	private SetOperandToken.SetBuilder setBuilder;
%}

white	= [ \t\f]+
digit 	= [0-9]
integer = [-]?{digit}+
real 	= [-]?{integer}("."{integer})

%state HIST
%state SET

%%

<YYINITIAL> {
	"hist" 		{ this.histFlag = false; this.currentToken = null; yybegin(HIST); }

	{white}		{ }
	{real} 		{ return(new DoubleOperandToken(Double.parseDouble(yytext()))); }
	{integer} 	{ return(new IntegerOperandToken(Integer.parseInt(yytext()))); }
    "true"		{return new BooleanOperandToken(true);}
    "false"		{return new BooleanOperandToken(false);}

    "not"		{ return(OperationToken.NOT);}
    "and"		{ return(OperationToken.AND);}
    "or"		{ return(OperationToken.OR);}
	"+" 		{ return(OperationToken.SUM); }
	"-" 		{ return(OperationToken.SUB); }
	"*" 		{ return(OperationToken.MULT); }
	"/" 		{ return(OperationToken.DIV); }
	"(" 		{ return(OperationToken.LPAREN); }
	")" 		{ return(OperationToken.RPAREN); }

	"inter"		{return(OperationToken.INTER);}
	"union"		{return(OperationToken.UNION);}
	"diff"		{return(OperationToken.DIFF);}

	"{" 		{ setBuilder = new SetOperandToken.SetBuilder(); yybegin(SET);}
}

<SET> {
	{integer}	{ setBuilder.add(new IntegerOperandToken(Integer.parseInt(yytext())));}
	{real} 		{ setBuilder.add(new DoubleOperandToken(Double.parseDouble(yytext()))); }
	"true"		{ setBuilder.add(new BooleanOperandToken(true));}
	"false"		{ setBuilder.add(new BooleanOperandToken(false));}
		
	{white}		{ }
	";"			{ }
	"}"			
		{
			yybegin(YYINITIAL);
			SetOperandToken res = setBuilder.build();
        	setBuilder = null;
        	return res;
		}
}
<HIST> {
	"("
	{
		if (false != this.histFlag)
			throw new InvalidHistoricTokenException();
		this.histFlag = true;
	}

	{integer}
	{
		if (true != this.histFlag)
			throw new InvalidHistoricTokenException();
		this.currentToken = new HistoricToken(Integer.parseInt(yytext()));
	}

	")"
	{
		if (null == this.currentToken)
			throw new InvalidHistoricTokenException();
		yybegin(YYINITIAL);
		return this.currentToken;
	}
}
