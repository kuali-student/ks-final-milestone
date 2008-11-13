package org.kuali.student.poc.common.util.jta;

import javax.transaction.TransactionManager;

import org.eclipse.persistence.transaction.JTATransactionController;

/**
 * 
 * @author Kuali Student
 * 
 * Passes the Atomikos TransactionManager to Eclipselink.  Set the property
 * <p>
 * eclipselink.target-server=org.kuali.student.poc.common.util.jta.AtomikosJtaTransactionController
 * </p>
 * The first instance Atomikos UserTransactionManager starts the Atomikos
 * service and only the first instance of UserTransactionManager will have a
 * handle to shut it down on close.
 * 
 */
public class AtomikosJtaTransactionController extends JTATransactionController {

    @Override
    protected TransactionManager acquireTransactionManager() throws Exception {
        Class<?> clazz = Class.forName("com.atomikos.icatch.jta.UserTransactionManager");
        return (TransactionManager) clazz.newInstance();
    }
}
