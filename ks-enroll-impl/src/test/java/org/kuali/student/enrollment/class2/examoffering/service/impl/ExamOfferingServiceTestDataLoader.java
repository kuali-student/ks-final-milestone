/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.examoffering.service.impl;

import org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader;
import org.kuali.student.common.test.spring.log4j.KSLog4JConfigurer;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.slf4j.Logger;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * This class is expected to be configured as a spring bean in order to have the resources loaded.
 * <p/>
 * We also define some methods that can be used to insert specific kinds of data into various spots for each example ExamOffering.
 *
 * @author Kuali Student Team
 */
public class ExamOfferingServiceTestDataLoader extends AbstractMockServicesAwareDataLoader {

    private static final Logger log = KSLog4JConfigurer.getLogger(ExamOfferingServiceTestDataLoader.class);

    private AcademicCalendarService acalService;

    public static final String TERM_ONE_ID = "term1";
    public static final String PERIOD_ONE_ID = "period1";

    /**
     */
    public ExamOfferingServiceTestDataLoader() {
        super();
    }


    public void beforeTest(boolean loadInstructors) throws Exception {
        initializeData();
        initialized = true;
    }

    /* (non-Javadoc)
    * @see org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader#initializeData()
    */
    @Override
    protected void initializeData() throws Exception {
        createExamPeriodData();
    }

    private void createExamPeriodData() throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException, AlreadyExistsException {

        //Create ExamPeriod
        ExamPeriodInfo examPeriodInfo = populateExamPeriod();
        ExamPeriodInfo createdExam = acalService.createExamPeriod(AtpServiceConstants.ATP_EXAM_PERIOD_TYPE_KEY, examPeriodInfo, context);

        //Create Term
        TermInfo term = populateTerm();
        TermInfo createdTerm = acalService.createTerm(AtpServiceConstants.ATP_FALL_TYPE_KEY, term, context);

        //Add
        StatusInfo ret = acalService.addExamPeriodToTerm(createdTerm.getId(), createdExam.getId(), context);
    }

    private TermInfo populateTerm() {
        TermInfo term = new TermInfo();
        term.setId(TERM_ONE_ID);
        term.setName("testNewTerm");
        term.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        term.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        term.setEndDate(new Date());
        term.setStartDate(new Date());
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain("");
        term.setDescr(richTextInfo);
        term.setStartDate(new Date(term.getStartDate().getTime()));
        return term;
    }

    private ExamPeriodInfo populateExamPeriod (){
        ExamPeriodInfo examPeriodInfo = new ExamPeriodInfo();
        examPeriodInfo.setId(PERIOD_ONE_ID);
        examPeriodInfo.setName("testCreate");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2005);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        examPeriodInfo.setStartDate(cal.getTime());
        cal.set(Calendar.YEAR, 2006);
        examPeriodInfo.setEndDate(cal.getTime());

        examPeriodInfo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        examPeriodInfo.setTypeKey(AtpServiceConstants.ATP_EXAM_PERIOD_TYPE_KEY);
        RichTextInfo descr = new RichTextInfo();
        descr.setPlain("Test");
        examPeriodInfo.setDescr(descr);
        return examPeriodInfo;
    }

    public AcademicCalendarService getAcalService() {
        return acalService;
    }

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

}
