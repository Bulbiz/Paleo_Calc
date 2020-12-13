/**
 * Lexer implementation for the paleo-lib.
 * (Generated from ./paleo-lib/src/main/jflex/jflexer.jflex)
 */

package paleo.lib.parser;

import java.util.ArrayList;
import paleo.lib.token.*;

%%

%public
%final
%class JFLexer
%unicode

white	= [ \t\f]+
digit 	= [0-9]
integer = [-]?{digit}+
real 	= [-]?{integer}("."{integer})

%%

{white}		{ }

{real} 		{
	return(new DoubleOperandToken(Double.parseDouble(yytext())));
}
{integer} 	{
	return(new IntegerOperandToken(Integer.parseInt(yytext())));
}

"true"			{
	return new BooleanOperandToken(true);
}

"false"			{
	return new BooleanOperandToken(false);
}


"+" 		{ return(OperationToken.SUM); }
"-" 		{ return(OperationToken.SUB); }
"*" 		{ return(OperationToken.MULT); }
"/" 		{ return(OperationToken.DIV); }
"(" 		{ return(OperationToken.LPAREN); }
")" 		{ return(OperationToken.RPAREN); }
