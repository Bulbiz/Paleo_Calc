/**
 * Lexer implementation for the paleo-lib.
 * (Generated from ./paleo-lib/src/main/jflex/jflexer.jflex)
 */

package paleo.lib.parser;

import java.util.ArrayList;

import paleo.lib.token.*;
import paleo.lib.historic.HistoricToken;
import paleo.lib.historic.exception.InvalidHistoricTokenException;

%%

%public
%final
%class JFLexer
%unicode

%{
	private boolean histFlag = false;
	private HistoricToken currentToken = null;
%}

white	= [ \t\f]+
digit 	= [0-9]
integer = [-]?{digit}+
real 	= [-]?{integer}("."{integer})

%state HIST
%state OPERATION

%%

<YYINITIAL> {
	"hist" 		{ this.histFlag = false; this.currentToken = null; yybegin(HIST); }

	{white}		{ }
	{real} 		{ yybegin(OPERATION); return(new DoubleOperandToken(Double.parseDouble(yytext()))); }
	{integer} 	{ yybegin(OPERATION); return(new IntegerOperandToken(Integer.parseInt(yytext()))); }

	"not"		{ return(OperationToken.NOT);}
    "true"		{ yybegin(OPERATION); return new BooleanOperandToken(true);}
    "false"		{ yybegin(OPERATION); return new BooleanOperandToken(false);}

	"(" 		{ return(OperationToken.LPAREN); }
	")" 		{ return(OperationToken.RPAREN); }
}

<OPERATION> {
	{white}		{ }

	"+" 		{ yybegin(YYINITIAL); return(OperationToken.SUM); }
	"-" 		{ yybegin(YYINITIAL); return(OperationToken.SUB); }
	"*" 		{ yybegin(YYINITIAL); return(OperationToken.MULT); }
	"/" 		{ yybegin(YYINITIAL); return(OperationToken.DIV); }

	"not"		{ yybegin(YYINITIAL); return(OperationToken.NOT);}
    "and"		{ yybegin(YYINITIAL); return(OperationToken.AND);}
    "or"		{ yybegin(YYINITIAL); return(OperationToken.OR);}

	"(" 		{ return(OperationToken.LPAREN); }
	")" 		{ return(OperationToken.RPAREN); }
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
		yybegin(OPERATION);
		return this.currentToken;
	}
}
