package org.kuali.student.lum.lu.service.impl;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.lum.lu.service.LuService;


@Daos( { @Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl"/*,testSqlFile="classpath:ks-lu.sql", testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestLuServiceImpl extends AbstractServiceTest {
	@Client(value = "org.kuali.student.lum.lu.service.impl.LuServiceImpl", port = "8181")
	public LuService client;

	@Test
	public void testNothing(){
		
	}
}
