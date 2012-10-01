package org.kuali.student.common.test.spring;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PersistObjectsBean {

	private Map<EntityManager, List<Object>> objectMap;

	public void loadData() {
		for (Entry<EntityManager, List<Object>> entry : objectMap.entrySet()) {
			EntityManager em = entry.getKey();
			for (Object o : entry.getValue()) {
				em.persist(o);
			}
		}
	}

	public void setObjectMap(Map<EntityManager, List<Object>> objectMap) {
		this.objectMap = objectMap;
	}

}
