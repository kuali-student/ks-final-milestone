/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.lum.lu.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
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
import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.LuStatementTypeHeaderTemplate;
import org.kuali.student.lum.lu.entity.LuStatementType;
import org.kuali.student.lum.lu.entity.ReqComponent;
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

    @Test
    public void testGetReqComponents() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        List<String> reqComponentIdList = Arrays.asList(new String[] {"REQCOMP-NL-1","REQCOMP-NL-2", "REQCOMP-NL-3", "REQCOMP-NL-4"});
    	List<ReqComponent> reqCompList = dao.getReqComponents(reqComponentIdList);
        
        assertNotNull(reqCompList);
        assertEquals(4, reqCompList.size());
    }
    
	@Test
	public void testGetLuStatements() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        List<String> luStatementIdList = Arrays.asList(new String[] {"STMT-1","STMT-2"});
	    List<LuStatement> stmtList = dao.getLuStatements(luStatementIdList);
	    
	    assertNotNull(stmtList);
	    assertEquals(2, stmtList.size());
	}	
}
