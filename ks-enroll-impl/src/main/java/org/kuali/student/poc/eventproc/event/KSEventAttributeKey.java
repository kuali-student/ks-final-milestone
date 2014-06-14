/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by Charles on 9/11/13
 */
package org.kuali.student.poc.eventproc.event;

/**
 * Events have key-value attributes.  These represent the key.
 *
 * @author Kuali Student Team
 */
public class KSEventAttributeKey {
    private String attribute;
    private String shortName; // shorter name for attribute (unofficial, for printing purposes)
    private String valueType;

    public KSEventAttributeKey(String attribute, String shortName, String valueType) {
        this.attribute = attribute;
        this.shortName = shortName;
        this.valueType = valueType;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getValueType() {
        return valueType;
    }

    public String getShortName() {
        return shortName;
    }

    public boolean isEqualTo(KSEventAttributeKey o) {
        return attribute.equals(o.getAttribute());
    }

    @Override
    public int hashCode() {
        return attribute.hashCode();
    }
}
