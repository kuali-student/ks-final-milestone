/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.enrollment.class1.hold.form;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.core.person.dto.PersonInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a form for HoldIssue objects
 *
 * @author Kuali Student Team
 */
public class AppliedHoldManagementForm extends UifFormBase {

    private static final long serialVersionUID = 4898118410378641665L;

    /**
     * Contains the personal information for the selected student.
     */
    private PersonInfo person;

    private boolean validPerson;

    private List<AppliedHoldResult> holdResultList = new ArrayList<AppliedHoldResult>();

    private boolean hasSearchBeenCalled;

    public AppliedHoldManagementForm(){
        super();
        this.person = new PersonInfo();
        setHasSearchBeenCalled(false);
    }

    public void clearResults() {
        this.holdResultList = new ArrayList<AppliedHoldResult>();
    }

    public PersonInfo getPerson() {
        return person;
    }

    public void setPerson(PersonInfo person) {
        this.person = person;
    }

    public List<AppliedHoldResult> getHoldResultList() {
        return holdResultList;
    }

    public void setHoldResultList(List<AppliedHoldResult> holdResultList) {
        this.holdResultList = holdResultList;
    }

    public boolean isHasSearchBeenCalled() {
        return hasSearchBeenCalled;
    }

    public void setHasSearchBeenCalled(boolean hasSearchBeenCalled) {
        this.hasSearchBeenCalled = hasSearchBeenCalled;
    }

    public String getPersonHeaderInfo() {

        if (person.getId() != null && isValidPerson()) {
            return ": " + person.getName() + " (" + person.getId() + ")";
        }

        return StringUtils.EMPTY;
    }

    public boolean isValidPerson() {
        return validPerson;
    }

    public void setValidPerson(boolean validPerson) {
        this.validPerson = validPerson;
    }
}
