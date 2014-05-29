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

import org.kuali.student.ap.coursesearch.CourseSearchItem;
import org.kuali.student.ap.coursesearch.Credit;

/**
 * Stores credit information to use in the course search
 */
public class CreditImpl implements Credit {
    private String id;
    private String display;
    private float min;
    private float max;
    private float[] multiple;
    private CourseSearchItem.CreditType type;

    public String getId() {
        return id;
    }

    public String getDisplay() {
        return display;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public float[] getMultiple() {
        return multiple;
    }

    public CourseSearchItem.CreditType getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public void setMultiple(float[] multiple) {
        this.multiple = multiple;
    }
    public void setMultiple(int i, float multiple) {
        this.multiple[i] = multiple;
    }

    public void setType(CourseSearchItem.CreditType type) {
        this.type = type;
    }
}
