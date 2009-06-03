package org.kuali.student.lum.lu.dao.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.Lui;

@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestLuDaoImpl extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql")
	public LuDao dao;

	@Test
	public void testGetLuLuRelationTypeInfo(){
		List<Lui> luis = dao.getLuisByRelationType("LUI-3", "luLuType.type1");
		assertEquals(1, luis.size());
		assertEquals("LUI-1", luis.get(0).getId());
	}
}