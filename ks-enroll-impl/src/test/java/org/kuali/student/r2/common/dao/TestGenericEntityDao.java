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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:genericentitydao-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestGenericEntityDao {

    @Autowired
    protected StateDao stateDao;

    protected  List<String> primaryKeys = new ArrayList<String>();

    @Before
    public void setUp() {

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
            stateDao.persist(stateEntity);

            primaryKeys.add(stateEntity.getId());
        }

        stateDao.em.flush();

    }

    @Test
    public void testStateDao() {

        try {

            List<StateEntity> lstStateEntity1 = stateDao.findByIds("id", primaryKeys);

            assertNotEquals(0,lstStateEntity1.size());


            stateDao.setEnableMaxIdFetch(false);
            List<StateEntity> lstStateEntity2 = null;
            try {
                lstStateEntity2 = stateDao.findByIds("id", primaryKeys);
            } catch(Exception e) {
                e.printStackTrace();
            }
            assertNull("Maximum \'IN CLAUSE\' elements check : This test case can be failed if target database could allow more than 1000 elements in IN clause. In such case IGNORE this failure : ",lstStateEntity2);

        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
