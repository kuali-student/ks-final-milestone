package org.kuali.student.enrollment.class1.hold.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.enrollment.class1.hold.model.HoldEntity;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;

//@Ignore
@PersistenceFileLocation("classpath:META-INF/acal-persistence.xml")
public class TestHoldDao extends AbstractTransactionalDaoTest{
	@Dao(value = "org.kuali.student.enrollment.class1.hold.dao.HoldDao", testSqlFile = "classpath:ks-hold.sql")
	private HoldDao dao;
	
	@Test
	public void testGetHold(){
		try{
			HoldEntity obj = dao.find("Hold-1");
			assertNotNull(obj);
	        assertEquals("Hold one", obj.getName()); 
	        assertEquals(HoldServiceConstants.HOLD_ACIVE_STATE_KEY, obj.getHoldState().getId()); 
	        assertEquals(HoldServiceConstants.STUDENT_HOLD_TYPE_KEY, obj.getHoldType().getId()); 
	        assertEquals("Hold Desc student", obj.getDescr().getPlain());  
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
