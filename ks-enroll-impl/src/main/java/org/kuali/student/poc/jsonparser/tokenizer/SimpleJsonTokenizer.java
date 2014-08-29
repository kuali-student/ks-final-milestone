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
 * Created by Charles on 7/30/2014
 */
package org.kuali.student.poc.jsonparser.tokenizer;

import org.kuali.student.poc.jsonparser.consumer.AtomConsumer;
import org.kuali.student.poc.jsonparser.consumer.BaseConsumer;
import org.kuali.student.poc.jsonparser.consumer.ConstantConsumer;
import org.kuali.student.poc.jsonparser.consumer.NumberConsumer;
import org.kuali.student.poc.jsonparser.consumer.StringConsumer;
import org.kuali.student.poc.jsonparser.consumer.WhitespaceConsumer;
import org.kuali.student.poc.jsonparser.producer.FileProducer;
import org.kuali.student.poc.jsonparser.tokenizer.token.BaseToken;
import org.kuali.student.poc.jsonparser.tokenizer.token.WhitespaceToken;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Tokenizes a simple JSON file
 *
 * @author Kuali Student Team
 */
public class SimpleJsonTokenizer implements Iterable<BaseToken> {
    FileProducer producer;
    String resourcePath;
    List<BaseConsumer> consumers;

    public SimpleJsonTokenizer(String resourcePath) {
        producer = new FileProducer(resourcePath);
        this.resourcePath = resourcePath;
        // Create list of consumers
        consumers = new ArrayList<>();
        consumers.add(new AtomConsumer());
        consumers.add(new WhitespaceConsumer());
        consumers.add(new StringConsumer());
        consumers.add(new NumberConsumer());
        consumers.add(new ConstantConsumer());
    }

    public List<BaseToken> tokenize() throws ParseException {
        // Use internal iterator to do the work
        List<BaseToken> tokens = new ArrayList<>();
        for (BaseToken token: this) {
            tokens.add(token);
        }
        return tokens;
    }

    @Override
    public Iterator<BaseToken> iterator() {
        Iterator<BaseToken> it = new Iterator<BaseToken>() {
            private FileProducer producer;
            private BaseToken nextToken;
            {
                // Using this instead of a constructor
                producer = new FileProducer(resourcePath);
                nextToken = getNextToken();
            }

            @Override
            public boolean hasNext() {
                return nextToken != null;
            }

            @Override
            public BaseToken next() {
                BaseToken saved = nextToken;
                if (saved == null) {
                    throw new NoSuchElementException();
                } else {
                    nextToken = getNextToken();
                }

                return saved;
            }

            private BaseToken getNextToken() {
                // Allows for searching for next tokens
                BaseToken localNextToken = null; // Assume there is no next token
                if (!producer.hasNext()) {
                    return null;
                }
                // Search for next token (skipping over white space)
                do {
                    localNextToken = iterateThroughConsumersToGetToken();

                    if (localNextToken == null || !(localNextToken instanceof WhitespaceToken)) {
                        break;
                    }
                } while (true);
                return localNextToken;
            }

            private BaseToken iterateThroughConsumersToGetToken() {
                for (BaseConsumer consumer: consumers) {
                    BaseToken token = null;
                    try {
                        token = consumer.consume(producer);
                    } catch (ParseException e) {
                        throw new RuntimeException("Parse error", e);
                    }
                    if (token != null) {
                        return token;
                    }
                }
                return null; // Unable to find token
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove() not supported");
            }
        };
        return it;
    }
}
