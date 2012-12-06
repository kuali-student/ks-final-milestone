/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.enrollment.class2.acal.util;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author andrewlubbers
 * This Academic Calendar data loader does not require an instance of the Atp service.
 * Intended for use with mock Acal service implementations for testing
 */
public class MockAcalTestDataLoader {

    private AcademicCalendarService acalService;
    private static String principalId = MockAcalTestDataLoader.class.getSimpleName();

    public AcademicCalendarService getAcalService() {
        return acalService;
    }

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    public MockAcalTestDataLoader(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    public void loadData() {
        loadTerm("testAtpId1", "test1", "2000-01-01 00:00:00.0", "2100-12-31 00:00:00.0", AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "description 1");
    }

    public TermInfo loadTerm(String id,
                             String name,
                             String startDate, String endDate, String type, String state, String description) {

        TermInfo info = new TermInfo();
        info.setId(id);
        info.setCode(id);// use id for code
        info.setName(name);
        info.setDescr(new RichTextHelper().fromPlain(description));
        info.setTypeKey(type);
        info.setStateKey(state);
        info.setStartDate(str2Date(startDate, id));
        info.setEndDate(str2Date(endDate, id));


        ContextInfo context = new ContextInfo();
        context.setPrincipalId(principalId);
        context.setCurrentDate(new Date());
        try {
            return this.acalService.createTerm(type, info, context);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Date str2Date(String str, String context) {
        if (str == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S");
        try {
            return df.parse(str);
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Bad date " + str + " in " + context);
        }
    }

}
