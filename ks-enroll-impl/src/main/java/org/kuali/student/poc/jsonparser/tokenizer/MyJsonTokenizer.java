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
import java.util.List;

/**
 * Tokenizes a simple JSON file
 *
 * @author Kuali Student Team
 */
public class MyJsonTokenizer {
    FileProducer producer;
    List<BaseToken> tokens;
    List<BaseConsumer> consumers;

    public MyJsonTokenizer(String fileName) {
        producer = new FileProducer(fileName);
        tokens = new ArrayList<>();
        // Create list of consumers
        consumers = new ArrayList<>();
        consumers.add(new AtomConsumer());
        consumers.add(new WhitespaceConsumer());
        consumers.add(new StringConsumer());
        consumers.add(new NumberConsumer());
        consumers.add(new ConstantConsumer());
    }

    public List<BaseToken> tokenize() throws ParseException {
        while (producer.hasNext()) {
            for (BaseConsumer consumer: consumers) {
                BaseToken token = consumer.consume(producer);
                if (token != null) {
                    if (!(token instanceof WhitespaceToken)) {
                        // Don't add whitespace tokens
                        tokens.add(token);
                    }
                    break;
                }
            }
        }
        return tokens;
    }
}
