/**
 * Lexer implementation for the paleo-lib.
 * (Generated from ./paleo-lib/src/main/jflex/jflexer.jflex)
 */

package paleo.lib.parser;

import java.util.ArrayList;

import paleo.lib.token.*;
import paleo.lib.historic.HistoricToken;

%%

%public
%final
%class JFLexer
%unicode

white	= [ \t\f]+
digit 	= [0-9]
integer = [-]?{digit}+
real 	= [-]?{integer}("."{integer})

%state HIST

%%

<YYINITIAL> {
	"hist" 		{ yybegin(HIST);	}

	{white}		{ }
	{real} 		{ return(new DoubleOperandToken(Double.parseDouble(yytext()))); }
	{integer} 	{ return(new IntegerOperandToken(Integer.parseInt(yytext()))); }

	"+" 		{ return(OperationToken.SUM); }
	"-" 		{ return(OperationToken.SUB); }
	"*" 		{ return(OperationToken.MULT); }
	"/" 		{ return(OperationToken.DIV); }
	"(" 		{ return(OperationToken.LPAREN); }
	")" 		{ return(OperationToken.RPAREN); }
}

<HIST> {
	"(" 		{ }
	{integer} 	{ return(new HistoricToken(Integer.parseInt(yytext()))); }
	")" 		{ yybegin(YYINITIAL); }
}
