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

package org.kuali.student.lum.ui.requirements.server.gwt;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.brms.ruleexecution.runtime.drools.DroolsKnowledgeBase;
import org.kuali.student.brms.ruleexecution.runtime.drools.SimpleExecutorDroolsImpl;
import org.kuali.student.brms.ruleexecution.runtime.report.ast.BooleanOperators;
import org.kuali.student.brms.statement.dto.ReqComponentInfo;
import org.kuali.student.brms.statement.dto.StatementInfo;
import org.kuali.student.brms.statement.dto.StatementOperatorTypeKey;
//import org.kuali.student.brms.statement.naturallanguage.translators.NaturalLanguageMessageBuilder;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.util.StatementServiceMock;

public class TestRequirementsRpcGwtServlet {

	private static StatementServiceMock statementService = new StatementServiceMock();
	private static RequirementsRpcGwtServlet requirementsRpcGwtServlet = new RequirementsRpcGwtServlet();
	
	private static ReqComponentInfo reqComp1;
	private static ReqComponentInfo reqComp2;
	
	@BeforeClass
	public static void setupOnce() throws Exception {
		createTranslator();
		createLuData();
		setRpcGwtServices();
	}

 // NJW 3/5/2010:  commented out NaturalLanguageMessageBuilder because
 // the message builder wasn't in the pom dependencies
 // and wasn't used so I could not compile
//    private static NaturalLanguageMessageBuilder createMessageBuilder() {
//    	SimpleExecutorDroolsImpl executor = new SimpleExecutorDroolsImpl();
//    	final DroolsKnowledgeBase ruleBase = new DroolsKnowledgeBase();
//		executor.setEnableStatisticsLogging(false);
//		executor.setRuleBaseCache(ruleBase);
//
//		Map<String, BooleanOperators> booleanLanguageMap = new HashMap<String, BooleanOperators>();
//		booleanLanguageMap.put("en", new BooleanOperators("and", "or"));
//
//		return new NaturalLanguageMessageBuilder(executor, "en", booleanLanguageMap);
//    }

    @Ignore
    private static void createTranslator() {
//    	ContextRegistry<Context<CustomReqComponentInfo>> reqComponentContextRegistry = RequirementsUtil.getReqComponentContextRegistry(statementService);
//    	ContextRegistry<Context<LuStatementAnchor>> statementContextRegistry = RequirementsUtil.getStatementContextRegistry(statementService);
//
//    	ReqComponentTranslator englishReqComponentTranslator = new ReqComponentTranslator();
//    	englishReqComponentTranslator.setContextRegistry(reqComponentContextRegistry);
//    	englishReqComponentTranslator.setLanguage("en");
//
//    	StatementTranslator englishStatementTranslator = new StatementTranslator();
//    	englishStatementTranslator.setContextRegistry(statementContextRegistry);
//    	englishStatementTranslator.setReqComponentTranslator(englishReqComponentTranslator);
//    	englishStatementTranslator.setMessageBuilder(createMessageBuilder());
//    	englishStatementTranslator.setLanguage("en");
//
//    	NaturalLanguageTranslatorImpl englishTranslator = new NaturalLanguageTranslatorImpl();
//    	englishTranslator.setLanguage("en");
//    	englishTranslator.setReqComponentTranslator(englishReqComponentTranslator);
//    	englishTranslator.setStatementTranslator(englishStatementTranslator);
//
////		translationService.setLuService(luService);
//		translationService.setNaturalLanguageTranslator(englishTranslator);
    }

    private static void createLuData() throws Exception {
//    	statementService.addAll(RequirementsUtil.createData());
//		reqComp1 = createReqComponent("req-1", "CLUSET-NL-1");
//		reqComp2 = createReqComponent("req-2", "CLUSET-NL-2");
//
//		statementService.addObject(reqComp1.getId(), reqComp1);
//		statementService.addObject(reqComp2.getId(), reqComp2);
//		
//		ReqComponentTypeInfo reqType = RequirementsUtil.createDefaultReqComponentType("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
//		statementService.addObject(reqType.getId(), reqType);
//		
//		StatementTypeInfo statementTypeInfo = RequirementsUtil.createLuStatementTypeInfo("kuali.luStatementType.prereqAcademicReadiness");
//		statementService.addObject(statementTypeInfo.getId(), statementTypeInfo);
    }
    
    private static void setRpcGwtServices() {
		requirementsRpcGwtServlet.setStatementService(statementService);
    }
    
//    private static ReqComponentInfo createReqComponent(String reqComponentId, String cluSetId) throws Exception {
//        ReqComponentInfo reqCompInfo = RequirementsUtil.createReqComponent(
//        		"KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
//        reqCompInfo.setId(reqComponentId);
//        
//        List<ReqCompFieldInfo> fieldList = new ArrayList<ReqCompFieldInfo>();
//        
//        ReqCompFieldInfo field1 = new ReqCompFieldInfo();
//        field1.setId("reqCompFieldType.requiredCount");
//        field1.setValue("1");
//        fieldList.add(field1);
//        
//        ReqCompFieldInfo field2 = new ReqCompFieldInfo();
//        field2.setId("reqCompFieldType.operator");
//        field2.setValue("greater_than_or_equal_to");
//        fieldList.add(field2);
//        
//        ReqCompFieldInfo field3 = new ReqCompFieldInfo();
//        field3.setId("reqCompFieldType.cluSet");
//        field3.setValue(cluSetId);
//        fieldList.add(field3);
//        
//        reqCompInfo.setReqCompFields(fieldList);
//        return reqCompInfo;
//    }
    
    @Ignore
    @Test
    public void testGetNaturalLanguageForReqComponentInfo() throws Exception {
    	String naturalLanguage = requirementsRpcGwtServlet.getNaturalLanguageForReqComponentInfo(reqComp1, "KUALI.CATALOG", "en");
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
    }

    @Ignore
    @Test
    public void testGetNaturalLanguageForStatementVO() throws Exception {
		StatementInfo statementInfo = new StatementInfo();
		statementInfo.setType("kuali.luStatementType.prereqAcademicReadiness");
		statementInfo.setOperator(StatementOperatorTypeKey.OR);
		statementInfo.setReqComponentIds(Arrays.asList("req-1", "req-2"));

    	ReqComponentVO reqVo1 = new ReqComponentVO();
    	reqVo1.setReqComponentInfo(reqComp1);
    	ReqComponentVO reqVo2 = new ReqComponentVO();
    	reqVo2.setReqComponentInfo(reqComp2);

    	StatementVO statementVO = new StatementVO(statementInfo);
    	statementVO.addReqComponentVO(reqVo1);
    	statementVO.addReqComponentVO(reqVo2);
    	
    	String naturalLanguage = requirementsRpcGwtServlet.getNaturalLanguageForStatementVO("CLU-NL-1", statementVO, "KUALI.CATALOG", "en");
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 1 of MATH 152, MATH 221, MATH 180", naturalLanguage);
    }
}
