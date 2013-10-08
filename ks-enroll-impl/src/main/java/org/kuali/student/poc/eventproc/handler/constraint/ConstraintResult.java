/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 9/16/13
 */
package org.kuali.student.poc.eventproc.handler.constraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Indicates whether constraint was satisfied or not.
 *
 * @author Kuali Student Team
 */
public class ConstraintResult {
    private boolean satisfiesConstraint = true;
    boolean noStateChange = false;
    private List<String> errors = new ArrayList<String>();

    public void setSatisfiesConstraint(boolean isSatisfied) {
        satisfiesConstraint = isSatisfied;
    }

    public boolean satisfiesConstraint() {
        return satisfiesConstraint;
    }

    public void addError(String errorMessage) {
        satisfiesConstraint = false;
        errors.add(errorMessage);
    }

    public void setNoStateChange(boolean noStateChange) {
        this.noStateChange = noStateChange;
    }

    public boolean isNoStateChange() {
        return noStateChange;
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
