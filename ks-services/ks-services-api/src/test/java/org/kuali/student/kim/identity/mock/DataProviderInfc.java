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
package org.kuali.student.kim.identity.mock;

import java.util.List;
import org.kuali.rice.kns.datadictionary.validation.DataType;

/**
 * Provides values to compare
 *
 * @author nwright
 */
public interface DataProviderInfc<I, O> {

    /**
     * Gets the list of values to be compared
     *
     * @param obj data object
     * @param fieldKey path to field on data object
     * @return list of values for the specified field, if field occurs only once on object the list is of size one
     * @throws IllegalArgumentException if field key is invalid or not supported
     */
    public List<Object> getValues(I obj, String fieldKey)
            throws IllegalArgumentException;

    /**
     * Get the data type of the field
     *
     * @param fieldKey
     * @return DataType of the field key, null if fieldKey is not valid or not supported
     * @throws IllegalArgumentException if field key is invalid or not supported
     */
    public DataType getDataType(String fieldKey)
            throws IllegalArgumentException;

    /**
     * Checks if the field key is supported
     * @param fieldKey
     * @return true if it is supported, else false
     */
    public boolean supportsField(String fieldKey);

    /**
     * Parses the supplied value to which the data field is to be compared
     * @param fieldKey
     * @param value
     * @return the value parsed to match the data type so it can be compaerd
     * @throws IllegalArgumentException if field key is invalid or not supported*
     */
    public Object parseValue(String fieldKey, String value)
            throws IllegalArgumentException;
}
