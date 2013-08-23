
/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.common.util;

import java.util.List;
import java.util.Map;
import org.kuali.student.r2.common.dto.AttributeInfo;

/**
 * Utility to convert text between plain and formatted
 *
 * @author nwright
 */
public class AttributeHelper {

    private List<AttributeInfo> attrs;

    public AttributeHelper(List<AttributeInfo> attrs) {
        this.attrs = attrs;
    }

    public void putAll(Map<String, String> map) {
        for (String key : map.keySet()) {
            this.put(key, map.get(key));
        }
    }

    public String get(String key) {
        for (AttributeInfo attr : attrs) {
            if (attr.getKey().equals(key)) {
                return attr.getValue();
            }
        }
        return null;
    }

    public void put(String key, String value) {
        for (AttributeInfo attr : attrs) {
            if (attr.getKey().equals(key)) {
                attr.setValue(value);
                return;
            }
        }
        AttributeInfo info = new AttributeInfo(key, value);
        attrs.add(info);
    }
}
