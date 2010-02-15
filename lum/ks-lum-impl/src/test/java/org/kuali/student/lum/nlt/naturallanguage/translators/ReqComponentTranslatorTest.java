/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.nlt.naturallanguage.translators;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.nlt.naturallanguage.ContextRegistry;
import org.kuali.student.lum.nlt.naturallanguage.LuServiceMock;
import org.kuali.student.lum.nlt.naturallanguage.NaturalLanguageUtil;
import org.kuali.student.lum.nlt.naturallanguage.context.Context;
import org.kuali.student.lum.nlt.naturallanguage.translators.ReqComponentTranslator;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomReqComponentInfo;

public class ReqComponentTranslatorTest {
	
	private static LuServiceMock luService = new LuServiceMock();

	private static ReqComponentTranslator englishTranslator = new ReqComponentTranslator();
	private static ReqComponentTranslator germanTranslator = new ReqComponentTranslator();
	private CustomReqComponentInfo reqComponent;

    @BeforeClass
    public static void setUp() throws Exception {
    	luService.addAll(NaturalLanguageUtil.createData());
    	createTranslator();
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    private static void createTranslator() {
    	ContextRegistry<Context<CustomReqComponentInfo>> contextRegistry = NaturalLanguageUtil.getReqComponentContextRegistry(luService);

    	englishTranslator.setContextRegistry(contextRegistry);
		englishTranslator.setLanguage("en");

		germanTranslator.setContextRegistry(contextRegistry);
		germanTranslator.setLanguage("de");
    }

    private CustomReqComponentInfo createReqComponent(String nlUsageTypeKey, String reqComponentType) throws Exception {
    	this.reqComponent = NaturalLanguageUtil.createCustomReqComponent(nlUsageTypeKey, reqComponentType);
    	return this.reqComponent;
    }
    
    private List<ReqCompFieldInfo> createReqComponentFieldsForCluSet(String expectedValue, String operator) {
    	List<ReqCompFieldInfo> fieldList = NaturalLanguageUtil.createReqComponentFieldsForCluSet(expectedValue, operator, "CLUSET-NL-3");
    	this.reqComponent.setReqCompFields(fieldList);
    	return fieldList;
    }

    private void createReqComponentFieldsForClu(String expectedValue, String operator, String cluIds) {
		List<ReqCompFieldInfo> fieldList = NaturalLanguageUtil.createReqComponentFieldsForClu(expectedValue, operator, cluIds);
		this.reqComponent.setReqCompFields(fieldList);
    }

	@Test
	public void testInvalidReqComponentType() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
		CustomReqComponentInfo reqComponent = new CustomReqComponentInfo();
		ReqComponentTypeInfo reqCompType = new ReqComponentTypeInfo();
		reqCompType.setId("xxx.xxx.xxx");
		reqComponent.setRequiredComponentType(reqCompType);

		try {
			englishTranslator.translate(reqComponent, nlUsageTypeKey);
			Assert.fail("Translate method should have thrown a DoesNotExistException for requirement component type id xxx.xxx.xxx");
		} catch(DoesNotExistException e) {
			Assert.assertEquals("Requirement component context not found in registry for requirement component type id: xxx.xxx.xxx", e.getMessage());
		}
	}

	@Test
	public void testTranslate_OneOf_English() throws Exception {
		String nlUsageTypeKey = "KUALI.CATALOG";
    	createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to");
		
		String text = englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_OneOf_German() throws Exception {
		String nlUsageTypeKey = "KUALI.CATALOG";
    	createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to");
		
		String text = germanTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student muss abgeschlossen 1 von MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_OneOf_EnglishGerman() throws Exception {
		String nlUsageTypeKey = "KUALI.CATALOG";
		CustomReqComponentInfo reqComp = createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		List<ReqCompFieldInfo> fields = createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to");
		reqComp.setReqCompFields(fields);
		
		String text = englishTranslator.translate(reqComp, nlUsageTypeKey);
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221", text);

		englishTranslator.setLanguage("de");
		text = englishTranslator.translate(reqComp, nlUsageTypeKey);
		Assert.assertEquals("Student muss abgeschlossen 1 von MATH 152, MATH 221", text);

		englishTranslator.setLanguage("en");
		text = englishTranslator.translate(reqComp, nlUsageTypeKey);
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_OneOf_1Clu() throws Exception {
		String nlUsageTypeKey = "KUALI.CATALOG";
    	createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.all");
		createReqComponentFieldsForClu("1", "greater_than_or_equal_to", "CLU-NL-1");
		
		String text = englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed all of MATH 152", text);
	}

	@Test
	public void testTranslate_OneOf_2Clus() throws Exception {
		String nlUsageTypeKey = "KUALI.CATALOG";
		String clus = "CLU-NL-1, CLU-NL-2";
    	createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		createReqComponentFieldsForClu("1", "greater_than_or_equal_to", clus);
		
		String text = englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_AllOf() throws Exception {
		String nlUsageTypeKey = "KUALI.CATALOG";
    	createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.all");
		createReqComponentFieldsForCluSet("2", "equal_to");
		
		String text = englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed all of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_NoneOf() throws Exception {
		String nlUsageTypeKey = "KUALI.CATALOG";
    	createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.none");
		createReqComponentFieldsForCluSet("0", "less_than_or_equal_to");
		
		String text = englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed none of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_InvalidReqComponentId() throws Exception {
		try {
			englishTranslator.translate(null, "KUALI.CATALOG");
			Assert.fail("Requirement component translation should have failed since requirement component is null");
		} catch (DoesNotExistException e) {
			Assert.assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testTranslate_InvalidNlUsageTypeKey() throws Exception {
    	createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.none");
		createReqComponentFieldsForCluSet("0", "less_than_or_equal_to");

		try {
			englishTranslator.translate(this.reqComponent, "KUALI.xxx.CATALOG");
			Assert.fail("Requirement component translation should have failed since 'KUALI.xxx.CATALOG' is not a valid nlUsageTypeKey");
		} catch (DoesNotExistException e) {
			Assert.assertNotNull(e.getMessage());
		}
	}
	
	@Test
	public void testTranslate1_1Of2() throws Exception {
		String nlUsageTypeKey = "KUALI.CATALOG";
		String cluIds = "CLU-NL-1, CLU-NL-2";
		createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.1of2");
		createReqComponentFieldsForClu("1", "greater_than_or_equal_to", cluIds);
		
		String text = englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed MATH 152 or MATH 221", text);
	}

	@Test
	public void testTranslate_TotalCredits() throws Exception {
		String nlUsageTypeKey = "KUALI.CATALOG";
		String cluIds = "CLU-NL-1, CLU-NL-2";
		createReqComponent("KUALI.CATALOG", "kuali.reqCompType.grdCondCourseList");
		List<ReqCompFieldInfo> fieldList = new ArrayList<ReqCompFieldInfo>();
		ReqCompFieldInfo field1 = new ReqCompFieldInfo();
		field1.setId("reqCompFieldType.clu");
		field1.setValue(cluIds);
		fieldList.add(field1);
		ReqCompFieldInfo field2 = new ReqCompFieldInfo();
		field2.setId("reqCompFieldType.totalCredits");
		field2.setValue("6");
		fieldList.add(field2);

		this.reqComponent.setReqCompFields(fieldList);
		
		String text = englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Students must take 6 credits from MATH 152, MATH 221", text);
	}


	@Test
	public void testTranslate_GPA() throws Exception {
		String nlUsageTypeKey = "KUALI.CATALOG";
		createReqComponent("KUALI.CATALOG", "kuali.reqCompType.gradecheck");
		List<ReqCompFieldInfo> fieldList = new ArrayList<ReqCompFieldInfo>();
		ReqCompFieldInfo field1 = new ReqCompFieldInfo();
		field1.setId("reqCompFieldType.gpa");
		field1.setValue("70.0%");
		fieldList.add(field1);

		this.reqComponent.setReqCompFields(fieldList);
		
		String text = englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student needs a minimum GPA of 70.0%", text);
	}
}
