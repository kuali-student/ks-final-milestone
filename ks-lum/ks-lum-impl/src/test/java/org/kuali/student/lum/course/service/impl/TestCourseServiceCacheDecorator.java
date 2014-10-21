/**
 * 
 */
package org.kuali.student.lum.course.service.impl;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Kuali Student Team
 * 
 * Verify that the course service tests still work ok when using the cache decorator on the top of the stack.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:course-test-cache-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestCourseServiceCacheDecorator {

	@Resource (name="CourseService")
	private CourseService courseService;
	
	public TestCourseServiceCacheDecorator() {
		super();
	}

	@Test
	public void testDTOImmutability () throws InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchFieldException, IntrospectionException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DoesNotExistException {
		
		/*
		 * Make sure that the dto that is stored in the cache is not mutable.
		 * 
		 */
		 CourseDataGenerator generator = new CourseDataGenerator();
	     
		 CourseInfo cInfo = generator.getCourseTestData();
	     
		 cInfo.setSpecialTopicsCourse(true);
	     cInfo.setPilotCourse(true);
	     cInfo.setCode("");
	        
		 ContextInfo context;
		
		 CourseInfo createdCourse = courseService.createCourse(cInfo, context = ContextInfoTestUtility.getEnglishContextInfo());
	        
		 CourseInfo cachedCourse = courseService.getCourse(cInfo.getId(), context);
		 
		 Assert.assertNotEquals("Expected different objects", createdCourse, cachedCourse);

		 /*
		  * Make sure changed to one returned dto doesn't change the value of the cached dto.
		  */
		 cachedCourse.setCode("ABCDEFG");
		 
		 CourseInfo secondCachedGet = courseService.getCourse(cInfo.getId(), context);
		 
		 Assert.assertNotEquals("Expected the code to not be ABCDEFG", cachedCourse.getCode(), secondCachedGet.getCode());
	}
	

}
