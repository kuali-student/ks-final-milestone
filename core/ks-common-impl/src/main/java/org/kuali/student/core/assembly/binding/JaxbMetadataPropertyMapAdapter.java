/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.core.assembly.binding;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.kuali.student.core.assembly.data.Metadata;

public class JaxbMetadataPropertyMapAdapter extends XmlAdapter<JaxbMetadataPropertyList, Map<String, Metadata>> {
    
    public Map<String, Metadata> unmarshal(JaxbMetadataPropertyList value) {
        if (value == null){
            return null;
        }
    
        Map<String, Metadata> resultMap = new HashMap<String, Metadata>();
        for (JaxbMetadataProperty a : value.getMetaData()) {
            resultMap.put(a.name, a.metadata);
        }
        return resultMap;
    }

    public JaxbMetadataPropertyList marshal(Map<String, Metadata> value) {
        if (value == null)
            return null;
        JaxbMetadataPropertyList attributes = new JaxbMetadataPropertyList();
        for (Map.Entry<String, Metadata> e : value.entrySet()) {
            attributes.getMetaData().add(new JaxbMetadataProperty(e.getKey(), e.getValue()));
        }
        return attributes;
    }

}
