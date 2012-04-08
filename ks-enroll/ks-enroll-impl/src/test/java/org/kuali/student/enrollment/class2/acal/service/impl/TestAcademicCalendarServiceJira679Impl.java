package org.kuali.student.enrollment.class2.acal.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.atp.service.impl.AtpTestDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:acal-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestAcademicCalendarServiceJira679Impl {

    @Autowired
    @Qualifier("acalServiceAuthDecorator")
    private AcademicCalendarService acalService;

    @Autowired
    @Qualifier("atpServiceAuthorization")
    private AtpService atpService;

    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        try {
            loadData();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void loadData() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException, VersionMismatchException, AlreadyExistsException {
        AtpTestDataLoader loader = new AtpTestDataLoader(this.atpService);
        loader.loadData();
    }

    @Test
    public void testJira679()
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, ReadOnlyException, VersionMismatchException {
        AcademicCalendarInfo orig = new AcademicCalendarInfo();
        orig.setName("testAcal1 name");
        orig.setEndDate(new Date());
        orig.setStartDate(new Date());
        orig.setDescr(new RichTextHelper().fromPlain("test descrtion 1"));
        // Assume holidayCalendarIds picking up from dropdown and valid(already
        // in db)
        List<String> holidayCalendarIds = new ArrayList<String>();
        holidayCalendarIds.add("testAtpId2");
        orig.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        orig.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        orig.setHolidayCalendarIds(holidayCalendarIds);
        AcademicCalendarInfo info = acalService.createAcademicCalendar(orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals (orig.getName(), info.getName());
        assertEquals (orig.getHolidayCalendarIds().size(), info.getHolidayCalendarIds().size());
        assertEquals (orig.getHolidayCalendarIds().get(0), info.getHolidayCalendarIds().get(0));
        
        
        orig = info;
        info = acalService.getAcademicCalendar(info.getId(), callContext);
        assertNotNull(info);
        assertEquals (orig.getName(), info.getName());
        assertEquals (orig.getHolidayCalendarIds().size(), info.getHolidayCalendarIds().size());
        assertEquals (orig.getHolidayCalendarIds().get(0), info.getHolidayCalendarIds().get(0));

        orig = info;
        orig.setName("testNewAcal name");
        info = acalService.updateAcademicCalendar(info.getId(), info, callContext);
        assertNotNull(info);
        assertEquals (orig.getName(), info.getName());
        assertEquals (orig.getHolidayCalendarIds().size(), info.getHolidayCalendarIds().size());
        assertEquals (orig.getHolidayCalendarIds().get(0), info.getHolidayCalendarIds().get(0));
    }

}
