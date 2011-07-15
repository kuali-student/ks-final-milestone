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
import org.kuali.student.r2.common.datadictionary.infc.AttributeDefinitionInfc;
import org.kuali.student.r2.common.datadictionary.infc.DictionaryEntry;

/**
 *
 * @author nwright
 */
public class Student2RiceDictionaryEntryConverter {

    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Student2RiceDictionaryEntryConverter.class);

    public DataObjectEntry convert(DictionaryEntry student) {
        DataObjectEntry rice = new DataObjectEntry ();
        try {
            rice.setObjectClass(Class.forName(student.getObjectClass()));
        } catch (ClassNotFoundException ex) {
           throw new IllegalArgumentException (student.getObjectClass(), ex);
        }
        rice.setName(student.getName());
        rice.setObjectLabel(student.getObjectLabel());
        rice.setObjectDescription(student.getObjectDescription());
        rice.setTitleAttribute(student.getTitleAttribute());
        rice.setPrimaryKeys(student.getPrimaryKeys());
        if (student.getAttributes() != null) {
            List<AttributeDefinition> list = new ArrayList(student.getAttributes().size());
            for (AttributeDefinitionInfc ad : student.getAttributes()) {
                list.add(new Student2RiceAttributeDefinitionConverter ().convert(ad));
            }
            rice.setAttributes (list);
        }
        return rice;
    }
}
