package org.kuali.student.r2.common.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.common.model.StateProcessEntity;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

@Ignore
@PersistenceFileLocation("classpath:META-INF/acal-persistence.xml")
public class TestStateProcessDao extends AbstractTransactionalDaoTest{
	@Dao(value = "org.kuali.student.r2.common.dao.StateProcessDao", testSqlFile = "classpath:ks-common.sql")
    private StateProcessDao dao;
	
	@Test
	public void testGetProcessByKey(){
		StateProcessEntity sp = dao.getProcessByKey(AtpServiceConstants.ATP_PROCESS_KEY);
		assertNotNull(sp);
		assertEquals(sp.getId(), AtpServiceConstants.ATP_PROCESS_KEY);   
	}
}
