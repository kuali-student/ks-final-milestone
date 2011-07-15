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
package org.kuali.student.r2.common.datadictionary;

import java.util.ArrayList;
import java.util.List;
import org.kuali.rice.kns.datadictionary.AttributeDefinition;
import org.kuali.rice.kns.datadictionary.DataObjectEntry;
import org.kuali.student.r2.common.datadictionary.dto.AttributeDefinitionInfo;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;

/**
 *
 * @author nwright
 */
public class Rice2StudentDictionaryEntryConverter {

    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Rice2StudentDictionaryEntryConverter.class);

    public DictionaryEntryInfo convert(DataObjectEntry rice) {
        DictionaryEntryInfo.Builder bldr = new DictionaryEntryInfo.Builder();
        bldr.setObjectClass(rice.getFullClassName());
        bldr.setName(rice.getName());
        bldr.setObjectLabel(rice.getObjectLabel());
        bldr.setObjectDescription(rice.getObjectDescription());
        bldr.setTitleAttribute(rice.getTitleAttribute());
        bldr.setPrimaryKeys(rice.getPrimaryKeys());
        if (rice.getAttributes() != null) {
            List<AttributeDefinitionInfo> list = new ArrayList(rice.getAttributes().size());
            for (AttributeDefinition ad : rice.getAttributes()) {
                list.add(new Rice2StudentAttributeDefinitionConverter ().convert(ad));
            }
            bldr.setAttributes (list);
        }
        return bldr.build();
    }
}
