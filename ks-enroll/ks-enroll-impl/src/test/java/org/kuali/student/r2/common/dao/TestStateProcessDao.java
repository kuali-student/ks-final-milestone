package org.kuali.student.r2.common.dao;

import org.kuali.student.r2.core.class1.state.dao.LifecycleDao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.core.class1.state.model.LifecycleEntity;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

@PersistenceFileLocation("classpath:META-INF/acal-persistence.xml")
public class TestStateProcessDao extends AbstractTransactionalDaoTest{
	@Dao(value = "org.kuali.student.r2.core.class1.state.dao.LifecycleDao", testSqlFile = "classpath:ks-common.sql")
    private LifecycleDao dao;
	
	@Test
	public void testGetProcessByKey(){
		LifecycleEntity sp = dao.getProcessByKey(AtpServiceConstants.ATP_PROCESS_KEY);
		assertNotNull(sp);
		assertEquals(AtpServiceConstants.ATP_PROCESS_KEY, sp.getId());
        assertEquals("kuali atp state process", sp.getDescription());
	}
}
