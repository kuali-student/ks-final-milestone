package org.kuali.student.common.test.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class PersistObjectsListener implements ApplicationListener {
	private PersistObjectsBean persistObjectsBean;
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			persistObjectsBean.loadData();
		}
	}

	public void setPersistObjectsBean(PersistObjectsBean persistObjectsBean) {
		this.persistObjectsBean = persistObjectsBean;
	}

}
