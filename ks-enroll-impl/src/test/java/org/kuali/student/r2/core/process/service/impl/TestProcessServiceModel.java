/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Mezba Mahtab on 6/8/12
 */
package org.kuali.student.r2.core.process.service.impl;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.process.dao.CheckDao;
import org.kuali.student.r2.core.process.model.CheckEntity;
import org.kuali.student.r2.core.process.service.ProcessService;
import org.mortbay.log.Log;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This class tests the database models for Process Service.
 *
 * @author Mezba Mahtab
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:process-test-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestProcessServiceModel {

    ///////////////////
    // CONSTANTS
    ///////////////////

    private static final Logger logger = Logger.getLogger(TestProcessServiceModel.class);

    ///////////////////
    // DATA FIELDS
    ///////////////////

    public static String principalId = "123";

    public ContextInfo callContext = null;

    @Resource (name = "processServiceImpl")
    private ProcessService processService;

    @Resource
    private CheckDao checkDao;

    @Resource
    private DataSource dataSource;

    @Resource (name="JtaTxManager")
    private PlatformTransactionManager txManager;

    @Resource
    private boolean debugMode;

    /////////////////////////
    // GETTERS AND SETTERS
    /////////////////////////

    public ProcessService getProcessService() {
        return processService;
    }

    public void setProcessService(ProcessService processService) {
        this.processService = processService;
    }

    public CheckDao getCheckDao() {
        return checkDao;
    }

    public void setCheckDao(CheckDao checkDao) {
        this.checkDao = checkDao;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public PlatformTransactionManager getTxManager() {
        return txManager;
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    ////////////////////
    // FUNCTIONALS
    ////////////////////

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        try {
            if (debugMode) { Log.warn ("Creating loader"); }
            ProcessServiceModelTestDataLoader loader = new ProcessServiceModelTestDataLoader(checkDao, logger);
            loader.setDebugMode(debugMode);
            if (debugMode) { Log.warn("Calling loader.loadData()"); }
            loader.loadData();
        } catch (Exception ex) {
            throw new RuntimeException (ex);
        }
    }

    @Test
    public void testAttributePersistance() throws Exception {
        assertNotNull(processService);
        List<CheckEntity> checks = checkDao.getByName("is alive");
        for (CheckEntity check: checks) {
            assertNotNull(check);
            if (debugMode) { Log.warn("Retrieved: " + check) ; }
        }
        // check the schema
        validateSchemaAndContent("select * from KSEN_PROCESS_CHECK", 18);
        validateSchemaAndContent("select * from KSEN_PROCESS_CHECK_ATTR", 5);

    }

    private void validateSchemaAndContent (String query, int numberOfColumnsExpected) throws SQLException {
        Statement stmt = dataSource.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();
        if (debugMode) {
            for (int i = 0; i < cols; i++) {
                logger.warn("col [" + i + "] : " + meta.getColumnLabel(i + 1) + " " + meta.getColumnTypeName(i + 1));
            }
        }
        assertEquals(numberOfColumnsExpected, cols);
        /*
        int rowNum = 1;
        while (rs.next()) {
            String rowData = "";
            for (int i=1; i<=numberOfColumnsExpected; i++) {
                rowData += rs.getString(i);
                if (i<numberOfColumnsExpected) rowData+= ",";
            }
            if (debugMode) { logger.warn("row [" + rowNum + "] = " + rowData); }
            rowNum++;
        }
        assertEquals(numberOfRowsExpected, rowNum - 1);
        */
        rs.close();
        stmt.close();
    }

}
