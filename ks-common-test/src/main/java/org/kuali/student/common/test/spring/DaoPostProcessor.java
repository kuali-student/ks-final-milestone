package org.kuali.student.common.test.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;

public class DaoPostProcessor implements BeanFactoryPostProcessor {
	private String daoImplClasses;

	/**
	 * @return the daoImplClasses
	 */
	public String getDaoImplClasses() {
		return daoImplClasses;
	}

	/**
	 * @param daoImplClasses
	 *            the daoImplClasses to set
	 */
	public void setDaoImplClasses(String daoImplClasses) {
		this.daoImplClasses = daoImplClasses;
	}

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		String[] classes = daoImplClasses.split(",");
		BeanDefinitionRegistry registry = ((BeanDefinitionRegistry) beanFactory);
		PersistenceAnnotationBeanPostProcessor pabpp = new PersistenceAnnotationBeanPostProcessor();
		if (classes != null && classes.length > 0) {
			for (String line : classes) {
				if (!"".equals(line)) {
					try {
						String[] split = line.split("\\|");
						String className = split[0];
						Class<?> clazz = Class.forName(className);
						BeanDefinition definition = new RootBeanDefinition(
								clazz);
						registry.registerBeanDefinition(clazz.getSimpleName(),
								definition);
						pabpp.postProcessMergedBeanDefinition(
								(RootBeanDefinition) definition, clazz, clazz
										.getSimpleName());

					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}
}
