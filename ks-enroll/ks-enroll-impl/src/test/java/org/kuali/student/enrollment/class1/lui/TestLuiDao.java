package org.kuali.student.enrollment.class1.lui;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.enrollment.class1.lui.dao.LuiDao;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;

//TODO: un-ignore when fixing mvn build
@Ignore
@PersistenceFileLocation("classpath:META-INF/acal-persistence.xml")
public class TestLuiDao extends AbstractTransactionalDaoTest{
	@Dao(value = "org.kuali.student.enrollment.class1.lui.dao.LuiDao", testSqlFile = "classpath:ks-lui.sql")
	private LuiDao dao;
	
	@Test
	public void testGetLui(){
		try{
			LuiEntity obj = dao.find("Lui-1");
			assertNotNull(obj);
		}catch (Exception ex){
			ex.printStackTrace();
		}		
	}
}
