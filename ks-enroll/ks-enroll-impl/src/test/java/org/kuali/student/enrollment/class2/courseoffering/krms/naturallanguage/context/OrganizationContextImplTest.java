package org.kuali.student.enrollment.class2.courseoffering.krms.naturallanguage.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.core.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;

import java.util.HashMap;
import java.util.Map;

@Daos( { @Dao(value = "org.kuali.student.r1.core.organization.dao.impl.OrganizationDaoImpl", testSqlFile = "classpath:ks-org.sql") })
@PersistenceFileLocation("classpath:META-INF/organization-persistence.xml")
public class OrganizationContextImplTest extends AbstractServiceTest {

    @Client(value = "org.kuali.student.r2.core.class1.organization.service.impl.OrganizationServiceImpl", additionalContextFile = "classpath:nl-test-context.xml")
    private OrganizationService orgService;
    private OrganizationContextImpl orgContext = new OrganizationContextImpl();

	private Map<String, Object> term;
	private Map<String, Object> term2;

	private void setupTerm1() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(TermParameterTypes.ORGANIZATION_KEY.getId(),"1");
        term = parameters;
	}

	private void setupTerm2() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(TermParameterTypes.ORGANIZATION_KEY.getId(),null);
		term2 = parameters;
	}

	@Before
	public void beforeMethod() {
		orgContext.setOrganizationService(orgService);
		setupTerm1();
		setupTerm2();
	}

	@Test
    public void testCreateContextMap_Org() throws OperationFailedException {
		Map<String, Object> contextMap = orgContext.createContextMap(term);
        OrgInfo org = (OrgInfo) contextMap.get(OrganizationContextImpl.ORG_TOKEN);

		Assert.assertNotNull(contextMap);
		Assert.assertEquals("1", org.getId());

		Assert.assertEquals("KUSystem", org.getShortName());
		Assert.assertEquals("Kuali University System", org.getLongName());
	}
	
	@Test
    public void testCreateContextMap_NullTokenValues() {
        Map<String, Object> contextMap = orgContext.createContextMap(term2);
        OrgInfo org = (OrgInfo) contextMap.get(OrganizationContextImpl.ORG_TOKEN);

        Assert.assertNotNull(contextMap);
        Assert.assertNull(org);
	}

}
