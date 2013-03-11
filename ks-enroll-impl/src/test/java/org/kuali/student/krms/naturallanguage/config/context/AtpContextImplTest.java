package org.kuali.student.krms.naturallanguage.config.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.krms.api.repository.term.TermDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinitionContract;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.krms.naturallanguage.KRMSDataGenerator;
import org.kuali.student.r2.core.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:type-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class AtpContextImplTest extends AbstractServiceTest {

    @Resource
    private TypeService typeService;
    private AtpContextImpl atpContext = new AtpContextImpl();

    private TermDefinitionContract term;
    private TermDefinitionContract term2;
    private TypeInfo type1;

    private void setupTerm1() {
        List<TermParameterDefinitionContract> parameterList = new ArrayList<TermParameterDefinitionContract>();
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null, TermParameterTypes.DURATION_TYPE_KEY.getId(), "kuali.atp.type.TestAtp1", null, 0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.DURATION_KEY.getId(),"ATP-1",null,0L));
        term = KRMSDataGenerator.createTermDefinition(null,null,parameterList,null,0L);
	}

	private void setupTerm2() {
        List<TermParameterDefinitionContract> parameterList = new ArrayList<TermParameterDefinitionContract>();
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.DURATION_TYPE_KEY.getId(),null,null,0L));
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.DURATION_KEY.getId(),null,null,0L));
		term2 = KRMSDataGenerator.createTermDefinition(null,null,parameterList,null,0L);
	}

	@Before
	public void beforeMethod() {
        atpContext.setTypeService(typeService);
        type1 = new TypeInfo();
        type1.setKey("kuali.atp.type.TestAtp1");
        type1.setName("ATP test create1");
        type1.setDescr(new RichTextHelper().fromPlain("ATP description 1"));
        type1.setEffectiveDate(new Date());
        type1.setRefObjectUri(AtpServiceConstants.REF_OBJECT_URI_ATP);
        try {
            ContextInfo context = new ContextInfo();
            context.setPrincipalId("testUser1");
            context.setCurrentDate(new Date());
            typeService.createType(type1.getKey(), type1, context);
        } catch (AlreadyExistsException e) {
            //Might already be inserted
        }
        catch (Exception e) {
            Assert.fail("Problem creating test data in the Type Service");
        }
        setupTerm1();
        setupTerm2();
    }

	@Test
    public void testCreateContextMap_Atp() throws OperationFailedException {
		Map<String, Object> contextMap = atpContext.createContextMap(term, ContextUtils.getContextInfo());
        String duration = (String) contextMap.get(AtpContextImpl.DURATION_TOKEN);
        TypeInfo durationType = (TypeInfo) contextMap.get(AtpContextImpl.DURATION_TYPE_TOKEN);

		Assert.assertNotNull(contextMap);
        Assert.assertEquals("ATP-1", duration);
        Assert.assertEquals("kuali.atp.type.TestAtp1", durationType.getKey());
    }

    @Test
    public void testCreateContextMap_NullTokenValues() throws OperationFailedException {
        Map<String, Object> contextMap = atpContext.createContextMap(term2, ContextUtils.getContextInfo());
        String duration = (String) contextMap.get(AtpContextImpl.DURATION_TOKEN);
        TypeInfo durationType = (TypeInfo) contextMap.get(AtpContextImpl.DURATION_TYPE_TOKEN);

        Assert.assertNotNull(contextMap);
        Assert.assertEquals(null, duration);
        Assert.assertEquals(null, durationType);

	}

}
