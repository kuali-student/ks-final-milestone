package org.kuali.student.krms.naturallanguage.config.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.krms.api.repository.term.TermDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinitionContract;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.krms.naturallanguage.KRMSDataGenerator;
import org.kuali.student.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.ContextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Ignore
public class BasicContextImplTest extends AbstractServiceTest {

	private BasicContextImpl basicContextImpl = new BasicContextImpl();

	private TermDefinitionContract term;
    private TermDefinitionContract term2;
	
	
	private void setupTerm1() {
        List<TermParameterDefinitionContract> parameterList = new ArrayList<TermParameterDefinitionContract>();
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null, TermParameterTypes.INTEGER_VALUE1_KEY.getId(),"234",null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.OPERATOR_KEY.getId(),"<",null,0L));
        term = KRMSDataGenerator.createTermDefinition(null,null,parameterList,null,0L);
	}


	@Before
	public void beforeMethod() {
		setupTerm1();

        term2 = KRMSDataGenerator.createTermDefinition(null,null,new ArrayList<TermParameterDefinitionContract>(),null,0L);
	}

	@Test
    public void testCreateContextMap_valid() throws OperationFailedException {
		Map<String, Object> contextMap = basicContextImpl.createContextMap(term, ContextUtils.getContextInfo());
        String operator = (String) contextMap.get(BasicContextImpl.OPERATOR_TOKEN);
        Integer integerValue = (Integer) contextMap.get(BasicContextImpl.INTEGER_VALUE_TOKEN);


		Assert.assertNotNull(contextMap);
		Assert.assertEquals("<", operator);
		Assert.assertEquals(Integer.valueOf(234), integerValue);
	}


    @Test
    public void testCreateContextMap_null() throws OperationFailedException {
        Map<String, Object> contextMap = basicContextImpl.createContextMap(term2, ContextUtils.getContextInfo());
        String operator = (String) contextMap.get(BasicContextImpl.OPERATOR_TOKEN);
        Integer integerValue = (Integer) contextMap.get(BasicContextImpl.INTEGER_VALUE_TOKEN);


        Assert.assertNotNull(contextMap);
        Assert.assertEquals(null, operator);
        Assert.assertEquals(null, integerValue);
    }


}
