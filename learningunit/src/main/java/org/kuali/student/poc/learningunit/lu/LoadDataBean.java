package org.kuali.student.poc.learningunit.lu;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class LoadDataBean {
	@PersistenceContext(unitName="Lu")
	EntityManager em;
	private boolean loaded = false;
	private String contextLocation;

	public void loadData() {
		if (contextLocation == null || loaded == true) {
			return;
		}

		// Load all the beans
		try {
			// Get the dao you just created
			ApplicationContext ac = new FileSystemXmlApplicationContext(
					contextLocation);
			for (Object bean : (List<?>) ac.getBean("persistList")) {
				if(!em.contains(bean)){
					em.persist(bean);
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		loaded = true;

	}

	/**
	 * @return the contextLocation
	 */
	public String getContextLocation() {
		return contextLocation;
	}

	/**
	 * @param contextLocation
	 *            the contextLocation to set
	 */
	public void setContextLocation(String contextLocation) {
		this.contextLocation = contextLocation;
	}

}
