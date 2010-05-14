grammar BooleanFunction;

options {
	output=AST;
}

tokens {
	OR 	= '+' ;
	AND	= '*' ;
	LP	= '(' ;
	RP	= ')' ;
}

@header { package org.kuali.student.rules.internal.common.parsers; }
@lexer::header { package org.kuali.student.rules.internal.common.parsers; }

@members {
public boolean isParen(String paren){
	if (paren != null) {
		if ( paren.equals("(") || paren.equals(")") )
			return true;
	}
	return false;
}
}

@rulecatch {
catch (RecognitionException e) {
throw e;
}
}

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

//boolexpr:	orterm {if ( isParen(input.LT(1).getText()) ) System.out.println("got paren"); };
boolexpr:	orterm {if ( isParen(input.LT(1).getText()) ) throw new RecognitionException(); };
orterm	:	andterm (OR^ andterm)*;
andterm : 	atom (AND^ atom)*;
atom 	:	ALPHA | (LP! orterm RP!);

/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

ALPHA	: UPPERCASE | (UPPERCASE NUMBER) ;

NUMBER  : ('0'..'9')+ ;

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ 	{ $channel = HIDDEN; } ;

LOWERCASE : 'a'..'z' ;

fragment UPPERCASE : 'A'..'Z' ;
