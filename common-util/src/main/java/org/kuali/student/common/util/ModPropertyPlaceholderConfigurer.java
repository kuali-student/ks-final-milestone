package org.kuali.student.common.util;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class ModPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	
    public static final String KS_MVN_ARTIFACTID_INITIALCAPS = "ks.mvn.artifactId.initialCaps";
	public static final String KS_MVN_ARTIFACTID_LOWERCASE = "ks.mvn.artifactId.lowercase";
	public static final String KS_MVN_PARENT_ARTIFACTID_LOWERCASE = "ks.mvn.parent.artifactId.lowercase";
	public static final String JPA_VENDOR_ADAPTER = "jpa.vendorAdapter";
	public static final String JPA_DATABASEPLATFORM = "jpa.databasePlatform";
		
	//Class to check for in CLASSPATH to determine which jpa impl is being used
	public static final String JPA_HIBERNATE_PROVIDER = "org.hibernate.ejb.HibernatePersistence";
	
	public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	
	@Override
	protected String resolvePlaceholder(String placeholder, Properties props) {

	    if(JPA_VENDOR_ADAPTER.equals(placeholder)){
	        try{
	            Class.forName(JPA_HIBERNATE_PROVIDER);            
                return "org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter";
	        } catch (ClassNotFoundException e) {
                return "org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter";
	        }
		}
		
		if(JPA_DATABASEPLATFORM.equals(placeholder)){
	        try{
	            Class.forName(JPA_HIBERNATE_PROVIDER);
	            try{
		            Class.forName(ORACLE_DRIVER);
	            	return "org.hibernate.dialect.OracleDialect";
	            }catch (ClassNotFoundException e) {
	            	return "org.hibernate.dialect.DerbyDialect";
	            }
	        } catch (ClassNotFoundException oracleNotFoundException) {
	            try{
		            Class.forName(ORACLE_DRIVER);
	            	return "org.eclipse.persistence.platform.database.OraclePlatform";
	            }catch (ClassNotFoundException hibernateNotFoundException) {
	            	return "org.eclipse.persistence.platform.database.DerbyPlatform";
	            }
	        }
		}
		
		/*
		 *For the properties ks.mvn.artifactId.initialCaps and
		 *ks.mvn.artifactId.initialCaps, we need to process the strings to convert from 
		 *the maven artifact id to what we assume to be the class name and remove the "-ri"
		 */
		if (KS_MVN_ARTIFACTID_INITIALCAPS.equals(placeholder)) {
			String resolved = super.resolvePlaceholder(placeholder, props);
			if(resolved.toLowerCase().endsWith("-ri")){
				resolved = resolved.substring(0, resolved.length()-3);
			}
			return resolved;
		}
		
		if (KS_MVN_ARTIFACTID_LOWERCASE.equals(placeholder) ||
			KS_MVN_PARENT_ARTIFACTID_LOWERCASE.equals(placeholder)	) {
			String resolved = super.resolvePlaceholder(placeholder, props);
			if(resolved.toLowerCase().endsWith("-ri")){
				resolved = resolved.substring(0, resolved.length()-3);
			}
			return resolved.toLowerCase();
		}

		return super.resolvePlaceholder(placeholder, props);
	}

}
