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

package org.kuali.student.common.test.spring;

import com.atomikos.jdbc.AtomikosDataSourceBean;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class generates a (mostly) unique resource name for use in testing when the testing framework
 * forks tests and two tests using the same dataSource name run into conflicts
 */
@Deprecated
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

    /**
     * overridden method avoids console warning while running tests
     * "getConnection ( user , password ) ignores authentication - returning default connection"
     */
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return super.getConnection();
    }
}
