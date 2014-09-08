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
 * Created by chongzhu on 6/27/14
 */
package org.kuali.student.cm.course.form;

import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.student.cm.course.form.wrapper.LoCategoryInfoWrapper;

import java.util.List;

/**
 * Form class to handle LoCategoryType Browsing functionality.
 *
 * @author Kuali Student Team
 */
public class LoCategoryTypeBrowseForm extends LookupForm {

    private List<LoCategoryInfoWrapper> loCategoryInfoWrappers;

    private String categoryFilter;

    private List<String> typeList;

    private Integer totalTypes = 0;

    public List<LoCategoryInfoWrapper> getLoCategoryInfoWrappers() {
        return loCategoryInfoWrappers;
    }

    public void setLoCategoryInfoWrappers(List<LoCategoryInfoWrapper> loCategoryInfoWrappers) {
        this.loCategoryInfoWrappers = loCategoryInfoWrappers;
    }

    public String getCategoryFilter() {
        return categoryFilter;
    }

    public void setCategoryFilter(String categoryFilter) {
        this.categoryFilter = categoryFilter;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

    public Integer getTotalTypes() {
        return totalTypes;
    }

    public void setTotalTypes(Integer totalTypes) {
        this.totalTypes = totalTypes;
    }
}
