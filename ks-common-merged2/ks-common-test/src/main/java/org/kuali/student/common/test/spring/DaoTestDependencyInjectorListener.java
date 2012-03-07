/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.test.spring;

import java.lang.reflect.AccessibleObject;
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
				Field.setAccessible(new AccessibleObject[]{f}, true);
				f.set(testContext.getTestInstance(), testContext
						.getApplicationContext().getBean(f.getName()));
			}
		}

		super.injectDependencies(testContext);

	}
}
