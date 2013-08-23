package org.kuali.student.krms.naturallanguage.config.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.r2.core.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:nl-test-context.xml"})
public class GPAContextImplTest extends AbstractServiceTest {

    private GpaContextImpl gpaContext = new GpaContextImpl();

	private Map<String, Object> term;
	private Map<String, Object> term2;
	
	private void setupTerm1() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(TermParameterTypes.GPA_KEY.getId(),"2");
        term = parameters;
	}

	private void setupTerm2() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(TermParameterTypes.GPA_KEY.getId(),null);
		term2 = parameters;
	}

	@Before
	public void beforeMethod() {
		setupTerm1();
		setupTerm2();
	}

	@Test
    public void testCreateContextMap_Gpa() {
		Map<String, Object> contextMap = gpaContext.createContextMap(term);
        String gpa = (String) contextMap.get(GpaContextImpl.GPA_TOKEN);

		Assert.assertNotNull(contextMap);
		Assert.assertEquals("2", gpa);
	}
	
	@Test
    public void testCreateContextMap_NullTokenValues()  {
        Map<String, Object> contextMap = gpaContext.createContextMap(term2);
        String gpa = (String) contextMap.get(GpaContextImpl.GPA_TOKEN);

        Assert.assertNotNull(contextMap);
        Assert.assertEquals(null, gpa);

	}

}
