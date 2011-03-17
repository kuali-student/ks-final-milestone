 /*
 * Copyright 2011 The Kuali Foundation
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
package org.kuali.student.enrollment.lpr.mock;

import org.kuali.student.common.infc.CriteriaInfc;
import org.kuali.student.core.exceptions.InvalidParameterException;

/**
 * A helper class for the Mock implementation to match criteria to values on the object
 * @author nwright
 */
public class CriteriaMatcher {

    private CriteriaInfc criteria;
    private Class infcClass;

    public CriteriaMatcher(CriteriaInfc criteria, Class infcClass)
            throws InvalidParameterException {
        this.criteria = criteria;
        this.infcClass = infcClass;
        this.validate();
    }

    private void validate()
            throws InvalidParameterException {
        if (criteria.getOperator().equals("=")) {
            return;
        } else {
            throw new InvalidParameterException("Unsupported operator " + criteria.getOperator());
        }

    }

    public boolean matches(Object infoObject)
            throws InvalidParameterException {
        return true;
    }

    private boolean equals(int obj1, int obj2) {
        if (obj1 == obj2) {
            return true;
        }
        return false;
    }

    private boolean equals(long obj1, long obj2) {
        if (obj1 == obj2) {
            return true;
        }
        return false;
    }

    private boolean equals(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        }
        if (obj1 == null) {
            return false;
        }
        if (obj1.equals(obj2)) {
            return true;
        }
        return false;
    }
}

