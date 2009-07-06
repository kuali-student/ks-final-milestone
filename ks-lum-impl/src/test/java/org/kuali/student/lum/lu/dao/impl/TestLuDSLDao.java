package org.kuali.student.lum.lu.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.LuStatementTypeHeaderTemplate;
import org.kuali.student.lum.lu.entity.LuStatementType;
import org.kuali.student.lum.lu.entity.ReqComponentTypeNLTemplate;
import org.kuali.student.lum.lu.entity.ReqComponentType;

@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestLuDSLDao extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql")
	public LuDao dao;

	@Test
	public void testGetLuStatementTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
	    List<LuStatementType> stmtTypeList = dao.find(LuStatementType.class);
	    
	    assertNotNull(stmtTypeList);
	    assertEquals(3, stmtTypeList.size());
	}	

    @Test
    public void testGetReqComponentTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        List<ReqComponentType> reqCompTypeList = dao.find(ReqComponentType.class);
        
        assertNotNull(reqCompTypeList);
        assertEquals(8, reqCompTypeList.size());
    }
    
    @Test
    public void testGetReqCompNLTemplate() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
    	ReqComponentType reqComp  = dao.fetch(ReqComponentType.class, "kuali.reqCompType.courseList.nof");

        List<ReqComponentTypeNLTemplate> templates = reqComp.getNlUsageTemplates();

        assertEquals(templates.size(), 3);
        
        ReqComponentTypeNLTemplate template = null;
        if (templates.get(0).getNlUsageTypeKey().equals("KUALI.CATALOG")) {
        	template = templates.get(0);
        } else {
        	template = templates.get(1);
        }
        assertEquals("KUALI.CATALOG", template.getNlUsageTypeKey());
        assertTrue(template.getTemplate().startsWith("Student must have completed $expectedValue"));
    }

    @Test
    public void testGetStatementHeaderTemplate() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
    	LuStatementType stmtType  = dao.fetch(LuStatementType.class, "kuali.luStatementType.prereqAcademicReadiness");

        List<LuStatementTypeHeaderTemplate> templates = stmtType.getHeaders();

        LuStatementTypeHeaderTemplate header = templates.get(0);
        assertEquals(templates.size(), 2);
        assertEquals("KUALI.CATALOG", header.getNlUsageTypeKey());
        assertEquals(header.getTemplate(), "Requirement for $clu.getOfficialIdentifier().getLongName(): ");
    }
}
