// $ANTLR 3.1.1 BooleanFunction.g 2009-03-12 14:57:12
 package org.kuali.student.rules.internal.common.parsers; 

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

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


    public boolean isParen(String paren){
    	if (paren != null) {
    		if ( paren.equals("(") || paren.equals(")") )
    			return true;
    	}
    	return false;
    }


    public static class boolexpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "boolexpr"
    // BooleanFunction.g:38:1: boolexpr : orterm ;
    public final BooleanFunctionParser.boolexpr_return boolexpr() throws RecognitionException {
        BooleanFunctionParser.boolexpr_return retval = new BooleanFunctionParser.boolexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        BooleanFunctionParser.orterm_return orterm1 = null;



        try {
            // BooleanFunction.g:38:9: ( orterm )
            // BooleanFunction.g:38:11: orterm
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_orterm_in_boolexpr92);
            orterm1=orterm();

            state._fsp--;

            adaptor.addChild(root_0, orterm1.getTree());
            if ( isParen(input.LT(1).getText()) ) throw new RecognitionException(); 

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
    // $ANTLR end "boolexpr"

    public static class orterm_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "orterm"
    // BooleanFunction.g:39:1: orterm : andterm ( OR andterm )* ;
    public final BooleanFunctionParser.orterm_return orterm() throws RecognitionException {
        BooleanFunctionParser.orterm_return retval = new BooleanFunctionParser.orterm_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OR3=null;
        BooleanFunctionParser.andterm_return andterm2 = null;

        BooleanFunctionParser.andterm_return andterm4 = null;


        Object OR3_tree=null;

        try {
            // BooleanFunction.g:39:8: ( andterm ( OR andterm )* )
            // BooleanFunction.g:39:10: andterm ( OR andterm )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_andterm_in_orterm101);
            andterm2=andterm();

            state._fsp--;

            adaptor.addChild(root_0, andterm2.getTree());
            // BooleanFunction.g:39:18: ( OR andterm )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==OR) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // BooleanFunction.g:39:19: OR andterm
            	    {
            	    OR3=(Token)match(input,OR,FOLLOW_OR_in_orterm104); 
            	    OR3_tree = (Object)adaptor.create(OR3);
            	    root_0 = (Object)adaptor.becomeRoot(OR3_tree, root_0);

            	    pushFollow(FOLLOW_andterm_in_orterm107);
            	    andterm4=andterm();

            	    state._fsp--;

            	    adaptor.addChild(root_0, andterm4.getTree());

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
    // $ANTLR end "orterm"

    public static class andterm_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "andterm"
    // BooleanFunction.g:40:1: andterm : atom ( AND atom )* ;
    public final BooleanFunctionParser.andterm_return andterm() throws RecognitionException {
        BooleanFunctionParser.andterm_return retval = new BooleanFunctionParser.andterm_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token AND6=null;
        BooleanFunctionParser.atom_return atom5 = null;

        BooleanFunctionParser.atom_return atom7 = null;


        Object AND6_tree=null;

        try {
            // BooleanFunction.g:40:9: ( atom ( AND atom )* )
            // BooleanFunction.g:40:12: atom ( AND atom )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_atom_in_andterm117);
            atom5=atom();

            state._fsp--;

            adaptor.addChild(root_0, atom5.getTree());
            // BooleanFunction.g:40:17: ( AND atom )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==AND) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // BooleanFunction.g:40:18: AND atom
            	    {
            	    AND6=(Token)match(input,AND,FOLLOW_AND_in_andterm120); 
            	    AND6_tree = (Object)adaptor.create(AND6);
            	    root_0 = (Object)adaptor.becomeRoot(AND6_tree, root_0);

            	    pushFollow(FOLLOW_atom_in_andterm123);
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
    // $ANTLR end "andterm"

    public static class atom_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "atom"
    // BooleanFunction.g:41:1: atom : ( ALPHA | ( LP orterm RP ) );
    public final BooleanFunctionParser.atom_return atom() throws RecognitionException {
        BooleanFunctionParser.atom_return retval = new BooleanFunctionParser.atom_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ALPHA8=null;
        Token LP9=null;
        Token RP11=null;
        BooleanFunctionParser.orterm_return orterm10 = null;


        Object ALPHA8_tree=null;
        Object LP9_tree=null;
        Object RP11_tree=null;

        try {
            // BooleanFunction.g:41:7: ( ALPHA | ( LP orterm RP ) )
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
                    // BooleanFunction.g:41:9: ALPHA
                    {
                    root_0 = (Object)adaptor.nil();

                    ALPHA8=(Token)match(input,ALPHA,FOLLOW_ALPHA_in_atom133); 
                    ALPHA8_tree = (Object)adaptor.create(ALPHA8);
                    adaptor.addChild(root_0, ALPHA8_tree);


                    }
                    break;
                case 2 :
                    // BooleanFunction.g:41:17: ( LP orterm RP )
                    {
                    root_0 = (Object)adaptor.nil();

                    // BooleanFunction.g:41:17: ( LP orterm RP )
                    // BooleanFunction.g:41:18: LP orterm RP
                    {
                    LP9=(Token)match(input,LP,FOLLOW_LP_in_atom138); 
                    pushFollow(FOLLOW_orterm_in_atom141);
                    orterm10=orterm();

                    state._fsp--;

                    adaptor.addChild(root_0, orterm10.getTree());
                    RP11=(Token)match(input,RP,FOLLOW_RP_in_atom143); 

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


 

    public static final BitSet FOLLOW_orterm_in_boolexpr92 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andterm_in_orterm101 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_OR_in_orterm104 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_andterm_in_orterm107 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_atom_in_andterm117 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_AND_in_andterm120 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_atom_in_andterm123 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_ALPHA_in_atom133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LP_in_atom138 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_orterm_in_atom141 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RP_in_atom143 = new BitSet(new long[]{0x0000000000000002L});

}