/*
 * Copyright 2014 The Kuali Foundation Licensed under the
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

package org.kuali.student.ap.coursesearch;

import org.kuali.rice.core.api.util.KeyValue;

/**
 * Simple object for tracking facet click/count state.
 */
public interface FacetState {

    /**
     * Get the value
     * @return The KeyValue value
     */
    KeyValue getValue();

    /**
     * Get the value of the checked flag
     * @return True if checked, false if not checked
     */
    boolean isChecked();

    /**
     * Get the count of how many results this facet includes
     * @return The count
     */
    int getCount();

    /**
     * Increment the facet count by 1
     */
    void incrementCount();

    /**
     * Get the description
     * @return The description
     */
    String getDescription();

    /**
     * Set the checked flag
     * @param checked
     */
    void setChecked(boolean checked);

    /**
     * Reset the facet count back to 0
     */
    void resetCount();
}
