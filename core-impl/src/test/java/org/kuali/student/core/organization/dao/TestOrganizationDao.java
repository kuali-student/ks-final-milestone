package org.kuali.student.core.organization.dao;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.organization.entity.Org;
import org.kuali.student.core.organization.entity.OrgHierarchy;
import org.kuali.student.core.organization.entity.OrgType;
import org.springframework.core.io.ClassPathResource;

@PersistenceFileLocation("classpath:META-INF/organization-persistence.xml")
public class TestOrganizationDao extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.core.organization.dao.impl.OrganizationDaoImpl")
	public OrganizationDao dao;

	private boolean preloaded = false;
	
	private void loadSql(String sql){
		em.createNativeQuery(sql).executeUpdate();
	}
	
	@Before
	public void loadOrgTestData() throws IOException {
		if(!preloaded){
			ClassPathResource cpr = new ClassPathResource("ks-org.sql");
			BufferedReader in
			   = new BufferedReader(new FileReader(cpr.getFile()));
			String ln;
			while((ln=in.readLine())!=null){
				if(!ln.startsWith("/")&&!ln.isEmpty()){
					loadSql(ln);
				}
			}
			preloaded=true;
		}
	}
	
	@Test
	public void testCreateOrganization(){

		
		
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
		
		assertNotNull(dao.fetch(OrgHierarchy.class, "kuali.org.hierarchy.Curriculum"));
		
	}
}