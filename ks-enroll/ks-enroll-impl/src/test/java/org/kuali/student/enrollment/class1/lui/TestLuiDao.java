package org.kuali.student.enrollment.class1.lui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.enrollment.class1.lui.dao.LuiDao;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

//TODO: works fine in eclipse. un-ignore when fixing mvn build
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
			assertEquals("Lui one", obj.getName()); 
			assertEquals("ENGL 100 section 123", obj.getLuiCode());
	        assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, obj.getLuiState().getId()); 
	        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, obj.getLuiType().getId()); 
	        assertEquals("Lui Desc 101", obj.getDescr().getPlain());  
		}catch (Exception ex){
			ex.printStackTrace();
		}		
	}
}
