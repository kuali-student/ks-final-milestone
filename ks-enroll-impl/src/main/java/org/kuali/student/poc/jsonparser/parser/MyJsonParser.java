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
 * Created by Charles on 8/5/2014
 */
package org.kuali.student.poc.jsonparser.parser;

import org.kuali.student.poc.jsonparser.json.BaseJsonObject;
import org.kuali.student.poc.jsonparser.json.MyJsonBoolean;
import org.kuali.student.poc.jsonparser.json.MyJsonList;
import org.kuali.student.poc.jsonparser.json.MyJsonMap;
import org.kuali.student.poc.jsonparser.json.MyJsonNull;
import org.kuali.student.poc.jsonparser.json.MyJsonNumber;
import org.kuali.student.poc.jsonparser.json.MyJsonString;
import org.kuali.student.poc.jsonparser.tokenizer.MyJsonTokenizer;
import org.kuali.student.poc.jsonparser.tokenizer.token.AtomToken;
import org.kuali.student.poc.jsonparser.tokenizer.token.BaseToken;
import org.kuali.student.poc.jsonparser.tokenizer.token.ConstantToken;
import org.kuali.student.poc.jsonparser.tokenizer.token.NumberToken;
import org.kuali.student.poc.jsonparser.tokenizer.token.StringToken;
import org.kuali.student.poc.jsonparser.tokenizer.token.TokenConstants;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Parses a simple JSON file
 *
 * @author Kuali Student Team
 */
public class MyJsonParser {
    MyJsonTokenizer tokenizer;

    public MyJsonParser(String fileName) {
        tokenizer = new MyJsonTokenizer(fileName);
    }

    public List<BaseJsonObject> parse() throws MyJsonParseException {
        List<BaseJsonObject> jsonObjects = new ArrayList<>();
        try {
            List<BaseToken> tokens = tokenizer.tokenize();
            Iterator<BaseToken> iterator = tokens.iterator();
            while (iterator.hasNext()) {
                BaseJsonObject jsonObject = recursiveParse(iterator);
                jsonObjects.add(jsonObject);
            }
        } catch (ParseException e) {
            return null;
        }
        return jsonObjects;
    }

    private BaseJsonObject recursiveParse(Iterator<BaseToken> iterator) throws MyJsonParseException {
        BaseToken token = iterator.next();
        if (token instanceof AtomToken) {
            AtomToken aToken = (AtomToken) token;
            if (aToken.getTokenType().equals(TokenConstants.START_MAP_TOKEN)) {
                MyJsonMap map = new MyJsonMap();
                parseJsonMap(map, iterator);
                return map;
            } else if (aToken.getTokenType().equals(TokenConstants.START_LIST_TOKEN)) {
                MyJsonList list = new MyJsonList();
                parseJsonList(list, iterator);
                return list;
            }
        } else if (token instanceof StringToken) {
            StringToken sToken = (StringToken) token;
            return new MyJsonString(sToken.getString());
        } else if (token instanceof NumberToken) {
            NumberToken nToken = (NumberToken) token;
            return new MyJsonNumber(nToken.getValue());
        } else if (token instanceof ConstantToken) {
            ConstantToken cToken = (ConstantToken) token;
            if (cToken == ConstantToken.TRUE_TOKEN) {
                return MyJsonBoolean.TRUE;
            } else if (cToken == ConstantToken.FALSE_TOKEN) {
                return MyJsonBoolean.FALSE;
            } else {
                // Assume it must be null
                return MyJsonNull.NULL;
            }
        }
        throw new MyJsonParseException("Unknown token found");
    }

    private void parseJsonList(MyJsonList list, Iterator<BaseToken> iterator) throws MyJsonParseException {
        // At this point, the start brace
        BaseToken token = iterator.next();
        while (!isEndListToken(token)) {
            // Next token should be a key, which is a string token
            BaseJsonObject element = recursiveParse(iterator);
            list.add(element);
            // Check for comma
            token = iterator.next();
            if (!isEndListToken(token)) {
                if (!isCommaToken(token)) {
                    throw new MyJsonParseException("Expecting a comma");
                } else {
                    // Get token after the comma
                    token = iterator.next();
                }
            }
        }
    }

    private void parseJsonMap(MyJsonMap map, Iterator<BaseToken> iterator) throws MyJsonParseException {
        // At this point, the start brace
        BaseToken token = iterator.next();
        while (!isEndMapToken(token)) {
            System.err.println("here");
            // Next token should be a key, which is a string token
            StringToken sToken = (StringToken) token; // May class-cast
            System.err.println("---" + sToken);
            // Next token should be a colon
            token = iterator.next();
            if (!isColonToken(token)) {
                throw new MyJsonParseException("Expecting a colon");
            }
            BaseJsonObject value = recursiveParse(iterator);
            map.put(sToken.getString(), value);
            // Check for comma
            token = iterator.next();
            System.err.println(token);
            if (!isEndMapToken(token)) {
                if (!isCommaToken(token)) {
                    throw new MyJsonParseException("Expecting a comma");
                } else {
                    // Get token after the comma
                    token = iterator.next();
                }
            }
        }
    }

    private boolean isEndMapToken(BaseToken token) {
        if (token instanceof AtomToken) {
            AtomToken aToken = (AtomToken) token;
            return aToken.getTokenType().equals(TokenConstants.END_MAP_TOKEN);
        }
        return false;
    }

    private boolean isEndListToken(BaseToken token) {
        if (token instanceof AtomToken) {
            AtomToken aToken = (AtomToken) token;
            return aToken.getTokenType().equals(TokenConstants.END_LIST_TOKEN);
        }
        return false;
    }

    private boolean isColonToken(BaseToken token) {
        if (token instanceof AtomToken) {
            AtomToken aToken = (AtomToken) token;
            return aToken.getTokenType().equals(TokenConstants.COLON_TOKEN);
        }
        return false;
    }

    private boolean isCommaToken(BaseToken token) {
        if (token instanceof AtomToken) {
            AtomToken aToken = (AtomToken) token;
            return aToken.getTokenType().equals(TokenConstants.COMMA_TOKEN);
        }
        return false;
    }
}
