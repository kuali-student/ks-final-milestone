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
 * Created by Charles on 8/4/2014
 */
package org.kuali.student.poc.jsonparser.consumer;

import org.kuali.student.poc.jsonparser.producer.BaseProducer;
import org.kuali.student.poc.jsonparser.tokenizer.token.BaseToken;
import org.kuali.student.poc.jsonparser.tokenizer.token.NumberToken;

import java.text.ParseException;

/**
 * Tokenizes a JSON number
 *
 * @author Kuali Student Team
 */
public class NumberConsumer implements BaseConsumer {
    @Override
    public BaseToken consume(BaseProducer producer) throws ParseException {
        if (!producer.hasNext()) {
            return null;
        }
        char ch = producer.peek();
        StringBuilder buf = new StringBuilder();
        int dotCount = 0;
        int i = 0;
        while (ch == '-' || Character.isDigit(ch) || ch == '.') {
            if (ch == '-' && i > 0) {
                String err = "Error at row = " + producer.getRow() + " col = " + producer.getColumn();
                throw new ParseException(err, producer.getColumn());
            }
            if (ch == '.') {
                dotCount++;
                if (dotCount > 1) {
                    String err = "Error at row = " + producer.getRow() + " col = " + producer.getColumn();
                    throw new ParseException(err, producer.getColumn());
                }
            }
            buf.append(ch);
            producer.consume();
            ch = producer.peek();
            i++;
        }
        String numStr = buf.toString();
        NumberToken numberToken = new NumberToken(numStr);
        return numberToken;
    }
}
