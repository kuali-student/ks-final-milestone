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
package org.kuali.student.poc.jsonparser.tokenizer.token;

/**
 * Stores a double quote JSON string (no escape sequences handled yet)
 *
 * @author Kuali Student Team
 */
public class StringToken implements BaseToken {
    String token;
    public StringToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "token/string/" + token;
    }

    public String getString() {
        return token;
    }
}
