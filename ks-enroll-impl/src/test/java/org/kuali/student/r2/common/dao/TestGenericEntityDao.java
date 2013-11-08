package org.kuali.student.r2.common.dao;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.util.query.QueryUtil;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.core.class1.state.dao.StateDao;
import org.kuali.student.r2.core.class1.state.model.StateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:state-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestGenericEntityDao {
    private static final int MAXIMUM_ENTITIES = 4000;

    @Autowired
    private StateDao stateDao;

    private List<String> primaryKeys = new ArrayList<String>();

    private String queryString;

    private class TestStateDao extends GenericEntityDao<StateEntity> {
        @Override
        public List<StateEntity> findByIds(String primaryKeyMemberName, List<String> primaryKeys) throws DoesNotExistException {

            // fix for jira KSENROLL-2949
            if (primaryKeys == null || primaryKeys.isEmpty())
                return new ArrayList<StateEntity>();

            Set<String>primaryKeySet = new HashSet<String>(primaryKeys.size());
            // remove duplicates from the key list
            primaryKeySet.addAll(primaryKeys);

            StringBuilder queryStringRef = new StringBuilder();
            queryStringRef.append("from ").append(entityClass.getSimpleName()).append(" where ");

            QueryUtil queryUtil = new QueryUtil();
            queryUtil.setEntityManager(em);
            queryUtil.setMaxInClauseElements(maxInClauseElements);
            boolean enableMaxIdFetch = false;
            if (!primaryKeys.isEmpty() && primaryKeys.size() > maxInClauseElements) {
                enableMaxIdFetch = true;
            }
            queryUtil.setEnableMaxIdFetch(enableMaxIdFetch);
            TypedQuery<StateEntity> query = queryUtil.buildQuery(queryStringRef, null, primaryKeyMemberName, primaryKeys, StateEntity.class);

            List<StateEntity> resultList = query.getResultList();

            verifyResults(resultList, primaryKeySet);

            queryString = queryStringRef.toString();

            return resultList;
        }
    }

    private TestStateDao testStateDao = new TestStateDao();

    @Before
    public void setUp() {

        testStateDao.setEm(stateDao.getEm());
        //Loading data and preparing primaryKeys list.
        for(int i = 0; i < MAXIMUM_ENTITIES; i++) {

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
            testStateDao.persist(stateEntity);

            primaryKeys.add(stateEntity.getId());
        }

        testStateDao.em.flush();
        queryString = "";

    }

    @Test
    public void testStateDao() {

        try {
            List<StateEntity> result = testStateDao.findByIds("id", primaryKeys);
            int queryCount = getSubstringCount(queryString, "IN");

            assertNotNull("Result should be non-null", result);
            assertEquals(MAXIMUM_ENTITIES + " entities should be returned", MAXIMUM_ENTITIES, result.size());
            assertEquals("We have asked for " + MAXIMUM_ENTITIES + " ids, so the query should have 4 'in' clauses", queryCount, 4);

            // set the DAO to not allow splitting up of query
            testStateDao.setMaxInClauseElements(5000);
            // re-run the query
            result = testStateDao.findByIds("id", primaryKeys);
            queryCount = getSubstringCount(queryString, "IN");

            assertNotNull("Result should be non-null", result);
            assertEquals(MAXIMUM_ENTITIES + " entities should be returned", MAXIMUM_ENTITIES, result.size());
            assertEquals("Since we disabled splitting up of query, it should have only one 'in' clause", queryCount, 1);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testStateDaoWithNullAndEmptyPrimaryKeys() throws Exception {
        // run query with null primary keys
        List<StateEntity> result = testStateDao.findByIds("id", null);
        assertNotNull("Result should be non-null", result);
        assertTrue("Result should be empty", result.isEmpty());
        assertTrue("Since the query never ran, the query string should be empty", queryString.isEmpty());

        // re-run query with empty primary keys
        testStateDao.findByIds("id", new ArrayList<String>());
        assertNotNull("Result should be non-null", result);
        assertTrue("Result should be empty", result.isEmpty());
        assertTrue("Since the query never ran, the query string should be empty", queryString.isEmpty());
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
