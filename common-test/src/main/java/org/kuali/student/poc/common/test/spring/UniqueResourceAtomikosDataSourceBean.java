package org.kuali.student.poc.common.test.spring;

import com.atomikos.jdbc.AtomikosDataSourceBean;

public class UniqueResourceAtomikosDataSourceBean extends
		AtomikosDataSourceBean {
	private static int count = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.atomikos.jdbc.AbstractDataSourceBean#setUniqueResourceName(java.lang.String)
	 */
	@Override
	public void setUniqueResourceName(String resourceName) {
		// TODO Auto-generated method stub
		super.setUniqueResourceName(resourceName+count++);
	}



}
