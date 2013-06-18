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

import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.state.service.StateService;

import java.io.Serializable;

/**
 * Holds properties for the Context-bar that appears on several different CourseOffering-views.
 *
 * This is intended to be a property of wrappers/forms to hold context-bar details used only at the presentation layer.
 */
public class CourseOfferingContextBar implements Serializable {

    public static final CourseOfferingContextBar NULL_SAFE_INSTANCE = new CourseOfferingContextBar("", "", 0);

    private String termName;
    private String termSocState;
    private int termDayOfYear;

    public CourseOfferingContextBar() { }

    public CourseOfferingContextBar(String termName, String termSocState, int termDayOfYear) {
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
     * Sets the term-name to be displayed in the context-bar xml.
     * (ie: "Spring 2013")
     */
    @SuppressWarnings("unused")
    public void setTermName( String termName ) {
        this.termName = termName;
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
     * Sets the soc-state to be displayed in the context-bar xml.
     * (ie: "Open")
     */
    @SuppressWarnings("unused")
    public void setTermSocState( String termSocState ) {
        this.termSocState = termSocState;
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

    /**
     * Sets the term-day-of-the-year for use in determining the color of the side-bar in the context-bar xml.
     */
    @SuppressWarnings("unused")
    public void setTermDayOfYear( int termDayOfYear ) {
        this.termDayOfYear = termDayOfYear;
    }

    /**
     * Convenience-method to build an instance of CourseOfferingContextBar
     *
     * @param termInfo
     * @param socInfo
     * @param stateService
     * @param academicCalendarService
     * @param contextInfo
     * @return
     * @throws Exception
     */
    public static CourseOfferingContextBar NEW_INSTANCE( TermInfo termInfo, SocInfo socInfo, StateService stateService, AcademicCalendarService academicCalendarService, ContextInfo contextInfo ) throws Exception {
        return NEW_INSTANCE( termInfo, socInfo.getStateKey(), stateService, academicCalendarService, contextInfo );
    }

    /**
     * Convenience-method to build an instance of CourseOfferingContextBar
     *
     * @param termInfo
     * @param socStateKey a String representing the SOC's state (ie: "Open")
     * @param stateService
     * @param academicCalendarService
     * @param contextInfo
     * @return
     * @throws Exception
     */
    public static CourseOfferingContextBar NEW_INSTANCE( TermInfo termInfo, String socStateKey, StateService stateService, AcademicCalendarService academicCalendarService, ContextInfo contextInfo ) throws  Exception {

        String termName = termInfo.getName();
        String termSocState = stateService.getState( socStateKey, contextInfo ).getName();
        int termDayOfYear = CourseOfferingViewHelperUtil.calculateTermDayOfYear(termInfo, academicCalendarService, contextInfo);

        CourseOfferingContextBar instance = new CourseOfferingContextBar();
        instance.setTermName( termName );
        instance.setTermSocState( termSocState );
        instance.setTermDayOfYear( termDayOfYear );

        return instance;
    }

}
