/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

// $ANTLR 3.1.1 BooleanFunction.g 2009-06-03 01:05:39
 package org.kuali.student.common.messagebuilder.booleanmessage.ast.parsers;

import java.util.List;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.TreeAdaptor;

public class BooleanFunctionParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "OR", "AND", "LP", "RP", "ALPHA", "UPPERCASE", "NUMBER", "WHITESPACE", "LOWERCASE"
    };
    public static final int UPPERCASE=9;
    public static final int LOWERCASE=12;
    public static final int RP=7;
    public static final int OR=4;
    public static final int NUMBER=10;
    public static final int LP=6;
    public static final int WHITESPACE=11;
    public static final int AND=5;
    public static final int EOF=-1;
    public static final int ALPHA=8;

    // delegates
    // delegators


        public BooleanFunctionParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public BooleanFunctionParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);

        }

    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return BooleanFunctionParser.tokenNames; }
    public String getGrammarFileName() { return "BooleanFunction.g"; }


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


    public static class booleanExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "booleanExpression"
    // BooleanFunction.g:60:1: booleanExpression : orTerm ;
    public final BooleanFunctionParser.booleanExpression_return booleanExpression() throws RecognitionException {
        BooleanFunctionParser.booleanExpression_return retval = new BooleanFunctionParser.booleanExpression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        BooleanFunctionParser.orTerm_return orTerm1 = null;



        try {
            // BooleanFunction.g:60:18: ( orTerm )
            // BooleanFunction.g:60:22: orTerm
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_orTerm_in_booleanExpression111);
            orTerm1=orTerm();

            state._fsp--;

            adaptor.addChild(root_0, orTerm1.getTree());
            if ( isTokenParenthesis(input.LT(1).getText()) ) throw new RecognitionException();

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        catch (RecognitionException e) {
            throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "booleanExpression"

    public static class orTerm_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "orTerm"
    // BooleanFunction.g:61:1: orTerm : andTerm ( OR andTerm )* ;
    public final BooleanFunctionParser.orTerm_return orTerm() throws RecognitionException {
        BooleanFunctionParser.orTerm_return retval = new BooleanFunctionParser.orTerm_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OR3=null;
        BooleanFunctionParser.andTerm_return andTerm2 = null;

        BooleanFunctionParser.andTerm_return andTerm4 = null;


        Object OR3_tree=null;

        try {
            // BooleanFunction.g:61:9: ( andTerm ( OR andTerm )* )
            // BooleanFunction.g:61:13: andTerm ( OR andTerm )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_andTerm_in_orTerm123);
            andTerm2=andTerm();

            state._fsp--;

            adaptor.addChild(root_0, andTerm2.getTree());
            // BooleanFunction.g:61:21: ( OR andTerm )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==OR) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // BooleanFunction.g:61:22: OR andTerm
            	    {
            	    OR3=(Token)match(input,OR,FOLLOW_OR_in_orTerm126);
            	    OR3_tree = (Object)adaptor.create(OR3);
            	    root_0 = (Object)adaptor.becomeRoot(OR3_tree, root_0);

            	    pushFollow(FOLLOW_andTerm_in_orTerm129);
            	    andTerm4=andTerm();

            	    state._fsp--;

            	    adaptor.addChild(root_0, andTerm4.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        catch (RecognitionException e) {
            throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "orTerm"

    public static class andTerm_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "andTerm"
    // BooleanFunction.g:62:1: andTerm : atom ( AND atom )* ;
    public final BooleanFunctionParser.andTerm_return andTerm() throws RecognitionException {
        BooleanFunctionParser.andTerm_return retval = new BooleanFunctionParser.andTerm_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token AND6=null;
        BooleanFunctionParser.atom_return atom5 = null;

        BooleanFunctionParser.atom_return atom7 = null;


        Object AND6_tree=null;

        try {
            // BooleanFunction.g:62:9: ( atom ( AND atom )* )
            // BooleanFunction.g:62:13: atom ( AND atom )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_atom_in_andTerm140);
            atom5=atom();

            state._fsp--;

            adaptor.addChild(root_0, atom5.getTree());
            // BooleanFunction.g:62:18: ( AND atom )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==AND) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // BooleanFunction.g:62:19: AND atom
            	    {
            	    AND6=(Token)match(input,AND,FOLLOW_AND_in_andTerm143);
            	    AND6_tree = (Object)adaptor.create(AND6);
            	    root_0 = (Object)adaptor.becomeRoot(AND6_tree, root_0);

            	    pushFollow(FOLLOW_atom_in_andTerm146);
            	    atom7=atom();

            	    state._fsp--;

            	    adaptor.addChild(root_0, atom7.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        catch (RecognitionException e) {
            throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "andTerm"

    public static class atom_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "atom"
    // BooleanFunction.g:63:1: atom : ( ALPHA | ( LP orTerm RP ) );
    public final BooleanFunctionParser.atom_return atom() throws RecognitionException {
        BooleanFunctionParser.atom_return retval = new BooleanFunctionParser.atom_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ALPHA8=null;
        Token LP9=null;
        Token RP11=null;
        BooleanFunctionParser.orTerm_return orTerm10 = null;


        Object ALPHA8_tree=null;
        Object LP9_tree=null;
        Object RP11_tree=null;

        try {
            // BooleanFunction.g:63:9: ( ALPHA | ( LP orTerm RP ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==ALPHA) ) {
                alt3=1;
            }
            else if ( (LA3_0==LP) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // BooleanFunction.g:63:13: ALPHA
                    {
                    root_0 = (Object)adaptor.nil();

                    ALPHA8=(Token)match(input,ALPHA,FOLLOW_ALPHA_in_atom160);
                    ALPHA8_tree = (Object)adaptor.create(ALPHA8);
                    adaptor.addChild(root_0, ALPHA8_tree);


                    }
                    break;
                case 2 :
                    // BooleanFunction.g:63:21: ( LP orTerm RP )
                    {
                    root_0 = (Object)adaptor.nil();

                    // BooleanFunction.g:63:21: ( LP orTerm RP )
                    // BooleanFunction.g:63:22: LP orTerm RP
                    {
                    LP9=(Token)match(input,LP,FOLLOW_LP_in_atom165);
                    pushFollow(FOLLOW_orTerm_in_atom168);
                    orTerm10=orTerm();

                    state._fsp--;

                    adaptor.addChild(root_0, orTerm10.getTree());
                    RP11=(Token)match(input,RP,FOLLOW_RP_in_atom170);

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        catch (RecognitionException e) {
            throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "atom"

    // Delegated rules




    public static final BitSet FOLLOW_orTerm_in_booleanExpression111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andTerm_in_orTerm123 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_OR_in_orTerm126 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_andTerm_in_orTerm129 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_atom_in_andTerm140 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_AND_in_andTerm143 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_atom_in_andTerm146 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_ALPHA_in_atom160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LP_in_atom165 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_orTerm_in_atom168 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RP_in_atom170 = new BitSet(new long[]{0x0000000000000002L});

}