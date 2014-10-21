/**
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
 *
 * Created by prasannag on 7/1/14
 */

package org.kuali.student.cm.course.form;

import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.student.cm.course.form.wrapper.LoDisplayInfoWrapper;

import java.util.List;

/**
 * Form class to handle LearningObjectiveLookup functionality.
 *
 * @author Kuali Student Team
 */

public class LoDisplayBrowseForm extends LookupForm {

    private List<LoDisplayInfoWrapper> loDisplayInfoWrapperList;

    private String findObjectivesFilter;

    public List<LoDisplayInfoWrapper> getLoDisplayInfoWrapperList() {
        return loDisplayInfoWrapperList;
    }

    public void setLoDisplayInfoWrapperList(List<LoDisplayInfoWrapper> loDisplayInfoWrapperList) {
        this.loDisplayInfoWrapperList = loDisplayInfoWrapperList;
    }

    public String getFindObjectivesFilter() {
        return findObjectivesFilter;
    }

    public void setFindObjectivesFilter(String findObjectivesFilter) {
        this.findObjectivesFilter = findObjectivesFilter;
    }

}
