package org.kuali.student.poc.dummy.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.runner.RunWith;

import org.kuali.student.poc.dummy.Dummy;
import org.kuali.student.poc.dummy.DummyAttribute;
import org.kuali.student.poc.dummy.dao.DummyDAO;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:META-INF/default-dao-context-test.xml"})
@Transactional
@TransactionConfiguration(transactionManager="JtaTxManager")
public class TestDummyDAO{

	@Resource //JSR 250 instead of Spring Autowired
    private DummyDAO dummyDAO;
	
	@PersistenceContext
	private EntityManager em;
	
	private long dummyId;
	final private String dummyAttrValue = "dummy attribute value";
	final private String updatedDummyAttrValue = "updated dummy attribute value";
	
	private DummyAttribute createDummyAttribute(Dummy dummy) {		
		DummyAttribute dummyAttribute = new DummyAttribute();
		dummyAttribute.setValue(this.dummyAttrValue);
		dummyAttribute.setDummy(dummy);	
		return dummyAttribute;
	}
	
	private Dummy createDummy() {
		Dummy dummy = new Dummy();				
		dummy.getAttributes().add(createDummyAttribute(dummy));	
		return dummy;
	}

	/*
	 * Methods with Spring's @BeforeTransaction run before entering a transaction
	 * can be used to check database is ready for the test
	 * Called before every @Test method
	@BeforeTransaction
	public verifyInitialDatabaseState() {
		
	}
	*/
	
	/*
	 * Methods with JUnit 4 @Before run when transaction has begun
	 * * Called before every @Test method
	@Before
	public void setUpDataWithinTransaction() throws Exception {
		
	}
	*/
	
	/*
	 * Methods with JUnit 4 @Test do not need method names starting with 'test'
	 */
	@Test
	public void createDBDummyWithAttribute() {	
		boolean pass = false;
		Dummy dummy = dummyDAO.createDummy(createDummy());
		assertTrue(dummy.getId() != null);
		this.dummyId = dummy.getId();
		dummy = dummyDAO.lookupDummy(this.dummyId);
		assertNotNull("dummy with id " + this.dummyId + " not found", dummy);
		Set<DummyAttribute> attrs = dummy.getAttributes();
		assertNotNull("attributes for dummy with id " + this.dummyId + " not found", attrs);
		for(DummyAttribute attr : attrs) {
			if(attr.getValue().equals(this.dummyAttrValue)) {
				pass = true;
			}
		}
		assertEquals(this.dummyAttrValue + " not found", true, pass);
		
	}

	@Test
	public void readDBDummyWithAttribute() {		
		Dummy dummy = dummyDAO.createDummy(createDummy());
		this.dummyId = dummy.getId();
		dummy = dummyDAO.lookupDummy(this.dummyId);
		assertNotNull("dummy with id " + this.dummyId + " not found", dummy);
		Set<DummyAttribute> attrs = dummy.getAttributes();
		assertNotNull("attributes for dummy with id " + this.dummyId + " not found", attrs);
		for(DummyAttribute attr : attrs) {
			if(attr.getValue().equals(this.dummyAttrValue)) {
				return;
			}
		}
		fail(this.dummyAttrValue + " not found");
	}
	
	@Test
	public void updateDBDummyWithAttribute() {		
		Dummy dummy = dummyDAO.createDummy(createDummy());
		this.dummyId = dummy.getId();
		dummy = dummyDAO.lookupDummy(this.dummyId);
		for(DummyAttribute attr : dummy.getAttributes()) {
			if(attr.getValue().equals(this.dummyAttrValue)) {
				attr.setValue(this.updatedDummyAttrValue);
				dummyDAO.updateDummy(dummy);
			}
		}
		dummy = dummyDAO.lookupDummy(this.dummyId);
		for(DummyAttribute attr : dummy.getAttributes()) {
			if(attr.getValue().equals(this.updatedDummyAttrValue)) {
				return;
			}
		}
		fail(this.dummyAttrValue + " not updated");
	}
	
	@After
	public void tearDownWithinTransaction() {
	    Dummy dummy = dummyDAO.lookupDummy(dummyId);
	    dummyDAO.deleteDummy(dummy);
	}
	/*
	 * Methods with Spring's @AfterTransaction run after a transaction ends
	 * can be used to ensure test data was deleted from database
	 * Called after every @Test method
	 */
	@AfterTransaction 
	public void verifyFinalDatabaseState() {
		Dummy dummy = dummyDAO.lookupDummy(dummyId);
		assertNull("dummy with id " + this.dummyId + " not deleted", dummy);
	}	
}
