package org.kuali.student.common.util.spring;

import java.lang.reflect.Field;
import java.util.Map;

import javax.persistence.spi.PersistenceUnitTransactionType;

import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;

public class MergingPersistenceUnitManager extends
		DefaultPersistenceUnitManager {

	private String mergedPersistenceUnitName;
	private PersistenceUnitTransactionType transactionTypeOverride = PersistenceUnitTransactionType.RESOURCE_LOCAL;
	
	public void setMergedPersistenceUnitName(String mergedPersistenceUnitName) {
		this.mergedPersistenceUnitName = mergedPersistenceUnitName;
	}

	@Override
	protected void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
		try {
			Field persistenceUnitInfosField = DefaultPersistenceUnitManager.class.getDeclaredField("persistenceUnitInfos");
			persistenceUnitInfosField.setAccessible(true);
			Map<String, MutablePersistenceUnitInfo> persistenceUnitInfos = (Map<String, MutablePersistenceUnitInfo>) persistenceUnitInfosField.get(this);

			if(persistenceUnitInfos.isEmpty()){
				pui.setPersistenceUnitName(mergedPersistenceUnitName);
				pui.setTransactionType(transactionTypeOverride);
			}else{
				MutablePersistenceUnitInfo currentlyMergedPui = persistenceUnitInfos.get(mergedPersistenceUnitName);
				for(String className:pui.getManagedClassNames()){
					currentlyMergedPui.getManagedClassNames().add(className);
				}
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Error while merging persistence units.",e);
		}
		
		super.postProcessPersistenceUnitInfo(pui);
	}

	public void setTransactionTypeOverride(PersistenceUnitTransactionType transactionTypeOverride) {
		this.transactionTypeOverride = transactionTypeOverride;
	}


}
