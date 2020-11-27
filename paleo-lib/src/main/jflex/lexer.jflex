package paleo.lib.parser;

import java.util.ArrayList;
import paleo.lib.token.*;

%%

%public
%final
%class Lexer
%unicode

white	= [ \t\f]+
digit 	= [0-9]
integer = {digit}+
real 	= {integer}("."{integer})

%%

{white}		{ }

{real} 		{
	OperandToken tok = OperandToken.REAL_VALUE;
	tok.setValue(yytext());
	return(tok);
}
{integer} 	{
	OperandToken tok = OperandToken.INTEGER_VALUE;
	tok.setValue(yytext());
	return(tok);
}
"+" 		{ return(OperationToken.SUM); }
"-" 		{ return(OperationToken.SUB); }
"*" 		{ return(OperationToken.MULT); }
"/" 		{ return(OperationToken.DIV); }
"(" 		{ return(OperationToken.LPAREN); }
")" 		{ return(OperationToken.RPAREN); }
