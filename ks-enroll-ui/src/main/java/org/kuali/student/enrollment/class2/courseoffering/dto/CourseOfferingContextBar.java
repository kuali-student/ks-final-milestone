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
 * Created by brandon.gresham on 6/13/2013
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Holds properties for the Context-bar that appears on several different CourseOffering-views.
 *
 * This is intended to be a property of wrappers/forms to hold context-bar details used only at the presentation layer.
 */
public class CourseOfferingContextBar implements Serializable {

    private static final CourseOfferingContextBar NULL_SAFE_INSTANCE = new CourseOfferingContextBar("", "", 0);

    private String termName;
    private String termSocState;
    private int termDayOfYear;

    private CourseOfferingContextBar(String termName, String termSocState, int termDayOfYear) {
        this.termName = termName;
        this.termSocState = termSocState;
        this.termDayOfYear = termDayOfYear;
    }

    /**
     * Gets the term-name to be displayed in the context-bar xml.
     * (ie: "Spring 2013")
     *
     * @return
     */
    @SuppressWarnings("unused")
    public String getTermName() {
        return termName;
    }

    /**
     * Gets the soc-state to be displayed in the context-bar xml.
     * (ie: "Open")
     *
     * @return
     */
    @SuppressWarnings("unused")
    public String getTermSocState() {
        return termSocState;
    }

    /**
     * Gets the term-day-of-the-year for use in determining the color of the side-bar in the context-bar xml.
     *
     * @return
     */
    @SuppressWarnings("unused")
    public int getTermDayOfYear() {
        return termDayOfYear;
    }

    public static CourseOfferingContextBar NEW_INSTANCE( TermInfo termInfo, SocInfo socInfo, StateService stateService, AcademicCalendarService academicCalendarService, ContextInfo contextInfo ) throws Exception {
        return NEW_INSTANCE( termInfo, socInfo.getStateKey(), stateService, academicCalendarService, contextInfo );
    }

    public static CourseOfferingContextBar NEW_INSTANCE( TermInfo termInfo, String socStateKey, StateService stateService, AcademicCalendarService academicCalendarService, ContextInfo contextInfo ) throws  Exception {

        String termName = termInfo.getName();
        String termSocState = stateService.getState( socStateKey, contextInfo ).getName();
        int termDayOfYear = calculateTermDayOfYear( termInfo, academicCalendarService, contextInfo );

        return new CourseOfferingContextBar( termName, termSocState, termDayOfYear );
    }

    public static CourseOfferingContextBar NULL_SAFE_INSTANCE() {
        return NULL_SAFE_INSTANCE;
    }

    private static int calculateTermDayOfYear( TermInfo termInfo, AcademicCalendarService academicCalendarService, ContextInfo contextInfo ) throws Exception {

        int termDayOfYear = 0; // default to 1st day of the year

        List<KeyDateInfo> keyDateInfoList = academicCalendarService.getKeyDatesForTerm( termInfo.getId(), contextInfo );
        for(KeyDateInfo keyDateInfo : keyDateInfoList ) {
            if( keyDateInfo.getTypeKey().equalsIgnoreCase(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY)
                    && keyDateInfo.getStartDate() != null
                    && keyDateInfo.getEndDate() != null )
            {
                Date termClassStartDate = keyDateInfo.getStartDate();
                Date termClassEndDate = keyDateInfo.getEndDate();
                Date avgDate = new Date( termClassStartDate.getTime() + ( (termClassEndDate.getTime() - termClassStartDate.getTime()) /2 ) );
                Calendar cal = Calendar.getInstance();
                cal.setTime( avgDate) ;
                termDayOfYear = cal.get( Calendar.DAY_OF_YEAR );
                break;
            }
        }

        return termDayOfYear;
    }

}
