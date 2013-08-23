/*
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

package org.kuali.student.r2.core.enumerationmanagement.infc;

import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.HasMeta;

import java.util.List;

/**
 * Descriptive information about an enumeration, including field constraints and
 * supported contexts.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface EnumeratedValue extends HasEffectiveDates, HasMeta {
    /**
     * Typically coincides with a code representation. Likely the key if this is
     * a reference to another object.
     *
     * @name Code
     */
    String getCode();

    /**
     * Typically coincides with a shortened name. May be equal to the code or
     * value fields.
     *
     * @name Abbrev Value
     */
    String getAbbrevValue();

    /**
     * Typically coincides with a name for display.
     *
     * @name Value
     */
    String getValue();

    /**
     * Default position for the enumerated value. This might or might not exist,
     * particularly in cases where the enumeration consists solely of a view.
     *
     * @name Sort Key
     */
    String getSortKey();

    /**
     * Indicates which context types and values this particular enumerated value
     * participates in.
     *
     * @name Contexts
     */
    List<? extends EnumContextValue> getContexts();

    /**
     * Identifier for the enumeration
     *
     * @name Enumeration Key
     */
    String getEnumerationKey();
}
