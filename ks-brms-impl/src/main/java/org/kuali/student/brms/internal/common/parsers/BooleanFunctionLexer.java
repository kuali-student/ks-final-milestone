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
 package org.kuali.student.brms.internal.common.parsers; 

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class BooleanFunctionLexer extends Lexer {
    public static final int UPPERCASE=9;
    public static final int LOWERCASE=12;
    public static final int RP=7;
    public static final int OR=4;
    public static final int LP=6;
    public static final int NUMBER=10;
    public static final int WHITESPACE=11;
    public static final int AND=5;
    public static final int EOF=-1;
    public static final int ALPHA=8;

    // delegates
    // delegators

    public BooleanFunctionLexer() {;} 
    public BooleanFunctionLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public BooleanFunctionLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "BooleanFunction.g"; }

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BooleanFunction.g:5:4: ( '+' )
            // BooleanFunction.g:5:6: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OR"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BooleanFunction.g:6:5: ( '*' )
            // BooleanFunction.g:6:7: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "LP"
    public final void mLP() throws RecognitionException {
        try {
            int _type = LP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BooleanFunction.g:7:4: ( '(' )
            // BooleanFunction.g:7:6: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LP"

    // $ANTLR start "RP"
    public final void mRP() throws RecognitionException {
        try {
            int _type = RP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BooleanFunction.g:8:4: ( ')' )
            // BooleanFunction.g:8:6: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RP"

    // $ANTLR start "ALPHA"
    public final void mALPHA() throws RecognitionException {
        try {
            int _type = ALPHA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BooleanFunction.g:69:9: ( UPPERCASE | ( UPPERCASE NUMBER ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( ((LA1_0>='A' && LA1_0<='Z')) ) {
                int LA1_1 = input.LA(2);

                if ( ((LA1_1>='0' && LA1_1<='9')) ) {
                    alt1=2;
                }
                else {
                    alt1=1;}
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // BooleanFunction.g:69:11: UPPERCASE
                    {
                    mUPPERCASE(); 

                    }
                    break;
                case 2 :
                    // BooleanFunction.g:69:23: ( UPPERCASE NUMBER )
                    {
                    // BooleanFunction.g:69:23: ( UPPERCASE NUMBER )
                    // BooleanFunction.g:69:24: UPPERCASE NUMBER
                    {
                    mUPPERCASE(); 
                    mNUMBER(); 

                    }


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ALPHA"

    // $ANTLR start "NUMBER"
    public final void mNUMBER() throws RecognitionException {
        try {
            int _type = NUMBER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BooleanFunction.g:71:9: ( ( '0' .. '9' )+ )
            // BooleanFunction.g:71:11: ( '0' .. '9' )+
            {
            // BooleanFunction.g:71:11: ( '0' .. '9' )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // BooleanFunction.g:71:12: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUMBER"

    // $ANTLR start "WHITESPACE"
    public final void mWHITESPACE() throws RecognitionException {
        try {
            int _type = WHITESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BooleanFunction.g:73:12: ( ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+ )
            // BooleanFunction.g:73:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            {
            // BooleanFunction.g:73:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='\t' && LA3_0<='\n')||(LA3_0>='\f' && LA3_0<='\r')||LA3_0==' ') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // BooleanFunction.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);

             _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHITESPACE"

    // $ANTLR start "LOWERCASE"
    public final void mLOWERCASE() throws RecognitionException {
        try {
            int _type = LOWERCASE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BooleanFunction.g:75:11: ( 'a' .. 'z' )
            // BooleanFunction.g:75:13: 'a' .. 'z'
            {
            matchRange('a','z'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LOWERCASE"

    // $ANTLR start "UPPERCASE"
    public final void mUPPERCASE() throws RecognitionException {
        try {
            // BooleanFunction.g:77:20: ( 'A' .. 'Z' )
            // BooleanFunction.g:77:22: 'A' .. 'Z'
            {
            matchRange('A','Z'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "UPPERCASE"

    public void mTokens() throws RecognitionException {
        // BooleanFunction.g:1:8: ( OR | AND | LP | RP | ALPHA | NUMBER | WHITESPACE | LOWERCASE )
        int alt4=8;
        switch ( input.LA(1) ) {
        case '+':
            {
            alt4=1;
            }
            break;
        case '*':
            {
            alt4=2;
            }
            break;
        case '(':
            {
            alt4=3;
            }
            break;
        case ')':
            {
            alt4=4;
            }
            break;
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
            {
            alt4=5;
            }
            break;
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            {
            alt4=6;
            }
            break;
        case '\t':
        case '\n':
        case '\f':
        case '\r':
        case ' ':
            {
            alt4=7;
            }
            break;
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'f':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 't':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
            {
            alt4=8;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 4, 0, input);

            throw nvae;
        }

        switch (alt4) {
            case 1 :
                // BooleanFunction.g:1:10: OR
                {
                mOR(); 

                }
                break;
            case 2 :
                // BooleanFunction.g:1:13: AND
                {
                mAND(); 

                }
                break;
            case 3 :
                // BooleanFunction.g:1:17: LP
                {
                mLP(); 

                }
                break;
            case 4 :
                // BooleanFunction.g:1:20: RP
                {
                mRP(); 

                }
                break;
            case 5 :
                // BooleanFunction.g:1:23: ALPHA
                {
                mALPHA(); 

                }
                break;
            case 6 :
                // BooleanFunction.g:1:29: NUMBER
                {
                mNUMBER(); 

                }
                break;
            case 7 :
                // BooleanFunction.g:1:36: WHITESPACE
                {
                mWHITESPACE(); 

                }
                break;
            case 8 :
                // BooleanFunction.g:1:47: LOWERCASE
                {
                mLOWERCASE(); 

                }
                break;

        }

    }


 

}