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
package org.kuali.student.poc.jsonparser.tokenizer.token;

/**
 * This class represents constants in JSON: true, false, null
 *
 * @author Kuali Student Team
 */
public class ConstantToken implements BaseToken {
    public static final String TRUE_CONSTANT = "true";
    public static final String FALSE_CONSTANT = "false";
    public static final String NULL_CONSTANT = "null";

    public static final ConstantToken TRUE_TOKEN = new ConstantToken(TRUE_CONSTANT);
    public static final ConstantToken FALSE_TOKEN = new ConstantToken(FALSE_CONSTANT);
    public static final ConstantToken NULL_TOKEN = new ConstantToken(NULL_CONSTANT);

    private String constantType;

    private ConstantToken(String str) {
        constantType = str;
    }

    public static ConstantToken createTrueToken() {
        return TRUE_TOKEN;
    }

    public static ConstantToken createFalseToken() {
        return FALSE_TOKEN;
    }

    public static ConstantToken createNullToken() {
        return NULL_TOKEN;
    }

    public String getValue() {
        return constantType;
    }

    @Override
    public String toString() {
        return "token/constant/" + constantType;
    }
}
