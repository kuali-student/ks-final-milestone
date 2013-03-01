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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterContract;
import org.kuali.rice.krms.api.repository.term.TermDefinitionContract;
import org.kuali.student.krms.naturallanguage.KRMSDataGenerator;
import org.kuali.student.krms.naturallanguage.translators.PropositionTranslator;
import org.kuali.student.krms.naturallanguage.NaturalLanguageUtil;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.krms.naturallanguage.Context;
import org.kuali.student.r2.core.krms.naturallanguage.ContextRegistry;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:nl-test-context.xml"})
@Ignore //TODO KSENROLL-5427 Create test for the Proposition translator
public class PropositionTranslatorTest{

    @Resource
	private PropositionTranslator englishTranslator;

    private PropositionDefinitionContract proposition;
    private String nlUsageTypeKey = "KUALI.RULE";

    private void createTranslator() {
        ContextRegistry<Context<TermDefinitionContract>> contextRegistry = NaturalLanguageUtil.getPropositionContextRegistry();

        englishTranslator.setContextRegistry(contextRegistry);
        englishTranslator.setLanguage("en");
    }

    @Before
    public void beforeMethod() {
    	createTranslator();
    }

    private PropositionDefinitionContract createProposition(String nlUsageTypeKey, String propositionType, String expectedValue, String operator) throws Exception {
    	this.proposition = NaturalLanguageUtil.createProposition(nlUsageTypeKey, propositionType, expectedValue, operator);
    	return this.proposition;
    }

	@Test
	public void testInvalidPropositionType() throws DoesNotExistException, OperationFailedException {
		proposition = KRMSDataGenerator.createPropositionDefinition(null, "xxx.xxx.xxx", null, null, null, null, null, null, 0L);

		try {
			englishTranslator.translate(proposition, nlUsageTypeKey);
			Assert.fail("Translate method should have thrown a DoesNotExistException for requirement component type id xxx.xxx.xxx");
		} catch(DoesNotExistException e) {
			Assert.assertEquals("Requirement component context not found in registry for requirement component type id: xxx.xxx.xxx", e.getMessage());
		}
	}

    @Test
    public void testTranslate_InvalidReqComponentId() throws Exception {
        try {
            englishTranslator.translate(null, nlUsageTypeKey);
            Assert.fail("Requirement component translation should have failed since requirement component is null");
        } catch (DoesNotExistException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

	@Test
	public void testTranslate_OneOf_English() throws Exception {
		String propositionType = "kuali.krms.proposition.type.course.courseset.completed.nof";
    	createProposition(nlUsageTypeKey, propositionType, "1", ">");

		String text = englishTranslator.translate(this.proposition, nlUsageTypeKey);
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_AllOf_1Clu() throws Exception {
        String propositionType = "kuali.krms.proposition.type.success.course.courseset.completed.all";
        createProposition(nlUsageTypeKey, propositionType, "2", "=");

		String text = englishTranslator.translate(this.proposition, nlUsageTypeKey);
		Assert.assertEquals("Student must have completed all of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_NoneOf() throws Exception {
        String propositionType = "kuali.krms.proposition.type.course.courseset.completed.none";
        createProposition(nlUsageTypeKey, propositionType, "0", "=");

		String text = englishTranslator.translate(this.proposition, nlUsageTypeKey);
		Assert.assertEquals("Student must have completed none of MATH 152, MATH 221", text);
	}

	@Test
	public void testTranslate_GPA() throws Exception {
        String propositionType = "kuali.krms.proposition.type.course.courseset.gpa.min";
		createProposition(nlUsageTypeKey, propositionType, "1", "=");

        String text = englishTranslator.translate(this.proposition, nlUsageTypeKey);
		Assert.assertEquals("Student needs a minimum GPA of 70.0%", text);
	}

}
