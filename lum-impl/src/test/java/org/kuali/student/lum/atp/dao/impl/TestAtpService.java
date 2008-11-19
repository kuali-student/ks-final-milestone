package org.kuali.student.lum.atp.dao.impl;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.lum.atp.service.AtpService;

@Daos( { @Dao(value = "org.kuali.student.lum.atp.dao.impl.AtpDaoImpl", testDataFile = "classpath:test-beans.xml") })
@PersistenceFileLocation("classpath:META-INF/atp-persistence.xml")
public class TestAtpService extends AbstractServiceTest {
	@Client(value = "org.kuali.student.lum.atp.service.impl.AtpServiceImpl", port = "8181")
	public AtpService client;
	
	@Test
	public void TestNothing(){
		
	}
	
}
