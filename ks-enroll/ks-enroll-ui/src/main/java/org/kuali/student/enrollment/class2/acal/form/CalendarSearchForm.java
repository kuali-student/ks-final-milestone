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
package org.kuali.student.enrollment.class2.acal.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.acal.dto.AcalSearchResult;


import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a form for CalendarSearch objects
 *
 * @author Kuali Student Team
 */
public class CalendarSearchForm extends UifFormBase {
    private String calendarType;
    private String name;
    private String year;
    private List<AcalSearchResult> searchResults;

    private String selectedLineIndex;
    private String selectedCollectionPath;
    private boolean clickSearchButton;

    public CalendarSearchForm(){
        super();
        searchResults =  new ArrayList<AcalSearchResult>();
        clickSearchButton = false;
    }

    public String getCalendarType() {
        return calendarType;
    }

    public void setCalendarType(String calendarType) {
        this.calendarType = calendarType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSelectedLineIndex() {
        return selectedLineIndex;
    }

    public void setSelectedLineIndex(String selectedLineIndex) {
        this.selectedLineIndex = selectedLineIndex;
    }
    public String getSelectedCollectionPath() {
        return selectedCollectionPath;
    }

    public void setSelectedCollectionPath(String selectedCollectionPath) {
        this.selectedCollectionPath = selectedCollectionPath;
    }

    public List<AcalSearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<AcalSearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    public boolean isClickSearchButton() {
        return clickSearchButton;
    }

    public void setClickSearchButton(boolean clickSearchButton) {
        this.clickSearchButton = clickSearchButton;
    }
}
