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
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CalendarSearchForm extends UifFormBase {
    private String calendarType;
    private String name;
    private String year;
    private List<HolidayCalendarInfo> holidayCalendars;
    private List<AcademicCalendarInfo> academicCalendars;
    private List<TermInfo> terms;

    private String selectedLineIndex;
    private String selectedCollectionPath;

    public CalendarSearchForm(){
        super();
        holidayCalendars =  new ArrayList<HolidayCalendarInfo>();
        academicCalendars = new ArrayList<AcademicCalendarInfo>();
        terms =  new ArrayList<TermInfo>();
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

    public List<HolidayCalendarInfo> getHolidayCalendars() {
        return holidayCalendars;
    }

    public void setHolidayCalendars(List<HolidayCalendarInfo> holidayCalendars) {
        this.holidayCalendars = holidayCalendars;
    }

    public List<AcademicCalendarInfo> getAcademicCalendars() {
        return academicCalendars;
    }

    public void setAcademicCalendars(List<AcademicCalendarInfo> academicCalendars) {
        this.academicCalendars = academicCalendars;
    }

    public List<TermInfo> getTerms() {
        return terms;
    }

    public void setTerms(List<TermInfo> terms) {
        this.terms = terms;
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
}
