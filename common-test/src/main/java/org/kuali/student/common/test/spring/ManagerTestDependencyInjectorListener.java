package org.kuali.student.common.test.spring;

import java.lang.reflect.Field;

import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

public class ManagerTestDependencyInjectorListener extends
		DependencyInjectionTestExecutionListener {

	@Override
	protected void injectDependencies(TestContext testContext) throws Exception {

		ApplicationContext context = testContext.getApplicationContext();

		BeanDefinitionRegistry registry = ((BeanDefinitionRegistry) context);

		// Create the Daos
		Daos daos = testContext.getTestClass().getAnnotation(Daos.class);

		for (Dao dao : daos.value()) {
			Class<?> daoClass = Class.forName(dao.value());
			BeanDefinition definition = new RootBeanDefinition(daoClass);
			registry.registerBeanDefinition(daoClass.getSimpleName(),
					definition);
		}

		// Create the business manager
		Field[] fields = testContext.getTestClass().getDeclaredFields();
		for (Field f : fields) {
			Manager a = f.getAnnotation(Manager.class);
			if (a != null) {
				Class<?> clazz = Class.forName(a.value());
				BeanDefinition definition = new RootBeanDefinition(clazz);
				registry.registerBeanDefinition(f.getName(), definition);
				Object manager = context.getBean(f.getName());

				// AutoWire the Daos to the business manager by Type
				AutowireCapableBeanFactory beanFactory = testContext
						.getApplicationContext()
						.getAutowireCapableBeanFactory();
				beanFactory.autowireBeanProperties(manager,
						AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);

				// Wire the manager to the test class
				DirectFieldAccessor dfa = new DirectFieldAccessor(testContext
						.getTestInstance());
				dfa.setPropertyValue(f.getName(), manager);
			}
		}

		super.injectDependencies(testContext);

	}
}