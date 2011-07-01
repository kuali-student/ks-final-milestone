package org.kuali.student.enrollment.class1.lui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.enrollment.class1.lui.dao.LuiLuiRelationDao;
import org.kuali.student.enrollment.class1.lui.model.LuiLuiRelationEntity;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

//TODO: works fine in eclipse. un-ignore when fixing mvn build
@Ignore
@PersistenceFileLocation("classpath:META-INF/acal-persistence.xml")
public class TestLuiLuiRelationDao extends AbstractTransactionalDaoTest{
	@Dao(value = "org.kuali.student.enrollment.class1.lui.dao.LuiLuiRelationDao", testSqlFile = "classpath:ks-lui.sql")
	private LuiLuiRelationDao dao;
	
	@Test
	public void testGetLuiLuiRelation(){
		LuiLuiRelationEntity obj = dao.find("LUILUIREL-1");
		assertNotNull(obj);
        assertEquals(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, obj.getLuiluiRelationState().getId()); 
        assertEquals(LuiServiceConstants.ASSOCIATED_LUI_LUI_RELATION_TYPE_KEY, obj.getLuiLuiRelationType().getId()); 
        assertEquals("Lui-1", obj.getLui().getId());
        assertEquals("Lui-2", obj.getRelatedLui().getId());
	}
}
