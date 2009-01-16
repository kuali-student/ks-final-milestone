package org.kuali.student.core.organization.dao;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.organization.entity.Org;
import org.kuali.student.core.organization.entity.OrgHierarchy;
import org.kuali.student.core.organization.entity.OrgType;

@PersistenceFileLocation("classpath:META-INF/organization-persistence.xml")
public class TestOrganizationDao extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.core.organization.dao.impl.OrganizationDaoImpl")
	public OrganizationDao dao;

	@Test
	public void testCreateOrganization() {
		
		OrgType orgType = new OrgType();
		orgType.setKey("kuali.org.CorporateEntity");
		orgType.setName("Corporate Entity");
		orgType.setDesc("A legal corporate entity");
		
		Org org = new Org();
		org.setType(orgType);
		org.setShortName("KU");
		org.setDesc("Kuali University");
		
		OrgHierarchy orgHierarchy = new OrgHierarchy();
		orgHierarchy.setKey("kuali.org.Main");
		orgHierarchy.setName("Main");
		orgHierarchy.setDesc("Main Organizational Hierarchy");
		orgHierarchy.setRootOrg(org);
		
		dao.create(orgType);
		dao.create(org);
		dao.create(orgHierarchy);
		
	}
}