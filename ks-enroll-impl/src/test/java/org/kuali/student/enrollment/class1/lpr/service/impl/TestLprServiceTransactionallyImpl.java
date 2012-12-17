/**
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.student.enrollment.class1.lpr.service.impl;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.common.test.util.ListOfStringTester;
import org.kuali.student.common.test.util.MetaTester;
import org.kuali.student.common.test.util.RelationshipTester;
import org.kuali.student.enrollment.class1.lpr.dao.LprDao;
import org.kuali.student.enrollment.class1.lpr.service.impl.mock.LprTestDataLoader;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:lpr-test-context.xml" })
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = false)
/**
 * Here all of the test methods are not transactional.
 * 
 * This allows us to call crud methods on a service and have then occur transactionally within each call (rather than inherit from an outer transactional scope).
 * 
 * We can also go behind and check out the resultant database content for consistency.
 * 
 * @author ocleirig
 *
 */
public class TestLprServiceTransactionallyImpl {

	private static final Logger log = Logger
			.getLogger(TestLprServiceTransactionallyImpl.class);

	public LprService getLprService() {
		return lprService;
	}

	public void setLprService(LprService lprService) {
		this.lprService = lprService;
	}

	@Resource
	private LprService lprService;

	@Resource
	private LprDao lprDao;

	@Resource
	private DataSource dataSource;

	@Resource(name = "JtaTxManager")
	private PlatformTransactionManager txManager;

	private TransactionTemplate txTemplate;

	private ContextInfo callContext;

	public LprDao getLprDao() {
		return lprDao;
	}

	public void setLprDao(LprDao lprDao) {
		this.lprDao = lprDao;
	}

	@Before
	public  void setUp() {
		// intentionally does not call super.setUp()

			String principalId = "123";
			callContext = new ContextInfo();
			callContext.setPrincipalId(principalId);

			txTemplate = new TransactionTemplate(txManager);

			txTemplate
					.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			txTemplate.execute(new TransactionCallback<Void>() {

				@Override
				public Void doInTransaction(TransactionStatus status) {

					try {
						
						if (lprDao.findAll().size() == 0)
							new LprTestDataLoader(lprDao).loadData();
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
					return null;
				}

			});

	}
	
	@After
	public void cleanup() {
		
	}

	

	
	

	/**
	 * Tests that the deleteOrphan JPA annotation is working properly.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteOrphanAnnotation() throws Exception {

		Assert.assertTrue(true);

		// test create
		LprInfo expected = new LprInfo();
		expected.setPersonId("person1");
		expected.setLuiId("lui1");
		expected.setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
		expected.setStateKey(LprServiceConstants.ASSIGNED_STATE_KEY);
		expected.setEffectiveDate(new Date());
		expected.setExpirationDate(new Date(new Date().getTime() + 1000));
		expected.setCommitmentPercent("100.00");
		expected.getResultValuesGroupKeys().add("rvg1");
		expected.getResultValuesGroupKeys().add("rvg2");
		new AttributeTester().add2ForCreate(expected.getAttributes());
		LprInfo actual = lprService.createLpr(expected.getPersonId(),
				expected.getLuiId(), expected.getTypeKey(), expected,
				callContext);
		assertNotNull(actual.getId());
		new RelationshipTester().check(expected, actual);
		new AttributeTester().check(expected.getAttributes(),
				actual.getAttributes());
		new MetaTester().checkAfterCreate(actual.getMeta());
		new ListOfStringTester().check(expected.getResultValuesGroupKeys(),
				actual.getResultValuesGroupKeys());
		assertEquals(expected.getPersonId(), actual.getPersonId());
		assertEquals(expected.getLuiId(), actual.getLuiId());
		assertEquals(expected.getCommitmentPercent(),
				actual.getCommitmentPercent());

		// test read
		expected = actual;
		actual = lprService.getLpr(actual.getId(), callContext);
		assertEquals(expected.getId(), actual.getId());
		new RelationshipTester().check(expected, actual);
		new AttributeTester().check(expected.getAttributes(),
				actual.getAttributes());
		new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
		new ListOfStringTester().check(expected.getResultValuesGroupKeys(),
				actual.getResultValuesGroupKeys());
		assertEquals(expected.getPersonId(), actual.getPersonId());
		assertEquals(expected.getLuiId(), actual.getLuiId());
		assertEquals(expected.getCommitmentPercent(),
				actual.getCommitmentPercent());

		// test update
		expected = actual;
		expected.setEffectiveDate(new Timestamp(expected.getEffectiveDate()
				.getTime() - 2000));
		expected.setExpirationDate(new Timestamp(expected.getExpirationDate()
				.getTime() + 2000));
		expected.setCommitmentPercent("33.33");
		expected.getResultValuesGroupKeys().remove(0);
		expected.getResultValuesGroupKeys().add("rvg3");
		new AttributeTester().delete1Update1Add1ForUpdate(expected
				.getAttributes());
		actual = lprService.updateLpr(expected.getId(), expected, callContext);
		assertEquals(expected.getId(), actual.getId());
		new RelationshipTester().check(expected, actual);
		
		 for (AttributeInfo itemInfo : expected.getAttributes()) {
				
	        	// clear out any id's set during the persistence
	        	// to let the checks work properly
	        	itemInfo.setId(null);
			}
		 
		new AttributeTester().check(expected.getAttributes(),
				actual.getAttributes());
		new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
		new ListOfStringTester().check(expected.getResultValuesGroupKeys(),
				actual.getResultValuesGroupKeys());
		assertEquals(expected.getPersonId(), actual.getPersonId());
		assertEquals(expected.getLuiId(), actual.getLuiId());
		assertEquals(expected.getCommitmentPercent(),
				actual.getCommitmentPercent());

		checkTableContents("KSEN_LPR_ATTR");
		checkTableRows("KSEN_LPR_ATTR", "OWNER_ID", actual.getId(), 2);

	}

	private void checkTableContents(final String table) throws SQLException {

		txTemplate.execute(new TransactionCallback<Void>() {

			@Override
			public Void doInTransaction(TransactionStatus status) {
				try {
					String query = "select * from " + table;

					ResultSet rs = dataSource.getConnection().createStatement()
							.executeQuery(query);

					while (rs.next()) {
						Object row = rs.getObject(1);

						log.debug("row = " + row);

					}
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
				return null;
			}
		});

	}

	private void checkTableRows(final String table, final String keyColumn,
			final String key, final int expectedRows) throws SQLException {

		txTemplate.execute(new TransactionCallback<Void>() {

			@Override
			public Void doInTransaction(TransactionStatus status) {
				try {
					String query = "select * from " + table + " where "
							+ keyColumn + " = '" + key + "'";

					ResultSet rs = dataSource.getConnection().createStatement()
							.executeQuery(query);

					AtomicInteger rowCounter = new AtomicInteger(0);

					while (rs.next()) {
						Object row = rs.getObject(1);

						log.debug("row = " + row);

						rowCounter.addAndGet(1);

					}

					Assert.assertEquals(expectedRows, rowCounter.intValue());
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
				return null;
			}
		});
	}

}
