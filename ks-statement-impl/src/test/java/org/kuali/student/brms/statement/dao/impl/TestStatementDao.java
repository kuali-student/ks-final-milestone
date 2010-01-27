package org.kuali.student.brms.statement.dao.impl;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.brms.statement.dao.StatementDao;
import org.kuali.student.brms.statement.entity.ReqComponent;
import org.kuali.student.brms.statement.entity.ReqComponentType;
import org.kuali.student.brms.statement.entity.ReqComponentTypeNLTemplate;
import org.kuali.student.brms.statement.entity.Statement;
import org.kuali.student.brms.statement.entity.StatementType;
import org.kuali.student.brms.statement.entity.StatementTypeHeaderTemplate;

@PersistenceFileLocation("classpath:META-INF/statement-persistence.xml")
public class TestStatementDao extends AbstractTransactionalDaoTest {
    @Dao(value = "org.kuali.student.brms.statement.dao.impl.StatementDaoImpl", testSqlFile = "classpath:ks-statement.sql")
    public StatementDao dao;
    
    @Test
    public void testGetLuStatementTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        List<StatementType> stmtTypeList = dao.find(StatementType.class);
        
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
        StatementType stmtType  = dao.fetch(StatementType.class, "kuali.luStatementType.prereqAcademicReadiness");

        List<StatementTypeHeaderTemplate> templates = stmtType.getHeaders();

        StatementTypeHeaderTemplate header = templates.get(0);
        assertEquals(templates.size(), 2);
        assertEquals("KUALI.CATALOG", header.getNlUsageTypeKey());
        // FIXME should the statement header template be "$clu.getLongName()" instead of 
        //       "$clu.getOfficialIdentifier().getLongName()"? 
        //       in the old test sql file ks-lu.sql it is "$clu.getOfficialIdentifier().getLongName()" 
        //       but in ks-statement.sql it is "$clu.getLongName()".
        assertEquals(header.getTemplate(), "Requirement for $clu.getLongName(): ");
    }

    @Test
    public void testGetReqComponents() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        List<String> reqComponentIdList = Arrays.asList(new String[] {"REQCOMP-NL-1","REQCOMP-NL-2", "REQCOMP-NL-3", "REQCOMP-NL-4"});
        List<ReqComponent> reqCompList = dao.getReqComponents(reqComponentIdList);
        
        assertNotNull(reqCompList);
        assertEquals(4, reqCompList.size());
    }
    
    @Test
    public void testGetStatements() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        List<String> statementIdList = Arrays.asList(new String[] {"STMT-1","STMT-2"});
        List<Statement> stmtList = dao.getStatements(statementIdList);
        
        assertNotNull(stmtList);
        assertEquals(2, stmtList.size());
    }   
}
