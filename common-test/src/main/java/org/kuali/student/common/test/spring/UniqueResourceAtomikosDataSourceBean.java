package org.kuali.student.common.test.spring;

import com.atomikos.jdbc.AtomikosDataSourceBean;

/**
 * This class generates a (mostly) unique resource name for use in testing when the testing framework
 * forks tests and two tests using the same dataSource name run into conflicts
 */
public class UniqueResourceAtomikosDataSourceBean extends
		AtomikosDataSourceBean {
	private static int count = 0;
	
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.atomikos.jdbc.AbstractDataSourceBean#setUniqueResourceName(java.lang.String)
	 */
	@Override
	public void setUniqueResourceName(String resourceName) {
		super.setUniqueResourceName(resourceName+count++);
	}



}
