package org.kuali.student.enrollment.class1.hold.dao;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.enrollment.class1.hold.model.RestrictionEntity;
//@Ignore
@PersistenceFileLocation("classpath:META-INF/acal-persistence.xml")
public class TestRestrictionDao extends AbstractTransactionalDaoTest{
	@Dao(value = "org.kuali.student.enrollment.class1.hold.dao.RestrictionDao", testSqlFile = "classpath:ks-atp.sql")
	private RestrictionDao dao;
	
	@Test
	public void testGetRestriction(){
		try{
			RestrictionEntity restriction = dao.find("Hold-Restriction-1");
			assertNotNull(restriction);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
