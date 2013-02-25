package org.kuali.student.krms.naturallanguage.config.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.krms.api.repository.term.TermDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinitionContract;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.krms.naturallanguage.KRMSDataGenerator;
import org.kuali.student.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.r1.lum.statement.typekey.ReqComponentFieldTypes;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Daos( { @Dao(value = "org.kuali.student.r1.core.organization.dao.impl.OrganizationDaoImpl", testSqlFile = "classpath:ks-org.sql") })
@PersistenceFileLocation("classpath:META-INF/organization-persistence.xml")
public class OrganizationContextImplTest extends AbstractServiceTest {

    @Client(value = "org.kuali.student.r2.core.class1.organization.service.impl.OrganizationServiceImpl", additionalContextFile = "classpath:nl-test-context.xml")
    private OrganizationService orgService;
    private OrganizationContextImpl orgContext = new OrganizationContextImpl();

	private TermDefinitionContract term;
	private TermDefinitionContract term2;
	
	
	private void setupTerm1() {
        List<TermParameterDefinitionContract> parameterList = new ArrayList<TermParameterDefinitionContract>();
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null, TermParameterTypes.ORGANIZATION_KEY.getId(),"1",null,0L));
        term = KRMSDataGenerator.createTermDefinition(null,null,parameterList,null,0L);
	}

	private void setupTerm2() {
        List<TermParameterDefinitionContract> parameterList = new ArrayList<TermParameterDefinitionContract>();
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.ORGANIZATION_KEY.getId(),null,null,0L));
		term2 = KRMSDataGenerator.createTermDefinition(null,null,parameterList,null,0L);
	}

	@Before
	public void beforeMethod() {
		orgContext.setOrganizationService(orgService);
		setupTerm1();
		setupTerm2();
	}

	@Test
    public void testCreateContextMap_Org() throws OperationFailedException {
		Map<String, Object> contextMap = orgContext.createContextMap(term, ContextUtils.getContextInfo());
        OrgInfo org = (OrgInfo) contextMap.get(OrganizationContextImpl.ORG_TOKEN);

		Assert.assertNotNull(contextMap);
		Assert.assertEquals("1", org.getId());

		Assert.assertEquals("KUSystem", org.getShortName());
		Assert.assertEquals("Kuali University System", org.getLongName());
	}
	
	@Test
    public void testCreateContextMap_NullTokenValues() throws OperationFailedException {
        Map<String, Object> contextMap = orgContext.createContextMap(term2, ContextUtils.getContextInfo());
        OrgInfo org = (OrgInfo) contextMap.get(OrganizationContextImpl.ORG_TOKEN);

        Assert.assertNotNull(contextMap);
        Assert.assertEquals(null, org);
	}

}
