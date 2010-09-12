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

package org.kuali.student.core.statement.naturallanguage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kuali.student.core.statement.config.context.lu.CluInfo;
import org.kuali.student.core.statement.config.context.lu.CluSetInfo;
import org.kuali.student.core.statement.config.context.lu.CourseListContextImpl;
import org.kuali.student.core.statement.config.context.lu.GradeCheckContextImpl;
import org.kuali.student.core.statement.config.context.lu.CreditContextImpl;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.entity.ReqComponentField;
import org.kuali.student.core.statement.entity.ReqComponentType;
import org.kuali.student.core.statement.entity.ReqComponentTypeNLTemplate;
import org.kuali.student.core.statement.entity.Statement;
import org.kuali.student.core.statement.entity.StatementType;
import org.kuali.student.core.statement.naturallanguage.ReqComponentFieldTypes;

/**
 * Utility class to support testing.
 */
public class NaturalLanguageUtil {

	private final static List<CluInfo> cluList = new ArrayList<CluInfo>();
	private final static List<CluSetInfo> cluSetList = new ArrayList<CluSetInfo>();

    public static void createData() {
    	CluInfo clu1 = new CluInfo();
    	clu1.setId("CLU-NL-1");
    	clu1.setCode("MATH152");
    	clu1.setShortName("MATH 152");
    	clu1.setLongName("MATH 152 Linear Systems");
    	cluList.add(clu1);

    	CluInfo clu2 = new CluInfo();
    	clu2.setId("CLU-NL-2");
    	clu2.setCode("MATH221");
    	clu2.setShortName("MATH 221");
    	clu2.setLongName("MATH 221 Matrix Algebra");
    	cluList.add(clu2);

    	CluInfo clu3 = new CluInfo();
    	clu3.setId("CLU-NL-3");
    	clu3.setCode("MATH180");
    	clu3.setShortName("MATH 180");
    	clu3.setLongName("MATH 180 Differential Calculus with Physical Applications");
    	cluList.add(clu3);
    	
    	CluSetInfo cluSet1 = new CluSetInfo();
    	cluSet1.setId("CLUSET-NL-1");
    	cluSet1.setCluIds(Arrays.asList(new String[] {clu1.getId(), clu3.getId()}));
    	cluSet1.setCluList(Arrays.asList(new CluInfo[] {clu1, clu3}));
    	cluSetList.add(cluSet1);

    	CluSetInfo cluSet2 = new CluSetInfo();
    	cluSet2.setId("CLUSET-NL-2");
    	cluSet2.setCluIds(Arrays.asList(new String[] {clu1.getId(), clu2.getId(), clu3.getId()}));
    	cluSet2.setCluList(Arrays.asList(new CluInfo[] {clu1, clu2, clu3}));
    	cluSetList.add(cluSet2);
    	
    	CluSetInfo cluSet3 = new CluSetInfo();
    	cluSet3.setId("CLUSET-NL-3");
    	cluSet3.setCluIds(Arrays.asList(new String[] {clu1.getId(), clu2.getId()}));
    	cluSet3.setCluList(Arrays.asList(new CluInfo[] {clu1, clu2}));
    	cluSetList.add(cluSet3);
    }
	
    public static ReqComponentType createReqComponentType(String reqComponentType) {
    	ReqComponentType reqCompType = new ReqComponentType();
    	reqCompType.setId(reqComponentType);
    	return reqCompType;
    }

    public static ReqComponentType createDefaultReqComponentType(String nlUsageTypeKey, String reqComponentType) {
    	ReqComponentType reqCompType = new ReqComponentType();
    	reqCompType.setId(reqComponentType);
    	
    	List<ReqComponentTypeNLTemplate> templateList = new ArrayList<ReqComponentTypeNLTemplate>();
    	ReqComponentTypeNLTemplate templateEn = new ReqComponentTypeNLTemplate();
    	templateEn.setLanguage("en");
    	templateEn.setNlUsageTypeKey(nlUsageTypeKey);

    	ReqComponentTypeNLTemplate templateDe = new ReqComponentTypeNLTemplate();
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
    
    public static ReqComponent createReqComponent(String nlUsageTypeKey, String reqComponentType) throws Exception {
    	ReqComponent reqComponent = new ReqComponent();
    	ReqComponentType reqCompType = createDefaultReqComponentType(nlUsageTypeKey, reqComponentType);
    	reqComponent.setRequiredComponentType(reqCompType);
    	return reqComponent;
    }
	
    public static ReqComponent createReqComponent(String reqComponentType, List<ReqComponentField> fieldList) {
    	ReqComponentType reqCompType = createDefaultReqComponentType("KUALI.RULEEDIT", reqComponentType);
		
		ReqComponent reqComp = new ReqComponent();
		reqComp.setReqComponentFields(fieldList);
		reqComp.setRequiredComponentType(reqCompType);
		
		return reqComp;
    }

	public static List<ReqComponentField> createReqComponentFieldsForCluSet(String expectedValue, String operator, String target) {
		List<ReqComponentField> fieldList = new ArrayList<ReqComponentField>();
		ReqComponentField field1 = new ReqComponentField();
		field1.setId("1");
		field1.setType(ReqComponentFieldTypes.REQUIRED_COUNT_KEY.getType());
		field1.setValue(expectedValue);
		fieldList.add(field1);
		
		ReqComponentField field2 = new ReqComponentField();
		field2.setId("2");
		field2.setType(ReqComponentFieldTypes.OPERATOR_KEY.getType());
		field2.setValue(operator);
		fieldList.add(field2);
				
		ReqComponentField field3 = new ReqComponentField();
		field3.setId("3");
		field3.setType(ReqComponentFieldTypes.CLUSET_KEY.getType());
		field3.setValue(target);
		fieldList.add(field3);
		
		return fieldList;
    }

    public static List<ReqComponentField> createReqComponentFieldsForClu(String expectedValue, String operator, String cluIds) {
		List<ReqComponentField> fieldList = new ArrayList<ReqComponentField>();
		ReqComponentField field1 = new ReqComponentField();
		field1.setId("4");
		field1.setType(ReqComponentFieldTypes.REQUIRED_COUNT_KEY.getType());
		field1.setValue(expectedValue);
		fieldList.add(field1);
		
		ReqComponentField field2 = new ReqComponentField();
		field2.setId("5");
		field2.setType(ReqComponentFieldTypes.OPERATOR_KEY.getType());
		field2.setValue(operator);
		fieldList.add(field2);
		
		ReqComponentField field3 = new ReqComponentField();
		field3.setId("6");
		field3.setType(ReqComponentFieldTypes.CLU_KEY.getType());
		field3.setValue(cluIds);
		fieldList.add(field3);
		
		return fieldList;
    }

    /*public static List<ReqComponentField> createReqComponentFields(String expectedValue, String operator, String reqCompFieldType, String id) {
		List<ReqComponentField> fieldList = new ArrayList<ReqComponentField>();
		ReqComponentField field1 = new ReqComponentField();
		field1.setId("7");
		field1.setType(ReqComponentFieldTypes.REQUIRED_COUNT_KEY.getType());
		field1.setValue(expectedValue);
		fieldList.add(field1);
		
		ReqComponentField field2 = new ReqComponentField();
		field2.setId("8");
		field2.setType(ReqComponentFieldTypes.OPERATOR_KEY.getType());
		field2.setValue(operator);
		fieldList.add(field2);
		
		ReqComponentField field3 = new ReqComponentField();
		field3.setId("9");
		field3.setType(reqCompFieldType);
		field3.setValue(id);
		fieldList.add(field3);
		
		return fieldList;
    }*/
    
	public static Statement createStatement(StatementOperatorTypeKey operator) throws Exception {
		Statement stmt = new Statement();
		stmt.setOperator(operator);
		StatementType type = new StatementType();
		type.setId("kuali.luStatementType.prereqAcademicReadiness");
		//type.setStatementHeaders(createHeaders());
		stmt.setStatementType(type);
		return stmt;
	}

	public static Statement createLuStatementInfo(String id, StatementOperatorTypeKey operator) throws Exception {
		Statement stmt = new Statement();
		stmt.setId(id);
		stmt.setOperator(operator);
//		LuStatementTypeInfo type = new LuStatementTypeInfo();
//		type.setId("kuali.luStatementType.prereqAcademicReadiness");
//		type.setHeaders(createHeaders());
		StatementType type = createLuStatementTypeInfo("kuali.luStatementType.prereqAcademicReadiness");
		stmt.setStatementType(type);
		return stmt;
	}

	public static StatementType createLuStatementTypeInfo(String id) throws Exception {
		StatementType type = new StatementType();
		type.setId(id);
		//type.setStatementHeaders(createHeaders());
		return type;
	}
	
//	public static List<StatementTypeHeaderTemplate> createHeaders() {
//		List<StatementTypeHeaderTemplate> headerList = new ArrayList<StatementTypeHeaderTemplate>();
//		StatementTypeHeaderTemplate header1 = new StatementTypeHeaderTemplate();
//		header1.setLanguage("en");
//		header1.setNlUsageTypeKey("KUALI.RULEEDIT");
//		header1.setTemplate("Requirement for $clu.getOfficialIdentifier().getLongName(): ");
//		headerList.add(header1);
//		StatementTypeHeaderTemplate header2 = new StatementTypeHeaderTemplate();
//		header2.setLanguage("de");
//		header2.setNlUsageTypeKey("KUALI.RULEEDIT");
//		header2.setTemplate("Voraussetzung fur die $clu.getOfficialIdentifier().getLongName(): ");
//		headerList.add(header2);
//		
//		return headerList;
//	}
    
    public static ContextRegistry<Context<ReqComponent>> getReqComponentContextRegistry() {
    	ContextRegistry<Context<ReqComponent>> contextRegistry = new ContextRegistry<Context<ReqComponent>>();

    	createData();
    	CourseListContextImpl.setCluInfo(cluList);
    	CourseListContextImpl.setCluSetInfo(cluSetList);
    	CourseListContextImpl courseListContext = new CourseListContextImpl();

    	contextRegistry.add("kuali.reqCompType.courseList.none", courseListContext);
    	contextRegistry.add("kuali.reqCompType.courseList.all", courseListContext);
    	contextRegistry.add("kuali.reqCompType.courseList.nof", courseListContext);
    	contextRegistry.add("kuali.reqCompType.courseList.1of2", courseListContext);

    	CreditContextImpl.setCluInfo(cluList);
    	CreditContextImpl.setCluSetInfo(cluSetList);
    	CreditContextImpl gradeConditionCourseListContext = new CreditContextImpl();
    	contextRegistry.add("kuali.reqCompType.grdCondCourseList", gradeConditionCourseListContext);
    	contextRegistry.add("kuali.reqCompType.grdCondCourseList", courseListContext);
    	
    	GradeCheckContextImpl.setCluInfo(cluList);
    	GradeCheckContextImpl.setCluSetInfo(cluSetList);
    	GradeCheckContextImpl gradeCheckContext = new GradeCheckContextImpl();
    	contextRegistry.add("kuali.reqCompType.gradecheck", gradeCheckContext);
    	
    	return contextRegistry;
    }
}
