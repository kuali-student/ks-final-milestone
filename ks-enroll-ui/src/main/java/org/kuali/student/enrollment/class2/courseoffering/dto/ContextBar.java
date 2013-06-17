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

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.StateServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Holds properties for the Context-bar that appears on several different views
 *
 * Similarly as all the other wrappers, meant to hold the details used at the presentation layer,
 * but intended to be a property of wrappers/forms rather than a wrapper-type object in it's
 * own right
 */
public class ContextBar implements Serializable {


    private static final StateService STATE_SERVICE;
    static {
        STATE_SERVICE = (StateService) GlobalResourceLoader.getService( new QName(StateServiceConstants.NAMESPACE, StateServiceConstants.SERVICE_NAME_LOCAL_PART) );
    }

    private static final AcademicCalendarService ACADEMIC_CALENDAR_SERVICE;
    static {
        ACADEMIC_CALENDAR_SERVICE = (AcademicCalendarService) GlobalResourceLoader.getService( new QName("http://student.kuali.org/wsdl/acal", "AcademicCalendarService") );
    }

    private static final ContextBar NULL_SAFE_INSTANCE = new ContextBar("", "", 0);

    private String termName;
    private String termSocState;
    private int termDayOfYear;

    private ContextBar( String termName, String termSocState, int termDayOfYear ) {
        this.termName = termName;
        this.termSocState = termSocState;
        this.termDayOfYear = termDayOfYear;
    }

    public String getTermName() {
        return termName;
    }

    public String getTermSocState() {
        return termSocState;
    }

    public int getTermDayOfYear() {
        return termDayOfYear;
    }

    public static ContextBar NEW_INSTANCE( TermInfo termInfo, SocInfo socInfo, ContextInfo contextInfo ) throws Exception {
        return NEW_INSTANCE( termInfo, socInfo.getStateKey(), contextInfo );
    }

    public static ContextBar NEW_INSTANCE( TermInfo termInfo, String socStateKey, ContextInfo contextInfo ) throws  Exception {

        String termName = termInfo.getName();
        String termSocState = STATE_SERVICE.getState( socStateKey, contextInfo ).getName();
        int termDayOfYear = calculateTermDayOfYear( termInfo, contextInfo );

        return new ContextBar( termName, termSocState, termDayOfYear );
    }

    public static ContextBar NULL_SAFE_INSTANCE() {
        return NULL_SAFE_INSTANCE;
    }

    private static int calculateTermDayOfYear( TermInfo termInfo, ContextInfo contextInfo ) throws Exception {

        int termDayOfYear = 0; // default to 1st day of the year

        List<KeyDateInfo> keyDateInfoList = ACADEMIC_CALENDAR_SERVICE.getKeyDatesForTerm(termInfo.getId(), contextInfo);
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
