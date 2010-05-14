/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.util.jpa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class LoadSqlListener implements ApplicationListener,
		ApplicationContextAware {

	final static Logger logger = LoggerFactory.getLogger(LoadSqlListener.class);
	
	private ApplicationContext applicationContext;
	
	private boolean loaded = false;
	
	private Map<String,String> preloadMap;
	private JtaTransactionManager jtaTxManager;	
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent && !loaded) {
		
			for (Entry<String, String> entry : preloadMap.entrySet()) {
				String sqlFileName = entry.getValue();
				EntityManagerFactory emf = EntityManagerFactoryUtils
						.findEntityManagerFactory(applicationContext, entry
								.getKey());
				EntityManager em = SharedEntityManagerCreator
						.createSharedEntityManager(emf);
				
				File sqlFile;
				BufferedReader in;
				try{
				    if(sqlFileName.startsWith("classpath:")){
				 	 	sqlFile = new ClassPathResource(sqlFileName.substring("classpath:".length())).getFile();
					}else{
				    	sqlFile = new File(sqlFileName);
				    }
					in = new BufferedReader(new FileReader(sqlFile));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				
				String ln;
				
				TransactionDefinition txDefinition = new DefaultTransactionDefinition() ;
				TransactionStatus txStatus = jtaTxManager.getTransaction(txDefinition);

				try {
					while((ln=in.readLine())!=null){
						if(!ln.startsWith("/")&&!ln.startsWith("--")&&StringUtils.isNotBlank(ln)){
							ln=ln.replaceFirst("[;/]\\s*$","");
							em.createNativeQuery(ln).executeUpdate();
						}
					}
					jtaTxManager.commit(txStatus);
				} catch (Exception e) {
					logger.error("Error loading sql file "+sqlFileName+".",e);
					jtaTxManager.rollback(txStatus);
				}
			}
			loaded=true;
		}
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public JtaTransactionManager getJtaTxManager() {
		return jtaTxManager;
	}

	public void setJtaTxManager(JtaTransactionManager jtaTxManager) {
		this.jtaTxManager = jtaTxManager;
	}

	public Map<String, String> getPreloadMap() {
		return preloadMap;
	}

	public void setPreloadMap(Map<String, String> preloadMap) {
		this.preloadMap = preloadMap;
	}

}
