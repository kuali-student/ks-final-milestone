package org.kuali.student.common.util.jpa;

import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.spi.PersistenceProvider;

//import org.eclipse.persistence.jpa.config.PersistenceUnitProperties;
import org.eclipse.persistence.config.PersistenceUnitProperties;

import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;

public class EclipseLinkJpaVendorAdapter extends AbstractJpaVendorAdapter {

	private final PersistenceProvider persistenceProvider = new org.eclipse.persistence.jpa.PersistenceProvider();

	private final JpaDialect jpaDialect = null;

	@Override
	public Class<? extends EntityManager> getEntityManagerInterface() {
		return org.eclipse.persistence.jpa.JpaEntityManager.class;
	}

	@Override
	public JpaDialect getJpaDialect() {
		return this.jpaDialect;
	}

	@Override
	public Map<?, ?> getJpaPropertyMap() {
		Properties jpaProperties = new Properties();

		if (isGenerateDdl()) {
			jpaProperties.setProperty(PersistenceUnitProperties.DDL_GENERATION,
					PersistenceUnitProperties.CREATE_ONLY);
			jpaProperties.setProperty(
					PersistenceUnitProperties.DDL_GENERATION_MODE,
					PersistenceUnitProperties.DDL_DATABASE_GENERATION);
		}
		if (isShowSql()) {
			jpaProperties.setProperty(PersistenceUnitProperties.LOGGING_LEVEL,
					Level.FINE.toString());
		}

		return jpaProperties;
	}

	@Override
	public PersistenceProvider getPersistenceProvider() {
		return this.persistenceProvider;
	}

}
