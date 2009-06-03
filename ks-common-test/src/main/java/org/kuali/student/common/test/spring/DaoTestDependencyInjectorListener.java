package org.kuali.student.common.test.spring;

import java.lang.reflect.Field;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

public class DaoTestDependencyInjectorListener extends
		DependencyInjectionTestExecutionListener {

	@Override
	protected void injectDependencies(TestContext testContext) throws Exception {

		ApplicationContext context = testContext.getApplicationContext();

		BeanDefinitionRegistry registry = ((BeanDefinitionRegistry) context);

		Field[] fields = testContext.getTestClass().getDeclaredFields();
		for (Field f : fields) {
			Dao a = f.getAnnotation(Dao.class);
			if (a != null) {
				Class<?> clazz = Class.forName(a.value());
				BeanDefinition definition = new RootBeanDefinition(clazz);
				registry.registerBeanDefinition(f.getName(), definition);
				f.set(testContext.getTestInstance(), testContext
						.getApplicationContext().getBean(f.getName()));
			}
		}

		super.injectDependencies(testContext);

	}
}
