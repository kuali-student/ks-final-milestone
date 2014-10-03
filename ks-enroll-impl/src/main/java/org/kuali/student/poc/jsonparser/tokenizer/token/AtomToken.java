/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 7/31/2014
 */
package org.kuali.student.poc.jsonparser.tokenizer.token;

/**
 * Represents a single character JSON token
 *
 * @author Kuali Student Team
 */
public class AtomToken implements BaseToken {
    public String tokenType;

    private AtomToken(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return tokenType;
    }

    public static AtomToken createStartListToken() {
        return new AtomToken(TokenConstants.START_LIST_TOKEN);
    }

    public static AtomToken createEndListToken() {
        return new AtomToken(TokenConstants.END_LIST_TOKEN);
    }

    public static AtomToken createStartMapToken() {
        return new AtomToken(TokenConstants.START_MAP_TOKEN);
    }

    public static AtomToken createEndMapToken() {
        return new AtomToken(TokenConstants.END_MAP_TOKEN);
    }

    public static AtomToken createCommaToken() {
        return new AtomToken(TokenConstants.COMMA_TOKEN);
    }

    public static AtomToken createColonToken() {
        return new AtomToken(TokenConstants.COLON_TOKEN);
    }

    public static boolean isStartListToken(char ch) { return ch == '['; }

    public static boolean isEndListToken(char ch) { return ch == ']'; }

    public static boolean isStartMapToken(char ch) { return ch == '{'; }

    public static boolean isEndMapToken(char ch) { return ch == '}'; }

    public static boolean isCommaToken(char ch) { return ch == ','; }

    public static boolean isColonToken(char ch) { return ch == ':'; }

    @Override
    public String toString() {
        String result = "token/";
        if (tokenType.equals(TokenConstants.START_LIST_TOKEN)) {
            result += "startList";
        } else if (tokenType.equals(TokenConstants.END_LIST_TOKEN)) {
            result += "endList";
        } else if (tokenType.equals(TokenConstants.START_MAP_TOKEN)) {
            result += "startMap";
        } else if (tokenType.equals(TokenConstants.END_MAP_TOKEN)) {
            result += "endMap";
        } else if (tokenType.equals(TokenConstants.COLON_TOKEN)) {
            result += "colon";
        } else if (tokenType.equals(TokenConstants.COMMA_TOKEN)) {
            result += "comma";
        } else {
            result += "error";
        }
        return result;
    }

}
