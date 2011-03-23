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
package org.kuali.student.datadictionary.util;

import org.kuali.rice.kns.datadictionary.AttributeDefinition;
import org.kuali.rice.kns.datadictionary.DataObjectEntry;


public class DictionaryConstants {

    public static final String OBJECT_STRUCTURE_CLASS =
            DataObjectEntry.class.getSimpleName();
    public static final String ATTRIBUTE_DEFINITION_CLASS =
            AttributeDefinition.class.getSimpleName();
    public static final int UNBOUNDED = 999999999;
    public static final int SINGLE = 1;
    public static final String ATTRIBUTES = "attributes";
    public static final String NAME = "name";
    public static final String DATA_DICTIONARY_OBJECT_STRUCTURE = "dataDictionaryObjectStructure";

    public static final String BASE_REPEATING_INTEGER_FIELD =
            "baseRepeatingInteger";
    public static final String BASE_REPEATING_LONG_FIELD = "baseRepeatingLong";
    public static final String BASE_REPEATING_DOUBLE_FIELD = "baseRepeatingDouble";
    public static final String BASE_REPEATING_FLOAT_FIELD = "baseRepeatingFloat";
    public static final String BASE_REPEATING_BOOLEAN_FIELD =
            "baseRepeatingBoolean";
    public static final String BASE_REPEATING_DATE_FIELD = "baseRepeatingDate";
    public static final String BASE_REPEATING_STRING_FIELD = "baseRepeatingString";
    public static final String BASE_REPEATING_COMPLEX_FIELD =
            "baseRepeatingComplex";
    public static final String BASE_SINGLE_INTEGER_FIELD = "baseSingleInteger";
    public static final String BASE_SINGLE_LONG_FIELD = "baseRepeatingLong";
    public static final String BASE_SINGLE_DOUBLE_FIELD = "baseSingleDouble";
    public static final String BASE_SINGLE_FLOAT_FIELD = "baseSingleFloat";
    public static final String BASE_SINGLE_BOOLEAN_FIELD = "baseSingleBoolean";
    public static final String BASE_SINGLE_DATE_FIELD = "baseSingleDate";
    public static final String BASE_SINGLE_STRING_FIELD = "baseSingleString";
    public static final String BASE_SINGLE_COMPLEX_FIELD = "baseSingleComplex";
    public static final String BASE_ID_FIELD = "baseId";
}

