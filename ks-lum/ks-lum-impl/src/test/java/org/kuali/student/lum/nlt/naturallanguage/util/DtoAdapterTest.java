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
package org.kuali.student.lum.nlt.naturallanguage.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.LuStatementTypeInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeNLTemplateInfo;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;
import org.kuali.student.lum.nlt.dto.LuNlStatementInfo;
import org.kuali.student.lum.nlt.naturallanguage.LuServiceMock;
import org.kuali.student.lum.nlt.naturallanguage.NaturalLanguageUtil;

public class DtoAdapterTest {

	private static LuServiceMock luService = new LuServiceMock();
	private static DtoAdapter adapter = new DtoAdapter();
	
    @BeforeClass
    public static void setUp() throws Exception {
    	createData();
    	adapter.setLuService(luService);
    }
    
    private static void createData() throws Exception {
    	Map<String,Object> map = NaturalLanguageUtil.createData();
    	luService.addAll(map);

		LuStatementInfo stmtInfo1 = NaturalLanguageUtil.createLuStatementInfo("STMT-101", StatementOperatorTypeKey.AND);
		LuStatementInfo stmtInfo2 = NaturalLanguageUtil.createLuStatementInfo("STMT-102", StatementOperatorTypeKey.OR);
		LuStatementInfo stmtInfo3 = NaturalLanguageUtil.createLuStatementInfo("STMT-103", StatementOperatorTypeKey.AND);
		LuStatementInfo stmtInfo4 = NaturalLanguageUtil.createLuStatementInfo("STMT-104", null);
		LuStatementInfo stmtInfo5 = NaturalLanguageUtil.createLuStatementInfo("STMT-105", StatementOperatorTypeKey.OR);

		stmtInfo1.setLuStatementIds(Arrays.asList(new String[] {"STMT-102", "STMT-105"}));
		luService.addObject(stmtInfo1.getId(), stmtInfo1);
		stmtInfo2.setParentId("STMT-101");
		stmtInfo2.setLuStatementIds(Arrays.asList(new String[] {"STMT-103", "STMT-104"}));
		luService.addObject(stmtInfo2.getId(), stmtInfo2);
		stmtInfo3.setParentId("STMT-102");
		stmtInfo3.setReqComponentIds(Arrays.asList(new String[] {"req-1", "req-2"}));
		luService.addObject(stmtInfo3.getId(), stmtInfo3);
		stmtInfo4.setParentId("STMT-102");
		stmtInfo4.setReqComponentIds(Arrays.asList(new String[] {"req-3"}));
		luService.addObject(stmtInfo4.getId(), stmtInfo4);
		stmtInfo5.setParentId("STMT-101");
		stmtInfo5.setReqComponentIds(Arrays.asList(new String[] {"req-1", "req-4"}));
		luService.addObject(stmtInfo5.getId(), stmtInfo5);

		// Lu statements to test cyclic dependency
		LuStatementInfo stmtInfo6 = NaturalLanguageUtil.createLuStatementInfo("STMT-CYCLE-1", StatementOperatorTypeKey.AND);
		stmtInfo6.setLuStatementIds(Arrays.asList(new String[] {"STMT-CYCLE-2"}));
		luService.addObject(stmtInfo6.getId(), stmtInfo6);
		LuStatementInfo stmtInfo7 = NaturalLanguageUtil.createLuStatementInfo("STMT-CYCLE-2", StatementOperatorTypeKey.AND);
		stmtInfo7.setParentId("STMT-CYCLE-1");
		stmtInfo7.setLuStatementIds(Arrays.asList(new String[] {"STMT-CYCLE-3"}));
		luService.addObject(stmtInfo7.getId(), stmtInfo7);
		LuStatementInfo stmtInfo8 = NaturalLanguageUtil.createLuStatementInfo("STMT-CYCLE-3", StatementOperatorTypeKey.AND);
		stmtInfo8.setParentId("STMT-CYCLE-2");
		stmtInfo8.setLuStatementIds(Arrays.asList(new String[] {"STMT-CYCLE-1"}));
		luService.addObject(stmtInfo8.getId(), stmtInfo8);
		
		LuStatementTypeInfo statementTypeInfo = new LuStatementTypeInfo();
		statementTypeInfo.setId("kuali.luStatementType.prereqAcademicReadiness");
		statementTypeInfo.setHeaders(NaturalLanguageUtil.createHeaders());
		luService.addObject(statementTypeInfo.getId(), statementTypeInfo);
    	
    	String reqCompType = "kuali.reqCompType.courseList.nof";
    	ReqComponentTypeInfo type = NaturalLanguageUtil.createReqComponentType(reqCompType);
		luService.addObject(type.getId(), type);
		
		ReqComponentInfo req1 = createReqComponentInfo("req-1", "reqCompFieldType.requiredCount", "1");
		luService.addObject(req1.getId(), req1);

		ReqComponentInfo req2 = createReqComponentInfo("req-2", "reqCompFieldType.requiredCount", "2");
		luService.addObject(req2.getId(), req2);
		
		ReqComponentInfo req3 = createReqComponentInfo("req-3", "reqCompFieldType.requiredCount", "3");
		luService.addObject(req3.getId(), req3);
		
		ReqComponentInfo req4 = createReqComponentInfo("req-4", "reqCompFieldType.requiredCount", "4");
		luService.addObject(req4.getId(), req4);
		
		ReqComponentTypeInfo reqCompTypeInfo = NaturalLanguageUtil.createReqComponentType(reqCompType);
		luService.addObject(reqCompType, reqCompTypeInfo);
    }
    
	private static ReqComponentInfo createReqComponentInfo(String reqId, String fieldId, String value) throws Exception {
		ReqComponentInfo req = NaturalLanguageUtil.createReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		req.setId(reqId);
		List<ReqCompFieldInfo> fieldList = new ArrayList<ReqCompFieldInfo>();
		ReqCompFieldInfo field1 = new ReqCompFieldInfo();
		field1.setId(fieldId);
		field1.setValue(value);
		fieldList.add(field1);
		req.setReqCompFields(fieldList);
		return req;
	}
	
	private void assertReqComponent(ReqComponentInfo req, CustomReqComponentInfo customReq) {
		Assert.assertNotNull(customReq);
		Assert.assertEquals(req.getId(), customReq.getId());
		// Test fields
		List<ReqCompFieldInfo> reqFields = req.getReqCompFields();
		List<ReqCompFieldInfo> customFields = customReq.getReqCompFields();
		Assert.assertNotNull(customReq.getReqCompFields());
		Assert.assertEquals(reqFields.size(), customFields.size());
		Assert.assertEquals(1, reqFields.size());
		Assert.assertEquals(reqFields.get(0).getId(), customFields.get(0).getId());
		Assert.assertEquals(reqFields.get(0).getValue(), customFields.get(0).getValue());
		// Test type
		ReqComponentTypeInfo reqType = req.getRequiredComponentType();
		ReqComponentTypeInfo customType = customReq.getRequiredComponentType();
		Assert.assertNotNull(customType);
		Assert.assertEquals(reqType.getId(), customType.getId());
		// Test templates
		List<ReqComponentTypeNLTemplateInfo> templates = reqType.getNlUsageTemplates();
		List<ReqComponentTypeNLTemplateInfo> customTemplates = customType.getNlUsageTemplates();
		Assert.assertNotNull(customReq.getRequiredComponentType().getNlUsageTemplates());
		Assert.assertEquals(templates.size(), customTemplates.size());
		Assert.assertEquals(2, customReq.getRequiredComponentType().getNlUsageTemplates().size());
		Assert.assertEquals(templates.get(0).getLanguage(), customTemplates.get(0).getLanguage());
		Assert.assertEquals(templates.get(1).getLanguage(), customTemplates.get(1).getLanguage());
		Assert.assertEquals(templates.get(0).getNlUsageTypeKey(), customTemplates.get(0).getNlUsageTypeKey());
		Assert.assertEquals(templates.get(1).getNlUsageTypeKey(), customTemplates.get(1).getNlUsageTypeKey());
		Assert.assertEquals(templates.get(0).getTemplate(), customTemplates.get(0).getTemplate());
		Assert.assertEquals(templates.get(1).getTemplate(), customTemplates.get(1).getTemplate());
	}
	
	private void assertLuStatementInfo(boolean isRoot, LuStatementInfo stmtInfo, CustomLuStatementInfo customInfo) {
		Assert.assertEquals(stmtInfo.getId(), customInfo.getId());

		Assert.assertNotNull(customInfo.getLuStatementType());
		Assert.assertEquals(stmtInfo.getId(), customInfo.getId());
		Assert.assertEquals(stmtInfo.getName(), customInfo.getName());
		Assert.assertEquals(stmtInfo.getOperator(), customInfo.getOperator());
		if(isRoot) {
			Assert.assertEquals(null, customInfo.getParent());
		} else {
			Assert.assertEquals(stmtInfo.getParentId(), customInfo.getParent().getId());
		}
		Assert.assertEquals(stmtInfo.getType(), customInfo.getLuStatementType().getId());
		Assert.assertNotNull(customInfo.getLuStatementType().getHeaders());
		Assert.assertEquals(stmtInfo.getLuStatementType().getHeaders().size(), customInfo.getLuStatementType().getHeaders().size());
		Assert.assertEquals(stmtInfo.getLuStatementType().getHeaders().get(0).getLanguage(), customInfo.getLuStatementType().getHeaders().get(0).getLanguage());
		Assert.assertEquals(stmtInfo.getLuStatementType().getHeaders().get(1).getLanguage(), customInfo.getLuStatementType().getHeaders().get(1).getLanguage());
	}

	private CustomLuStatementInfo getChildById(List<CustomLuStatementInfo> children, String id) {
		for(CustomLuStatementInfo info : children) {
			if(id.equals(info.getId())) {
				return info;
			}
		}
		return null;
	}
	
	private CustomLuStatementInfo getChildByName(List<CustomLuStatementInfo> children, String name) {
		for(CustomLuStatementInfo info : children) {
			if(name.equals(info.getName())) {
				return info;
			}
		}
		return null;
	}
	
	private CustomReqComponentInfo getReqComponentById(List<CustomReqComponentInfo> children, String id) {
		for(CustomReqComponentInfo info : children) {
			if(id.equals(info.getId())) {
				return info;
			}
		}
		return null;
	}
	
	@Test
	public void testToCustomLuStatementInfoFromLuStatementInfo_StatementTypeString() throws Exception {
		LuStatementInfo stmtInfo = new LuStatementInfo();
		stmtInfo.setId("STMT-101");
		stmtInfo.setType("kuali.luStatementType.prereqAcademicReadiness");
		stmtInfo.setReqComponentIds(Arrays.asList(new String[] {"req-1", "req-2"}));

		CustomLuStatementInfo customInfo = adapter.toCustomLuStatementInfo(stmtInfo);
		
		Assert.assertEquals("STMT-101", customInfo.getId());

		Assert.assertNotNull(customInfo.getLuStatementType());
		Assert.assertEquals(stmtInfo.getType(), customInfo.getLuStatementType().getId());
		Assert.assertNotNull(customInfo.getLuStatementType().getHeaders());
		Assert.assertEquals(2, customInfo.getLuStatementType().getHeaders().size());
		Assert.assertEquals("en", customInfo.getLuStatementType().getHeaders().get(0).getLanguage());
		Assert.assertEquals("de", customInfo.getLuStatementType().getHeaders().get(1).getLanguage());
	}
	
	@Test
	public void testToCustomLuStatementInfoFromLuStatementInfo() throws Exception {
		LuStatementInfo stmtInfo1 = luService.getLuStatement("STMT-101");
		LuStatementInfo stmtInfo2 = luService.getLuStatement("STMT-102");
		LuStatementInfo stmtInfo3 = luService.getLuStatement("STMT-103");
		LuStatementInfo stmtInfo4 = luService.getLuStatement("STMT-104");
		LuStatementInfo stmtInfo5 = luService.getLuStatement("STMT-105");

		ReqComponentInfo req1 = luService.getReqComponent("req-1");
		ReqComponentInfo req2 = luService.getReqComponent("req-2");
		ReqComponentInfo req3 = luService.getReqComponent("req-3");
		ReqComponentInfo req11 = luService.getReqComponent("req-1");
		ReqComponentInfo req4 = luService.getReqComponent("req-4");
		
		CustomLuStatementInfo customInfo = adapter.toCustomLuStatementInfo(stmtInfo1);

		assertLuStatementInfo(true, stmtInfo1, customInfo);
		Assert.assertEquals(2, customInfo.getChildren().size());
		assertLuStatementInfo(false, stmtInfo2, getChildById(customInfo.getChildren(),"STMT-102"));
		assertLuStatementInfo(false, stmtInfo5, getChildById(customInfo.getChildren(),"STMT-105"));
		
		List<CustomReqComponentInfo> reqList105 = getChildById(customInfo.getChildren(),"STMT-105").getRequiredComponents();
		Assert.assertEquals(2, reqList105.size());
		assertReqComponent(req11, reqList105.get(0));
		assertReqComponent(req4, reqList105.get(1));
		
		List<CustomLuStatementInfo> children102 = getChildById(customInfo.getChildren(), "STMT-102").getChildren();
		Assert.assertEquals(2, children102.size());

		assertLuStatementInfo(false, stmtInfo3, getChildById(children102, "STMT-103"));
		List<CustomReqComponentInfo> reqList103 = getChildById(children102, "STMT-103").getRequiredComponents();
		Assert.assertEquals(2, reqList103.size());
		assertReqComponent(req1, reqList103.get(0));
		assertReqComponent(req2, reqList103.get(1));

		assertLuStatementInfo(false, stmtInfo4, getChildById(children102, "STMT-104"));
		List<CustomReqComponentInfo> reqList104 = getChildById(children102, "STMT-104").getRequiredComponents();
		Assert.assertEquals(1, reqList104.size());
		assertReqComponent(req3, reqList104.get(0));
	}

	@Test
	public void testToCustomLuStatementInfoFromLuStatementInfo_CyclicDependency() throws Exception {
		LuStatementInfo stmtInfo1 = luService.getLuStatement("STMT-CYCLE-1");

		try {
			adapter.toCustomLuStatementInfo(stmtInfo1);
		} catch(OperationFailedException e) {
			Assert.assertNotNull(e.getMessage());
		}
	}
	
	@Test
	public void testToCustomReqComponentInfoFromReqComponentInfoInfo() throws Exception {
		ReqComponentInfo req = createReqComponentInfo("req", "reqCompFieldType.requiredCount", "1");
		CustomReqComponentInfo customReq = adapter.toCustomReqComponentInfo(req);
		assertReqComponent(req, customReq);
	}

	@Test
	public void testToCustomReqComponentsInfoFromReqComponentInfoInfos() throws Exception {
		ReqComponentInfo req1 = createReqComponentInfo("req1", "reqCompFieldType.requiredCount", "1");
		ReqComponentInfo req2 = createReqComponentInfo("req2", "reqCompFieldType.operator", "AND");
		ReqComponentInfo req3 = createReqComponentInfo("req3", "reqCompFieldType.cluSet", "clu1");
		List<ReqComponentInfo> list = new ArrayList<ReqComponentInfo>();
		list.add(req1);
		list.add(req2);
		list.add(req3);

		List<CustomReqComponentInfo> customReq = adapter.toCustomReqComponentInfos(list);
		assertReqComponent(req1, customReq.get(0));
		assertReqComponent(req2, customReq.get(1));
		assertReqComponent(req3, customReq.get(2));
	}

	private void assertReqComponentTypeInfo(ReqComponentTypeInfo type, String typeId, int templateSize) {
		Assert.assertEquals(typeId, type.getId());
		Assert.assertEquals(templateSize, type.getNlUsageTemplates().size());
	}

	private void assertReqCompFieldInfo(ReqCompFieldInfo type, String typeId, String value) {
		Assert.assertEquals(typeId, type.getId());
		Assert.assertEquals(value, type.getValue());
	}
	
	@Test
	public void testToCustomLuStatementInfoFromLuNlStatementInfo1() throws Exception {
		//Rule: ((R1 OR R2) AND R3) OR R4
		LuNlStatementInfo stmt = NaturalLanguageUtil.getComplexLuNlStatementInfo1();
		
		CustomLuStatementInfo s1 = adapter.toCustomLuStatementInfo(stmt);
		
		Assert.assertEquals("s1", s1.getName());
		Assert.assertEquals("kuali.luStatementType.prereqAcademicReadiness", s1.getLuStatementType().getId());
		Assert.assertEquals(StatementOperatorTypeKey.OR, s1.getOperator());
		Assert.assertEquals(2, s1.getChildren().size());

		CustomLuStatementInfo s2 = getChildByName(s1.getChildren(), "s2");
		Assert.assertEquals("kuali.luStatementType.prereqAcademicReadiness", s2.getLuStatementType().getId());
		Assert.assertEquals(StatementOperatorTypeKey.AND, s2.getOperator());
		Assert.assertNotNull(s2);
		Assert.assertNotNull(s2.getChildren());
		Assert.assertEquals(2, s2.getChildren().size());
		
		CustomLuStatementInfo s3 = getChildByName(s2.getChildren(), "s3");
		Assert.assertEquals("kuali.luStatementType.prereqAcademicReadiness", s3.getLuStatementType().getId());
		Assert.assertEquals(StatementOperatorTypeKey.OR, s3.getOperator());
		Assert.assertNotNull(s3);
		Assert.assertNull(s3.getChildren());
		Assert.assertNotNull(s3.getRequiredComponents());
		Assert.assertEquals(2, s3.getRequiredComponents().size());

		CustomReqComponentInfo req1 = getReqComponentById(s3.getRequiredComponents(), "req-1");
		Assert.assertEquals("req-1", req1.getId());
		assertReqComponentTypeInfo(req1.getRequiredComponentType(), "kuali.reqCompType.courseList.none", 2);
		
		Assert.assertEquals(3, req1.getReqCompFields().size());
		assertReqCompFieldInfo(req1.getReqCompFields().get(0), "reqCompFieldType.requiredCount", "0");
		assertReqCompFieldInfo(req1.getReqCompFields().get(1), "reqCompFieldType.operator", "greater_than_or_equal_to");
		assertReqCompFieldInfo(req1.getReqCompFields().get(2), "reqCompFieldType.cluSet", "CLUSET-NL-1");
		
		CustomReqComponentInfo req2 = getReqComponentById(s3.getRequiredComponents(), "req-2");
		Assert.assertEquals("req-2", req2.getId());
		assertReqComponentTypeInfo(req2.getRequiredComponentType(), "kuali.reqCompType.courseList.all", 2);
		
		Assert.assertEquals(3, req2.getReqCompFields().size());
		assertReqCompFieldInfo(req2.getReqCompFields().get(0), "reqCompFieldType.requiredCount", "3");
		assertReqCompFieldInfo(req2.getReqCompFields().get(1), "reqCompFieldType.operator", "greater_than_or_equal_to");
		assertReqCompFieldInfo(req2.getReqCompFields().get(2), "reqCompFieldType.clu", "CLU-NL-1,CLU-NL-2,CLU-NL-3");
		
		CustomLuStatementInfo s4 = getChildByName(s2.getChildren(), "s4");
		Assert.assertEquals("kuali.luStatementType.prereqAcademicReadiness", s4.getLuStatementType().getId());
		Assert.assertEquals(StatementOperatorTypeKey.AND, s4.getOperator());
		Assert.assertNotNull(s4);
		Assert.assertNull(s4.getChildren());
		Assert.assertNotNull(s4.getRequiredComponents());
		Assert.assertEquals(1, s4.getRequiredComponents().size());

		CustomReqComponentInfo req3 = getReqComponentById(s4.getRequiredComponents(), "req-3");
		Assert.assertEquals("req-3", req3.getId());
		assertReqComponentTypeInfo(req3.getRequiredComponentType(), "kuali.reqCompType.courseList.nof", 2);

		Assert.assertEquals(3, req3.getReqCompFields().size());
		assertReqCompFieldInfo(req3.getReqCompFields().get(0), "reqCompFieldType.requiredCount", "2");
		assertReqCompFieldInfo(req3.getReqCompFields().get(1), "reqCompFieldType.operator", "greater_than_or_equal_to");
		assertReqCompFieldInfo(req3.getReqCompFields().get(2), "reqCompFieldType.cluSet", "CLUSET-NL-2");
		
		CustomLuStatementInfo s5 = getChildByName(s1.getChildren(), "s5");
		Assert.assertEquals("kuali.luStatementType.prereqAcademicReadiness", s5.getLuStatementType().getId());
		Assert.assertEquals(StatementOperatorTypeKey.AND, s5.getOperator());
		Assert.assertNotNull(s5);
		Assert.assertNull(s5.getChildren());
		Assert.assertNotNull(s5.getRequiredComponents());
		Assert.assertEquals(1, s5.getRequiredComponents().size());
		
		CustomReqComponentInfo req4 = getReqComponentById(s5.getRequiredComponents(), "req-4");
		Assert.assertEquals("req-4", req4.getId());
		assertReqComponentTypeInfo(req4.getRequiredComponentType(), "kuali.reqCompType.courseList.all", 2);
		
		Assert.assertEquals(3, req4.getReqCompFields().size());
		assertReqCompFieldInfo(req4.getReqCompFields().get(0), "reqCompFieldType.requiredCount", "4");
		assertReqCompFieldInfo(req4.getReqCompFields().get(1), "reqCompFieldType.operator", "greater_than_or_equal_to");
		assertReqCompFieldInfo(req4.getReqCompFields().get(2), "reqCompFieldType.cluSet", "CLUSET-NL-3");
	}

	@Test
	public void testToCustomLuStatementInfoFromLuNlStatementInfo2() throws Exception {
		//Rule: ((R1 OR R2) AND (R3 OR R4)) OR R5 OR (R6 AND (R7 OR R8))
		LuNlStatementInfo stmt = NaturalLanguageUtil.getComplexLuNlStatementInfo2();
		
		CustomLuStatementInfo s1 = adapter.toCustomLuStatementInfo(stmt);

		Assert.assertEquals("s1", s1.getName());
		Assert.assertEquals("kuali.luStatementType.prereqAcademicReadiness", s1.getLuStatementType().getId());
		Assert.assertEquals(StatementOperatorTypeKey.OR, s1.getOperator());
		Assert.assertEquals(3, s1.getChildren().size());

		CustomLuStatementInfo s2 = getChildByName(s1.getChildren(), "s2");
		Assert.assertEquals(StatementOperatorTypeKey.AND, s2.getOperator());
		Assert.assertEquals(2, s2.getChildren().size());

		CustomLuStatementInfo s3 = getChildByName(s2.getChildren(), "s3");
		Assert.assertEquals(StatementOperatorTypeKey.OR, s3.getOperator());
		Assert.assertNull(s3.getChildren());
		Assert.assertEquals(2, s3.getRequiredComponents().size());

		CustomReqComponentInfo req1 = getReqComponentById(s3.getRequiredComponents(), "req-1");
		Assert.assertEquals("req-1", req1.getId());
		Assert.assertEquals(3, req1.getReqCompFields().size());
		CustomReqComponentInfo req2 = getReqComponentById(s3.getRequiredComponents(), "req-2");
		Assert.assertEquals("req-2", req2.getId());
		Assert.assertEquals(3, req2.getReqCompFields().size());

		CustomLuStatementInfo s4 = getChildByName(s2.getChildren(), "s4");
		Assert.assertEquals(StatementOperatorTypeKey.OR, s4.getOperator());
		Assert.assertNull(s4.getChildren());
		Assert.assertEquals(2, s4.getRequiredComponents().size());

		CustomReqComponentInfo req3 = getReqComponentById(s4.getRequiredComponents(), "req-3");
		Assert.assertEquals("req-3", req3.getId());
		Assert.assertEquals(3, req3.getReqCompFields().size());
		CustomReqComponentInfo req4 = getReqComponentById(s4.getRequiredComponents(), "req-4");
		Assert.assertEquals("req-4", req4.getId());
		Assert.assertEquals(3, req4.getReqCompFields().size());

		CustomLuStatementInfo s5 = getChildByName(s1.getChildren(), "s5");
		Assert.assertEquals(StatementOperatorTypeKey.AND, s5.getOperator());
		Assert.assertNull(s5.getChildren());
		Assert.assertEquals(1, s5.getRequiredComponents().size());

		CustomReqComponentInfo req5 = getReqComponentById(s5.getRequiredComponents(), "req-5");
		Assert.assertEquals("req-5", req5.getId());
		Assert.assertEquals(3, req5.getReqCompFields().size());

		CustomLuStatementInfo s6 = getChildByName(s1.getChildren(), "s6");
		Assert.assertEquals(StatementOperatorTypeKey.AND, s6.getOperator());
		Assert.assertEquals(2, s6.getChildren().size());

		CustomLuStatementInfo s7 = getChildByName(s6.getChildren(), "s7");
		Assert.assertEquals(StatementOperatorTypeKey.AND, s7.getOperator());
		Assert.assertNull(s7.getChildren());
		Assert.assertEquals(1, s7.getRequiredComponents().size());

		CustomReqComponentInfo req6 = getReqComponentById(s7.getRequiredComponents(), "req-6");
		Assert.assertEquals("req-6", req6.getId());
		Assert.assertEquals(3, req6.getReqCompFields().size());

		CustomLuStatementInfo s8 = getChildByName(s6.getChildren(), "s8");
		Assert.assertEquals(StatementOperatorTypeKey.OR, s8.getOperator());
		Assert.assertNull(s8.getChildren());
		Assert.assertEquals(2, s8.getRequiredComponents().size());

		CustomReqComponentInfo req7 = getReqComponentById(s8.getRequiredComponents(), "req-7");
		Assert.assertEquals("req-7", req7.getId());
		Assert.assertEquals(3, req7.getReqCompFields().size());
		CustomReqComponentInfo req8 = getReqComponentById(s8.getRequiredComponents(), "req-8");
		Assert.assertEquals("req-8", req8.getId());
		Assert.assertEquals(3, req8.getReqCompFields().size());
	}
}
