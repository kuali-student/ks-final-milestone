grammar BooleanFunction;

options {
    output=AST;
}

tokens {
    OR  = '+' ;
    AND = '*' ;
    LP  = '(' ;
    RP  = ')' ;
}

@header { package org.kuali.student.brms.internal.common.parsers; }
@lexer::header { package org.kuali.student.brms.internal.common.parsers; }

@members {
@Override
public String getErrorMessage(RecognitionException e, String[] tokenNames) {
    List<?> stack = getRuleInvocationStack(e, this.getClass().getName());
    String msg = null;
    if ( e instanceof NoViableAltException ) {
       NoViableAltException nvae = (NoViableAltException)e;
       msg = " No viable alternatives; token=" + e.token
          + " (decision=" + nvae.decisionNumber
          + " state " + nvae.stateNumber+")"
          + " decision=<<" + nvae.grammarDecisionDescription + ">>";
    }
    else {
       msg = super.getErrorMessage(e, tokenNames);
    }
    return stack + " " + msg;
}

@Override
public String getTokenErrorDisplay(Token t) {
    return t.toString();
}

private boolean isTokenParenthesis(String paren){
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

//booleanExpression: orTerm {if ( isTokenParenthesis(input.LT(1).getText()) ) System.out.println("+++ got paren +++"); };
booleanExpression:   orTerm {if ( isTokenParenthesis(input.LT(1).getText()) ) throw new RecognitionException(); };
orTerm  :   andTerm (OR^ andTerm)*;
andTerm :   atom (AND^ atom)*;
atom    :   ALPHA | (LP! orTerm RP!);

/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

ALPHA   : UPPERCASE | (UPPERCASE NUMBER) ;

NUMBER  : ('0'..'9')+ ;

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+    { $channel = HIDDEN; } ;

LOWERCASE : 'a'..'z' ;

fragment UPPERCASE : 'A'..'Z' ;
