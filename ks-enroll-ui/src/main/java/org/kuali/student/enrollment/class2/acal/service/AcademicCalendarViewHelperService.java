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
package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.AcademicTermWrapper;
import org.kuali.student.enrollment.class2.acal.dto.AcalEventWrapper;
import org.kuali.student.enrollment.class2.acal.dto.KeyDatesGroupWrapper;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.enrollment.uif.service.KSViewHelperService;

import java.util.List;

/**
 * @author Kuali Student Team
 */
public interface AcademicCalendarViewHelperService extends KSViewHelperService {

    public void populateAcademicCalendar(String acalId, AcademicCalendarForm acalForm);

    public AcademicCalendarInfo createAcademicCalendar(AcademicCalendarForm acalForm) throws Exception;

    public AcademicCalendarInfo getLatestAcademicCalendar() throws Exception;

    public void copyToCreateAcademicCalendar(AcademicCalendarForm form);

    public List<AcalEventWrapper> populateEventWrappers(String acalId) throws Exception;

    public AcalEventWrapper createEvent(String acalId, AcalEventWrapper event,boolean isAcalOfficial) throws Exception;

    public AcalEventWrapper updateEvent(String eventId, AcalEventWrapper event) throws Exception;

    public void saveTerm(AcademicTermWrapper termWrapper, String acalId,boolean isOfficial,boolean calculateInstrDays) throws Exception;

    public void populateInstructionalDays(AcademicTermWrapper termWrapper);

    public List<AcademicTermWrapper> populateTermWrappers(String acalId, boolean isCopy,boolean calculateInstrDays);

    public void validateAcademicCalendar(AcademicCalendarForm acalForm);

    public void validateTerm(List<AcademicTermWrapper> termWrapper,int termToValidateIndex,AcademicCalendarInfo acal);

    public void populateAcademicCalendarDefaults(AcademicCalendarForm acalForm);

    public AcademicTermWrapper populateTermWrapper(TermInfo termInfo, boolean isCopy,boolean calculateInstrDays)throws Exception;

}
