/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.common.ws.binding;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * This is jaxb adapter for org.kuali.rice.kim.bo.types.dto.AttributeSet. 
 * This class should not be required if we add getter/setter in the rice DTO.
 *
 */
public class AttributeSetAdapter extends
        XmlAdapter<JaxbAttributeList, Map<String, String>> {
    public Map<String,String> unmarshal(JaxbAttributeList value) {
        if(value == null) return null;
        Map<String,String> result = new LinkedHashMap<String, String>();
        for (JaxbAttribute a : value.getAttribute()) {
            result.put(a.key, a.value);
        }
        return result;
    }

    public JaxbAttributeList marshal(Map<String,String> value) {
        if(value == null) return null;
        JaxbAttributeList attributes = new JaxbAttributeList();
        for (Map.Entry<String, String> e : value.entrySet()) {
            attributes.getAttribute().add(
                    new JaxbAttribute(e.getKey(), e.getValue()));
        }
        return attributes;
    }

}
