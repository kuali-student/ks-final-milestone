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
package org.kuali.student.poc.jsonparser.consumer;

import org.kuali.student.poc.jsonparser.producer.BaseProducer;
import org.kuali.student.poc.jsonparser.tokenizer.token.AtomToken;
import org.kuali.student.poc.jsonparser.tokenizer.token.BaseToken;

/**
 * This is a consumer that only consumes a single character
 *
 * @author Kuali Student Team
 */
public class AtomConsumer implements BaseConsumer {
    public static final String ATOMS = "[],{}:";

    private char savedChar = ' ';

    @Override
    public BaseToken consume(BaseProducer producer) {
        char ch = producer.peek();
        if (!ATOMS.contains(ch + "")) {
            return null;
        }
        producer.consume(); // Valid char so consume
        if (AtomToken.isStartListToken(ch)) {
            return AtomToken.createStartListToken();
        } else if (AtomToken.isEndListToken(ch)) {
            return AtomToken.createEndListToken();
        } else if (AtomToken.isStartMapToken(ch)) {
            return AtomToken.createStartMapToken();
        } else if (AtomToken.isEndMapToken(ch)) {
            return AtomToken.createEndMapToken();
        } else if (AtomToken.isCommaToken(ch)) {
            return AtomToken.createCommaToken();
        } else if (AtomToken.isColonToken(ch)) {
            return AtomToken.createColonToken();
        }
        return null;
    }
}
