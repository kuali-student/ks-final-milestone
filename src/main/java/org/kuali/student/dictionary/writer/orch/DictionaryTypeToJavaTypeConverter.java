/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.dictionary.writer.orch;

import org.kuali.student.dictionary.model.OrchestrationObjectField;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import java.util.Date;
import org.kuali.student.dictionary.writer.JavaClassWriter;

/**
 *
 * @author nwright
 */
public class DictionaryTypeToJavaTypeConverter
{
 public static String convert (OrchestrationObjectField field, JavaClassWriter writer)
 {
   if (field.getType ().equalsIgnoreCase ("string"))
    {
     return "String";
    }

    if (field.getType ().equalsIgnoreCase ("date"))
    {
     // TODO: figure out what to use for Date
     writer.importsAdd (Date.class.getName ());
     return "Date";
    }

    if (field.getType ().equalsIgnoreCase ("dateTime"))
    {
     writer.importsAdd (Date.class.getName ());
     return "Date";
    }

    if (field.getType ().equalsIgnoreCase ("boolean"))
    {
     return "Boolean";
    }

    if (field.getType ().equalsIgnoreCase ("integer"))
    {
     return "Integer";
    }

    if (field.getType ().equalsIgnoreCase ("long"))
    {
     return "Long";
    }

    throw new DictionaryValidationException (
     "Unknown/handled field type " +
     field.getType () + " " + field.getName ());

 }
}
