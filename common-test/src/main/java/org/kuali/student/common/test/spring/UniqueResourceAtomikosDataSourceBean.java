package org.kuali.student.common.test.spring;

import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;

import com.atomikos.jdbc.AtomikosDataSourceBean;

/**
 * This class generates a (mostly) unique resource name for use in testing when
 * the testing framework forks tests and two tests using the same dataSource
 * name run into conflicts
 */
public class UniqueResourceAtomikosDataSourceBean extends
		AtomikosDataSourceBean implements InitializingBean {
	private static int count = 0;

	private static final long serialVersionUID = 1L;

	private String user;
	private String password;
	private String databaseName;
	private boolean createDatabase;

	private static final String DB_TYPE_DERBY = "org.apache.derby.jdbc.EmbeddedXADataSource";
	private static final String DB_TYPE_ORACLE = "oracle.jdbc.xa.client.OracleXADataSource";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atomikos.jdbc.AbstractDataSourceBean#setUniqueResourceName(java.lang.String)
	 */
	@Override
	public void setUniqueResourceName(String resourceName) {
		super.setUniqueResourceName(resourceName + count++);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Properties props = new Properties();
		if(DB_TYPE_DERBY.equals(getXaDataSourceClassName())){
			props.put("user", user);
			props.put("password", password);
			props.put("databaseName",databaseName);
			props.put("createDatabase",createDatabase==true?"create":"");
		}else if(DB_TYPE_ORACLE.equals(getXaDataSourceClassName())){
			props.put("user", user);
			props.put("password", password);
			props.put("URL",databaseName);
		}
		super.setXaProperties(props);
	}
	
	public void setUser() {

	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public boolean isCreateDatabase() {
		return createDatabase;
	}

	public void setCreateDatabase(boolean createDatabase) {
		this.createDatabase = createDatabase;
	}

}
