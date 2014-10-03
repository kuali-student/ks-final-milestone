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
 * Created by Charles on 8/6/2014
 */
package org.kuali.student.poc.jsonparser.json;

import java.math.BigDecimal;

/**
 * Represent a simple JSON number (doesn't handle exponential notation)
 *
 * @author Kuali Student Team
 */
public class SimpleJsonNumber extends SimpleJsonAtom {
    private String numStr;

    public SimpleJsonNumber(String numStr) {
        super("jsonNumber");
        this.numStr = numStr;
    }

    public Integer getIntegerValue() {
        return Integer.parseInt(numStr);
    }

    public BigDecimal getBigDecimalValue() {
        return BigDecimal.valueOf(Double.parseDouble(numStr));
    }
}
