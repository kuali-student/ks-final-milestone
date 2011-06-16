package org.kuali.student.enrollment.class1.hold.dao;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.enrollment.class1.hold.model.RestrictionEntity;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;
import org.kuali.student.r2.core.class1.atp.model.AtpEntity;
import org.kuali.student.r2.core.class1.atp.model.AtpRichTextEntity;
//@Ignore
@PersistenceFileLocation("classpath:META-INF/acal-persistence.xml")
public class TestRestrictionDao extends AbstractTransactionalDaoTest{
	@Dao(value = "org.kuali.student.enrollment.class1.hold.dao.RestrictionDao", testSqlFile = "classpath:ks-atp.sql")
	private RestrictionDao dao;
	
	@Test
	public void testGetRestriction(){
		try{
			RestrictionEntity obj = dao.find("Hold-Restriction-1");
			assertNotNull(obj);
	        assertEquals("Restriction one", obj.getName()); 
	        assertEquals(HoldServiceConstants.RESTRICTION_ACIVE_STATE_KEY, obj.getRestrictionState().getId()); 
	        assertEquals(HoldServiceConstants.REGISTERATION_RESTRICTION_TYPE_KEY, obj.getRestrictionType().getId()); 
	        assertEquals("Hold Desc 101", obj.getDescr().getPlain());  
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	@Test
	public void testCreateRestriction(){
		try{
			RestrictionEntity existingEntity = dao.find("Hold-Restriction-1");
	        
			RestrictionEntity obj = new RestrictionEntity();
			obj.setName("Restriction Test");
			obj.setDescr(new AtpRichTextEntity("plain", "formatted"));
			obj.setRestrictionState(existingEntity.getRestrictionState());
			obj.setRestrictionType(existingEntity.getRestrictionType());
	        dao.persist(obj);
	        assertNotNull(obj.getId());
	        RestrictionEntity obj2 = dao.find(obj.getId());
	        assertEquals("Restriction Test", obj2.getName());         
	        assertEquals("plain", obj2.getDescr().getPlain()); 
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateRestriction(){
		try{
			RestrictionEntity existingEntity = dao.find("Hold-Restriction-1");
	       
			existingEntity.setName("Restriction Updated");
			existingEntity.setDescr(new AtpRichTextEntity("plain", "formatted"));
	        dao.merge(existingEntity);

	        RestrictionEntity obj2 = dao.find(existingEntity.getId());
	        assertEquals("Restriction Updated", obj2.getName());         
	        assertEquals("plain", obj2.getDescr().getPlain()); 
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
    @Test
    public void testDeleteRestriction() 
    {
    	try{
	    	RestrictionEntity obj = dao.find("Hold-Restriction-2");
	        assertNotNull(obj);
	        dao.remove(obj);
	        RestrictionEntity old = dao.find("Hold-Restriction-2");
	        assertNull(old);
		}catch (Exception ex){
			ex.printStackTrace();
		}
    }
}
