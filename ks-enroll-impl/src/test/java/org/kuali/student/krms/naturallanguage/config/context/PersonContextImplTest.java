package org.kuali.student.krms.naturallanguage.config.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.r2.core.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:nl-test-context.xml"})
public class PersonContextImplTest extends AbstractServiceTest {

    private PersonContextImpl personContext = new PersonContextImpl();

	private Map<String, Object> term;
	private Map<String, Object> term2;
	
	private void setupTerm1() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(TermParameterTypes.PERSON_KEY.getId(),"231231");
        term = parameters;
	}

	private void setupTerm2() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(TermParameterTypes.PERSON_KEY.getId(),null);
		term2 = parameters;
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
