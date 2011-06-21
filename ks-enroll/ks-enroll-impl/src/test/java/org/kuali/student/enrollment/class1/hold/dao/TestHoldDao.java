package org.kuali.student.enrollment.class1.hold.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.enrollment.class1.hold.model.HoldEntity;
import org.kuali.student.enrollment.class1.hold.model.HoldRichTextEntity;
import org.kuali.student.enrollment.class1.hold.model.RestrictionEntity;
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
	
	@Test
	public void testCreateHold(){
		try{
			HoldEntity existingEntity = dao.find("Hold-1");
	        
			HoldEntity obj = new HoldEntity();
			obj.setName("Hold Test");
			obj.setDescr(new HoldRichTextEntity("plain", "formatted"));
			obj.setHoldState(existingEntity.getHoldState());
			obj.setHoldType(existingEntity.getHoldType());
			obj.setIssue(existingEntity.getIssue());
	        dao.persist(obj);
	        assertNotNull(obj.getId());
	        HoldEntity obj2 = dao.find(obj.getId());
	        assertEquals("Hold Test", obj2.getName());         
	        assertEquals("plain", obj2.getDescr().getPlain()); 
	        assertEquals(HoldServiceConstants.HOLD_ACIVE_STATE_KEY, obj2.getHoldState().getId()); 
	        assertEquals(HoldServiceConstants.STUDENT_HOLD_TYPE_KEY, obj2.getHoldType().getId()); 
	        assertEquals("Hold-Issue-1", obj2.getIssue().getId()); 
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateRestriction(){
		try{
			HoldEntity existingEntity = dao.find("Hold-1");
	       
			existingEntity.setName("Hold Updated");
			existingEntity.setDescr(new HoldRichTextEntity("plain", "formatted"));
	        dao.merge(existingEntity);

	        HoldEntity obj2 = dao.find(existingEntity.getId());
	        assertEquals("Hold Updated", obj2.getName());         
	        assertEquals("plain", obj2.getDescr().getPlain()); 
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
    @Test
    public void testDeleteRestriction() 
    {
    	try{
    		HoldEntity obj = dao.find("Hold-2");
	        assertNotNull(obj);
	        dao.remove(obj);
	        HoldEntity old = dao.find("Hold-2");
	        assertNull(old);
		}catch (Exception ex){
			ex.printStackTrace();
		}
    }
}
