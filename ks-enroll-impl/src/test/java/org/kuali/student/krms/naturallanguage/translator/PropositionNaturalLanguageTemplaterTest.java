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

package org.kuali.student.krms.naturallanguage.translator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterType;
import org.kuali.rice.krms.impl.repository.language.PropositionNaturalLanguageTemplater;
import org.kuali.student.krms.naturallanguage.NaturalLanguageUtil;
import org.kuali.student.krms.naturallanguage.service.impl.NaturalLanguageTemplateBoMockService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;
import org.kuali.rice.krms.impl.repository.language.Context;
import org.kuali.rice.krms.impl.repository.language.ContextRegistry;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:nl-test-context.xml"})
public class PropositionNaturalLanguageTemplaterTest{

    @Resource
	private PropositionNaturalLanguageTemplater propositionTemplater;

    private PropositionDefinitionContract proposition;
    private String nlUsageTypeKey = "kuali.krms.preview";

    private NaturalLanguageTemplateBoMockService naturalLanguageTemplateBoService = new NaturalLanguageTemplateBoMockService();

    private void createTranslator() {
        ContextRegistry<Context> contextRegistry = NaturalLanguageUtil.getPropositionContextRegistry();

        propositionTemplater.setContextRegistry(contextRegistry);
    }

    @Before
    public void beforeMethod() {
    	createTranslator();
    }

    private PropositionDefinitionContract createProposition(String nlUsageTypeKey, String propositionType, String expectedValue, String operator) throws Exception {
    	this.proposition = NaturalLanguageUtil.createProposition(nlUsageTypeKey, propositionType, expectedValue, operator);
    	return this.proposition;
    }

//	@Test
//	public void testInvalidPropositionType() throws Exception {
//        NaturalLanguageTemplate template = naturalLanguageTemplateBoService.findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId("en","xxx.xxx.xxx",nlUsageTypeKey);
//        Map<String, Object> contextMap = new LinkedHashMap<String, Object>();
//        contextMap.put(PropositionParameterType.TERM.getCode(),"xterm");
//        contextMap.put(PropositionParameterType.CONSTANT.getCode(),"0");
//        contextMap.put(PropositionParameterType.OPERATOR.getCode(),">=");
//
//		try {
//			propositionTemplater.translate(template, contextMap);
//			Assert.fail("Translate method should have thrown a DoesNotExistException for proposition type id xxx.xxx.xxx");
//		} catch(DoesNotExistException e) {
//			Assert.assertEquals("Proposition context not found in registry for proposition type id: xxx.xxx.xxx", e.getMessage());
//		}
//	}

//    @Test
//    public void testTranslate_InvalidReqComponentId() throws Exception {
//        try {
//            propositionTemplater.translate(null, new LinkedHashMap<String, Object>());
//            Assert.fail("Translation should have failed since proposition is null");
//        } catch (DoesNotExistException e) {
//            Assert.assertNotNull(e.getMessage());
//        }
//    }

	@Test
	public void testTranslate_OneOf_English() throws Exception {
		String propositionType = "kuali.krms.proposition.type.course.courseset.completed.nof";
        NaturalLanguageTemplate template = naturalLanguageTemplateBoService.findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId("en",propositionType,nlUsageTypeKey);
        Map<String, Object> contextMap = new LinkedHashMap<String, Object>();
        contextMap.put(PropositionParameterType.TERM.getCode(),"cluSet1");
        contextMap.put(PropositionParameterType.OPERATOR.getCode(),">=");
        contextMap.put(PropositionParameterType.CONSTANT.getCode(),"1");

		String text = propositionTemplater.translate(template, contextMap);
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_AllOf_1Clu() throws Exception {
        String propositionType = "kuali.krms.proposition.type.success.course.courseset.completed.all";
        NaturalLanguageTemplate template = naturalLanguageTemplateBoService.findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId("en",propositionType,nlUsageTypeKey);
        Map<String, Object> contextMap = new LinkedHashMap<String, Object>();
        contextMap.put(PropositionParameterType.TERM.getCode(),"cluSet1");
        contextMap.put(PropositionParameterType.OPERATOR.getCode(),"=");
        contextMap.put(PropositionParameterType.CONSTANT.getCode(),"2");

        String text = propositionTemplater.translate(template, contextMap);
		Assert.assertEquals("Student must have completed all of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_NoneOf() throws Exception {
        String propositionType = "kuali.krms.proposition.type.course.courseset.completed.none";
        NaturalLanguageTemplate template = naturalLanguageTemplateBoService.findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId("en",propositionType,nlUsageTypeKey);
        Map<String, Object> contextMap = new LinkedHashMap<String, Object>();
        contextMap.put(PropositionParameterType.TERM.getCode(),"cluSet1");
        contextMap.put(PropositionParameterType.OPERATOR.getCode(),"=");
        contextMap.put(PropositionParameterType.CONSTANT.getCode(),"0");

        String text = propositionTemplater.translate(template, contextMap);
		Assert.assertEquals("Student must have completed none of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_GPA() throws Exception {
        String propositionType = "kuali.krms.proposition.type.course.courseset.gpa.min";
        NaturalLanguageTemplate template = naturalLanguageTemplateBoService.findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId("en",propositionType,nlUsageTypeKey);
        Map<String, Object> contextMap = new LinkedHashMap<String, Object>();
        contextMap.put(PropositionParameterType.TERM.getCode(),"xterm");
        contextMap.put(PropositionParameterType.OPERATOR.getCode(),">=");
        contextMap.put(PropositionParameterType.CONSTANT.getCode(),"3.0");

        String text = propositionTemplater.translate(template, contextMap);
		Assert.assertEquals("Student needs a minimum GPA of 3.0", text);
	}

}
