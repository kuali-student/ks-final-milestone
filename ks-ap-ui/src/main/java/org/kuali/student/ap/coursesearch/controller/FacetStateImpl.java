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

package org.kuali.student.ap.coursesearch.controller;

import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.ap.coursesearch.FacetState;

import java.io.Serializable;

/**
 * Simple object for tracking facet click/count state.
 */
public class FacetStateImpl implements Serializable, FacetState {
    private static final long serialVersionUID = 1719950239861974273L;

    private final KeyValue value;
    private boolean checked = true;
    private int count;
    private String description;

    public FacetStateImpl(KeyValue value) {
        this.value = value;
    }

    @Override
    public KeyValue getValue() {
        return value;
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Increment the count
     */
    @Override
    public void incrementCount() {
        count++;
    }

    @Override
    public void resetCount() {
        count = 0;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
