/**
 * Lexer implementation for the paleo-lib.
 * (Generated from ./paleo-lib/src/main/jflex/jflexer.jflex)
 */

package paleo.lib.parser;

import paleo.lib.token.operation.*;
import paleo.lib.token.operand.*;
import paleo.lib.token.Yytoken;
import paleo.lib.historic.HistoricToken;

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
	private SetOperandToken set;
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

	"not"		{ return(new NotOperationToken());}
    "true"		{ yybegin(OPERATION); return new BooleanOperandToken(true);}
    "false"		{ yybegin(OPERATION); return new BooleanOperandToken(false);}

	"(" 		{ return(ParenOperationToken.LEFT); }
	")" 		{ return(ParenOperationToken.RIGHT); }

	"{" 		{ set = new SetOperandToken(); yybegin(SET); }
}

<OPERATION> {
	{white}		{ }

	"+" 		{ yybegin(YYINITIAL); return(new SumOperationToken()); }
	"-" 		{ yybegin(YYINITIAL); return(new SubOperationToken()); }
	"*" 		{ yybegin(YYINITIAL); return(new MultOperationToken()); }
	"/" 		{ yybegin(YYINITIAL); return(new DivOperationToken()); }

	"not"		{ yybegin(YYINITIAL); return(new NotOperationToken()); }
    "and"		{ yybegin(YYINITIAL); return(new AndOperationToken()); }
    "or"		{ yybegin(YYINITIAL); return(new OrOperationToken()); }

	"(" 		{ return(ParenOperationToken.LEFT); }
	")" 		{ return(ParenOperationToken.RIGHT); }

	"inter"		{ yybegin(YYINITIAL); return(new InterOperationToken()); }
	"union"		{ yybegin(YYINITIAL); return(new UnionOperationToken()); }
	"diff"		{ yybegin(YYINITIAL); return(new DiffOperationToken()); }
}

<SET> {
	{integer}	{ set.add(new IntegerOperandToken(Integer.parseInt(yytext()))); }
	{real} 		{ set.add(new DoubleOperandToken(Double.parseDouble(yytext()))); }
	"true"		{ set.add(new BooleanOperandToken(true)); }
	"false"		{ set.add(new BooleanOperandToken(false)); }

	{white}		{ }
	";"			{ }
	","			{ }

	"}"
	{
		yybegin(OPERATION);
		return set;
	}
}

<HIST> {
	"("
	{
		if (false != this.histFlag)
			throw new HistoricToken.InvalidHistoricTokenError();
		this.histFlag = true;
	}

	{integer}
	{
		if (true != this.histFlag)
			throw new HistoricToken.InvalidHistoricTokenError();
		this.currentToken = new HistoricToken(Integer.parseInt(yytext()));
	}

	")"
	{
		if (null == this.currentToken)
			throw new HistoricToken.InvalidHistoricTokenError();
		yybegin(OPERATION);
		return this.currentToken;
	}
}

[^] 			{ throw new Parser.UnknownSymbError(yytext()); }
