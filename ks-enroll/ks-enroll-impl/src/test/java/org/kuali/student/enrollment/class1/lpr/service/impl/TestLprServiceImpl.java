package org.kuali.student.enrollment.class1.lpr.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class1.lpr.dao.LprDao;
import org.kuali.student.enrollment.class1.lpr.service.impl.mock.LprTestDataLoader;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.test.util.AttributeTester;
import org.kuali.student.enrollment.test.util.ListOfStringTester;
import org.kuali.student.enrollment.test.util.MetaTester;
import org.kuali.student.enrollment.test.util.RelationshipTester;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:lpr-test-context.xml" })
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback=true)
@Transactional
public class TestLprServiceImpl extends TestLprServiceMockImpl {

	private static final Logger log = Logger
			.getLogger(TestLprServiceImpl.class);

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

	public LprDao getLprDao() {
		return lprDao;
	}

	public void setLprDao(LprDao lprDao) {
		this.lprDao = lprDao;
	}

	@Before
	public  void setUp() {
		// intentionally does not call super.setUp()

			principalId = "123";
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

	@Test
	public void testLuiServiceSetup() {
		assertNotNull(lprService);
	}

	/**
	 * Validate that there are 3 columns in the KSEN_LPR_RESULT_VAL_GRP table.
	 * 
	 * (ID, LPR_ID, RESULT_VAL_GRP_ID)
	 * 
	 * @throws SQLException
	 */
	@Test
	public void validateSchema() throws SQLException {

		txTemplate.execute(new TransactionCallback<Void>() {

			@Override
			public Void doInTransaction(TransactionStatus status) {

				try {
					ResultSet rs = dataSource
							.getConnection()
							.createStatement()
							.executeQuery(
									"select * from KSEN_LPR_RESULT_VAL_GRP");

					ResultSetMetaData meta = rs.getMetaData();

					int cols = meta.getColumnCount();

					for (int i = 0; i < cols; i++) {
						log.warn("row [" + i + "] : "
								+ meta.getColumnLabel(i + 1) + " "
								+ meta.getColumnTypeName(i + 1));
					}

					assertEquals(2, cols);
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}

				return null;
			}

		});

	}

	private void assertTableColumnType(final String table,
			final String columnName, final String expectedColumnType)
			throws SQLException {

		txTemplate.execute(new TransactionCallback<Void>() {

			@Override
			public Void doInTransaction(TransactionStatus status) {

				try {
					ResultSet rs = dataSource
							.getConnection()
							.createStatement()
							.executeQuery(
									"select " + columnName + " from " + table);

					ResultSetMetaData meta = rs.getMetaData();

					String columnType = meta.getColumnTypeName(1);

					assertEquals(expectedColumnType, columnType.toUpperCase());
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}

				return null;
			}

		});
	}

	@Test
	public void validateCommitmentPercentDatabaseColumn() throws SQLException {

		assertTableColumnType("KSEN_LPR", "COMMIT_PERCT", "NUMERIC");

	}

	@Test
	public void testCreateLpr() throws Exception {

		LprInfo lpr = new LprInfo();
		lpr.setId("lpr-4");
		lpr.setLuiId("lui-4");
		lpr.setPersonId("person-4");
		lpr.setCommitmentPercent("20.0");
		lpr.setStateKey("kuali.courseoffering.");
		lpr.setTypeKey("kuali.lpr.type.courseoffering.instructor.main");
		LprInfo newLprInfo = lprService.createLpr("person-4", "lui-4",
				"kuali.lpr.type.courseoffering.instructor.main", lpr,
				callContext);
		assertNotNull(newLprInfo.getId());
		assertEquals(lpr.getId(), newLprInfo.getId());

	}

	@Test
	public void testGetLPR() throws Exception {

		LprInfo lpr = lprService.getLpr("Lpr-1", callContext);

		assertNotNull(lpr);
		assertNotNull(lpr.getStateKey());
		assertNotNull(lpr.getTypeKey());

	}


}
