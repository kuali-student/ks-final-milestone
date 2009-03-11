/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.factfinder;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.rules.factfinder.dao.impl.FactFinderDAOImpl;
import org.kuali.student.rules.factfinder.entity.LUIPerson;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This is a <code>FunctionalBusinessRuleDAOImpl</code> test class.
 * 
 * @author Kuali Student Team (zdenek.kuali@google.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@PersistenceFileLocation("classpath:META-INF/factfinder-persistence.xml")
public class TestFactFinderDAO extends AbstractTransactionalDaoTest {

    @Dao(value = "org.kuali.student.rules.factfinder.dao.impl.FactFinderDAOImpl", testDataFile = "classpath:fact-data-beans.xml")
    public FactFinderDAOImpl factFinderDAO;
    
    @Test
    public void testLookupLPR() {
        List<LUIPerson> lprList = factFinderDAO.lookupByStudentId("student1");
        
        assertEquals(3, lprList.size());
        
        assertEquals("PSYC 200", lprList.get(0).getCluId());
        assertEquals("2.5", lprList.get(1).getCredits().toString());
    }
    
    /**
     * @return the factFinderDAO
     */
    public FactFinderDAOImpl getFactFinderDAO() {
        return factFinderDAO;
    }

    /**
     * @param factFinderDAO the factFinderDAO to set
     */
    public void setFactFinderDAO(FactFinderDAOImpl factFinderDAO) {
        this.factFinderDAO = factFinderDAO;
    }
}
