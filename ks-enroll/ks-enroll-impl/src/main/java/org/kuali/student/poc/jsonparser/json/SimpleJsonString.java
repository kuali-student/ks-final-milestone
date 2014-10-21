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

/**
 * This class represents a JSON string (used as a key in a JSON map, for example)
 *
 * @author Kuali Student Team
 */
public class SimpleJsonString extends SimpleJsonAtom {
    String value;

    public SimpleJsonString(String value) {
        super("jsonString");
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public String getStringValue() {
        return value;
    }
}
