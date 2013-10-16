package org.kuali.student.r2.common.dao;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.core.class1.state.dao.StateDao;
import org.kuali.student.r2.core.class1.state.model.StateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:state-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestGenericEntityDao {

    @Autowired
    protected StateDao stateDao;

    protected  List<String> primaryKeys = new ArrayList<String>();

    protected  StringBuilder queryStringRefLocal = new  StringBuilder();

    protected class TestStateDao extends GenericEntityDao<StateEntity> {
        @Override
        protected Query buildQuery(StringBuilder queryStringRef, String primaryKeyMemberName, Set<String> primaryKeySet) {
           Query q =  super.buildQuery(queryStringRef, primaryKeyMemberName, primaryKeySet);    //To change body of overridden methods use File | Settings | File Templates.
            queryStringRefLocal = queryStringRef;
            return q;
        }
    }

    protected    TestStateDao localTest = new TestStateDao();

    @Before
    public void setUp() {

        localTest.setEm(stateDao.getEm());
        //Loading data and preparing primaryKeys list.
        for(int i=0 ; i<4000 ; i++) {

            StateEntity stateEntity = new StateEntity();
            stateEntity.setCreateId("SYSTEMLOADER");
            stateEntity.setCreateTime(new Date());
            stateEntity.setDescrFormatted("The time slot is active " + i);
            stateEntity.setDescrPlain("The time slot is active"+i);
            stateEntity.setId("kuali.scheduling.timeslot.state.active" + i);
            stateEntity.setInitialState(false);
            stateEntity.setLifecycleKey("kuali.scheduling.timeslot.lifecycle"+i);
            stateEntity.setName("Active"+i);
            stateEntity.setVersionNumber(0L);
            localTest.persist(stateEntity);

            primaryKeys.add(stateEntity.getId());
        }

        localTest.em.flush();

    }

    @Test
    public void testStateDao() {

        try {

            List<StateEntity> lstStateEntity1 = localTest.findByIds("id", primaryKeys);

            int queryCount = getSubstringCount(queryStringRefLocal.toString(), "in");

            assertEquals("We have asked for 4K ids, so the query should have 4", queryCount, 4);


            localTest.setEnableMaxIdFetch(false);
            List<StateEntity> lstStateEntity2 = null;
            try {
                lstStateEntity2 = localTest.findByIds("id", primaryKeys);
            } catch(Exception e) {
                // for most DBs this will throw a javax.transaction.xa.XAException.
                e.printStackTrace();
            }


            assertNull("Maximum \'IN CLAUSE\' elements check : This test case can be failed if target database could allow more than 1000 elements in IN clause. In such case IGNORE this failure : ",lstStateEntity2);



        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    private int getSubstringCount(String fullString, String subString){

        int lastIndex = 0;
        int count =0;

        while(lastIndex != -1){

            lastIndex = fullString.indexOf(subString,lastIndex);

            if( lastIndex != -1){
                count ++;
                lastIndex+=subString.length();
            }
        }
        return count;
    }
}
