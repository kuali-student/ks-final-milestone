package org.kuali.student.common.util.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class LoadJpaBean {
	public void loadData(List<Object> beanList, EntityManager em) {
		for (Object bean : beanList) {
			if (!em.contains(bean)) {
				em.persist(bean);
			}
		}
		em.flush();
	}
}
