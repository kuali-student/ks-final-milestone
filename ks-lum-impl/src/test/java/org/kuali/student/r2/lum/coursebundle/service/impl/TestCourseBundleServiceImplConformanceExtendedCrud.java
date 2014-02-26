/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lic+ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.lum.coursebundle.service.impl;


import org.junit.After;
import org.junit.Test;
import org.kuali.student.common.test.util.RichTextTester;
import org.kuali.student.lum.coursebundle.dto.CourseBundleInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@Transactional
public abstract class TestCourseBundleServiceImplConformanceExtendedCrud extends TestCourseBundleServiceImplConformanceBaseCrud
{
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @Resource
    private CourseBundleDataLoader dataLoader;

    @After
    public void tearDownExtended() throws Exception {
        dataLoader.afterTest();
    }

	// ========================================
	// DTO FIELD SPECIFIC METHODS
	// ========================================
	
	// ****************************************************
	//           CourseBundleInfo
	// ****************************************************
	
	/*
		A method to set the fields for a CourseBundle in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudCourseBundle_setDTOFieldsForTestCreate(CourseBundleInfo expected) throws ParseException
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
		expected.setName("name01");
		expected.setDescr(RichTextHelper.buildRichTextInfo("descr01", "descr01"));

        expected.setEffectiveDate(dateFormat.parse("20130611"));
        expected.setExpirationDate(dateFormat.parse("20500101"));
		expected.setCourseBundleCode("courseBundleCode01");
		expected.setStartTermId("startTermId01");
		expected.setEndTermId("endTermId01");
		expected.setSubjectAreaOrgId("subjectAreaOrgId01");
		expected.setCourseBundleCodeSuffix("courseBundleCodeSuffix01");
        expected.setAdminOrgIds(Arrays.asList("1", "2", "3", "4", "5", "6"));
        expected.setCourseIds(Arrays.asList("A", "B", "C"));
	}
	
	/*
		A method to test the fields for a CourseBundle. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudCourseBundle_testDTOFieldsForTestCreateUpdate(CourseBundleInfo expected, CourseBundleInfo actual) 
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		new RichTextTester().check(expected.getDescr(), actual.getDescr());
		assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
		assertEquals (expected.getExpirationDate(), actual.getExpirationDate());
		assertEquals (expected.getCourseBundleCode(), actual.getCourseBundleCode());
		assertEquals (expected.getStartTermId(), actual.getStartTermId());
		assertEquals (expected.getEndTermId(), actual.getEndTermId());
		assertEquals (expected.getSubjectAreaOrgId(), actual.getSubjectAreaOrgId());
		assertEquals (expected.getCourseBundleCodeSuffix(), actual.getCourseBundleCodeSuffix());
        assertEquals(expected.getAdminOrgIds().size(), actual.getAdminOrgIds().size());
        for(String orgId : expected.getAdminOrgIds())  {
            assertTrue(actual.getAdminOrgIds().contains(orgId));
        }
        assertEquals(expected.getCourseIds().size(), actual.getCourseIds().size());
        for(String id : expected.getCourseIds())  {
            assertTrue(actual.getCourseIds().contains(id));
        }
	}
	
	/*
		A method to set the fields for a CourseBundle in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudCourseBundle_setDTOFieldsForTestUpdate(CourseBundleInfo expected) throws ParseException
	{
		expected.setTypeKey("typeKey_Updated");
		expected.setStateKey("stateKey_Updated");
		expected.setName("name_Updated");
		expected.setDescr(RichTextHelper.buildRichTextInfo("descr_Updated", "descr_Updated"));
        expected.setEffectiveDate(dateFormat.parse("20200611"));
        expected.setExpirationDate(dateFormat.parse("20550101"));
		expected.setCourseBundleCode("courseBundleCode_Updated");
		expected.setStartTermId("startTermId_Updated");
		expected.setEndTermId("endTermId_Updated");
		expected.setSubjectAreaOrgId("subjectAreaOrgId_Updated");
		expected.setCourseBundleCodeSuffix("courseBundleCodeSuffix_Updated");
        expected.setAdminOrgIds(Arrays.asList("SINGLE_ID"));
        expected.setCourseIds(Arrays.asList("1", "2", "3"));
	}
	
	/*
		A method to test the fields for a CourseBundle after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudCourseBundle_testDTOFieldsForTestReadAfterUpdate(CourseBundleInfo expected, CourseBundleInfo actual) 
	{
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		new RichTextTester().check(expected.getDescr(), actual.getDescr());
        assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals (expected.getExpirationDate(), actual.getExpirationDate());
		assertEquals (expected.getCourseBundleCode(), actual.getCourseBundleCode());
		assertEquals (expected.getStartTermId(), actual.getStartTermId());
		assertEquals (expected.getEndTermId(), actual.getEndTermId());
		assertEquals (expected.getSubjectAreaOrgId(), actual.getSubjectAreaOrgId());
		assertEquals (expected.getCourseBundleCodeSuffix(), actual.getCourseBundleCodeSuffix());
        assertEquals(expected.getAdminOrgIds().size(), actual.getAdminOrgIds().size());
        for(String orgId : expected.getAdminOrgIds())  {
            assertTrue(actual.getAdminOrgIds().contains(orgId));
        }
        assertEquals(expected.getCourseIds().size(), actual.getCourseIds().size());
        for(String id : expected.getCourseIds())  {
            assertTrue(actual.getCourseIds().contains(id));
        }
	}
	
	/*
		A method to set the fields for a CourseBundle in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudCourseBundle_setDTOFieldsForTestReadAfterUpdate(CourseBundleInfo expected) throws ParseException {
		expected.setName("name_Updated");
        expected.setEffectiveDate(dateFormat.parse("19000611"));
        expected.setExpirationDate(dateFormat.parse("30300101"));
		expected.setCourseBundleCode("courseBundleCode_Updated");
		expected.setStartTermId("startTermId_Updated");
		expected.setEndTermId("endTermId_Updated");
		expected.setSubjectAreaOrgId("subjectAreaOrgId_Updated");
		expected.setCourseBundleCodeSuffix("courseBundleCodeSuffix_Updated");

        expected.setAdminOrgIds(Arrays.asList("SINGLE_ID_UPDATED"));
        expected.setCourseIds(Arrays.asList("X", "Y", "Z"));
	}
	
	
	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================
	
	/* Method Name: searchForCourseBundleIds */
	@Test
	public void test_searchForCourseBundleIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForCourseBundles */
	@Test
	public void test_searchForCourseBundles() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validateCourseBundle */
	@Test
	public void test_validateCourseBundle() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: changeCourseBundleState */
	@Test
	public void test_changeCourseBundleState() throws Exception	{
        loadData();
        List<String> infos = getCourseBundleService().getCourseBundleIdsByType(CourseBundleDataLoader.COURSE_BUNDLE_TYPE_KEY, contextInfo);
        assertTrue(infos.size() > 0);
        CourseBundleInfo original = getCourseBundleService().getCourseBundle(infos.get(0), contextInfo);
        assertEquals(CourseBundleDataLoader.COURSE_BUNDLE_ACTIVE_STATE_KEY,original.getStateKey());
        original.setStateKey(CourseBundleDataLoader.COURSE_BUNDLE_INACTIVE_STATE_KEY);

        getCourseBundleService().updateCourseBundle(original.getId(), original, contextInfo);
        CourseBundleInfo updated = getCourseBundleService().getCourseBundle(original.getId(), contextInfo);
        assertEquals(CourseBundleDataLoader.COURSE_BUNDLE_INACTIVE_STATE_KEY,updated.getStateKey());
	}


    private void loadData() throws Exception {
        dataLoader.beforeTest();
    }
}


