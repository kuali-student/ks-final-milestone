package org.kuali.student.core.statement.dao.impl;

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
import org.kuali.student.core.statement.dao.StatementDao;
import org.kuali.student.core.statement.entity.StatementType;

@PersistenceFileLocation("classpath:META-INF/statement-persistence.xml")
public class TestStatementDao extends AbstractTransactionalDaoTest {
    @Dao(value = "org.kuali.student.core.statement.dao.impl.StatementDaoImpl", testSqlFile = "classpath:ks-statement.sql")
    public StatementDao dao;
    
    @Test
    public void testGetLuStatementTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        List<StatementType> stmtTypeList = dao.find(StatementType.class);
        
        assertNotNull(stmtTypeList);
        assertEquals(3, stmtTypeList.size());
    }   
    
    
}
