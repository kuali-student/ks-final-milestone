package org.kuali.student.krms.naturallanguage.config.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.krms.api.repository.term.TermDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinitionContract;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.krms.naturallanguage.KRMSDataGenerator;
import org.kuali.student.r2.core.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public void testCreateContextMap_Gpa() throws OperationFailedException {
		Map<String, Object> contextMap = gpaContext.createContextMap(term, ContextUtils.getContextInfo());
        String gpa = (String) contextMap.get(GpaContextImpl.GPA_TOKEN);

		Assert.assertNotNull(contextMap);
		Assert.assertEquals("2", gpa);
	}
	
	@Test
    public void testCreateContextMap_NullTokenValues() throws OperationFailedException {
        Map<String, Object> contextMap = gpaContext.createContextMap(term2, ContextUtils.getContextInfo());
        String gpa = (String) contextMap.get(GpaContextImpl.GPA_TOKEN);

        Assert.assertNotNull(contextMap);
        Assert.assertEquals(null, gpa);

	}

}
