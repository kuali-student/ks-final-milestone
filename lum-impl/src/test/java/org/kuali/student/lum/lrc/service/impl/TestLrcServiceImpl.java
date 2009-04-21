package org.kuali.student.lum.lrc.service.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lrc.service.LrcService;

@Daos( { @Dao(value = "org.kuali.student.lum.lrc.dao.impl.LrcDaoImpl",testSqlFile="classpath:ks-lrc.sql" /*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/lrc-persistence.xml")
public class TestLrcServiceImpl extends AbstractServiceTest {
	@Client(value = "org.kuali.student.lum.lrc.service.impl.LrcServiceImpl", port = "8181",additionalContextFile="classpath:lrc-additional-context.xml")
	public LrcService client;

	@Test
	public void testLrc() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		client.getGradeTypes();
		assertTrue(true);
	}
}
