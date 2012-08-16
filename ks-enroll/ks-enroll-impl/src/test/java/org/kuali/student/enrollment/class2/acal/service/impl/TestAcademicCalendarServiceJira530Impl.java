package org.kuali.student.enrollment.class2.acal.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.common.dto.TypeInfo;
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
public class TestAcademicCalendarServiceJira530Impl {

    @Autowired
    @Qualifier("acalServiceAuthDecorator")
    private AcademicCalendarService acalService;
    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testJira530()
            throws AlreadyExistsException, DataValidationErrorException, 
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, ReadOnlyException, VersionMismatchException {
        
        String termType = AtpServiceConstants.ATP_FALL_TYPE_KEY;
        List<TypeInfo> types = this.acalService.getKeyDateTypesForTermType(termType, callContext);
        assertNotNull(types);
        if (types.size () < 2) {
            fail ("too few key date types");
        }
        System.out.println ("Found " + types.size() + " keydate types for " + termType);
        for (TypeInfo type : types) {
            System.out.println(type.getKey() + " " + type.getName());
        }
    }

}
