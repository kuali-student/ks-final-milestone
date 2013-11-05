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
package org.kuali.student.enrollment.class2.bundledoffering.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.IdEntityTester;
import org.kuali.student.common.test.util.KeyEntityTester;
import org.kuali.student.common.test.util.RichTextTester;
import org.kuali.student.enrollment.bundledoffering.dto.BundledOfferingInfo;
import org.kuali.student.enrollment.bundledoffering.service.BundledOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeStateEntityInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@Transactional
public abstract class TestBundledOfferingServiceImplConformanceExtendedCrud extends TestBundledOfferingServiceImplConformanceBaseCrud
{

    @Resource
    private BundledOfferingDataLoader dataLoader;

    @After
    public void tearDownExtended() throws Exception {
        dataLoader.afterTest();
    }
	
	// ========================================
	// DTO FIELD SPECIFIC METHODS
	// ========================================
	
	// ****************************************************
	//           BundledOfferingInfo
	// ****************************************************
	
	/*
		A method to set the fields for a BundledOffering in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudBundledOffering_setDTOFieldsForTestCreate(BundledOfferingInfo expected) 
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
		expected.setName("name01");
		expected.setDescr(RichTextHelper.buildRichTextInfo("descr01", "descr01"));
		expected.setCourseBundleId("courseBundleId01");
		expected.setTermId("termId01");
		expected.setBundledOfferingCode("bundledOfferingCode01");
		expected.setSubjectAreaOrgId("subjectAreaOrgId01");
		expected.setBundledOfferingCodeSuffix("bundledOfferingCodeSuffix01");
        expected.setAdminOrgIds(Arrays.asList(new String[]{"1", "2", "3", "4", "5", "6"}));
        expected.setFormatOfferingIds(Arrays.asList(new String[] {"A", "B", "C"}));
        expected.setRegistrationGroupIds(Arrays.asList(new String[] {"D"}));
	}
	
	/*
		A method to test the fields for a BundledOffering. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudBundledOffering_testDTOFieldsForTestCreateUpdate(BundledOfferingInfo expected, BundledOfferingInfo actual) 
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		new RichTextTester().check(expected.getDescr(), actual.getDescr());
		assertEquals (expected.getCourseBundleId(), actual.getCourseBundleId());
		assertEquals (expected.getTermId(), actual.getTermId());
		assertEquals (expected.getBundledOfferingCode(), actual.getBundledOfferingCode());
		assertEquals (expected.getSubjectAreaOrgId(), actual.getSubjectAreaOrgId());
		assertEquals (expected.getBundledOfferingCodeSuffix(), actual.getBundledOfferingCodeSuffix());
        assertEquals(expected.getAdminOrgIds().size(), actual.getAdminOrgIds().size());
        for(String orgId : expected.getAdminOrgIds())  {
            assertTrue(actual.getAdminOrgIds().contains(orgId));
        }
        assertEquals(expected.getFormatOfferingIds().size(), actual.getFormatOfferingIds().size());
        for(String id : expected.getFormatOfferingIds())  {
            assertTrue(actual.getFormatOfferingIds().contains(id));
        }
        assertEquals(expected.getRegistrationGroupIds().size(), actual.getRegistrationGroupIds().size());
        for(String id : expected.getRegistrationGroupIds())  {
            assertTrue(actual.getRegistrationGroupIds().contains(id));
        }
	}
	
	/*
		A method to set the fields for a BundledOffering in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudBundledOffering_setDTOFieldsForTestUpdate(BundledOfferingInfo expected) 
	{
		expected.setTypeKey("typeKey_Updated");
		expected.setStateKey("stateKey_Updated");
		expected.setName("name_Updated");
		expected.setDescr(RichTextHelper.buildRichTextInfo("descr_Updated", "descr_Updated"));
		expected.setCourseBundleId("courseBundleId_Updated");
		expected.setTermId("termId_Updated");
		expected.setBundledOfferingCode("bundledOfferingCode_Updated");
		expected.setSubjectAreaOrgId("subjectAreaOrgId_Updated");
		expected.setBundledOfferingCodeSuffix("bundledOfferingCodeSuffix_Updated");
        expected.setAdminOrgIds(Arrays.asList(new String[]{"UPDATED"}));
        expected.setFormatOfferingIds(Arrays.asList(new String[] {"Q"}));
        expected.setRegistrationGroupIds(Arrays.asList(new String[] {}));
	}
	
	/*
		A method to test the fields for a BundledOffering after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudBundledOffering_testDTOFieldsForTestReadAfterUpdate(BundledOfferingInfo expected, BundledOfferingInfo actual) 
	{
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		new RichTextTester().check(expected.getDescr(), actual.getDescr());
		assertEquals (expected.getCourseBundleId(), actual.getCourseBundleId());
		assertEquals (expected.getTermId(), actual.getTermId());
		assertEquals (expected.getBundledOfferingCode(), actual.getBundledOfferingCode());
		assertEquals (expected.getSubjectAreaOrgId(), actual.getSubjectAreaOrgId());
		assertEquals (expected.getBundledOfferingCodeSuffix(), actual.getBundledOfferingCodeSuffix());
        assertEquals(expected.getAdminOrgIds().size(), actual.getAdminOrgIds().size());
        for(String orgId : expected.getAdminOrgIds())  {
            assertTrue(actual.getAdminOrgIds().contains(orgId));
        }
        assertEquals(expected.getFormatOfferingIds().size(), actual.getFormatOfferingIds().size());
        for(String id : expected.getFormatOfferingIds())  {
            assertTrue(actual.getFormatOfferingIds().contains(id));
        }
        assertEquals(expected.getRegistrationGroupIds().size(), actual.getRegistrationGroupIds().size());
        for(String id : expected.getRegistrationGroupIds())  {
            assertTrue(actual.getRegistrationGroupIds().contains(id));
        }
	}
	
	/*
		A method to set the fields for a BundledOffering in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudBundledOffering_setDTOFieldsForTestReadAfterUpdate(BundledOfferingInfo expected) 
	{
		expected.setName("name_Updated");
		expected.setCourseBundleId("courseBundleId_Updated");
		expected.setTermId("termId_Updated");
		expected.setBundledOfferingCode("bundledOfferingCode_Updated");
		expected.setSubjectAreaOrgId("subjectAreaOrgId_Updated");
		expected.setBundledOfferingCodeSuffix("bundledOfferingCodeSuffix_Updated");
        expected.setAdminOrgIds(Arrays.asList(new String[]{"1"}));
        expected.setFormatOfferingIds(Arrays.asList(new String[] {"2"}));
        expected.setRegistrationGroupIds(Arrays.asList(new String[] {"3"}));
	}
	
	
	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================
	
	/* Method Name: getBundledOfferingsByCourseBundle */
	@Test
	public void test_getBundledOfferingsByCourseBundle() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();
        List<BundledOfferingInfo> infos = getBundledOfferingService().getBundledOfferingsByCourseBundle("COURSE_BUNDLE0", contextInfo);
        assertEquals(1, infos.size());
        assertEquals(infos.get(0).getCourseBundleId(), "COURSE_BUNDLE0");
        assertEquals(infos.get(0).getName(), "NAME0");

        infos = getBundledOfferingService().getBundledOfferingsByCourseBundle("COURSE_BUNDLEXXX", contextInfo);
        assertEquals(0, infos.size());
	}
	
	/* Method Name: getBundledOfferingsByTerm */
	@Test
	public void test_getBundledOfferingsByTerm() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();
        List<BundledOfferingInfo> infos = getBundledOfferingService().getBundledOfferingsByTerm("1", contextInfo);
        assertEquals(1, infos.size());
        assertEquals(infos.get(0).getTermId(), "1");
        assertEquals(infos.get(0).getName(), "NAME1");

        infos = getBundledOfferingService().getBundledOfferingsByTerm("BAD_ID", contextInfo);
        assertEquals(0, infos.size());
	}
	
	/* Method Name: getBundledOfferingsByCourseBundleAndTerm */
	@Test
	public void test_getBundledOfferingsByCourseBundleAndTerm() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();

        List<BundledOfferingInfo> infos = getBundledOfferingService().getBundledOfferingsByCourseBundleAndTerm("COURSE_BUNDLE1", "1", contextInfo);
        assertEquals(1, infos.size());
        assertEquals(infos.get(0).getTermId(), "1");
        assertEquals(infos.get(0).getCourseBundleId(), "COURSE_BUNDLE1");
        assertEquals(infos.get(0).getName(), "NAME1");

        infos = getBundledOfferingService().getBundledOfferingsByCourseBundleAndTerm("BAD_ID1", "BAD_ID2", contextInfo);
        assertEquals(0, infos.size());

        infos = getBundledOfferingService().getBundledOfferingsByCourseBundleAndTerm("BAD_ID1", "1", contextInfo);
        assertEquals(0, infos.size());

        infos = getBundledOfferingService().getBundledOfferingsByCourseBundleAndTerm("COURSE_BUNDLE1", "BAD_ID2", contextInfo);
        assertEquals(0, infos.size());

        infos = getBundledOfferingService().getBundledOfferingsByCourseBundleAndTerm("COURSE_BUNDLE1", "0", contextInfo);
        assertEquals(0, infos.size());
	}
	
	/* Method Name: getBundledOfferingsByRegistrationGroup */
	@Test
	public void test_getBundledOfferingsByRegistrationGroup() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();

        List<BundledOfferingInfo> infos = getBundledOfferingService().getBundledOfferingsByRegistrationGroup("9",contextInfo);

        assertEquals(1, infos.size());
        assertEquals(infos.get(0).getTermId(), "9");
        assertEquals(infos.get(0).getCourseBundleId(), "COURSE_BUNDLE9");
        assertEquals(infos.get(0).getName(), "NAME9");

        infos = getBundledOfferingService().getBundledOfferingsByRegistrationGroup("XXX",contextInfo);
        assertEquals(0, infos.size());

        infos = getBundledOfferingService().getBundledOfferingsByRegistrationGroup("1",contextInfo);
        assertEquals(9, infos.size());
	}

	
	/* Method Name: getBundledOfferingsByTermAndCode */
	@Test
	public void test_getBundledOfferingsByTermAndCode() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();

        List<BundledOfferingInfo> infos = getBundledOfferingService().getBundledOfferingsByTermAndCode("1","BO_CODE1",contextInfo);
        assertEquals(1, infos.size());
        assertEquals(infos.get(0).getTermId(), "1");
        assertEquals(infos.get(0).getCourseBundleId(), "COURSE_BUNDLE1");
        assertEquals(infos.get(0).getName(), "NAME1");

        infos = getBundledOfferingService().getBundledOfferingsByTermAndCode("BAD_ID1", "BAD_ID2", contextInfo);
        assertEquals(0, infos.size());

        infos = getBundledOfferingService().getBundledOfferingsByTermAndCode("BAD_ID1", "BO_CODE1", contextInfo);
        assertEquals(0, infos.size());

        infos = getBundledOfferingService().getBundledOfferingsByTermAndCode("1", "BAD_ID2", contextInfo);
        assertEquals(0, infos.size());

        infos = getBundledOfferingService().getBundledOfferingsByTermAndCode("1", "BO_CODE2", contextInfo);
        assertEquals(0, infos.size());
	}
	
	/* Method Name: getBundledOfferingsByTermAndSubjectAreaOrg */
	@Test
	public void test_getBundledOfferingsByTermAndSubjectAreaOrg() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();
        List<BundledOfferingInfo> infos = getBundledOfferingService().getBundledOfferingsByTermAndSubjectAreaOrg("1","SUBJECT_AREA_ORG_ID1",contextInfo);
        assertEquals(1, infos.size());
        assertEquals(infos.get(0).getTermId(), "1");
        assertEquals(infos.get(0).getCourseBundleId(), "COURSE_BUNDLE1");
        assertEquals(infos.get(0).getName(), "NAME1");

        infos = getBundledOfferingService().getBundledOfferingsByTermAndSubjectAreaOrg("BAD_ID1", "BAD_ID2", contextInfo);
        assertEquals(0, infos.size());

        infos = getBundledOfferingService().getBundledOfferingsByTermAndSubjectAreaOrg("BAD_ID1", "SUBJECT_AREA_ORG_ID1", contextInfo);
        assertEquals(0, infos.size());

        infos = getBundledOfferingService().getBundledOfferingsByTermAndSubjectAreaOrg("1", "BAD_ID2", contextInfo);
        assertEquals(0, infos.size());

        infos = getBundledOfferingService().getBundledOfferingsByTermAndSubjectAreaOrg("1", "SUBJECT_AREA_ORG_ID2", contextInfo);
        assertEquals(0, infos.size());
	}
	
	/* Method Name: searchForBundledOfferingIds */
	@Test
	public void test_searchForBundledOfferingIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForBundledOfferings */
	@Test
	public void test_searchForBundledOfferings() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validateBundledOffering */
	@Test
	public void test_validateBundledOffering() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: changeBundledOfferingState */
	@Test
	public void test_changeBundledOfferingState() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();
        List<BundledOfferingInfo> infos = getBundledOfferingService().getBundledOfferingsByTermAndSubjectAreaOrg("1","SUBJECT_AREA_ORG_ID1",contextInfo);
        assertEquals(1, infos.size());
        BundledOfferingInfo original = infos.get(0);
        assertEquals(BundledOfferingDataLoader.BUNDLED_OFFERING_ACTIVE_STATE_KEY,original.getStateKey());
        original.setStateKey(BundledOfferingDataLoader.BUNDLED_OFFERING_INACTIVE_STATE_KEY);

        try {
            getBundledOfferingService().updateBundledOffering(original.getId(),original,contextInfo);
        } catch (Exception e){
           throw new OperationFailedException(e);
        }
        BundledOfferingInfo updated = getBundledOfferingService().getBundledOffering(original.getId(),contextInfo);
        assertEquals(BundledOfferingDataLoader.BUNDLED_OFFERING_INACTIVE_STATE_KEY,updated.getStateKey());
    }


    private void loadData() throws OperationFailedException {
        try {
            dataLoader.beforeTest();
        } catch (Exception e) {
            throw new OperationFailedException("failed to load data", e);
        }
    }
}


