package org.kuali.student.common.ui.server.applicationstate;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.server.applicationstate.dao.ApplicationStateDao;

@PersistenceFileLocation("classpath:META-INF/application-state-persistence.xml")
public class ApplicationStateManagerImplTest extends AbstractTransactionalDaoTest {

	@Dao(value = "org.kuali.student.common.ui.server.applicationstate.dao.impl.ApplicationStateDaoImpl")
	public ApplicationStateDao applicationStateDao;

	private ApplicationStateManagerImpl applicationStateManager;
	
	@Before
	public void setup() {
		this.applicationStateManager = new ApplicationStateManagerImpl();
		this.applicationStateManager.setApplicationStateDao(applicationStateDao);
	}
	
	@Test
	public void testCreateApplicationState() throws Exception {
		ModelDTO model = new ModelDTO();
		model.putApplicationState("key-1", "value-1");
		
		applicationStateManager.createOrUpdateApplicationState(model);
		
		ModelDTO newModel = applicationStateManager.getApplicationState(model);
		
		Assert.assertEquals("value-1", newModel.getApplicationState("key-1"));
	}

	@Test
	public void testUpdateApplicationState() throws Exception {
		ModelDTO model = new ModelDTO();
		model.putApplicationState("key-1", "value-1");
		
		// Create application state
		applicationStateManager.createOrUpdateApplicationState(model);
		model.putApplicationState("key-2", "value-2");
		// Update application state
		applicationStateManager.createOrUpdateApplicationState(model);
		
		ModelDTO newModel = applicationStateManager.getApplicationState(model);
		
		Assert.assertEquals("value-1", newModel.getApplicationState("key-1"));
		Assert.assertEquals("value-2", newModel.getApplicationState("key-2"));
	}
}
