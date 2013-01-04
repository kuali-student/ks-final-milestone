/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.common.util.jta;

import javax.transaction.TransactionManager;

import org.eclipse.persistence.transaction.JTATransactionController;

/**
 * 
 * @author Kuali Student
 * 
 * Passes the Atomikos TransactionManager to Eclipselink.  Set the property
 * <p>
 * eclipselink.target-server=org.kuali.student.common.util.jta.AtomikosJtaTransactionController
 * </p>
 * The first instance Atomikos UserTransactionManager starts the Atomikos
 * service and only the first instance of UserTransactionManager will have a
 * handle to shut it down on close.
 * 
 */
public class AtomikosJtaTransactionController extends JTATransactionController {

	private static TransactionManager tm;
	
    @Override
    protected TransactionManager acquireTransactionManager() throws Exception {
    	if(tm==null){
    		Class<?> clazz = Class.forName("com.atomikos.icatch.jta.UserTransactionManager");
    		tm = (TransactionManager) clazz.newInstance();
    	}
        return tm;
    }

	public static TransactionManager getTm() {
		return tm;
	}

	public static void setTm(TransactionManager tm) {
		AtomikosJtaTransactionController.tm = tm;
	}

}
