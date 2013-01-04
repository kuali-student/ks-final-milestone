/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r1.core.statement.naturallanguage.translators;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.entity.ReqComponent;
import org.kuali.student.r1.core.statement.entity.ReqComponentField;
import org.kuali.student.r1.core.statement.entity.ReqComponentType;
import org.kuali.student.r1.core.statement.naturallanguage.ContextRegistry;
import org.kuali.student.r1.core.statement.naturallanguage.NaturalLanguageUtil;
import org.kuali.student.r1.core.statement.naturallanguage.Context;
import org.kuali.student.r1.core.statement.naturallanguage.translators.ReqComponentTranslator;
import org.kuali.student.r1.core.statement.naturallanguage.ReqComponentFieldTypes;

public class ReqComponentTranslatorTest {
	
	private static ReqComponentTranslator englishTranslator = new ReqComponentTranslator();
	private static ReqComponentTranslator germanTranslator = new ReqComponentTranslator();
	private ReqComponent reqComponent;

    @BeforeClass
    public static void setUp() throws Exception {
    	createTranslator();
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    private static void createTranslator() {
    	ContextRegistry<Context<ReqComponentInfo>> contextRegistry = NaturalLanguageUtil.getReqComponentContextRegistry();

    	englishTranslator.setContextRegistry(contextRegistry);
		englishTranslator.setLanguage("en");

		germanTranslator.setContextRegistry(contextRegistry);
		germanTranslator.setLanguage("de");
    }

    private ReqComponent createReqComponent(String nlUsageTypeKey, String reqComponentType) throws Exception {
    	this.reqComponent = NaturalLanguageUtil.createReqComponent(nlUsageTypeKey, reqComponentType);
    	return this.reqComponent;
    }
    
    private List<ReqComponentField> createReqComponentFieldsForCluSet(String expectedValue, String operator) {
    	List<ReqComponentField> fieldList = NaturalLanguageUtil.createReqComponentFieldsForCluSet(expectedValue, operator, "CLUSET-NL-3");
    	this.reqComponent.setReqComponentFields(fieldList);
    	return fieldList;
    }

    private void createReqComponentFieldsForClu(String expectedValue, String operator, String cluIds) {
		List<ReqComponentField> fieldList = NaturalLanguageUtil.createReqComponentFieldsForClu(expectedValue, operator, cluIds);
		this.reqComponent.setReqComponentFields(fieldList);
    }

	@Test
	public void testInvalidReqComponentType() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.RULE";
		ReqComponent reqComponent = new ReqComponent();
		ReqComponentType reqCompType = new ReqComponentType();
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
		String nlUsageTypeKey = "KUALI.RULE";
    	createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to");
		
		String text = englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_OneOf_German() throws Exception {
		String nlUsageTypeKey = "KUALI.RULE";
    	createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to");
		
		String text = germanTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student muss abgeschlossen 1 von MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_OneOf_EnglishGerman() throws Exception {
		String nlUsageTypeKey = "KUALI.RULE";
		ReqComponent reqComp = createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		List<ReqComponentField> fields = createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to");
		reqComp.setReqComponentFields(fields);
		
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
	public void testTranslate_AllOf_1Clu() throws Exception {
		String nlUsageTypeKey = "KUALI.RULE";
    	createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.all");
		createReqComponentFieldsForClu("1", "greater_than_or_equal_to", "CLU-NL-1");
		
		String text = englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed all of MATH 152", text);
	}

	@Test
	public void testTranslate_OneOf_2Clus() throws Exception {
		String nlUsageTypeKey = "KUALI.RULE";
		//Comma separated lists of clu ids no longer supported
		//String clus = "CLU-NL-1, CLU-NL-2";
		String clus = "CLU-NL-1";
    	createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		createReqComponentFieldsForClu("1", "greater_than_or_equal_to", clus);
		
		String text = englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed 1 of MATH 152", text);
	}

	@Test
	public void testTranslate_AllOf() throws Exception {
		String nlUsageTypeKey = "KUALI.RULE";
    	createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.all");
		createReqComponentFieldsForCluSet("2", "equal_to");
		
		String text = englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed all of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_NoneOf() throws Exception {
		String nlUsageTypeKey = "KUALI.RULE";
    	createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.none");
		createReqComponentFieldsForCluSet("0", "less_than_or_equal_to");
		
		String text = englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed none of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_InvalidReqComponentId() throws Exception {
		try {
			englishTranslator.translate(null, "KUALI.RULE");
			Assert.fail("Requirement component translation should have failed since requirement component is null");
		} catch (DoesNotExistException e) {
			Assert.assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testTranslate_InvalidNlUsageTypeKey() throws Exception {
    	createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.none");
		createReqComponentFieldsForCluSet("0", "less_than_or_equal_to");

		try {
			englishTranslator.translate(this.reqComponent, "KUALI.xxx.CATALOG");
			Assert.fail("Requirement component translation should have failed since 'KUALI.xxx.CATALOG' is not a valid nlUsageTypeKey");
		} catch (DoesNotExistException e) {
			Assert.assertNotNull(e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void testTranslate1_1Of2() throws Exception {
		String nlUsageTypeKey = "KUALI.RULE";
		//Comma separated lists of clu ids no longer supported
		String cluIds = "CLU-NL-1, CLU-NL-2";
		createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.1of2");
		createReqComponentFieldsForClu("1", "greater_than_or_equal_to", cluIds);
		
		String text = englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed MATH 152 or MATH 221", text);
	}

	@Test
	public void testTranslate_TotalCredits() throws Exception {
		String nlUsageTypeKey = "KUALI.RULE";
		//Comma separated lists of clu ids no longer supported
		//String cluIds = "CLU-NL-1, CLU-NL-2";
		String cluIds = "CLU-NL-1";
		createReqComponent("KUALI.RULE", "kuali.reqComponent.type.grdCondCourseList");
		List<ReqComponentField> fieldList = new ArrayList<ReqComponentField>();
		ReqComponentField field1 = new ReqComponentField();
		field1.setType(ReqComponentFieldTypes.CLU_KEY.getType());
		field1.setValue(cluIds);
		fieldList.add(field1);
		ReqComponentField field2 = new ReqComponentField();
		field2.setType(ReqComponentFieldTypes.TOTAL_CREDIT_KEY.getType());
		field2.setValue("6");
		fieldList.add(field2);

		this.reqComponent.setReqComponentFields(fieldList);
		
		String text = englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		//Assert.assertEquals("Students must take 6 credits from MATH 152, MATH 221", text);
		Assert.assertEquals("Students must take 6 credits from MATH 152", text);
	}


	@Test
	public void testTranslate_GPA() throws Exception {
		String nlUsageTypeKey = "KUALI.RULE";
		createReqComponent("KUALI.RULE", "kuali.reqComponent.type.gradecheck");
		List<ReqComponentField> fieldList = new ArrayList<ReqComponentField>();
		ReqComponentField field1 = new ReqComponentField();
		field1.setType(ReqComponentFieldTypes.GPA_KEY.getType());
		field1.setValue("70.0%");
		fieldList.add(field1);

		this.reqComponent.setReqComponentFields(fieldList);
		
		String text = englishTranslator.translate(this.reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student needs a minimum GPA of 70.0%", text);
	}
}
