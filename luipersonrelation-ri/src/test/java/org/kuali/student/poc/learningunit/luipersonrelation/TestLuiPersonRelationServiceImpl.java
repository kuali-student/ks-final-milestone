package org.kuali.student.poc.learningunit.luipersonrelation;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo;

@Daos({@Dao("org.kuali.student.poc.learningunit.luipersonrelation.dao.impl.LuiPersonRelationDAOImpl")})
@PersistenceFileLocation("classpath:META-INF/luipersonrelation-persistence.xml")   
public class TestLuiPersonRelationServiceImpl extends AbstractServiceTest{

    @Client(value="org.kuali.student.poc.learningunit.luipersonrelation.service.LuiPersonRelationServiceImpl", port="9191")
    public LuiPersonRelationService client;
    
    @Test
    public void testIsValidLuiPersonRelation() throws Exception{
        LuiPersonRelationTypeInfo lprTypeInfo = new LuiPersonRelationTypeInfo();
        lprTypeInfo.setName("Student");
        
        RelationStateInfo relationStateInfo = new RelationStateInfo();
        relationStateInfo.setState("Add");
        
        assertTrue(client.isValidLuiPersonRelation("person id", "lui id", lprTypeInfo, relationStateInfo));        
        
    }

}
