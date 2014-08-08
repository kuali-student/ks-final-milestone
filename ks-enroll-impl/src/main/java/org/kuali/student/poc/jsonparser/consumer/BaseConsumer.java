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
import org.kuali.student.poc.jsonparser.tokenizer.token.BaseToken;

import java.text.ParseException;

/**
 * The base interface for a tokenizer
 *
 * @author Kuali Student Team
 */
public interface BaseConsumer {
    // If the consumer can create a token, it will. If not, it returns null.
    public BaseToken consume(BaseProducer producer) throws ParseException;

}
