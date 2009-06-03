package org.kuali.student.common.util.jta;

import javax.transaction.TransactionManager;

import org.apache.openjpa.ee.AbstractManagedRuntime;
import org.apache.openjpa.ee.ManagedRuntime;

public class AtomikosManagedRuntime extends AbstractManagedRuntime implements ManagedRuntime {

	private static TransactionManager tm;
	private Throwable cause;
	
	public AtomikosManagedRuntime() {
		super();
	}

	@Override
	public Throwable getRollbackCause() throws Exception {
       return cause;
	}

	@Override
	public TransactionManager getTransactionManager() throws Exception {
		if(tm==null){
    		Class<?> clazz = Class.forName("com.atomikos.icatch.jta.UserTransactionManager");
    		tm = (TransactionManager) clazz.newInstance();
		}
		return tm;
	}

	@Override
	public void setRollbackOnly(Throwable cause) throws Exception {
        this.cause=cause;
        getTransactionManager().getTransaction().setRollbackOnly();
	}

	public static TransactionManager getTm() {
		return tm;
	}

	public static void setTm(TransactionManager tm) {
		AtomikosManagedRuntime.tm = tm;
	}



}
