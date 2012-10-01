/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
package org.kuali.student.r2.common.util.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.datadictionary.dto.AttributeDefinitionInfo;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.datadictionary.dto.ValidCharactersConstraintInfo;

/**
 * This class holds the constants used by the Academic Calendar service
 *
 * @author tom
 */
public class DataDictionaryServiceConstants {

    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "datadictionary";
    public static final String REF_OBJECT_URI_DICTIONARY_ENTRY = NAMESPACE + "/" + DictionaryEntryInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ATTRIBUTE_DEFINITION = NAMESPACE + "/" + AttributeDefinitionInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_VALID_CHARACTERS_CONSTRAINT = NAMESPACE + "/" + ValidCharactersConstraintInfo.class.getSimpleName();
   
}
