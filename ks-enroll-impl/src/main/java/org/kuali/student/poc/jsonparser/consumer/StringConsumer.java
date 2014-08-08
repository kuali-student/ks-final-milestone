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
import org.kuali.student.poc.jsonparser.tokenizer.token.StringToken;

/**
 * Can parse a double-quote string (can't handle escape sequences)
 *
 * @author Kuali Student Team
 */
public class StringConsumer implements BaseConsumer {
    @Override
    public BaseToken consume(BaseProducer producer) {
        if (!producer.hasNext()) {
            return null;
        }
        char start = producer.peek();
        if (start != '"') { // JSON strings are always double-quoted
            return null;
        }
        producer.consume();
        StringBuilder buffer = new StringBuilder();
        while (producer.peek() != start) {
            buffer.append(producer.consume());
        }
        producer.consume(); // Consume the close quote
        return new StringToken(buffer.toString());
    }
}
