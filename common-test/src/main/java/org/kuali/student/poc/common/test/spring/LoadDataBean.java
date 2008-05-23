package org.kuali.student.poc.common.test.spring;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class LoadDataBean {
	@PersistenceContext
	EntityManager em;
	private boolean loaded = false;
	private String daoAnnotations;

	public void loadData() {
		if (daoAnnotations == null || loaded == true) {
			return;
		}

		// Load all the beans
		String[] classes = daoAnnotations.split(",");
		for (String line : classes) {
			try {
				String[] split = line.split("\\|");
				if (split.length > 1) {
					String testDataFile = split[1];
					// Get the dao you just created
					ApplicationContext ac = new FileSystemXmlApplicationContext(
							testDataFile);
					for (Object bean : (List<?>) ac.getBean("persistList")) {
						if(!em.contains(bean)){
							em.persist(bean);
						}
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		loaded = true;

	}

	/**
	 * @return the daoAnnotations
	 */
	public String getDaoAnnotations() {
		return daoAnnotations;
	}

	/**
	 * @param daoAnnotations
	 *            the daoAnnotations to set
	 */
	public void setDaoAnnotations(String daoAnnotations) {
		this.daoAnnotations = daoAnnotations;
	}

}
