package org.kuali.student.krms.naturallanguage.config.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.krms.api.repository.term.TermDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinitionContract;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.krms.naturallanguage.KRMSDataGenerator;
import org.kuali.student.r2.core.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:nl-test-context.xml"})
public class PersonContextImplTest extends AbstractServiceTest {

    private PersonContextImpl personContext = new PersonContextImpl();

	private TermDefinitionContract term;
	private TermDefinitionContract term2;
	
	
	private void setupTerm1() {
        List<TermParameterDefinitionContract> parameterList = new ArrayList<TermParameterDefinitionContract>();
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.PERSON_KEY.getId(),"231231",null,0L));
        term = KRMSDataGenerator.createTermDefinition(null,null,parameterList,null,0L);
	}

	private void setupTerm2() {
        List<TermParameterDefinitionContract> parameterList = new ArrayList<TermParameterDefinitionContract>();
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null, TermParameterTypes.PERSON_KEY.getId(),null,null,0L));
		term2 = KRMSDataGenerator.createTermDefinition(null,null,parameterList,null,0L);
	}

	@Before
	public void beforeMethod() {
		setupTerm1();
		setupTerm2();
	}

	@Test
    public void testCreateContextMap_Person() throws OperationFailedException {
		Map<String, Object> contextMap = personContext.createContextMap(term, ContextUtils.getContextInfo());
		String person = (String) contextMap.get(PersonContextImpl.PERSON_TOKEN);

		Assert.assertNotNull(contextMap);
		Assert.assertEquals("231231", person);
	}
	
	@Test
    public void testCreateContextMap_NullTokenValues() throws OperationFailedException {
        Map<String, Object> contextMap = personContext.createContextMap(term2, ContextUtils.getContextInfo());
        String person = (String) contextMap.get(PersonContextImpl.PERSON_TOKEN);

        Assert.assertNotNull(contextMap);
        Assert.assertEquals(null, person);

	}

}
