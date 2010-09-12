/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.core.statement.dao.impl;

import java.util.Arrays;
import java.util.GregorianCalendar;
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
import org.kuali.student.core.statement.entity.NlUsageType;
import org.kuali.student.core.statement.entity.ObjectSubType;
import org.kuali.student.core.statement.entity.ObjectType;
import org.kuali.student.core.statement.entity.RefStatementRelation;
import org.kuali.student.core.statement.entity.RefStatementRelationType;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.entity.ReqComponentType;
import org.kuali.student.core.statement.entity.ReqComponentTypeNLTemplate;
import org.kuali.student.core.statement.entity.Statement;
import org.kuali.student.core.statement.entity.StatementType;

@PersistenceFileLocation("classpath:META-INF/statement-persistence.xml")
public class TestStatementDao extends AbstractTransactionalDaoTest {
    @Dao(value = "org.kuali.student.core.statement.dao.impl.StatementDaoImpl", testSqlFile = "classpath:ks-statement-config.sql,ks-statement-data.sql")
    public StatementDao dao;

    @Test
    public void testGetRefStatementRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
    	RefStatementRelation refStmtRel = dao.fetch(RefStatementRelation.class, "ref-stmt-rel-1") ;

        GregorianCalendar effDate = new GregorianCalendar(2000, 00, 01, 0, 0, 0);
        GregorianCalendar expDate = new GregorianCalendar(2100, 11, 31, 0, 0, 0);
        GregorianCalendar createTime = new GregorianCalendar(2000, 00, 01, 0, 0, 0);
        GregorianCalendar updateTime = new GregorianCalendar(2001, 00, 01, 0, 0, 0);

        assertNotNull(refStmtRel);
        assertEquals("ref-stmt-rel-1", refStmtRel.getId());
        assertEquals(effDate.getTime(), refStmtRel.getEffectiveDate());
        assertEquals(expDate.getTime(), refStmtRel.getExpirationDate());
        assertEquals("CREATEID", refStmtRel.getMeta().getCreateId());
        assertEquals(createTime.getTime(), refStmtRel.getMeta().getCreateTime());
        assertEquals("UPDATEID", refStmtRel.getMeta().getUpdateId());
        assertEquals(updateTime.getTime(), refStmtRel.getMeta().getUpdateTime());
        assertEquals(1, refStmtRel.getVersionInd());
        // Ref object type and object id
        assertEquals("CLU-NL-1", refStmtRel.getRefObjectId());
        assertEquals("clu", refStmtRel.getRefObjectTypeKey());
        // RefStatementRelationType
        RefStatementRelationType type = refStmtRel.getRefStatementRelationType();
        assertEquals("clu.prerequisites", type.getId());
        assertEquals("CLU Prereq", type.getName());
        assertEquals(effDate.getTime(), type.getEffectiveDate());
        assertEquals(expDate.getTime(), type.getExpirationDate());
        // ObjectSubTypes
        assertEquals(1, type.getObjectSubTypeList().size());
        // StatementTypes
        assertEquals(1, type.getStatementTypeList().size());
        // Statement
        Statement stmt = refStmtRel.getStatement();
        assertEquals("STMT-1", stmt.getId());
    }

    @Test
    public void testGetRefStatementRelationType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
    	RefStatementRelationType refStmtRelType = dao.fetch(RefStatementRelationType.class, "clu.prerequisites") ;

        GregorianCalendar gregEff = new GregorianCalendar(2000, 00, 01, 0, 0, 0);
        GregorianCalendar gregExp = new GregorianCalendar(2100, 11, 31, 0, 0, 0);

        assertNotNull(refStmtRelType);
        assertEquals("clu.prerequisites", refStmtRelType.getId());
        assertEquals("CLU Prereq", refStmtRelType.getName());
        assertEquals("CLU Prerequisites", refStmtRelType.getDescr());
        assertEquals(gregEff.getTime(), refStmtRelType.getEffectiveDate());
        assertEquals(gregExp.getTime(), refStmtRelType.getExpirationDate());
        // ObjectSubTypes
        assertEquals(1, refStmtRelType.getObjectSubTypeList().size());
        // StatementTypes
        assertEquals(1, refStmtRelType.getStatementTypeList().size());
    }

    @Test
    public void testGetObjectTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
    	ObjectType objectType = dao.fetch(ObjectType.class, "clu") ;

        GregorianCalendar gregEff = new GregorianCalendar(2000, 00, 01, 0, 0, 0);
        GregorianCalendar gregExp = new GregorianCalendar(2100, 11, 31, 0, 0, 0);

        assertNotNull(objectType);
        assertEquals("clu", objectType.getId());
        assertEquals("Kuali CLU", objectType.getName());
        assertEquals("Kuali CLU", objectType.getDescr());
        assertEquals(gregEff.getTime(), objectType.getEffectiveDate());
        assertEquals(gregExp.getTime(), objectType.getExpirationDate());
        // ObjectSubTypes
        assertEquals(2, objectType.getObjectSubTypes().size());
    }

    @Test
    public void testGetObjectSubTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
    	ObjectSubType objectSubType = dao.fetch(ObjectSubType.class, "program");

        GregorianCalendar gregEff = new GregorianCalendar(2000, 00, 01, 0, 0, 0);
        GregorianCalendar gregExp = new GregorianCalendar(2100, 11, 31, 0, 0, 0);

        assertNotNull(objectSubType);
        assertEquals("program", objectSubType.getId());
        assertEquals("Kuali Program", objectSubType.getName());
        assertEquals("Kuali Program", objectSubType.getDescr());
        assertEquals(gregEff.getTime(), objectSubType.getEffectiveDate());
        assertEquals(gregExp.getTime(), objectSubType.getExpirationDate());
    }

    @Test
    public void testGetNlUsageType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        NlUsageType nlUsageType = dao.fetch(NlUsageType.class, "KUALI.REQCOMP.EXAMPLE");

        GregorianCalendar gregEff = new GregorianCalendar(2010, 00, 01, 1, 1, 1);
        GregorianCalendar gregExp = new GregorianCalendar(2010, 11, 31, 1, 1, 1);
        
        assertNotNull(nlUsageType);
        assertEquals("NlUsageType[id=KUALI.REQCOMP.EXAMPLE]", nlUsageType.toString());
        assertEquals("KUALI.REQCOMP.EXAMPLE", nlUsageType.getId());
        assertEquals("Requirement Component Example", nlUsageType.getName());
        assertEquals("Kuali Requirement Component Rule Example", nlUsageType.getDescr());
        assertEquals(gregEff.getTime(), nlUsageType.getEffectiveDate());
        assertEquals(gregExp.getTime(), nlUsageType.getExpirationDate());
    }
    
    @Test
    public void testGetNlUsageTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        List<NlUsageType> nlUsageTypeList = dao.find(NlUsageType.class);

        GregorianCalendar gregEff = new GregorianCalendar(2000, 00, 01, 0, 0, 0);
        GregorianCalendar gregExp = new GregorianCalendar(2000, 11, 31, 0, 0, 0);
        
        assertNotNull(nlUsageTypeList);
        assertEquals(4, nlUsageTypeList.size());
        NlUsageType nlUsageType = nlUsageTypeList.get(2);
        assertEquals("KUALI.COURSE.CATALOG", nlUsageType.getId());
        assertEquals("Kuali Course Catalog", nlUsageType.getName());
        assertEquals("Full Kuali Course Catalog", nlUsageType.getDescr());
        assertEquals(gregEff.getTime(), nlUsageType.getEffectiveDate());
        assertEquals(gregExp.getTime(), nlUsageType.getExpirationDate());
    }
    
    @Test
    public void testGetStatementType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        StatementType stmtType = dao.fetch(StatementType.class, "kuali.luStatementType.prereqAcademicReadiness");
        
        assertNotNull(stmtType);
        assertEquals(1, stmtType.getRefStatementRelationTypes().size());
        RefStatementRelationType type = stmtType.getRefStatementRelationTypes().get(0);
        assertEquals("clu.prerequisites", type.getId());
        assertEquals("CLU Prereq", type.getName());
    }   
    
    @Test
    public void testGetStatementTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        List<StatementType> stmtTypeList = dao.find(StatementType.class);
        
        assertNotNull(stmtTypeList);
        assertEquals(3, stmtTypeList.size());
    }   
    
    @Test
    public void testGetReqComponentTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        List<ReqComponentType> reqCompTypeList = dao.find(ReqComponentType.class);
        
        assertNotNull(reqCompTypeList);
        assertEquals(9, reqCompTypeList.size());
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

//    @Test
//    public void testGetStatementHeaderTemplate() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
//        StatementType stmtType  = dao.fetch(StatementType.class, "kuali.luStatementType.prereqAcademicReadiness");
//
//        List<StatementTypeHeaderTemplate> templates = stmtType.getStatementHeaders();
//
//        StatementTypeHeaderTemplate header = templates.get(0);
//        assertEquals(templates.size(), 2);
//        assertEquals("KUALI.CATALOG", header.getNlUsageTypeKey());
//        // should the statement header template be "$clu.getLongName()" instead of 
//        //       "$clu.getOfficialIdentifier().getLongName()"? 
//        //       in the old test sql file ks-lu.sql it is "$clu.getOfficialIdentifier().getLongName()" 
//        //       but in ks-statement.sql it is "$clu.getLongName()".
//        assertEquals(header.getTemplate(), "Requirement for $clu.getLongName(): ");
//    }

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

    private boolean containsStatement(List<Statement> StatementList, String id) {
    	for(Statement statement : StatementList) {
    		if(statement.getId().equals(id)) return true;
    	}
    	return false;
    }

    @Test
    public void testGetStatementsForReqComponent() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        List<Statement> stmtList = dao.getStatementsForReqComponent("REQCOMP-NL-1");
        
        assertNotNull(stmtList);
        assertEquals(4, stmtList.size());
    	assertTrue(containsStatement(stmtList, "STMT-3"));
    	assertTrue(containsStatement(stmtList, "STMT-5"));
    	assertTrue(containsStatement(stmtList, "STMT-104"));
    	assertTrue(containsStatement(stmtList, "STMT-106"));
    }   
}
