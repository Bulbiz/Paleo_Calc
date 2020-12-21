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
	/** Attributes used for historic lexing. */
	private boolean histFlag = false;
	private HistoricToken currentToken = null;

	/** Attributes used for set lexing. */
	private SetOperandToken.SetBuilder setBuilder;
%}

white	= [ \t\f]+
digit 	= [0-9]
integer = [-]?{digit}+
real 	= [-]?{integer}("."{integer})

%state HIST
%state SET
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

	"{" 		{ setBuilder = new SetOperandToken.SetBuilder(); yybegin(SET); }
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

	"inter"		{ yybegin(YYINITIAL); return(OperationToken.INTER); }
	"union"		{ yybegin(YYINITIAL); return(OperationToken.UNION); }
	"diff"		{ yybegin(YYINITIAL); return(OperationToken.DIFF); }
}

<SET> {
	{integer}	{ setBuilder.add(new IntegerOperandToken(Integer.parseInt(yytext()))); }
	{real} 		{ setBuilder.add(new DoubleOperandToken(Double.parseDouble(yytext()))); }
	"true"		{ setBuilder.add(new BooleanOperandToken(true)); }
	"false"		{ setBuilder.add(new BooleanOperandToken(false)); }

	{white}		{ }
	";"			{ }

	"}"
	{
		SetOperandToken res = setBuilder.build();
		setBuilder = null;
		yybegin(OPERATION);
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
		yybegin(OPERATION);
		return this.currentToken;
	}
}
