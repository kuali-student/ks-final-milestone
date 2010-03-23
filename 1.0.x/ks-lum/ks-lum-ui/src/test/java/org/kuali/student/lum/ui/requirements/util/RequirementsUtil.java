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
package org.kuali.student.lum.ui.requirements.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.kuali.student.lum.lu.service.LuService;
//import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
//import org.kuali.student.lum.lu.dto.CluInfo;
//import org.kuali.student.lum.lu.dto.CluSetInfo;
//import org.kuali.student.lum.lu.dto.LuStatementTypeHeaderTemplateInfo;
//import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
//import org.kuali.student.lum.lu.dto.ReqComponentInfo;
//import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
//import org.kuali.student.lum.lu.dto.ReqComponentTypeNLTemplateInfo;

public class RequirementsUtil {

/*	private static DtoAdapter dtoAdapter = new DtoAdapter();
	
    public static Map<String,Object> createData() {
    	Map<String,Object> map = new HashMap<String,Object>();
    	
    	CluInfo clu1 = new CluInfo();
    	clu1.setId("CLU-NL-1");
    	clu1.setType("luType.nl.course");
    	CluIdentifierInfo cluId1 = new CluIdentifierInfo();
    	cluId1.setId("IDENT-NL-1");
    	cluId1.setCode("MATH152");
    	cluId1.setDivision("MATH");
    	cluId1.setLevel("152");
    	cluId1.setShortName("MATH 152");
    	cluId1.setLongName("MATH 152 Linear Systems");
    	clu1.setOfficialIdentifier(cluId1);
    	map.put(clu1.getId(), clu1);

    	CluInfo clu2 = new CluInfo();
    	clu2.setId("CLU-NL-2");
    	clu2.setType("luType.nl.course");
    	CluIdentifierInfo cluId2 = new CluIdentifierInfo();
    	cluId2.setId("IDENT-NL-2");
    	cluId2.setCode("MATH221");
    	cluId2.setDivision("MATH");
    	cluId2.setLevel("221");
    	cluId2.setShortName("MATH 221");
    	cluId2.setLongName("MATH 221 Matrix Algebra");
    	clu2.setOfficialIdentifier(cluId2);
    	map.put(clu2.getId(), clu2);

    	CluInfo clu3 = new CluInfo();
    	clu3.setId("CLU-NL-3");
    	clu3.setType("luType.nl.course");
    	CluIdentifierInfo cluId3 = new CluIdentifierInfo();
    	cluId3.setId("IDENT-NL-3");
    	cluId3.setCode("MATH180");
    	cluId3.setDivision("MATH");
    	cluId3.setLevel("180");
    	cluId3.setShortName("MATH 180");
    	cluId3.setLongName("MATH 180 Differential Calculus with Physical Applications");
    	clu3.setOfficialIdentifier(cluId3);
    	map.put(clu3.getId(), clu3);
    	
    	CluSetInfo cluSet1 = new CluSetInfo();
    	cluSet1.setId("CLUSET-NL-1");
    	cluSet1.setName("Math 152,180 CLU Set");
    	cluSet1.setCluIds(Arrays.asList(new String[] {clu1.getId(), clu3.getId()}));
    	map.put(cluSet1.getId(), cluSet1);

    	CluSetInfo cluSet2 = new CluSetInfo();
    	cluSet2.setId("CLUSET-NL-2");
    	cluSet2.setName("Math 152,221,180 CLU Set");
    	cluSet2.setCluIds(Arrays.asList(new String[] {clu1.getId(), clu2.getId(), clu3.getId()}));
    	map.put(cluSet2.getId(), cluSet2);
    	
    	CluSetInfo cluSet3 = new CluSetInfo();
    	cluSet3.setId("CLUSET-NL-3");
    	cluSet3.setName("Math 152,221 CLU Set");
    	cluSet3.setCluIds(Arrays.asList(new String[] {clu1.getId(), clu2.getId()}));
    	map.put(cluSet3.getId(), cluSet3);
    	
    	return map;
    }
	
    public static ReqComponentTypeInfo createReqComponentType(String reqComponentType) {
    	ReqComponentTypeInfo reqCompType = new ReqComponentTypeInfo();
    	reqCompType.setId(reqComponentType);
    	return reqCompType;
    }

    public static ReqComponentTypeInfo createDefaultReqComponentType(String nlUsageTypeKey, String reqComponentType) {
    	ReqComponentTypeInfo reqCompType = new ReqComponentTypeInfo();
    	reqCompType.setId(reqComponentType);
    	
    	List<ReqComponentTypeNLTemplateInfo> templateList = new ArrayList<ReqComponentTypeNLTemplateInfo>();
    	ReqComponentTypeNLTemplateInfo templateEn = new ReqComponentTypeNLTemplateInfo();
    	templateEn.setLanguage("en");
    	templateEn.setNlUsageTypeKey(nlUsageTypeKey);

    	ReqComponentTypeNLTemplateInfo templateDe = new ReqComponentTypeNLTemplateInfo();
    	templateDe.setLanguage("de");
    	templateDe.setNlUsageTypeKey(nlUsageTypeKey);
    	
    	if(reqComponentType.equals("kuali.reqCompType.courseList.nof")) {
    		templateEn.setTemplate("Student must have completed $expectedValue of $cluSet.getCluSetAsShortName()");
			templateDe.setTemplate("Student muss abgeschlossen $expectedValue von $cluSet.getCluSetAsShortName()");
    	} else if(reqComponentType.equals("kuali.reqCompType.courseList.all")) {
    		templateEn.setTemplate("Student must have completed all of $cluSet.getCluSetAsShortName()");
    		templateDe.setTemplate("Student must have completed all of $cluSet.getCluSetAsShortName()");
    	} else if(reqComponentType.equals("kuali.reqCompType.courseList.none")) {
    		templateEn.setTemplate("Student must have completed none of $cluSet.getCluSetAsShortName()");
    		templateDe.setTemplate("Student must have completed none of $cluSet.getCluSetAsShortName()");
    	} else if(reqComponentType.equals("kuali.reqCompType.courseList.1of2")) {
    		templateEn.setTemplate("Student must have completed $cluSet.getCluAsShortName(0) or $cluSet.getCluAsShortName(1)");
    		templateDe.setTemplate("Student must have completed $cluSet.getCluAsShortName(0) or $cluSet.getCluAsShortName(1)");
    	} else if(reqComponentType.equals("kuali.reqCompType.grdCondCourseList")) {
    		templateEn.setTemplate("Students must take $totalCredits credits from $cluSet.getCluSetAsShortName()");
    		templateDe.setTemplate("Students must take $totalCredits credits from $cluSet.getCluSetAsShortName()");
    	} else if(reqComponentType.equals("kuali.reqCompType.gradecheck")) {
    		templateEn.setTemplate("Student needs a minimum GPA of $gpa");
    		templateDe.setTemplate("Student needs a minimum GPA of $gpa");
    	}
    	
    	templateList.add(templateEn);
    	templateList.add(templateDe);
    	reqCompType.setNlUsageTemplates(templateList);
    	
    	return reqCompType;
    }
    
//    public static CustomReqComponentInfo createCustomReqComponent(String nlUsageTypeKey, String reqComponentType) throws Exception {
//    	ReqComponentInfo reqInfo = createReqComponent(nlUsageTypeKey, reqComponentType);
//    	CustomReqComponentInfo customReq = dtoAdapter.toCustomReqComponentInfo(reqInfo);
//    	return customReq;
//    }
    
    public static ReqComponentInfo createReqComponent(String nlUsageTypeKey, String reqComponentType) throws Exception {
    	ReqComponentInfo reqComponent = new ReqComponentInfo();
    	ReqComponentTypeInfo reqCompType = createDefaultReqComponentType(nlUsageTypeKey, reqComponentType);
    	reqComponent.setRequiredComponentType(reqCompType);
    	//reqComponent.setType(reqComponentType);
    	return reqComponent;
    }
	
    public static ReqComponentInfo createReqComponent(String reqComponentType, List<ReqCompFieldInfo> fieldList) {
		ReqComponentTypeInfo reqCompType = createDefaultReqComponentType("KUALI.CATALOG", reqComponentType);
		
		ReqComponentInfo reqComp = new ReqComponentInfo();
		reqComp.setReqCompFields(fieldList);
		reqComp.setType(reqComponentType);
		reqComp.setRequiredComponentType(reqCompType);
		
		return reqComp;
    }

	public static List<ReqCompFieldInfo> createReqComponentFieldsForCluSet(String expectedValue, String operator, String target) {
		List<ReqCompFieldInfo> fieldList = new ArrayList<ReqCompFieldInfo>();
		ReqCompFieldInfo field1 = new ReqCompFieldInfo();
		field1.setId("reqCompFieldType.requiredCount");
		field1.setValue(expectedValue);
		fieldList.add(field1);
		
		ReqCompFieldInfo field2 = new ReqCompFieldInfo();
		field2.setId("reqCompFieldType.operator");
		field2.setValue(operator);
		fieldList.add(field2);
				
		ReqCompFieldInfo field3 = new ReqCompFieldInfo();
		field3.setId("reqCompFieldType.cluSet");
		field3.setValue(target);
		fieldList.add(field3);
		
		return fieldList;
    }

    public static List<ReqCompFieldInfo> createReqComponentFieldsForClu(String expectedValue, String operator, String cluIds) {
		List<ReqCompFieldInfo> fieldList = new ArrayList<ReqCompFieldInfo>();
		ReqCompFieldInfo field1 = new ReqCompFieldInfo();
		field1.setId("reqCompFieldType.requiredCount");
		field1.setValue(expectedValue);
		fieldList.add(field1);
		
		ReqCompFieldInfo field2 = new ReqCompFieldInfo();
		field2.setId("reqCompFieldType.operator");
		field2.setValue(operator);
		fieldList.add(field2);
		
		ReqCompFieldInfo field3 = new ReqCompFieldInfo();
		field3.setId("reqCompFieldType.clu");
		field3.setValue(cluIds);
		fieldList.add(field3);
		
		return fieldList;
    }

    public static List<ReqCompFieldInfo> createReqComponentFields(String expectedValue, String operator, String reqCompFieldType, String id) {
		List<ReqCompFieldInfo> fieldList = new ArrayList<ReqCompFieldInfo>();
		ReqCompFieldInfo field1 = new ReqCompFieldInfo();
		field1.setId("reqCompFieldType.requiredCount");
		field1.setValue(expectedValue);
		fieldList.add(field1);
		
		ReqCompFieldInfo field2 = new ReqCompFieldInfo();
		field2.setId("reqCompFieldType.operator");
		field2.setValue(operator);
		fieldList.add(field2);
		
		ReqCompFieldInfo field3 = new ReqCompFieldInfo();
		field3.setId(reqCompFieldType);
		field3.setValue(id);
		fieldList.add(field3);
		
		return fieldList;
    }
    
//	public static CustomLuStatementInfo createStatement(StatementOperatorTypeKey operator) throws Exception {
//		CustomLuStatementInfo stmt = new CustomLuStatementInfo();
//		stmt.setOperator(operator);
//		LuStatementTypeInfo type = new LuStatementTypeInfo();
//		type.setId("kuali.luStatementType.prereqAcademicReadiness");
//		type.setHeaders(createHeaders());
//		stmt.setLuStatementType(type);
//		return stmt;
//	}
	
/*	public static LuStatementInfo createLuStatementInfo(String id, StatementOperatorTypeKey operator) throws Exception {
		LuStatementInfo stmt = new LuStatementInfo();
		stmt.setId(id);
		stmt.setOperator(operator);
//		LuStatementTypeInfo type = new LuStatementTypeInfo();
//		type.setId("kuali.luStatementType.prereqAcademicReadiness");
//		type.setHeaders(createHeaders());
		LuStatementTypeInfo type = createLuStatementTypeInfo("kuali.luStatementType.prereqAcademicReadiness");
		stmt.setLuStatementType(type);
		return stmt;
	}

	public static LuStatementTypeInfo createLuStatementTypeInfo(String id) throws Exception {
		LuStatementTypeInfo type = new LuStatementTypeInfo();
		type.setId(id);
		type.setHeaders(createHeaders());
		return type;
	}
	
	public static List<LuStatementTypeHeaderTemplateInfo> createHeaders() {
		List<LuStatementTypeHeaderTemplateInfo> headerList = new ArrayList<LuStatementTypeHeaderTemplateInfo>();
		LuStatementTypeHeaderTemplateInfo header1 = new LuStatementTypeHeaderTemplateInfo();
		header1.setLanguage("en");
		header1.setNlUsageTypeKey("KUALI.CATALOG");
		header1.setTemplate("Requirement for $clu.getOfficialIdentifier().getLongName(): ");
		headerList.add(header1);
		LuStatementTypeHeaderTemplateInfo header2 = new LuStatementTypeHeaderTemplateInfo();
		header2.setLanguage("de");
		header2.setNlUsageTypeKey("KUALI.CATALOG");
		header2.setTemplate("Voraussetzung fur die $clu.getOfficialIdentifier().getLongName(): ");
		headerList.add(header2);
		
		return headerList;
	}
/*    
    public static ContextRegistry<Context<CustomReqComponentInfo>> getReqComponentContextRegistry(LuService luService) {
    	ContextRegistry<Context<CustomReqComponentInfo>> contextRegistry = new ContextRegistry<Context<CustomReqComponentInfo>>();

    	CourseListContextImpl courseListContext = new CourseListContextImpl();
    	courseListContext.setLuService(luService);
    	
    	contextRegistry.add("kuali.reqCompType.courseList.none", courseListContext);
    	contextRegistry.add("kuali.reqCompType.courseList.all", courseListContext);
    	contextRegistry.add("kuali.reqCompType.courseList.nof", courseListContext);
    	contextRegistry.add("kuali.reqCompType.courseList.1of2", courseListContext);

    	GradeConditionCourseListContextImpl gradeConditionCourseListContext = new GradeConditionCourseListContextImpl();
    	gradeConditionCourseListContext.setLuService(luService);
    	contextRegistry.add("kuali.reqCompType.grdCondCourseList", gradeConditionCourseListContext);
    	
    	GradeCheckContextImpl gradeCheckContext = new GradeCheckContextImpl();
    	gradeConditionCourseListContext.setLuService(luService);
    	contextRegistry.add("kuali.reqCompType.gradecheck", gradeCheckContext);
    	
    	return contextRegistry;
    }

    public static ContextRegistry<Context<LuStatementAnchor>> getStatementContextRegistry(LuService luService) {
    	ContextRegistry<Context<LuStatementAnchor>> contextRegistry = new ContextRegistry<Context<LuStatementAnchor>>();

    	HeaderContextImpl headerContext = new HeaderContextImpl();
    	headerContext.setLuService(luService);

    	contextRegistry.add("kuali.luStatementType.prereqAcademicReadiness", headerContext);

    	return contextRegistry;
    }
    
    public static LuNlStatementInfo getComplexLuNlStatementInfo1() {
		//Rule: ((R1 OR R2) AND R3) OR R4
		String statementTypeId = "kuali.luStatementType.prereqAcademicReadiness";

    	LuNlStatementInfo s1 = new LuNlStatementInfo();
		s1.setStatementTypeId(statementTypeId);
		s1.setName("s1");
		s1.setOperator(StatementOperatorTypeKey.OR);

		LuNlStatementInfo s2 = new LuNlStatementInfo();
		s2.setStatementTypeId(statementTypeId);
		s2.setName("s2");
		s2.setOperator(StatementOperatorTypeKey.AND);

		LuNlStatementInfo s3 = new LuNlStatementInfo();
		s3.setStatementTypeId(statementTypeId);
		s3.setName("s3");
		s3.setOperator(StatementOperatorTypeKey.OR);

		LuNlStatementInfo s4 = new LuNlStatementInfo();
		s4.setStatementTypeId(statementTypeId);
		s4.setName("s4");
		s4.setOperator(StatementOperatorTypeKey.AND);

		s2.setChildren(Arrays.asList(s3, s4));

		LuNlStatementInfo s5 = new LuNlStatementInfo();
		s5.setStatementTypeId(statementTypeId);
		s5.setName("s5");
		s5.setOperator(StatementOperatorTypeKey.AND);

		s1.setChildren(Arrays.asList(s2, s5));
		
		List<ReqCompFieldInfo> fieldList1 = createReqComponentFields("0", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-1");
		ReqComponentInfo r1 = createReqComponent("kuali.reqCompType.courseList.none", fieldList1);
		r1.setId("req-1");
		List<ReqCompFieldInfo> fieldList2 = createReqComponentFields("3", "greater_than_or_equal_to", "reqCompFieldType.clu", "CLU-NL-1,CLU-NL-2,CLU-NL-3");
		ReqComponentInfo r2 = createReqComponent("kuali.reqCompType.courseList.all", fieldList2);
		r2.setId("req-2");
		List<ReqCompFieldInfo> fieldList3 = createReqComponentFields("2", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo r3 = createReqComponent("kuali.reqCompType.courseList.nof", fieldList3);
		r3.setId("req-3");
		List<ReqCompFieldInfo> fieldList4 = createReqComponentFields("4", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-3");
		ReqComponentInfo r4 = createReqComponent("kuali.reqCompType.courseList.all", fieldList4);
		r4.setId("req-4");
		
		s3.setRequiredComponents(Arrays.asList(r1, r2));
		s4.setRequiredComponents(Arrays.asList(r3));
		s5.setRequiredComponents(Arrays.asList(r4));
		
		return s1;
    }

    public static LuNlStatementInfo getComplexLuNlStatementInfo2() {
		//Rule: ((R1 OR R2) AND (R3 OR R4)) OR R5 OR (R6 AND (R7 OR R8))
		LuNlStatementInfo s1 = new LuNlStatementInfo();
		s1.setName("s1");
		s1.setStatementTypeId("kuali.luStatementType.prereqAcademicReadiness");
		s1.setOperator(StatementOperatorTypeKey.OR);

		LuNlStatementInfo s2 = new LuNlStatementInfo();
		s2.setName("s2");
		s2.setStatementTypeId("kuali.luStatementType.prereqAcademicReadiness");
		s2.setOperator(StatementOperatorTypeKey.AND);

		LuNlStatementInfo s3 = new LuNlStatementInfo();
		s3.setName("s3");
		s3.setStatementTypeId("kuali.luStatementType.prereqAcademicReadiness");
		s3.setOperator(StatementOperatorTypeKey.OR);

		LuNlStatementInfo s4 = new LuNlStatementInfo();
		s4.setName("s4");
		s4.setStatementTypeId("kuali.luStatementType.prereqAcademicReadiness");
		s4.setOperator(StatementOperatorTypeKey.OR);

		s2.setChildren(Arrays.asList(s3, s4));

		LuNlStatementInfo s5 = new LuNlStatementInfo();
		s5.setName("s5");
		s5.setStatementTypeId("kuali.luStatementType.prereqAcademicReadiness");
		s5.setOperator(StatementOperatorTypeKey.AND);

		LuNlStatementInfo s6 = new LuNlStatementInfo();
		s6.setName("s6");
		s6.setStatementTypeId("kuali.luStatementType.prereqAcademicReadiness");
		s6.setOperator(StatementOperatorTypeKey.AND);
		
		s1.setChildren(Arrays.asList(s2, s5, s6));

		LuNlStatementInfo s7 = new LuNlStatementInfo();
		s7.setName("s7");
		s7.setStatementTypeId("kuali.luStatementType.prereqAcademicReadiness");
		s7.setOperator(StatementOperatorTypeKey.AND);
		
		LuNlStatementInfo s8 = new LuNlStatementInfo();
		s8.setName("s8");
		s8.setStatementTypeId("kuali.luStatementType.prereqAcademicReadiness");
		s8.setOperator(StatementOperatorTypeKey.OR);
		
		s6.setChildren(Arrays.asList(s7, s8));

		List<ReqCompFieldInfo> fieldList1 = createReqComponentFields("0", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-1");
		ReqComponentInfo r1 = createReqComponent("kuali.reqCompType.courseList.none", fieldList1);
		r1.setId("req-1");
		List<ReqCompFieldInfo> fieldList2 = createReqComponentFields("1", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-1");
		ReqComponentInfo r2 = createReqComponent("kuali.reqCompType.courseList.nof", fieldList2);
		r2.setId("req-2");
		List<ReqCompFieldInfo> fieldList3 = createReqComponentFields("0", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo r3 = createReqComponent("kuali.reqCompType.courseList.none", fieldList3);
		r3.setId("req-3");
		List<ReqCompFieldInfo> fieldList4 = createReqComponentFields("1", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo r4 = createReqComponent("kuali.reqCompType.courseList.nof", fieldList4);
		r4.setId("req-4");
		List<ReqCompFieldInfo> fieldList5 = createReqComponentFields("3", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo r5 = createReqComponent("kuali.reqCompType.courseList.all", fieldList5);
		r5.setId("req-5");
		List<ReqCompFieldInfo> fieldList6 = createReqComponentFields("1", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-3");
		ReqComponentInfo r6 = createReqComponent("kuali.reqCompType.courseList.nof", fieldList6);
		r6.setId("req-6");
		List<ReqCompFieldInfo> fieldList7 = createReqComponentFields("2", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo r7 = createReqComponent("kuali.reqCompType.courseList.nof", fieldList7);
		r7.setId("req-7");
		List<ReqCompFieldInfo> fieldList8 = createReqComponentFields("4", "greater_than_or_equal_to", "reqCompFieldType.clu", "CLU-NL-2, CLU-NL-3, CLU-NL-4, CLU-NL-5");
		ReqComponentInfo r8 = createReqComponent("kuali.reqCompType.courseList.all", fieldList8);
		r8.setId("req-8");
		
		s3.setRequiredComponents(Arrays.asList(r1, r2));
		s4.setRequiredComponents(Arrays.asList(r3, r4));
		s5.setRequiredComponents(Arrays.asList(r5));
		s7.setRequiredComponents(Arrays.asList(r6));
		s8.setRequiredComponents(Arrays.asList(r7, r8));
		
		return s1;
    }
*/
}
