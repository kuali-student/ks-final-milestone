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
import org.kuali.student.poc.jsonparser.json.SimpleJsonBoolean;
import org.kuali.student.poc.jsonparser.json.SimpleJsonList;
import org.kuali.student.poc.jsonparser.json.SimpleJsonMap;
import org.kuali.student.poc.jsonparser.json.SimpleJsonNull;
import org.kuali.student.poc.jsonparser.json.SimpleJsonNumber;
import org.kuali.student.poc.jsonparser.json.SimpleJsonString;
import org.kuali.student.poc.jsonparser.tokenizer.SimpleJsonTokenizer;
import org.kuali.student.poc.jsonparser.tokenizer.token.AtomToken;
import org.kuali.student.poc.jsonparser.tokenizer.token.BaseToken;
import org.kuali.student.poc.jsonparser.tokenizer.token.ConstantToken;
import org.kuali.student.poc.jsonparser.tokenizer.token.NumberToken;
import org.kuali.student.poc.jsonparser.tokenizer.token.StringToken;
import org.kuali.student.poc.jsonparser.tokenizer.token.TokenConstants;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Parses a simple JSON file
 *
 * @author Kuali Student Team
 */
public class SimpleJsonParser implements Iterable<BaseJsonObject> {
    SimpleJsonTokenizer tokenizer;
    String resourcePath;
    public SimpleJsonParser(String resourcePath) {
        tokenizer = new SimpleJsonTokenizer(resourcePath);
        this.resourcePath = resourcePath;
    }

//    public List<BaseJsonObject> parse() throws MyJsonParseException {
//        List<BaseJsonObject> jsonObjects = new ArrayList<>();
//        try {
//            Iterator<BaseToken> iterator = tokenizer.iterator();
//            while (iterator.hasNext()) {
//                BaseJsonObject jsonObject = recursiveParse(iterator);
//                jsonObjects.add(jsonObject);
//            }
//        } catch (RuntimeException e) {
//            return null;
//        }
//        return jsonObjects;
//    }

    private BaseJsonObject recursiveParse(Iterator<BaseToken> iterator) throws SimpleJsonParseException {
        BaseToken token = iterator.next();
        if (token instanceof AtomToken) {
            AtomToken aToken = (AtomToken) token;
            if (aToken.getTokenType().equals(TokenConstants.START_MAP_TOKEN)) {
                SimpleJsonMap map = new SimpleJsonMap();
                parseJsonMap(map, iterator);
                return map;
            } else if (aToken.getTokenType().equals(TokenConstants.START_LIST_TOKEN)) {
                SimpleJsonList list = new SimpleJsonList();
                parseJsonList(list, iterator);
                return list;
            }
        } else if (token instanceof StringToken) {
            StringToken sToken = (StringToken) token;
            return new SimpleJsonString(sToken.getString());
        } else if (token instanceof NumberToken) {
            NumberToken nToken = (NumberToken) token;
            return new SimpleJsonNumber(nToken.getValue());
        } else if (token instanceof ConstantToken) {
            ConstantToken cToken = (ConstantToken) token;
            if (cToken == ConstantToken.TRUE_TOKEN) {
                return SimpleJsonBoolean.TRUE;
            } else if (cToken == ConstantToken.FALSE_TOKEN) {
                return SimpleJsonBoolean.FALSE;
            } else {
                // Assume it must be null
                return SimpleJsonNull.NULL;
            }
        }
        throw new SimpleJsonParseException("Unknown token found");
    }

    private void parseJsonList(SimpleJsonList list, Iterator<BaseToken> iterator) throws SimpleJsonParseException {
        BaseToken token = null;
        while (!isEndListToken(token)) {
            // Next token should be next element of list
            BaseJsonObject element = recursiveParse(iterator);
            list.add(element);
            // Check for comma
            token = iterator.next();
            if (!isEndListToken(token)) {
                if (!isCommaToken(token)) {
                    throw new SimpleJsonParseException("Expecting a comma");
                }
            }
        }
    }

    private void parseJsonMap(SimpleJsonMap map, Iterator<BaseToken> iterator) throws SimpleJsonParseException {
        // At this point, the start brace
        BaseToken token = iterator.next();
        while (!isEndMapToken(token)) {
            // Next token should be a key, which is a string token
            StringToken sToken = (StringToken) token; // May class-cast
            // Next token should be a colon
            token = iterator.next();
            if (!isColonToken(token)) {
                throw new SimpleJsonParseException("Expecting a colon");
            }
            BaseJsonObject value = recursiveParse(iterator);
            map.put(sToken.getString(), value);
            // Check for comma
            token = iterator.next();
            if (!isEndMapToken(token)) {
                if (!isCommaToken(token)) {
                    throw new SimpleJsonParseException("Expecting a comma");
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
        if (token == null) {
            return false;
        }
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

    @Override
    public Iterator<BaseJsonObject> iterator() {
        Iterator<BaseJsonObject> iter =
                new Iterator<BaseJsonObject>() {
                    SimpleJsonTokenizer tokenizer;
                    Iterator<BaseToken> iterator;
                    BaseJsonObject nextJsonObject;
                    {
                        tokenizer = new SimpleJsonTokenizer(resourcePath);
                        iterator = tokenizer.iterator();
                        nextJsonObject = getNextJsonObject();
                    }

                    private BaseJsonObject getNextJsonObject() {
                        BaseJsonObject result = null;
                        try {
                            if (!iterator.hasNext()) {
                                return null;
                            }
                            result = recursiveParse(iterator);
                        } catch (SimpleJsonParseException e) {
                            throw new RuntimeException("MyJsonParseException", e);
                        }
                        return result;
                    }

                    @Override
                    public boolean hasNext() {
                        return nextJsonObject != null;
                    }

                    @Override
                    public BaseJsonObject next() {
                        BaseJsonObject saved = nextJsonObject;
                        if (saved == null) {
                            throw new NoSuchElementException();
                        } else {
                            nextJsonObject = getNextJsonObject();
                        }

                        return saved;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("remove not supported");
                    }
                };
        return iter;
    }
}
