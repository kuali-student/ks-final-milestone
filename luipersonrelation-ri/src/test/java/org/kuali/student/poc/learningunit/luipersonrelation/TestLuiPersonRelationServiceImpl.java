package org.kuali.student.poc.learningunit.luipersonrelation;

import org.junit.Test;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService;

//@Daos({@Dao("org.kuali.student.poc.personidentity.person.dao.PersonDAOImpl")})
@PersistenceFileLocation("classpath:META-INF/luipersonrelation-persistence.xml")   
public class TestLuiPersonRelationServiceImpl {

    @Client(value="org.kuali.student.poc.learningunit.luipersonrelation.service.LuiPersonRelationServiceImpl", port="9191")
    public LuiPersonRelationService client;
    
    @Test
    public void dummy() {
        
    }

}
