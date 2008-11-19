package org.kuali.student.enumeration.service.impl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.kuali.student.enumeration.dao.impl.EnumerationManagementDAOImpl;
import org.kuali.student.enumeration.dto.EnumerationMeta;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
@PersistenceFileLocation("classpath:META-INF/enumerationmanagement-persistence.xml")
public class EnumerationServiceImplTest extends AbstractTransactionalDaoTest{
    @Dao(value = "org.kuali.student.enumeration.dao.impl.EnumerationManagementDAOImpl", testDataFile = "classpath:test-beans.xml")
    public EnumerationManagementDAOImpl enumerationManagementDAO;
	
    public EnumerationServiceImpl enumService = new EnumerationServiceImpl();
    
	@Test
	public void testSetEnumDAO(){
		enumService.setEnumDAO(enumerationManagementDAO);
	}
	
	@Test
	public void testFindEnumerationMetas(){
		enumService.setEnumDAO(enumerationManagementDAO);
		EnumerationMeta meta = enumService.fetchEnumerationMeta("key 1");
		assertEquals(meta.getDesc(), "desc 1");
	}
}
