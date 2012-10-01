package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.junit.Ignore;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiServiceDataLoader;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;

import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


/*
 * This class was used to test the class1 backed implementation of CourseOfferingService for CourseOffering, FormatOffering and ActivityOffering.
 * 
 * For M4 it has been refactored.  Most of the test are now in TestCourseOfferingServiceMockImpl and only db dependent tests go here.
 * 
 * See TestLprServiceImpl for an example.
 * 
 * Once the tests can be run this should be unignored.
 * 
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestCourseOfferingServiceImpl extends TestCourseOfferingServiceImplWithClass2Mocks {


	public TestCourseOfferingServiceImpl() {
		/*
		 * The tx for each transaction rollsback so we don't need to reload data at the end of each test.
		 */
		super(false);
	}

	public void simpleTest() {
		assertTrue(false);
	}

    @Test
    @Ignore
	// there is name property on CourseOffering right now so this will have to
	// be adjusted later.
	public void testSearchForCourseOfferings()
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		try {
			QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder
					.create();
			qbcBuilder.setPredicates(PredicateFactory.like("name", "*three*"));
			QueryByCriteria qbc = qbcBuilder.build();
			List<CourseOfferingInfo> coList = coService
					.searchForCourseOfferings(qbc, callContext);
			assertNotNull(coList);
			assertEquals(1, coList.size());
			CourseOfferingInfo coInfo = coList.get(0);
			assertEquals("Lui-3", coInfo.getId());
		} catch (Exception ex) {
			fail("Exception from service call :" + ex.getMessage());
		}
	}
   
}
